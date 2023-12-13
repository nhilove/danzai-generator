package com.gyc;

import com.gyc.cli.CommandExecutor;

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

    public static void main(String[] args) {

        args = new String[]{"generate","-l","-a","-o"};
//        args = new String[]{"config"};
//        args = new String[]{"list"};
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExecute(args);
    }
}
