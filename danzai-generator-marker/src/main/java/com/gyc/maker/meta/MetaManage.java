package com.gyc.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

public class MetaManage {

    private static volatile Meta meta;

    private MetaManage() {

    }

    public static Meta getMeta() {
        if (meta == null) {
            synchronized (Meta.class) {
                if (meta == null) {
                    meta = initMeta();
                }
            }
        }
        return meta;
    }

    private static Meta initMeta() {


        String metaJson = ResourceUtil.readUtf8Str("meta.json");
//        String metaJson = ResourceUtil.readUtf8Str("danzai-generator-web-backend-meta.json");
//                String metaJson = ResourceUtil.readUtf8Str("springboot-init-meta.json");

        Meta newMeta = JSONUtil.toBean(metaJson, Meta.class);
        // 校验
        MetaValidator.doValidateAndFill(newMeta);

        return newMeta;

    }
}