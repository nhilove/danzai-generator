package com.gyc.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.gyc.maker.generator.JarGenerator;
import com.gyc.maker.generator.ScriptGenerator;
import com.gyc.maker.generator.file.DynamicFileGenerator;
import com.gyc.maker.meta.Meta;
import com.gyc.maker.meta.MetaManage;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * ClassName: MainTemplate
 * Package: com.gyc.maker.generator.main
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/15 11:47
 * @Version 1.0
 */
public abstract class MainTemplate {

    public void doGenerate() throws TemplateException, IOException, InterruptedException {
        Meta meta = MetaManage.getMeta();
//        System.out.println(meta);
        //项目输出路径
        String projectPath = System.getProperty("user.dir"); //danzai-generator-maker
        String outputPath = projectPath + File.separator + "generated" + File.separator + meta.getName();
        if (!FileUtil.exist(outputPath)) {
            FileUtil.mkdir(outputPath);
        }
        //1. 复制源文件包
        String sourceRootPath = sourceCopyDestPath(meta, outputPath);

        //2. 代码生成
        generateCode(meta, outputPath);

        //3. 打jar包
        String jarPath = buildJar(outputPath, meta);

        //4. 封装脚本文件
        String shellOutputPath = buildScript(outputPath, jarPath);

        //5. 生成精简版的程序
        buildDist(outputPath, sourceRootPath, shellOutputPath, jarPath);
    }

    /**
     * 封装脚本文件
     *
     * @param outputPath
     * @param jarPath
     * @return
     */
    protected String buildScript(String outputPath, String jarPath) {
        String shellOutputPath = outputPath + File.separator + "generator";
        //执行脚本文件
        ScriptGenerator.doGenerate(shellOutputPath, jarPath);
        return shellOutputPath;
    }

    /**
     * 生成精简版的程序
     *
     * @param outputPath
     * @param sourceRootPath
     * @param shellOutputPath
     * @param jarPath
     */
    protected void buildDist(String outputPath, String sourceRootPath, String shellOutputPath, String jarPath) {
        //精简包
        //精简包项目根路径
        String outPutDistPath = outputPath + "-dist";
        String targetAbsolutePath = outPutDistPath + File.separator + "target";
        FileUtil.mkdir(targetAbsolutePath);
        //复制jar包
        String jarAbsolutePath = outputPath + File.separator + jarPath;
        FileUtil.copy(jarAbsolutePath, targetAbsolutePath, true);
        //复制源文件
        FileUtil.copy(sourceRootPath, outputPath, true);
        //复制脚本文件
        FileUtil.copy(shellOutputPath, outPutDistPath, true);
        FileUtil.copy(shellOutputPath + ".bat", outPutDistPath, true);
    }

    /**
     * 打jar包
     *
     * @param outputPath
     * @param meta
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    protected String buildJar(String outputPath, Meta meta) throws IOException, InterruptedException {
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target/" + jarName;
        //打jar包
        JarGenerator.doGenerate(outputPath);
        return jarPath;
    }

    /**
     * 代码生成
     *
     * @param meta
     * @param outputPath
     * @throws IOException
     * @throws TemplateException
     */
    protected void generateCode(Meta meta, String outputPath) throws IOException, TemplateException {
        //resources
        ClassPathResource classPathResource = new ClassPathResource("");
        String inputResourcePath = classPathResource.getAbsolutePath();

        //输出项目的包路径
        String outputBasePackage = meta.getBasePackage();
        // com.gyc => com/gyc
        String outputBasePackagePath = String.join("/", StrUtil.split(outputBasePackage, "."));
        String outputBaseJavaPackagePath = outputPath + File.separator + "src/main/java/" + outputBasePackagePath;

        //输入路径
        String inputFilePath;
        String outputFilePath;
        //com/gyc/cli/model
        inputFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "model/DataModel.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //com/gyc/cli/command
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ConfigCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "cli/command/ConfigCommand.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ListCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "cli/command/ListCommand.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/GenerateCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "cli/command/GenerateCommand.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);
        //com/gyc/cli
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/CommandExecutor.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "cli/CommandExecutor.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //com/gyc/generator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/DynamicGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "generator/DynamicGenerator.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/MainGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "generator/MainGenerator.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/StaticGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "generator/StaticGenerator.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //Main.java
        inputFilePath = inputResourcePath + File.separator + "templates/java/Main.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "Main.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //pom.xml
        inputFilePath = inputResourcePath + File.separator + "templates/pom.xml.ftl";
        outputFilePath = outputPath + File.separator + "pom.xml";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //README.md
        inputFilePath = inputResourcePath + File.separator + "templates/README.md.ftl";
        outputFilePath = outputPath + File.separator + "README.md";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);
    }

    /**
     * 复制源文件包
     *
     * @param meta
     * @param outputPath
     * @return
     */
    protected String sourceCopyDestPath(Meta meta, String outputPath) {
        //复制要动态生成文件的源文件包
        String sourceRootPath = meta.getFileConfig().getSourceRootPath();
        String sourceCopyProjectDestPath = outputPath + File.separator + ".source";
        FileUtil.copy(sourceRootPath, sourceCopyProjectDestPath, false);
        return sourceRootPath;
    }

}
