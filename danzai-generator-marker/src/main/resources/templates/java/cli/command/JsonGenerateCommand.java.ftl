package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import ${basePackage}.model.DataModel;
import ${basePackage}.generator.MainGenerator;
import lombok.Data;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.util.concurrent.Callable;

/**
 * ClassName: ConfigCommand
 * Package: ${basePackage}.cli.command
 * Description: ${description}
 *
 * @Author ${author}
 * @Create ${createTime}
 * @Version ${version}
 */
@Command( name = "json-generate", description = "读取json文件生成代码", mixinStandardHelpOptions = true )
@Data
public class JsonGenerateCommand implements Callable<Integer> {


    @CommandLine.Option( names = {"-f", "--file"}, arity = "0..1", description = "json文件路径", interactive = true, echo = true )
    private String filePath;

    @Override
    public Integer call() throws Exception {
        //填充数据模型对象
        String jsonStr = FileUtil.readUtf8String(filePath);
        DataModel dataModel = JSONUtil.toBean(jsonStr, DataModel.class);
        MainGenerator.doGenerator(dataModel);
        return 0;
    }
}