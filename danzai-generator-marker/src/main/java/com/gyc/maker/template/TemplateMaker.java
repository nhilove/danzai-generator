package com.gyc.maker.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.gyc.maker.meta.Meta;
import com.gyc.maker.meta.enums.FileGenerateTypeEnum;
import com.gyc.maker.meta.enums.FileTypeEnum;
import com.gyc.maker.template.model.TemplateMakerFileConfig;
import com.gyc.maker.template.model.TemplateMakerModelConfig;
import com.gyc.maker.template.model.TemplateMakerOutputConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: TemplateMaker
 * Package: com.gyc.maker.template
 * Description: 模板生成工具
 *
 * @Author gyc
 * @Create 2023/12/18 18:21
 * @Version 1.0
 */
public class TemplateMaker {

    /**
     * 制作模板
     *
     * @param templateMakerConfig
     * @return
     */
    public static long makeTemplate(TemplateMakerConfig templateMakerConfig) {
        long id = templateMakerConfig.getId();
        Meta meta = templateMakerConfig.getMeta();
        TemplateMakerFileConfig fileInfoConfig = templateMakerConfig.getFileConfig();
        TemplateMakerModelConfig modelConfig = templateMakerConfig.getModelConfig();
        String originProjectPath = templateMakerConfig.getOriginProjectPath();
        TemplateMakerOutputConfig outputConfig = templateMakerConfig.getOutputConfig();
        return makeTemplate(meta, originProjectPath, fileInfoConfig, modelConfig, outputConfig, id);
    }

    /**
     * 制作模板
     *
     * @param newMeta                   新的配置信息
     * @param originProjectPath         原始文件路径
     * @param templateMakerFileConfig   过滤规则集合
     * @param templateMakerModelConfig  模型參數集合
     * @param templateMakerOutputConfig 文件输出规则
     * @param id                        文件目录
     * @return 文件目录
     */
    public static long makeTemplate(Meta newMeta,
                                    String originProjectPath,
                                    TemplateMakerFileConfig templateMakerFileConfig,
                                    TemplateMakerModelConfig templateMakerModelConfig,
                                    TemplateMakerOutputConfig templateMakerOutputConfig,
                                    Long id) {
        //没有id则生成
        if (id == null) {
            id = IdUtil.getSnowflakeNextId();
        }

        //复制目录
        String projectPath = System.getProperty("user.dir");
        String tempDirPath = projectPath + File.separator + ".temp";
        String templatePath = tempDirPath + File.separator + id;
        //首次文件不存在
        if (!FileUtil.exist(templatePath)) {
            FileUtil.mkdir(templatePath);
            //文件夹不存在，需要将源文件拷贝到工作空间
            FileUtil.copy(originProjectPath, templatePath, true);
        }

        //一 输入信息

        //源文件目录
        String sourceRootPath = FileUtil.loopFiles(new File(templatePath), 1, null)
                .stream()
                .filter(File::isDirectory)
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getAbsolutePath();

        sourceRootPath = sourceRootPath.replaceAll("\\\\", "/");

        //制作模板
        List<Meta.FileConfig.FileInfo> newFileInfoList = makeFileTemplates(templateMakerFileConfig, templateMakerModelConfig, sourceRootPath);
        //处理模型信息
        List<Meta.ModelConfig.ModelInfo> newModelInfoList = getModelInfoList(templateMakerModelConfig);

        // 三 生成 meta.json
        String metaOutputPath = templatePath + File.separator + "meta.json";
        if (FileUtil.exist(metaOutputPath)) {
            //meta.json 存在追加配置
            Meta oldMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaOutputPath), Meta.class);
            //
            BeanUtil.copyProperties(newMeta, oldMeta, CopyOptions.create().ignoreNullValue());
            newMeta = oldMeta;
            //追加file
            List<Meta.FileConfig.FileInfo> fileInfoList = newMeta.getFileConfig().getFiles();
            fileInfoList.addAll(newFileInfoList);
            //追加model
            List<Meta.ModelConfig.ModelInfo> modelInfoList = newMeta.getModelConfig().getModels();
            modelInfoList.addAll(newModelInfoList);

