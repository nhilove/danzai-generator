package com.gyc.maker.template;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.gyc.maker.template.enums.FileFilterRangeEnum;
import com.gyc.maker.template.enums.FileFilterRuleEnum;
import com.gyc.maker.template.model.FileFilterConfig;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: FileFilter
 * Package: com.gyc.maker.template
 * Description: 文件过滤
 *
 * @Author gyc
 * @Create 2023/12/19 11:57
 * @Version 1.0
 */
public class FileFilter {

    /**
     * 单个文件过滤
     *
     * @param filterConfigList 过滤规则集合
     * @param file             源文件
     * @return 是否通过
     */
    public static boolean doSingleFileFilter(List<FileFilterConfig> filterConfigList, File file) {
        //根据文件名和文件内容判断
        String fileName = file.getName();
        String fileContent = FileUtil.readUtf8String(file);

        //所有过滤的结果
        boolean result = false;
        //没有过滤规则直接返回
        if (CollectionUtil.isEmpty(filterConfigList)) {
            return true;
        }
        for (FileFilterConfig filterConfig : filterConfigList) {
            String range = filterConfig.getRange();
            String rule = filterConfig.getRule();
            String value = filterConfig.getValue();

            //没有要匹配的内容直接跳过
            FileFilterRangeEnum fileFilterRangeEnum = FileFilterRangeEnum.getEnumByValue(range);
            if (fileFilterRangeEnum == null) {
                continue;
            }
            //将fileName 和 content 简化
            String content = fileName;
            switch (fileFilterRangeEnum) {
                case FILE_NAME:
                    content = fileName;
                    break;
                case FILE_CONTENT:
                    content = fileContent;
                    break;
                default:
            }

            FileFilterRuleEnum fileFilterRuleEnum = FileFilterRuleEnum.getEnumByValue(rule);
            if (fileFilterRuleEnum == null) {
                continue;
            }
            switch (fileFilterRuleEnum) {
                case CONTAINS:
                    result = content.contains(value);
                    break;
                case START_WITH:
                    result = content.startsWith(value);
                    break;
                case ENDS_WITH:
                    result = content.endsWith(value);
                    break;
                case REGEX:
                    result = content.matches(value);
                    break;
                case EQUALS:
                    result = content.equals(value);
                    break;
                default:
            }
            // 有一个不满足直接返回false
            if (!result){
                return false;
            }
        }
        //都满足
        return true;
    }

    /**
     * 对某个文件或目录进行过滤
     * @param filePath 文件路径
     * @param fileFilterConfigList 过滤规则集合
     * @return
     */
    public static List<File> doFilter(String filePath, List<FileFilterConfig> fileFilterConfigList){
        List<File> fileList = FileUtil.loopFiles(filePath);
        return fileList.stream().filter(file -> doSingleFileFilter(fileFilterConfigList,file))
                .collect(Collectors.toList());
    }
}
