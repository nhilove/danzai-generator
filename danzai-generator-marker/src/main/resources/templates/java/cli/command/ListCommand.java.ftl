package templates.java.cli.command;

{basePackage}.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine;

import java.io.File;
import java.util.List;

/**
* ClassName: ListCommand
* Package: ${basePackage}.cli.command
* Description: ${description}
*
* @Author ${author}
* @Create ${createTime}
* @Version ${version}
*/
@CommandLine.Command( name = "list",description = "文件列表",mixinStandardHelpOptions = true )
public class ListCommand implements Runnable{

    @Override
    public void run() {
        String inputPath = "${fileConfig.inputRootPath}";
        List<File> files = FileUtil.loopFiles(inputPath);
        for (File file : files) {
            System.out.println(file);
        }
    }
}