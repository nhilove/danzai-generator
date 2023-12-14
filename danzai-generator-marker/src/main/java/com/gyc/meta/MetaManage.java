package com.gyc.meta;

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
        Meta newMeta = JSONUtil.toBean(metaJson, Meta.class);
        //todo 校验
        return newMeta;

    }
}