package com.gyc.cli.command;

import cn.hutool.core.util.ReflectUtil;
import com.gyc.model.MainTemplateConfig;
import picocli.CommandLine;

import java.lang.reflect.Field;

/**
 * ClassName: ConfigCommand
 * Package: com.gyc.cli.command
 * Description: 参数列表信息命令
 *
 * @Author gyc
 * @Create 2023/12/12 18:24
 * @Version 1.0
 */
@CommandLine.Command( name = "config",description = "配置信息",mixinStandardHelpOptions = true )
public class ConfigCommand implements Runnable{

    @Override
    public void run() {
        Field[] fields = ReflectUtil.getFields(MainTemplateConfig.class);
        for (Field field : fields){
            System.out.println("类型："+field.getType());
            System.out.println("名字："+field.getName());
        }
    }
}
