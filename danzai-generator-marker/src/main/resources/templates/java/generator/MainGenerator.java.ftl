package ${basePackage}.generator;

import freemarker.template.TemplateException;

import ${basePackage}.model.DataModel;

import java.io.File;
import java.io.IOException;

/**
 * ClassName: MainGenerator
 * Package: ${basePackage}.generator
 * Description:
 *
 * @Author ${author}
 * @Create ${createTime}
 * @Version ${version}
 */
<#macro generateFile indent fileInfo>
${indent}inputPath = new File(inputRootPath,"${fileInfo.inputPath}").getAbsolutePath();
${indent}outputPath = new File(outputRootPath,"${fileInfo.outputPath}").getAbsolutePath();
<#if fileInfo.generateType == "static">
${indent}StaticGenerator.copyStaticByHutools(inputPath, outputPath);
<#else>
${indent}DynamicGenerator.doGenerator(inputPath, outputPath, dataModel);
</#if>
</#macro>
public class MainGenerator {


    public static void doGenerator(DataModel dataModel) throws IOException, TemplateException {

        String inputRootPath = "${fileConfig.inputRootPath}";
        String outputRootPath = "${fileConfig.outputRootPath}";
        String inputPath;
        String outputPath;
<#--获取属性值-->
<#list modelConfig.models as modelInfo>

        <#if modelInfo.groupKey??>
            <#list modelInfo.models as subModelInfo>
       ${subModelInfo.type} ${subModelInfo.fieldName} = dataModel.${modelInfo.groupKey}.${subModelInfo.fieldName};
            </#list>
        <#else>
       ${modelInfo.type} ${modelInfo.fieldName} = dataModel.${modelInfo.fieldName};
        </#if>
</#list>


<#list fileConfig.files as fileInfo>
<#-- 在遍历以下同组文件生成一起-->
    <#if fileInfo.groupKey??>
        <#if fileInfo.condition??>
        if (${fileInfo.condition}) {
        <#list fileInfo.files as subFileInfo>
            <@generateFile indent="            " fileInfo=subFileInfo />
        </#list>
        }
        <#else>
            <#list fileInfo.files as subFileInfo>
                <@generateFile indent="               " fileInfo=subFileInfo />
            </#list>
        </#if>
    <#else>
        <@generateFile indent="    " fileInfo=fileInfo />
    </#if>

</#list>
    }
}
