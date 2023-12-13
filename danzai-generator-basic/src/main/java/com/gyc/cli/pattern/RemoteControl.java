package com.gyc.cli.pattern;

/**
 * ClassName: RemoteControl
 * Package: com.gyc.cli.cmd
 * Description: 调用者，组装命令，相当于遥控器，每个命令就相当于一个按钮
 *
 * @Author gyc
 * @Create 2023/12/12 18:05
 * @Version 1.0
 */
public class RemoteControl {

    private Command command;

    public void SetCommand(Command command) {
        this.command = command;
    }

    public void pressButton(){
        command.execute();
    }

}
