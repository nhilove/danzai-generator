package com.gyc.maker.generator.file;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * ClassName: FileGenerator
 * Package: com.gyc.maker.generator
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/11 21:27
 * @Version 1.0
 */
public class FileGenerator {

    public static void main(String[] args) throws TemplateException, IOException {
        String root = System.getProperty("user.dir");//danzai-generator
        System.out.println(root);
//        doGenerator();
    }

    public static void doGenerator(Object dataMode) throws IOException, TemplateException {
        String root = System.getProperty("user.dir");//danzai-generator/danzai-generator-marker
        String parentPath = new File(root).getParent();
        String inputPath = parentPath + File.separator + "danzai-generator-demo"+File.separator+"acm-template";
        String outputPath = root;
        StaticFileGenerator.copyStaticByHutools(inputPath, outputPath);

        String projectPath = System.getProperty("user.dir");//danzai-generator/danzai-generator-marker
        String input = projectPath + File.separator + "/src/main/resources/templates/MainTemplate.java.ftl";
        String output = projectPath + File.separator + "acm-template/src/com/gyc/acm/MainTemplate.java";

        DynamicFileGenerator.doGenerator(input, output, dataMode);
    }
}
