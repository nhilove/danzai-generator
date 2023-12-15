package com.gyc.maker.generator.file;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ClassName: DynamicFileGenerator
 * Package: com.gyc.maker.generator
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/11 20:46
 * @Version 1.0
 */
public class DynamicFileGenerator {

    public static void doGenerator(String inputPath, String outputPath, Object model) throws IOException, TemplateException {
        //指定版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        //指定文件位置
        File templateDir = new File(inputPath).getParentFile();

        configuration.setDirectoryForTemplateLoading(templateDir);


        //指定字符编码
        configuration.setDefaultEncoding("UTF-8");
        configuration.setNumberFormat("0.######");

        String templateName = new File(inputPath).getName();
        Template template = configuration.getTemplate(templateName);

        //准备数据
        if (!FileUtil.exist(outputPath)){
            FileUtil.touch(outputPath);
        }
        FileWriter out = new FileWriter(outputPath);
        template.process(model, out);

        out.close();
    }
}
