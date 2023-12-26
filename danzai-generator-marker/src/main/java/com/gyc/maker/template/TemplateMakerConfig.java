package com.gyc.maker.template;

import com.gyc.maker.meta.Meta;
import com.gyc.maker.template.model.TemplateMakerFileConfig;
import com.gyc.maker.template.model.TemplateMakerModelConfig;
import com.gyc.maker.template.model.TemplateMakerOutputConfig;
import lombok.Data;

/**
 * ClassName: TemplateMakerConfig
 * Package: com.gyc.maker
 * Description: 模板制作的配置相关信息
 *
 * @Author gyc
 * @Create 2023/12/26 11:52
 * @Version 1.0
 */
@Data
public class TemplateMakerConfig {

    private long id;

    private Meta meta = new Meta();

    private TemplateMakerFileConfig fileConfig = new TemplateMakerFileConfig();

    private TemplateMakerModelConfig modelConfig = new TemplateMakerModelConfig();

    private String originProjectPath;

    private TemplateMakerOutputConfig outputConfig = new TemplateMakerOutputConfig();
}
