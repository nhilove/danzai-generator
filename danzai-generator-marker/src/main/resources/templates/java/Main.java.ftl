package ${basePackage};

import  ${basePackage}.cli.CommandExecutor;

/**
 * ClassName: CommandExecutor
 * Package: ${basePackage}
 * Description: ${description}
 *
 * @Author ${author}
 * @Create ${createTime}
 * @Version ${version}
 */
public class Main {

    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExecute(args);
    }
}