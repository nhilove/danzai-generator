package com.gyc.maker.meta;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.gyc.maker.meta.Meta.FileConfig;
import com.gyc.maker.meta.Meta.ModelConfig;
import com.gyc.maker.meta.enums.FileGenerateTypeEnum;
import com.gyc.maker.meta.enums.FileTypeEnum;
import com.gyc.maker.meta.enums.ModelTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

/**
 * ClassName: MetaValidator
 * Package: com.gyc.maker.meta
 * Description: meta 校验类
 *
 * @Author gyc
 * @Create 2023/12/15 9:59
 * @Version 1.0
 */
public class MetaValidator {

    public static void doValidateAndFill(Meta meta) {
        //基础信息校验
        basicValidateAndFill(meta);
        //meta的fileConfig 校验和填充默认值
        fileConfigValidateAndFill(meta);
        //meta的modelConfig 校验和填充默认值
        modelConfigValidateAndFill(meta);
    }

    /**
     * 基础信息校验
     * @param meta
     */
    private static void basicValidateAndFill(Meta meta) {
        //meta基础信息校验和填充默认值
        String name = StrUtil.blankToDefault(meta.getName(),"my-template-generator");
        String description = StrUtil.blankToDefault(meta.getDescription(),"我的模板");
        String basePackage = StrUtil.blankToDefault(meta.getBasePackage(),"com.gyc");
        String version = StrUtil.blankToDefault(meta.getVersion(),"1.0");
        String author = StrUtil.blankToDefault(meta.getAuthor(),"gyc");
        String createTime = StrUtil.blankToDefault(meta.getCreateTime(),DateUtil.now());
        meta.setName(name);
        meta.setDescription(description);
        meta.setBasePackage(basePackage);
        meta.setVersion(version);
        meta.setAuthor(author);
        meta.setCreateTime(createTime);
    }

    /**
     * meta的modelConfig 校验和填充默认值
     * @param meta
     */
    private static void modelConfigValidateAndFill(Meta meta) {

        ModelConfig modelConfig = meta.getModelConfig();
        if (modelConfig == null) {
            return;
        }
        List<ModelConfig.ModelInfo> models = modelConfig.getModels();
        if (!CollectionUtil.isNotEmpty(models)) {
            return;
        }
        for (ModelConfig.ModelInfo modelInfo : models) {
            String fieldName = modelInfo.getFieldName();
            if (StrUtil.isBlank(fieldName)) {
                throw new MetaException("未填写 modelConfig 的 filedName");
            }
            String modelInfoType = modelInfo.getType();
            if (StrUtil.isBlank(modelInfoType)) {
                modelInfo.setType(ModelTypeEnum.STRING.getValue());
            }
        }
    }

    /**
     * meta的fileConfig 校验和填充默认值
     * @param meta
     */
    private static void fileConfigValidateAndFill(Meta meta) {

        FileConfig fileConfig = meta.getFileConfig();
        if (fileConfig == null) {
            return;
        }
        String sourceRootPath = fileConfig.getSourceRootPath();
        if (StrUtil.isBlank(sourceRootPath)) {
            throw new MetaException("未填写 sourceRootPath");
        }
        String inputRootPath = fileConfig.getInputRootPath();
        String defaultInputRootPath = ".source" + File.separator + FileUtil.getLastPathEle(Paths.get(sourceRootPath)).getFileName().toString();
        if (StrUtil.isBlank(inputRootPath)) {
            fileConfig.setInputRootPath(defaultInputRootPath);
        }
        String outputRootPath = fileConfig.getOutputRootPath();
        String defaultOutputRootPath = "generated";
        if (StrUtil.isBlank(outputRootPath)) {
            fileConfig.setOutputRootPath(defaultOutputRootPath);
        }
        String fileConfigType = fileConfig.getType();
        if (StrUtil.isBlank(fileConfigType)) {
            fileConfigType = FileTypeEnum.DIR.getValue();
            fileConfig.setType(fileConfigType);
        }
        List<FileConfig.FileInfo> files = fileConfig.getFiles();
        if (!CollectionUtil.isNotEmpty(files)) {
            return;
        }
        for (FileConfig.FileInfo fileInfo : files) {
            String inputPath = fileInfo.getInputPath();
            if (StrUtil.isBlank(inputPath)) {
                throw new MetaException("未填写 fileConfig 的 inputPath");
            }
            String outputPath = fileInfo.getOutputPath();
            if (StrUtil.isBlank(outputPath)) {
                fileInfo.setOutputPath(inputPath);
            }
            String fileInfoType = fileInfo.getType();
            if (StrUtil.isBlank(fileInfoType)) {
                if (StrUtil.isBlank(FileUtil.getSuffix(inputPath))) {
                    fileInfo.setType(FileTypeEnum.DIR.getValue());
                } else {
                    fileInfo.setType(FileTypeEnum.FILE.getValue());
                }

            }
            String generateType = fileInfo.getGenerateType();
            if (StrUtil.isBlank(generateType)) {
                if (generateType.endsWith(".ftl")) {
                    fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
                } else {
                    fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
                }
            }
        }
    }

}
