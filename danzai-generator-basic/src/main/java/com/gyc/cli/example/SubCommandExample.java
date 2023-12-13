package com.gyc.cli.example;

import picocli.CommandLine;
import picocli.CommandLine.Command;


/**
 * ClassName: SubCommandExample
 * Package: com.gyc.cli.example
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/12 12:16
 * @Version 1.0
 */
@Command( name = "main", version = "main 1.0", mixinStandardHelpOptions = true )
public class SubCommandExample implements Runnable {


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("执行主命令");
    }

    @Command( name = "add", version = "add 1.0", mixinStandardHelpOptions = true )
    static class AddCommand implements Runnable {
        @Override
        public void run() {
            System.out.println("执行添加命令");
        }
    }

    @Command( name = "delete", version = "delete 1.0", mixinStandardHelpOptions = true )
    static class DeleteCommand implements Runnable {
        @Override
        public void run() {
            System.out.println("执行删除命令");
        }
    }

    @Command( name = "query", version = "add 1.0", mixinStandardHelpOptions = true )
    static class QueryCommand implements Runnable {
        @Override
        public void run() {
            System.out.println("执行查询命令");
        }
    }

    public static void main(String[] args) {
        String[] myar = new String[]{};
        //执行主命令
//        String[] myar = new String[]{"--help"};
        //执行子命令
//        String[] myar = new String[]{"add"};
        // 子命令帮助手册
//        String[] myar = new String[]{"add","--help"};
        //子命令不存在会报错
//        String[] myar = new String[]{"update"};


        int exitCode = new CommandLine(new SubCommandExample())
                .addSubcommand(new AddCommand())
                .addSubcommand(new DeleteCommand())
                .addSubcommand(new QueryCommand())
                .execute(myar);
        System.exit(exitCode);
    }

}
