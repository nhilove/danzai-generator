package templates.java.cli.command;

{basePackage}.cli.command;

import lombok.Data;
import cn.hutool.core.util.ReflectUtil;
import ${basePackage}.model.DataModel;
import picocli.CommandLine;

import java.lang.reflect.Field;

/**
* ClassName: ConfigCommand
* Package: ${basePackage}.cli.command
* Description: ${description}
*
* @Author ${author}
* @Create ${createTime}
* @Version ${version}
*/
@CommandLine.Command( name = "config",description = "配置信息",mixinStandardHelpOptions = true )
public class ConfigCommand implements Runnable{

    @Override
    public void run() {
        Field[] fields = ReflectUtil.getFields(DataModel.class);
        for (Field field : fields){
            System.out.println("类型："+field.getType());
            System.out.println("名字："+field.getName());
        }
    }
}
