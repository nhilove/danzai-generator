package com.gyc.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine;

import java.io.File;
import java.util.List;

/**
 * ClassName: ListCommand
 * Package: com.gyc.cli.command
 * Description: 列表信息命令
 *
 * @Author gyc
 * @Create 2023/12/12 18:23
 * @Version 1.0
 */
@CommandLine.Command( name = "list",description = "文件列表",mixinStandardHelpOptions = true )
public class ListCommand implements Runnable{

    @Override
    public void run() {
        String root = System.getProperty("user.dir");
        String inputPath = root + File.separator + "danzai-generator-demo"+File.separator+"acm-template";
        List<File> files = FileUtil.loopFiles(inputPath);
        for (File file : files) {
            System.out.println(file);
        }
    }
}
