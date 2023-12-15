package com.gyc.maker.meta.enums;

/**
 * ClassName: ModelTypeEnum
 * Package: com.gyc.maker.meta.meta
 * Description: 模型数据类型枚举
 *
 * @Author gyc
 * @Create 2023/12/15 11:17
 * @Version 1.0
 */
public enum ModelTypeEnum {
    STRING("字符串","String"),
    BOOLEAN("布尔","boolean");

    private final String text;
    private final String value;

    ModelTypeEnum(String text, String value) {
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
