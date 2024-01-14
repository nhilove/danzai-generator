package com.gyc.maker;

import com.gyc.maker.generator.main.MainGenerator;
import com.gyc.maker.generator.main.ZipGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * ClassName: Main
 * Package: com.gyc
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/12 21:04
 * @Version 1.0
 */
public class Main {

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
//        MainGenerator mainGenerator = new MainGenerator();
//        mainGenerator.doGenerate();
        ZipGenerator zipGenerator = new ZipGenerator();
        zipGenerator.doGenerate();
    }
}
