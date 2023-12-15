package com.gyc.maker.generator;

import java.io.*;

public class JarGenerator {

    public static void doGenerate(String projectPath) throws IOException, InterruptedException {
        String widowsCmd = "mvn.cmd clean package -DskipTests=true";
        String otherCmd = "mvn clean package -DskipTests=true";
        String cmd = widowsCmd;

        ProcessBuilder processBuilder = new ProcessBuilder(cmd.split(" "));
        processBuilder.directory(new File(projectPath));

        Process process = processBuilder.start();
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        int exitCode = process.waitFor();
        System.out.println("命令执行结束，退出码："+exitCode);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        doGenerate("D:\\Project\\yupi\\danzai\\danzai-generator\\danzai-generator-basic");
    }
}