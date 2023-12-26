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
public class TemplateMakerFileConfig {

    private List<FileInfoConfig> files;

    private FileGroupConfig fileGroupConfig;

    @Data
    public static class FileInfoConfig{
        private String path;

        private String condition;

        private List<FileFilterConfig> filterConfigList;
    }

    @Data
    public static class FileGroupConfig{

        private String groupKey;

        private String groupName;

        private String condition;
    }

}
