package templates.java.generator;

{basePackage}.generator;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ClassName: DynamicGenerator
 * Package: ${basePackage}.generator
 * Description:
 *
 * @Author ${author}
 * @Create ${createTime}
 * @Version ${version}
 */
public class DynamicGenerator {

    public static void doGenerator(String input, String output, Object dataMode) throws IOException, TemplateException {
        //指定版本号
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        //指定文件位置
        File parentDir = new File(input).getParentFile();

        cfg.setDirectoryForTemplateLoading(parentDir);


        //指定字符编码
        cfg.setDefaultEncoding("UTF-8");
        cfg.setNumberFormat("0.######");

        if (!FileUtil.exist(output)){
            FileUtil.touch(output);
        }

        String name = new File(input).getName();
        Template template = cfg.getTemplate(name);

        //准备数据

        FileWriter out = new FileWriter(output);
        template.process(dataMode, out);

        out.close();
    }
}