            //配置去重
            newMeta.getFileConfig().setFiles(distinctFiles(fileInfoList));
            newMeta.getModelConfig().setModels(distinctModels(modelInfoList));
        } else {
            Meta.FileConfig fileConfig = new Meta.FileConfig();
            newMeta.setFileConfig(fileConfig);
            fileConfig.setSourceRootPath(sourceRootPath);
            List<Meta.FileConfig.FileInfo> fileInfoList = new ArrayList<>();
            fileConfig.setFiles(fileInfoList);
            fileInfoList.addAll(newFileInfoList);

            List<Meta.ModelConfig.ModelInfo> modelInfoList = new ArrayList<>();
            Meta.ModelConfig modelConfig = new Meta.ModelConfig();
            modelInfoList.addAll(newModelInfoList);
            modelConfig.setModels(modelInfoList);
            newMeta.setModelConfig(modelConfig);

        }

        //额外输出配置
        if (templateMakerOutputConfig != null) {
            // 文件外层和分组去重
            if (templateMakerOutputConfig.isRemoveGroupFilesFromRoot()) {
                List<Meta.FileConfig.FileInfo> fileInfoList = newMeta.getFileConfig().getFiles();
                newMeta.getFileConfig().setFiles(TemplateMakerUtils.removeGroupFilesFromRoot(fileInfoList));
            }
        }

        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta), metaOutputPath);
        return id;
    }

    /**
     * @param templateMakerModelConfig
     * @return
     */
    private static List<Meta.ModelConfig.ModelInfo> getModelInfoList(TemplateMakerModelConfig templateMakerModelConfig) {
        List<Meta.ModelConfig.ModelInfo> newModelInfoList = new ArrayList<>();

        if (templateMakerModelConfig == null) {
            return newModelInfoList;
        }
        //处理模型信息
        List<TemplateMakerModelConfig.ModelInfoConfig> models = templateMakerModelConfig.getModels();
        if (CollUtil.isEmpty(models)) {
            return newModelInfoList;
        }

        //转换为可以接受的模型参数对象
        List<Meta.ModelConfig.ModelInfo> inputModelInfoList = models.stream().map(modelInfoConfig -> {
                    Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
                    String description = modelInfoConfig.getDescription();
                    BeanUtil.copyProperties(modelInfoConfig, modelInfo);
                    modelInfo.setDescription(description);
                    return modelInfo;
                }
        ).collect(Collectors.toList());

        //本次新增的模型配置
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        if (modelGroupConfig != null) {

            Meta.ModelConfig.ModelInfo groupModelInfo = new Meta.ModelConfig.ModelInfo();
            BeanUtil.copyProperties(modelGroupConfig,groupModelInfo);
            //同一组放到一起
            groupModelInfo.setModels(inputModelInfoList);
            newModelInfoList.add(groupModelInfo);
        } else {
            //不分组的都放一起
            newModelInfoList.addAll(inputModelInfoList);
        }
        return newModelInfoList;
    }

    /**
     * @param templateMakerFileConfig
     * @param templateMakerModelConfig
     * @param sourceRootPath
     * @return
     */
    private static List<Meta.FileConfig.FileInfo> makeFileTemplates(TemplateMakerFileConfig templateMakerFileConfig,
                                                                    TemplateMakerModelConfig templateMakerModelConfig,
                                                                    String sourceRootPath) {

        List<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>();
        if (templateMakerFileConfig == null) {
            return newFileInfoList;
        }
        List<TemplateMakerFileConfig.FileInfoConfig> fileInfoConfigList = templateMakerFileConfig.getFiles();
        if (CollUtil.isEmpty(fileInfoConfigList)) {
            return newFileInfoList;
        }

        for (TemplateMakerFileConfig.FileInfoConfig fileInfoConfig : fileInfoConfigList) {
            String inputFilePath = fileInfoConfig.getPath();

            if (!inputFilePath.startsWith(sourceRootPath)) {
                inputFilePath = sourceRootPath + File.separator + inputFilePath;
            }
            //获取过滤后的文件（不存在目录）
            List<File> fileList = FileFilter.doFilter(inputFilePath, fileInfoConfig.getFilterConfigList());

            //如果存在.ftl文件，排除掉
            fileList = fileList.stream().filter(file -> !file.getAbsolutePath().endsWith(".ftl")).collect(Collectors.toList());

            for (File file : fileList) {
                // todo
                Meta.FileConfig.FileInfo fileInfo = makeFileTemplate(templateMakerModelConfig, sourceRootPath, file, fileInfoConfig);
                newFileInfoList.add(fileInfo);
            }
        }

        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = templateMakerFileConfig.getFileGroupConfig();
        if (fileGroupConfig != null) {
            String groupKey = fileGroupConfig.getGroupKey();
            String groupName = fileGroupConfig.getGroupName();
            String condition = fileGroupConfig.getCondition();

            //新增分组配置
            Meta.FileConfig.FileInfo groupFileInfo = new Meta.FileConfig.FileInfo();
            groupFileInfo.setType(FileTypeEnum.GROUP.getValue());
            groupFileInfo.setCondition(condition);
            groupFileInfo.setGroupKey(groupKey);
            groupFileInfo.setGroupName(groupName);
            //文件放在同一组下
            groupFileInfo.setFiles(newFileInfoList);
            newFileInfoList = new ArrayList<>();
            newFileInfoList.add(groupFileInfo);
        }
        return newFileInfoList;
    }

    /**
     * 文件去重
     *
     * @param fileInfoList
     * @return
     */
    public static List<Meta.FileConfig.FileInfo> distinctFiles(List<Meta.FileConfig.FileInfo> fileInfoList) {
        //同组合并去重，不同组追加保留
        //1. 以组为单位
        Map<String, List<Meta.FileConfig.FileInfo>> groupKeyFileInfoListMap = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
                .collect(
                        Collectors.groupingBy(Meta.FileConfig.FileInfo::getGroupKey)
                );
        //2. 同组文件配置合并
        Map<String, Meta.FileConfig.FileInfo> groupKeyMergedFileInfoListMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.FileConfig.FileInfo>> entry : groupKeyFileInfoListMap.entrySet()) {
            List<Meta.FileConfig.FileInfo> tempFileInfoList = entry.getValue();
            List<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>(tempFileInfoList.stream()
                    .flatMap(fileInfo -> fileInfo.getFiles().stream())
                    .collect(Collectors.toMap(Meta.FileConfig.FileInfo::getOutputPath, o -> o, (e, r) -> r))
                    .values());
            //使用新的配置,覆盖旧的配置
            Meta.FileConfig.FileInfo newFileInfo = CollUtil.getLast(tempFileInfoList);
            newFileInfo.setFiles(newFileInfoList);
            String groupKey = entry.getKey();
            groupKeyMergedFileInfoListMap.put(groupKey, newFileInfo);
        }
        //3.将文件分组添加到结果列表
        List<Meta.FileConfig.FileInfo> result = new ArrayList<>(groupKeyMergedFileInfoListMap.values());

        //4.将未分组的文件添加到结果列表
        List<Meta.FileConfig.FileInfo> noGroupFileInfoList = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isBlank(fileInfo.getGroupKey()))
                .collect(Collectors.toList());
        result.addAll(new ArrayList<>(noGroupFileInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.FileConfig.FileInfo::getInputPath, o -> o, (e, r) -> r))
                .values()));

        return result;
    }

    /**
     * 模型去重
     *
     * @param modelInfoList
     * @return
     */
    public static List<Meta.ModelConfig.ModelInfo> distinctModels(List<Meta.ModelConfig.ModelInfo> modelInfoList) {
        //同组合并去重，不同组追加保留
        //1. 以组为单位
        Map<String, List<Meta.ModelConfig.ModelInfo>> groupKeyModelInfoListMap = modelInfoList.stream()
                .filter(modelInfo -> StrUtil.isNotBlank(modelInfo.getGroupKey()))
                .collect(
                        Collectors.groupingBy(Meta.ModelConfig.ModelInfo::getGroupKey)
                );
        //2. 同组模型配置合并
        Map<String, Meta.ModelConfig.ModelInfo> groupKeyMergedModelInfoListMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.ModelConfig.ModelInfo>> entry : groupKeyModelInfoListMap.entrySet()) {
            List<Meta.ModelConfig.ModelInfo> tempModelInfoList = entry.getValue();
            List<Meta.ModelConfig.ModelInfo> newModelInfoList = new ArrayList<>(tempModelInfoList.stream()
                    .flatMap(modelInfo -> modelInfo.getModels().stream())
                    .collect(Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName, o -> o, (e, r) -> r))
                    .values());
            //使用新的配置,覆盖旧的配置
            Meta.ModelConfig.ModelInfo newModelInfo = CollUtil.getLast(tempModelInfoList);
            newModelInfo.setModels(newModelInfoList);
            String groupKey = entry.getKey();
            groupKeyMergedModelInfoListMap.put(groupKey, newModelInfo);
        }
        //3.将模型分组添加到结果列表
        List<Meta.ModelConfig.ModelInfo> result = new ArrayList<>(groupKeyMergedModelInfoListMap.values());

        //4.将未分组的模型添加到结果列表
        List<Meta.ModelConfig.ModelInfo> noGroupModelInfoList = modelInfoList.stream()
                .filter(modelInfo -> StrUtil.isBlank(modelInfo.getGroupKey()))
                .collect(Collectors.toList());
        result.addAll(new ArrayList<>(noGroupModelInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName, o -> o, (e, r) -> r))
                .values()));

        return result;
    }

    /**
     * 文件模板制作
     *
     * @param templateMakerModelConfig 模型参数信息
     * @param sourceRootPath           文件源路径（绝对路径）
     * @param inputFile                输入文件路径
     * @return 文件模板
     */
    public static Meta.FileConfig.FileInfo makeFileTemplate(TemplateMakerModelConfig templateMakerModelConfig,
                                                            String sourceRootPath,
                                                            File inputFile,
                                                            TemplateMakerFileConfig.FileInfoConfig fileInfoConfig) {
        //一 输入信息
        //要挖坑文件的绝对路径
        //window盘符路径不同，转义
        String fileInputAbsolutePath = inputFile.getAbsolutePath().replaceAll("\\\\", "/");
        String fileOutputAbsolutePath = fileInputAbsolutePath + ".ftl";
        //文件输入输出的相对路径                              把文件绝对路径的前面用空白替换掉转成相对路径
        String fileInputPath = fileInputAbsolutePath.replace(sourceRootPath + "/", "");
        String fileOutputPath = fileInputPath + ".ftl";

        String fileContent = null;
        boolean hasTemplate = FileUtil.exist(fileOutputAbsolutePath);
        if (hasTemplate) {
            //已有项目模板文件，只需在改模板文件上追加配置即可
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        } else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }

        //支持多个模型：对同一个文件的内容，遍历模型进行多轮替换
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        String newFileContent = fileContent;
        String replacement;
        for (TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig : templateMakerModelConfig.getModels()) {
            //不是分组
            if (modelGroupConfig == null) {
                replacement = String.format("${%s}", modelInfoConfig.getFieldName());
            } else {//是分组
                String groupKey = modelGroupConfig.getGroupKey();
                //需要多挖一个层级
                replacement = String.format("${%s.%s}", groupKey, modelInfoConfig.getFieldName());
            }
            //多次替换
            newFileContent = StrUtil.replace(newFileContent, modelInfoConfig.getReplaceText(), replacement);
        }

        //文件配置信息
        Meta.FileConfig.FileInfo fileInfo = new Meta.FileConfig.FileInfo();
        fileInfo.setInputPath(fileOutputPath);
        fileInfo.setOutputPath(fileInputPath);
        fileInfo.setCondition(fileInfoConfig.getCondition());
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());

        boolean contentEquals = newFileContent.equals(fileContent);

        if (!hasTemplate) {
            //和原文件一致,并且没有生成过模板为静态文件
            if (contentEquals) {
                fileInfo.setInputPath(fileInputPath);
                fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
            } else {
                //没有动态模板但是有文件修改为动态文件
                FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);
            }
        } else if (!contentEquals) {//否则有模板文件，并且有内容修改，也为动态文件
            FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);
        }

        return fileInfo;
    }

}
