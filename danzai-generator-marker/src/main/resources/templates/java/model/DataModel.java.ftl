package ${basePackage}.model;

import lombok.Data;

/**
 * ClassName: DataModel
 * Package: ${basePackage}.maker.model
 * Description: ${description}
 *
 * @Author ${author}
 * @Create ${createTime}
 * @Version ${version}
 */
<#macro generateModel indent modelInfo>
<#if modelInfo.description??>
${indent}/**
${indent}*   ${modelInfo.description}
${indent}*/
</#if>
${indent}public ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??>= ${modelInfo.defaultValue?c}</#if>;
</#macro>
@Data
public class DataModel {

<#list modelConfig.models as modelInfo>
<#--一组模型参数-->
    <#if modelInfo.groupKey??>
    /**
     * ${modelInfo.groupName}
     */
    public ${modelInfo.type} ${modelInfo.groupKey} = new  ${modelInfo.type}();

    /**
     * ${modelInfo.groupName}
     */
    @Data
    public static class ${modelInfo.type} {
        <#list modelInfo.models as modelInfo>
            <@generateModel indent="        " modelInfo=modelInfo />
        </#list>
    }

    <#else>
    <@generateModel indent="    " modelInfo=modelInfo />
    </#if>
</#list>
}