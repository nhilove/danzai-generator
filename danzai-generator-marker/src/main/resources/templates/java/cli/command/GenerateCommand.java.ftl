package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import freemarker.template.TemplateException;
import lombok.Data;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * ClassName: GenerateCommand
 * Package: ${basePackage}.cli.command
 * Description: ${description}
 *
 * @Author ${author}
 * @Create ${createTime}
 * @Version ${version}
 */
<#--生成选项-->
<#macro generateOptions indent modelInfo>
    <#if modelInfo.description??>
${indent}/**
${indent}*   ${modelInfo.description}
${indent}*/
    </#if>
${indent}@CommandLine.Option(names = { <#if modelInfo.abbr??>"-${modelInfo.abbr}",</#if>"--${modelInfo.fieldName}" }, arity = "0..1",<#if modelInfo.description??>description = "${modelInfo.description}",</#if>interactive = true,echo = true)
${indent}private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??>= ${modelInfo.defaultValue?c}</#if>;
</#macro>
<#--生成命令调用-->
<#macro generateCommand indent modelInfo>
${indent}System.out.println("输入${modelInfo.groupName}配置:");
${indent}CommandLine ${modelInfo.groupKey}CommandLine = new CommandLine(${modelInfo.type}Command.class);
${indent}${modelInfo.groupKey}CommandLine.execute(${modelInfo.allArgsStr});
</#macro>

@CommandLine.Command( name = "generate",description = "文件生成",mixinStandardHelpOptions = true )
@Data
public class GenerateCommand implements Callable<Integer> {

    <#list modelConfig.models as modelInfo>
    <#--有分组-->
    <#if modelInfo.groupKey??>
    /**
    * ${modelInfo.groupName}
    */
    static DataModel.${modelInfo.type} ${modelInfo.groupKey} = new DataModel.${modelInfo.type}();

    <#--核心模板类-->
    @Command(name = "${modelInfo.groupKey}")
    @Data
    public static class ${modelInfo.type}Command implements Runnable {
        <#list modelInfo.models as subModelInfo>
            <@generateOptions indent="        " modelInfo=subModelInfo/>
        </#list>

        @Override
        public void run() {
            <#list modelInfo.models as subModelInfo>
            ${modelInfo.groupKey}.${subModelInfo.fieldName} = ${subModelInfo.fieldName};
            </#list>
        }
    }

    <#else>
        <@generateOptions indent="    " modelInfo=modelInfo />
    </#if>
    </#list>

    @Override
    public Integer call() throws Exception{
    <#list modelConfig.models as modelInfo>
        <#if modelInfo.groupKey??>
            <#if modelInfo.condition??>
        if (${modelInfo.condition}){
        <@generateCommand indent="        " modelInfo=modelInfo/>
        }
            <#else>
            <@generateCommand indent="    " modelInfo=modelInfo/>
            </#if>
        </#if>
    </#list>
        //填充数据模型对象
        DataModel dataModel = new DataModel();
        BeanUtil.copyProperties(this, dataModel);
        <#list modelConfig.models as modelInfo>
        <#if modelInfo.groupKey??>
        dataModel.${modelInfo.groupKey} = dataModel.${modelInfo.groupKey};
        </#if>
        </#list>
        MainGenerator.doGenerator(dataModel);
        return 0;
    }
}