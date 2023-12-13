package com.gyc.acm;

import java.util.Scanner;
/**
* author ${author}
*/
class MainTemplate{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
<#if loop>
    while (scanner.hasNext()) {
</#if>

            int i = scanner.nextInt();
            int[] arr = new int[i];

            for (int j = 0; j < i; j++) {
                arr[j] = scanner.nextInt();
            }

            int sum = 0;
            for (int num: arr){
                sum += num;
            }
            System.out.println("${outputText}" + sum);
<#if loop>
        }
</#if>

        scanner.close();
    }
}