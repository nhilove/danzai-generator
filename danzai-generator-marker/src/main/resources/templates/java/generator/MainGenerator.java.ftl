package ${basePackage}.generator;

import freemarker.template.TemplateException;

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
public class MainGenerator {


    public static void doGenerator(Object dataMode) throws IOException, TemplateException {

        String inputRootPath = "${fileConfig.inputRootPath}";
        String outputRootPath = "${fileConfig.outputRootPath}";
        String inputPath;
        String outputPath;

<#list fileConfig.files as file>
                inputPath = new File(inputRootPath,"${file.inputPath}").getAbsolutePath();
        outputPath = new File(outputRootPath,"${file.outputPath}").getAbsolutePath();
    <#if file.generateType == "static">
                StaticGenerator.copyStaticByHutools(inputPath, outputPath);

     <#else>
        DynamicGenerator.doGenerator(inputPath, outputPath, dataMode);

     </#if>
</#list>

    }
}
