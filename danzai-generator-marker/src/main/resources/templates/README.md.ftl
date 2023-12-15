# ${name}

> ${description}
>
> 作者：${author}
>
> 欢迎使用 [nhilove](https://github.com/nhilove) 的[蛋仔项目](https://github.com/nhilove/danzai-generator)，喜欢的请点个⭐

可以通过使用命令行的方式动态生成你想要的模板

## 使用说明

执行项目根目录下的脚本文件
```
generator <命令> <选项参数是>
```

示例命令
```
generator generate <#list modelConfig.models as modelInfo>-${modelInfo.abbr} </#list>
```

## 参数说明
<#list modelConfig.models as modelInfo>

(${modelInfo?index+1}). ${modelInfo.fieldName}

类型：${modelInfo.type}

描述：${modelInfo.description}

默认值：${modelInfo.defaultValue?c}

缩写： -${modelInfo.abbr}

全写： --${modelInfo.fieldName}

</#list>
