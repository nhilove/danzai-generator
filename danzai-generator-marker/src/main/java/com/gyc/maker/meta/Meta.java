package com.gyc.maker.meta;

import lombok.Data;

import java.util.List;

/**
 * ClassName: Meta
 * Package: com.gyc.maker.meta
 * Description:
 *
 * @Author gyc
 * @Create 2023/12/14 16:47
 * @Version 1.0
 */
//@NoArgsConstructor
@Data
public class Meta {


    private String name;
    private String description;
    private String basePackage;
    private String version;
    private String author;
    private String createTime;
    private FileConfig fileConfig;
    private ModelConfig modelConfig;

    //    @NoArgsConstructor
    @Data
    public static class FileConfig {
        private String inputRootPath;
        private String outputRootPath;
        private String sourceRootPath;
        private String type;
        private List<FileInfo> files;

        //        @NoArgsConstructor
        @Data
        public static class FileInfo {
            private String inputPath;
            private String outputPath;
            private String type;
            private String generateType;
        }
    }

    //    @NoArgsConstructor
    @Data
    public static class ModelConfig {
        private List<ModelInfo> models;

        //        @NoArgsConstructor
        @Data
        public static class ModelInfo {
            private String fieldName;
            private String type;
            private Object defaultValue;
            private String abbr;
            private String description;
        }
    }
}
