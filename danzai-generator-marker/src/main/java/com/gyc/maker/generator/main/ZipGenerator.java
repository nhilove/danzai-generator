package com.gyc.maker.generator.main;

/**
 * ClassName: ZipGenerator
 * Package: com.gyc.maker.generator.main
 * Description:
 *
 * @Author gyc
 * @Create 2024/1/8 19:33
 * @Version 1.0
 */
public class ZipGenerator extends MainTemplate{


    @Override
    protected String buildDist(String outputPath, String sourceRootPath, String shellOutputPath, String jarPath) {
        String distPath = super.buildDist(outputPath, sourceRootPath, shellOutputPath, jarPath);
        return super.buildZip(distPath);

    }
}
