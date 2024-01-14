package com.gyc.maker.template;

import cn.hutool.core.util.StrUtil;
import com.gyc.maker.meta.Meta;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: TemplateMakerUtils
 * Package: com.gyc.maker.template
 * Description: 文件制作模板工具类
 *
 * @Author gyc
 * @Create 2023/12/26 17:03
 * @Version 1.0
 */
public class TemplateMakerUtils {

    //从未分组的文件中移除在分组文件中同名的文件
    public static List<Meta.FileConfig.FileInfo> removeGroupFilesFromRoot(List<Meta.FileConfig.FileInfo> fileInfoList) {
        //获取所有分组
        List<Meta.FileConfig.FileInfo> groupFileInfoList = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
                .collect(Collectors.toList());
        //获取所有分组中的文件列表
        List<Meta.FileConfig.FileInfo> groupInnerFileInfoList = groupFileInfoList.stream()
                .flatMap(fileInfo -> fileInfo.getFiles().stream())
                .collect(Collectors.toList());

        //获取所有分组文件内的文件输入路径集合
        Set<String> fileInputPathSet = groupInnerFileInfoList.stream()
                .map(Meta.FileConfig.FileInfo::getInputPath)
                .collect(Collectors.toSet());
        //移除所有在set集合中的路径
        return fileInfoList
                .stream()
                .filter(fileInfo -> !fileInputPathSet.contains(fileInfo.getInputPath()))
                .collect(Collectors.toList());
    }

}
