import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java") // Java support
    alias(libs.plugins.kotlin) // Kotlin support
    alias(libs.plugins.intelliJPlatform) // IntelliJ Platform Gradle Plugin
    alias(libs.plugins.changelog) // Gradle Changelog Plugin
    alias(libs.plugins.qodana) // Gradle Qodana Plugin
    alias(libs.plugins.kover) // Gradle Kover Plugin
    alias(libs.plugins.grammarKit) // Grammar-Kit Gradle Plugin
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

// Set the JVM language level used to build the project.
kotlin {
    jvmToolchain(17)
}

sourceSets {
    main {
        java {
            srcDirs("src/main/gen")
        }
    }
}

// Configure project's dependencies
repositories {
    mavenCentral()

    // IntelliJ Platform Gradle Plugin Repositories Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-repositories-extension.html
    intellijPlatform {
        defaultRepositories()
    }
}

// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog
dependencies {
    testImplementation(libs.junit)

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        val platformLocalPath = providers.gradleProperty("platformLocalPath")
        if (platformLocalPath.isPresent && platformLocalPath.get().isNotBlank()) {
            local(platformLocalPath.get())
        } else {
            create(providers.gradleProperty("platformType"), providers.gradleProperty("platformVersion"))
        }

        // Plugin Dependencies. Uses `platformBundledPlugins` property from the gradle.properties file for bundled IntelliJ Platform plugins.
        bundledPlugins(providers.gradleProperty("platformBundledPlugins").map { it.split(',') })

        // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file for plugin from JetBrains Marketplace.
        plugins(providers.gradleProperty("platformPlugins").map { it.split(',') })

        instrumentationTools()
        // 固定 verifier-cli 版本，避免默认 LATEST/{prefer+} 在仓库元数据拉取失败时无 CLI（报 No executable）
        pluginVerifier(libs.versions.intellijPluginVerifier.get())
        zipSigner()
        testFramework(TestFrameworkType.Platform)
    }
}

// Configure IntelliJ Platform Gradle Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    pluginConfiguration {
        version = providers.gradleProperty("pluginVersion")

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"

            with(it.lines()) {
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
            }
        }

        val changelog = project.changelog // local variable for configuration cache compatibility
        // Get the latest available change notes from the changelog file
        changeNotes = providers.gradleProperty("pluginVersion").map { pluginVersion ->
            with(changelog) {
                renderItem(
                    (getOrNull(pluginVersion) ?: getUnreleased())
                        .withHeader(false)
                        .withEmptySections(false),
                    Changelog.OutputType.HTML,
                )
            }
        }

        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
            // 默认会把 until-build 设为当前 platformVersion 的 MAJOR.*（如 233.*），导致新版 GoLand（251）无法安装。
            // 官方文档：until-build 需用 provider { null } 显式去掉，才能兼容更高版本 IDE。
            val untilBuildProp = providers.gradleProperty("pluginUntilBuild").orNull?.trim()
            if (untilBuildProp.isNullOrEmpty()) {
                untilBuild = provider { null }
            } else {
                untilBuild = untilBuildProp
            }
        }
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
        // The pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels = providers.gradleProperty("pluginVersion").map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
    }

    // 必须在此处声明 pluginVerification，verifyPlugin 任务才能解析 Plugin Verifier CLI（dependencies 里的 pluginVerifier()）。
    //
    // 不要使用 recommended()：它会在「配置阶段」调用 ProductReleasesValueSource 拉取 JetBrains 发布的 IDE 列表，
    // 在企业代理 / 自定义 HTTPS 证书环境下容易 PKIX，且异常会被 Configuration Cache 序列化导致二次报错。
    // 以下与 gradle.properties 中 platformType / platformVersion（编译所用 SDK）一致即可满足上架前的二进制兼容性校验；
    // 若还需校验 GoLand 等，可追加一行：ide("GO", "2025.1")。
    pluginVerification {
        ides {
            ide(
                providers.gradleProperty("platformType"),
                providers.gradleProperty("platformVersion"),
            )
        }
    }
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups.empty()
    repositoryUrl = providers.gradleProperty("pluginRepositoryUrl")
}

// Configure Gradle Kover Plugin - read more: https://github.com/Kotlin/kotlinx-kover#configuration
kover {
    reports {
        total {
            xml {
                onCheck = true
            }
        }
    }
}

// Grammar-Kit 自动生成 Lexer / Parser
// 源文件:
//   - BNF:  src/main/grammars/xidl.bnf
//   - Flex: src/main/kotlin/com/wps/cloud/xidl/language/lexer/_XidlLexer.flex
// 生成目标: src/main/gen (已在 sourceSets 里注入为 main 源码)
tasks {
    wrapper {
        gradleVersion = providers.gradleProperty("gradleVersion").get()
    }

    publishPlugin {
        dependsOn(patchChangelog)
    }

    generateParser {
        sourceFile.set(file("src/main/grammars/xidl.bnf"))
        targetRootOutputDir.set(file("src/main/gen"))
        pathToParser.set("com/wps/cloud/xidl/language/parser/XidlParser.java")
        pathToPsiRoot.set("com/wps/cloud/xidl/language/psi")
        purgeOldFiles.set(true)
    }

    generateLexer {
        sourceFile.set(file("src/main/kotlin/com/wps/cloud/xidl/language/lexer/_XidlLexer.flex"))
        targetOutputDir.set(file("src/main/gen/com/wps/cloud/xidl/language/lexer"))
        // Lexer 引用生成的 XidlTypes 常量，需要 Parser 先生成
        dependsOn(generateParser)
        purgeOldFiles.set(true)
    }

    // 让所有编译任务都等待生成完成
    compileKotlin {
        dependsOn(generateLexer, generateParser)
    }

    compileJava {
        dependsOn(generateLexer, generateParser)
    }
}

intellijPlatformTesting {
    runIde {
        register("runIdeForUiTests") {
            task {
                jvmArgumentProviders += CommandLineArgumentProvider {
                    listOf(
                        "-Drobot-server.port=8082",
                        "-Dide.mac.message.dialogs.as.sheets=false",
                        "-Djb.privacy.policy.text=<!--999.999-->",
                        "-Djb.consents.confirmation.enabled=false",
                    )
                }
            }

            plugins {
                robotServerPlugin()
            }
        }
    }
}
