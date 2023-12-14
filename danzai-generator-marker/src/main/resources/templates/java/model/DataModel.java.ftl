package templates.java.model;

{basePackage}.model;

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
@Data
public class DataModel {

<#list modelConfig.models as modelInfo>
    <#if modelInfo.description??>
    /**
    *   ${modelInfo.description}
    */
    </#if>
    private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??>= ${modelInfo.defaultValue?c}</#if>;
</#list>
}