package com.gyc.generator;

import cn.hutool.Hutool;
import cn.hutool.core.io.FileUtil;

import java.io.File;

/**
 * ClassName: StaticGenerator
 * Package: com.gyc.generator
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/11 17:39
 * @Version 1.0
 */
public class StaticGenerator {

    public static void main(String[] args) {
        String root = System.getProperty("user.dir");
        String inputPath = root + File.separator + "danzai-generator-demo"+File.separator+"acm-template";
        System.out.println("inputPath: " + inputPath);
        String outputPath = root;
        copyStaticByHutools(inputPath, outputPath);
    }


    public static void copyStaticByHutools(String res, String des){
        FileUtil.copy(res, des,false);
    }
}
