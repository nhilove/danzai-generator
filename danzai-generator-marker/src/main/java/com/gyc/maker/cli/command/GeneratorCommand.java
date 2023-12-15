package com.gyc.maker.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.gyc.maker.generator.file.FileGenerator;
import com.gyc.maker.model.DataModel;
import freemarker.template.TemplateException;
import lombok.Data;
import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * ClassName: GeneratorCommand
 * Package: com.gyc.maker.cli.command
 * Description: 文件生成命令
 *
 * @Author gyc
 * @Create 2023/12/12 18:23
 * @Version 1.0
 */
@CommandLine.Command( name = "generate",description = "文件生成",mixinStandardHelpOptions = true )
@Data
public class GeneratorCommand implements Callable<Integer> {

    @CommandLine.Option(names = { "-a", "--author" }, arity = "0..1",description = "作者名称",interactive = true,echo = true)
    private String author = "master";

    @CommandLine.Option(names = { "-o", "--outputText" }, arity = "0..1",description = "输出文本",interactive = true,echo = true)
    private String outputText = "sum=";

    @CommandLine.Option(names = { "-l", "--loop" }, arity = "0..1",description = "是否循环",interactive = true,echo = true)
    private boolean loop;


    @Override
    public Integer call() {
        DataModel dataModel = new DataModel();
        BeanUtil.copyProperties(this, dataModel);
        System.out.println("配置信息："+ dataModel);
        try {
            FileGenerator.doGenerator(dataModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
