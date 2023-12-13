package com.gyc.cli.pattern;

/**
 * ClassName: TurnOffCommand
 * Package: com.gyc.cli.cmd
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/12 18:04
 * @Version 1.0
 */
public class TurnOffCommand implements Command{

    private Device device;

    public TurnOffCommand(Device device){
        this.device = device;
    }

    @Override
    public void execute() {
        device.turnOff();
    }
}
