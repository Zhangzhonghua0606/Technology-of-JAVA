# RequireJS 学习笔记

## Differences between `define` and `require`

- define和require在依赖处理和回调执行上都是一样的。
- 不一样的地方是define的回调函数需要有return语句返回模块对象，这样define定义的模块才能被其他模块引用；require的回调函数不需要return语句。
