package com.gyc.cli.example;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

/**
 * ClassName: Login
 * Package: com.gyc.cli.example
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/12 12:08
 * @Version 1.0
 */
@Command(name = "login", version = "login 1.0", mixinStandardHelpOptions = true)
public class Login implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String user;

    @Option(names = { "-p", "--password" }, arity = "0..1",description = "Password",interactive = true,prompt = "输入密码：")
    String password;

    @Option(names = { "-cp", "--checkPassword" }, arity = "0..1",description = "CheckPassword",interactive = true,prompt = "输入确认密码：")
    String checkPassword;


    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        System.out.println("password:"+password);
        System.out.println("checkPassword:"+checkPassword);
        return 0;
    }

    public static void main(String[] args) {
        new CommandLine(new Login()).execute("-u","user123","-p","-cp");
    }
}
