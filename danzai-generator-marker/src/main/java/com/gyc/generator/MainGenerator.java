package com.gyc.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.gyc.generator.file.DynamicFileGenerator;
import com.gyc.meta.Meta;
import com.gyc.meta.MetaManage;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator {

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        Meta meta = MetaManage.getMeta();
//        System.out.println(meta);
        //项目输出路径
        String projectPath = System.getProperty("user.dir"); //danzai-generator-maker
        String outputProjectPath = projectPath + File.separator + "generated";

        if (FileUtil.exist(outputProjectPath)) {
            FileUtil.mkdir(outputProjectPath);
        }

        //输出项目的包路径
        String basePackage = meta.getBasePackage();
        // com.gyc => com/gyc
        String packagePath = String.join("/", StrUtil.split(basePackage, "."));
        String outputPackagePath = outputProjectPath + File.separator + "src/main/java/" + packagePath;

        //resources
        ClassPathResource inputFileResourcePath = new ClassPathResource("");
        String resourcePath = inputFileResourcePath.getAbsolutePath();
        //输入路径
        String inputFilePath;
        String outputFilePath;
        //com/gyc/cli/model
        inputFilePath = resourcePath + File.separator + "templates/java/model/DataModel.java.ftl";
        outputFilePath = outputPackagePath + File.separator + "model/DataModel.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //com/gyc/cli/command
        inputFilePath = resourcePath + File.separator + "templates/java/cli/command/ConfigCommand.java.ftl";
        outputFilePath = outputPackagePath + File.separator + "cli/command/ConfigCommand.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);
        inputFilePath = resourcePath + File.separator + "templates/java/cli/command/ListCommand.java.ftl";
        outputFilePath = outputPackagePath + File.separator + "cli/command/ListCommand.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);
        inputFilePath = resourcePath + File.separator + "templates/java/cli/command/GenerateCommand.java.ftl";
        outputFilePath = outputPackagePath + File.separator + "cli/command/GenerateCommand.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);
        //com/gyc/cli
        inputFilePath = resourcePath + File.separator + "templates/java/cli/CommandExecutor.java.ftl.ftl";
        outputFilePath = outputPackagePath + File.separator + "cli/CommandExecutor.java.ftl";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //com/gyc/generator
        inputFilePath = resourcePath + File.separator + "templates/java/generator/DynamicGenerator.java.ftl";
        outputFilePath = outputPackagePath + File.separator + "generator/DynamicGenerator.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);
        inputFilePath = resourcePath + File.separator + "templates/java/generator/MainGenerator.java.ftl";
        outputFilePath = outputPackagePath + File.separator + "generator/MainGenerator.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);
        inputFilePath = resourcePath + File.separator + "templates/java/generator/StaticGenerator.java.ftl";
        outputFilePath = outputPackagePath + File.separator + "generator/StaticGenerator.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //pom.xml
        inputFilePath = resourcePath + File.separator + "templates/pom.xml.ftl";
        outputFilePath = outputProjectPath + File.separator + "pom.xml";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //打jar包
        JarGenerator.doGenerate(outputProjectPath);
    }
}  