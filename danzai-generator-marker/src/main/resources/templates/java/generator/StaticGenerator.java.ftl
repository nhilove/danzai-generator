package templates.java.generator;

{basePackage}.generator;

import cn.hutool.core.io.FileUtil;

/**
 * ClassName: StaticGenerator
 * Package: ${basePackage}.generator
 * Description:
 *
 * @Author ${author}
 * @Create ${createTime}fffffffffffffffffffffff
 * @Version ${version}
 */
public class StaticGenerator {

    public static void copyStaticByHutools(String res, String des){
        FileUtil.copy(res, des,false);
    }
}
