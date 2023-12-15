package ${basePackage}.generator;

import cn.hutool.core.io.FileUtil;

/**
 * ClassName: StaticGenerator
 * Package: ${basePackage}.generator
 * Description:
 *
 * @Author ${author}
 * @Create ${createTime}
 * @Version ${version}
 */
public class StaticGenerator {

    public static void copyStaticByHutools(String res, String des){
        FileUtil.copy(res, des,false);
    }
}
