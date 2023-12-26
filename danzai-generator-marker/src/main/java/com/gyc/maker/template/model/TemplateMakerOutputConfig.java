package com.gyc.maker.template.model;

import lombok.Data;

/**
 * ClassName: TemplateMakerOutputConfig
 * Package: com.gyc.maker.template.model
 * Description: 模板制作文件输出规则
 *
 * @Author gyc
 * @Create 2023/12/26 16:57
 * @Version 1.0
 */
@Data
public class TemplateMakerOutputConfig {

    //从未分组的文件中移除在分组文件中同名的文件
    private boolean removeGroupFilesFromRoot = true;

}
