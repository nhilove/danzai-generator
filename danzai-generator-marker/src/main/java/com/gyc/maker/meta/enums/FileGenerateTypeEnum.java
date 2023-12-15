package com.gyc.maker.meta.enums;

/**
 * ClassName: FileGenerateTypeEnum
 * Package: com.gyc.maker.meta.meta
 * Description: 文件生成类型枚举
 *
 * @Author gyc
 * @Create 2023/12/15 11:12
 * @Version 1.0
 */
public enum FileGenerateTypeEnum {
    DYNAMIC("动态","dynamic"),
    STATIC("静态","static"),;

    private final String text;
    private final String value;

    FileGenerateTypeEnum(String text, String value) {
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
