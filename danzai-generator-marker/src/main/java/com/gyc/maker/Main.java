package com.gyc.maker;

import com.gyc.maker.cli.CommandExecutor;
import com.gyc.maker.generator.main.MainGenerator;
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
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExecute(args);
        MainGenerator mainGenerator = new MainGenerator();
        mainGenerator.doGenerate();
    }
}
