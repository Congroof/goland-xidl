rootProject.name = "xidl"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/intellij-plugin-verifier/intellij-plugin-structure/") }
        maven { url = uri("https://maven.pkg.jetbrains.space/intellij/p/intellij-dependencies/maven/") }
        maven { url = uri("https://maven.pkg.jetbrains.space/intellij/p/grammarkit/maven/") }
        maven { url = uri("https://packages.jetbrains.team/maven/p/grm/intellij-plugin-grammar-kit") }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
