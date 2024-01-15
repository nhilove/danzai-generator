package com.yupi.web.model.dto.generator;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * ClassName: GeneratorUseRequest
 * Package: com.yupi.web.model.dto.generator
 * Description: 代码生成器请求
 *
 * @Author gyc
 * @Create 2024/1/15 11:15
 * @Version 1.0
 */
@Data
public class GeneratorUseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 生成器id
     */
    private long id;

    /**
     * 数据模型
     */
    private Map<String,Object> dataModel;

}
