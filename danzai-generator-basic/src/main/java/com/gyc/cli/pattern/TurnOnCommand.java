package com.gyc.cli.pattern;

/**
 * ClassName: TurnOnCommand
 * Package: com.gyc.cli.cmd
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/12 18:00
 * @Version 1.0
 */
public class TurnOnCommand implements Command{

    private Device device;

    public TurnOnCommand(Device device){
        this.device = device;
    }

    @Override
    public void execute() {
        device.turnOn();
    }
}
