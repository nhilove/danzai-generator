package com.gyc.generator;

import cn.hutool.core.io.FileUtil;
import com.gyc.model.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ClassName: DynamicGenerator
 * Package: com.gyc.generator
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/11 20:46
 * @Version 1.0
 */
public class DynamicGenerator {

    public static void main(String[] args) throws TemplateException, IOException {
        String projectPath = System.getProperty("user.dir");
        String input = projectPath + File.separator + "danzai-generator-basic/src/main/resources/templates/MainTemplate.java.ftl";
        String output = projectPath + File.separator + "MainTemplate.java";
        MainTemplateConfig dataMode = new MainTemplateConfig();
        dataMode.setAuthor("John");
        dataMode.setLoop(true);
        dataMode.setOutputText("sum =");

        doGenerator(input, output, dataMode);
    }

    public static void doGenerator(String input, String output, Object dataMode) throws IOException, TemplateException {
        //指定版本号
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        //指定文件位置
        File parentDir = new File(input).getParentFile();

        cfg.setDirectoryForTemplateLoading(parentDir);


        //指定字符编码
        cfg.setDefaultEncoding("UTF-8");
        cfg.setNumberFormat("0.######");

        String name = new File(input).getName();
        Template template = cfg.getTemplate(name);

        //准备数据
        if (!FileUtil.exist(output)){
            FileUtil.touch(output);
        }
        FileWriter out = new FileWriter(output);
        template.process(dataMode, out);

        out.close();
    }
}
