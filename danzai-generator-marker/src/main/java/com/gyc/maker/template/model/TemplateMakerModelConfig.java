package com.gyc.maker.template.model;

import lombok.Data;

import java.util.List;

/**
 * ClassName: TemplateMakerFileConfig
 * Package: com.gyc.maker.template.model
 * Description: 文件相关配置
 *
 * @Author gyc
 * @Create 2023/12/19 11:48
 * @Version 1.0
 */
@Data
public class TemplateMakerModelConfig {

    private List<ModelInfoConfig> models;

    private ModelGroupConfig modelGroupConfig;

    @Data
    public static class ModelInfoConfig{
        private String fieldName;

        private String type;

        private String description;

        private String defaultValue;

        private String abbr;

        /**
         * 要替换的文本
         */
        private String replaceText;

    }

    @Data
    public static class ModelGroupConfig{

        private String groupKey;

        private String groupName;

        private String condition;

        private String description;

        private String type;
    }

}
