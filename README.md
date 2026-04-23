# Xidl Language

[![Build](https://github.com/Congroof/goland-xidl/workflows/Build/badge.svg)](https://github.com/Congroof/goland-xidl/actions/workflows/build.yml)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

<!-- Plugin description -->
Xidl Language is an IntelliJ Platform plugin for the Xidl interface description language. It adds syntax highlighting, completion, annotators, folding, formatting, navigation, Find Usages, and a bundled color scheme for `.xidl` files in GoLand, IntelliJ IDEA, and compatible IDEs.

**中文简介：** **Xidl Language**（插件 ID：`com.congroof.xidl`）为 [IntelliJ 系列 IDE](https://www.jetbrains.com/idea/)（含 GoLand、IDEA 等）提供 `.xidl` 接口描述语言的完整支持。

Xidl 用于定义 schema、endpoints、HTTP、注解与中间件等契约，适用于微服务接口描述与管理。

核心能力：

- 语法高亮、代码折叠、括号匹配、注释、自动格式化
- 关键字 / 函数 / 模板 智能补全
- 实时语义标注（错误与警告提示）
- 跨文件类型引用解析 与 Find Usages
- 自带 `Xidl Default` 配色方案
<!-- Plugin description end -->

## 功能特性

- **语法高亮** — 关键字、类型、注解、字符串等完整着色
- **代码补全** — 关键字、函数、模板智能补全
- **语义标注** — 实时错误与警告提示
- **代码折叠** — 折叠 `schema` / `endpoint` 代码块
- **括号匹配** — 自动高亮匹配括号 / 引号
- **注释支持** — 行注释 (`//`) 与块注释 (`/* */`)
- **代码格式化** — 缩进、空格、`{}` 换行、`;` 后换行
- **查找用法** — 跨文件类型引用查找（Find Usages）
- **配色方案** — 内置 `Xidl Default` 配色，可在 `Settings → Editor → Color Scheme → Xidl` 调整

## 语法示例

```xidl
schema innerUser {
  age: int64
}

// region
schema response_type {
  @desc "字段描述"
  field_name?: string
  inner: innerUser
}
// endregion

@summary "接口概要"
@desc "接口描述"
@name "接口名称"
@tags {"标签1", "标签2"}
@middlewares {"validate-identity", "validate-enhancer"}
get /path/{@xtype "int64" param_name:string}/path2
  ? {
    offset: int64[0,]
    limit:  int64[1,1000]
  }
  200 response_type
;
```

更多示例见 [`samples/demo.xidl`](samples/demo.xidl)。

## 兼容性

| 项目 | 版本 |
|:---|:---|
| IntelliJ 平台 | `2020.3+` (build `203+`) |
| 支持的 IDE | IntelliJ IDEA、GoLand、PyCharm、WebStorm 等所有基于 IntelliJ Platform 的 IDE |
| JDK | 17+ |

## 安装

### 从 IDE 插件市场安装（推荐）

1. 打开 IDE，进入 `Settings / Preferences → Plugins`
2. 搜索 `Xidl Language`
3. 点击 **Install**，重启 IDE

### 本地安装

1. 在 [Releases](https://github.com/Congroof/goland-xidl/releases) 页面下载最新 `.zip` 文件
2. 进入 `Settings / Preferences → Plugins → ⚙️ → Install Plugin from Disk...`
3. 选择下载的文件并重启 IDE

## 开发构建

### 环境要求

- JDK 17+
- IntelliJ IDEA（推荐）

### 本地运行

```bash
# 启动带插件的 IDE 沙箱实例
./gradlew runIde
```

### 构建插件包

```bash
./gradlew buildPlugin
```

构建产物位于 `build/distributions/` 目录。

### 运行测试

```bash
./gradlew test
```

### 校验插件（可选，耗时较久）

```bash
./gradlew verifyPlugin
```

若出现 **PKIX / SSLHandshakeException**（企业代理或自定义 HTTPS 证书）：需把代理根证书导入 JDK 信任库，或为 Gradle 配置信任的 `trustStore`；仅在临时排查时可加 `./gradlew verifyPlugin --no-configuration-cache`。若报 **No IntelliJ Plugin Verifier executable**，多为无法从 Maven 下载 `verifier-cli`（同上网络/证书问题）；本项目已在 `gradle/libs.versions.toml` 中固定 `verifier-cli` 版本以减少解析失败。

## 项目结构

```text
src/main/
├── grammars/xidl.bnf              # Xidl 文法定义（Grammar-Kit）
├── kotlin/.../language/
│   ├── XidlLanguage.kt            # 语言定义
│   ├── XidlFileType.kt            # 文件类型（.xidl）
│   ├── XidlParserDefinition.kt    # 解析器入口
│   ├── lexer/                     # 词法分析
│   ├── psi/                       # PSI 语法树节点
│   ├── highlighting/              # 语法高亮 & 配色设置
│   ├── completion/                # 代码补全
│   ├── annotator/                 # 语义标注
│   ├── folding/                   # 代码折叠
│   ├── formatting/                # 代码格式化
│   ├── brace/                     # 括号匹配
│   ├── commenter/                 # 注释
│   ├── navigation/                # 导航
│   └── reference/                 # 引用 & 查找用法
├── gen/                           # Grammar-Kit 自动生成（勿手改）
└── resources/
    ├── META-INF/plugin.xml
    └── colorSchemes/xidlDefault.xml
```

## 发布流程

1. 更新 `gradle.properties` 中的 `pluginVersion`
2. 在 `CHANGELOG.md` 的 `[Unreleased]` 小节补充变更
3. 推送到 `main`，GitHub Actions 会自动构建并创建 **Release draft**
4. 人工 review / publish draft → 触发 `Release` workflow，自动发布到 JetBrains Marketplace

## 许可证

[Apache License 2.0](LICENSE)
