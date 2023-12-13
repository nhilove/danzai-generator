import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: FreeMarkerTest
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/11 19:57
 * @Version 1.0
 */
public class FreeMarkerTest {

    @Test
    public void test() throws IOException, TemplateException {
        //指定版本号
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        //指定文件位置
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        //指定字符编码
        cfg.setDefaultEncoding("UTF-8");

        Template template = cfg.getTemplate("myweb.html.ftl");

        //准备数据
        Map<String,Object> dataMode = new HashMap<>();
        dataMode.put("currentYear",2023);
        List<Map<String,Object>> menuItems = new ArrayList<>();
        Map<String,Object> menuItem1 = new HashMap<>();
        menuItem1.put("url", "http://xxxxxxxx");
        menuItem1.put("label", "编程导航");
        Map<String,Object> menuItem2 = new HashMap<>();
        menuItem2.put("url", "http://aaaaaaa");
        menuItem2.put("label", "老于建立");
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);
        dataMode.put("menuItems",menuItems);

        FileWriter out = new FileWriter("myweb.html");
        template.process(dataMode, out);

        out.close();
    }
}
