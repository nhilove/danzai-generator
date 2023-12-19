package com.gyc.maker.template.model;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: FileFilterConfig
 * Package: com.gyc.maker.template.model
 * Description: 文件过滤规则
 *
 * @Author gyc
 * @Create 2023/12/19 11:46
 * @Version 1.0
 */
@Data
@Builder
public class FileFilterConfig {

    /**
     * 过滤范围
     */
    private String range;

    /**
     * 过滤规则
     */
    private String rule;

    /**
     * 过滤值
     */
    private String value;
}
