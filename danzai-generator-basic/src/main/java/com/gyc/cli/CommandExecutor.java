package com.gyc.cli;

import com.gyc.cli.command.ConfigCommand;
import com.gyc.cli.command.GeneratorCommand;
import com.gyc.cli.command.ListCommand;
import picocli.CommandLine;

/**
 * ClassName: CommandExecutor
 * Package: com.gyc.cli
 * Description: 组合命令使用
 *
 * @Author gyc
 * @Create 2023/12/12 18:25
 * @Version 1.0
 */
@CommandLine.Command( name = "danzai", version = "danzai 1.0", mixinStandardHelpOptions = true )
public class CommandExecutor implements Runnable{

    private final CommandLine commandLine;

    {
        commandLine = new CommandLine(this)
                .addSubcommand(new GeneratorCommand())
                .addSubcommand(new ListCommand())
                .addSubcommand(new ConfigCommand());
    }


    @Override
    public void run() {
        System.out.println("请输入具体命令 或 --help 获取帮助信息");
    }

    public Integer doExecute(String[] args){
        return commandLine.execute(args);
    }
}
