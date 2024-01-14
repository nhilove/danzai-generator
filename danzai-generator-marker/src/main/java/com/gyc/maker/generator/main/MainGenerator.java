package com.gyc.maker.generator.main;

public class MainGenerator extends MainTemplate{

    /**
     * 生成精简版的程序
     *
     * @param outputPath
     * @param sourceRootPath
     * @param shellOutputPath
     * @param jarPath
     */
    @Override
    protected String buildDist(String outputPath, String sourceRootPath, String shellOutputPath, String jarPath) {
        System.out.println("我不要精简版");
        return outputPath;
    }
}