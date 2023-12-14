package com.gyc.generator.file;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ClassName: DynamicFileGenerator
 * Package: com.gyc.generator
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/11 20:46
 * @Version 1.0
 */
public class DynamicFileGenerator {

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

        FileWriter out = new FileWriter(output);
        template.process(dataMode, out);

        out.close();
    }
}
