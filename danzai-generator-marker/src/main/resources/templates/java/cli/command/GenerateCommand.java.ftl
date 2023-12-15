package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import freemarker.template.TemplateException;
import lombok.Data;
import picocli.CommandLine;

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
@CommandLine.Command( name = "generate",description = "文件生成",mixinStandardHelpOptions = true )
@Data
public class GenerateCommand implements Callable<Integer> {

    <#list modelConfig.models as modelInfo>
        <#if modelInfo.description??>
    /**
     *   ${modelInfo.description}
     */
        </#if>
    @CommandLine.Option(names = { <#if modelInfo.abbr??>"-${modelInfo.abbr}",</#if>"--${modelInfo.fieldName}" }, arity = "0..1",<#if modelInfo.description??>description = "${modelInfo.description}",</#if>interactive = true,echo = true)
    private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??>= ${modelInfo.defaultValue?c}</#if>;
    </#list>


    @Override
    public Integer call() {
        DataModel dataModel = new DataModel();
        BeanUtil.copyProperties(this, dataModel);
        System.out.println("配置信息："+ dataModel);
        try {
            MainGenerator.doGenerator(dataModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return 0;
    }
}