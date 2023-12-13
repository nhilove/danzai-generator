package com.gyc.cli.pattern;

/**
 * ClassName: Device
 * Package: com.gyc.cli.cmd
 * Description: 被调用者
 *
 * @Author gyc
 * @Create 2023/12/12 18:01
 * @Version 1.0
 */
public class Device {

    private String name;

    public Device(String name) {
        this.name = name;
    }

    public void turnOn(){
        System.out.println(name + "打开");
    }

    public void turnOff(){
        System.out.println(name + "关闭");
    }
}
