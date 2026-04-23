<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Xidl Language Changelog

## [Unreleased]

## [2.0.1] - 2026-04-23

### Changed

- 增加图标，代码格式化

## [2.0.0] - 2026-04-23

### Added

- 首个公开版本（`2.0.0`），提供对 `.xidl` 文件的基础语言支持
- 词法与语法解析（Grammar-Kit + JFlex）
- 语法高亮（关键字、类型、注解、字符串、注释等）
- 默认配色方案 `Xidl Default`
- 代码补全（关键字、函数名、模板片段）
- 语义标注（实时错误与警告提示）
- 代码折叠（`schema` / `endpoint` / `{}` 块）
- 括号 / 引号自动匹配
- 行注释、块注释支持（`//`、`/* */`）
- 代码格式化（缩进、空格、`{}` 换行、`;` 后换行）
- 跨文件引用与查找用法（`Find Usages`）
- 类型引用解析与 Schema 名称导航

