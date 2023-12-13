package com.gyc.model;

import lombok.Data;

/**
 * ClassName: MainTemplateConfig
 * Package: com.gyc.model
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/11 20:38
 * @Version 1.0
 */
@Data
public class MainTemplateConfig {

    private String author = "master";

    private String outputText = "sum=";

    private boolean loop = false;
}
