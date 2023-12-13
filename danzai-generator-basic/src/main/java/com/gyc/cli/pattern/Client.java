package com.gyc.cli.pattern;

/**
 * ClassName: Client
 * Package: com.gyc.cli.cmd
 * Description: 使用者
 *
 * @Author gyc
 * @Create 2023/12/12 18:07
 * @Version 1.0
 */
public class Client {

    public static void main(String[] args) {
        Device device = new Device("tv");
        Device device2 = new Device("tcl");

        TurnOnCommand turnOnCommand = new TurnOnCommand(device);
        TurnOffCommand turnOffCommand = new TurnOffCommand(device2);

        RemoteControl remoteControl = new RemoteControl();
        remoteControl.SetCommand(turnOnCommand);
        remoteControl.pressButton();

        remoteControl.SetCommand(turnOffCommand);
        remoteControl.pressButton();

    }
}
