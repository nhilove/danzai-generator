package com.gyc.maker.meta.enums;

/**
 * ClassName: FileTypeEnum
 * Package: com.gyc.maker.meta.meta
 * Description: 文件类型
 *
 * @Author gyc
 * @Create 2023/12/15 11:10
 * @Version 1.0
 */
public enum FileTypeEnum {
    DIR("目录","dir"),
    FILE("文件","file"),
    GROUP("组文件","group"),;

    private final String text;
    private final String value;


    FileTypeEnum(String text,String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
