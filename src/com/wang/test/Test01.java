package com.wang.test;

import java.util.Scanner;

public class Test01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int cur = 0;
        String curStr = "";
        for (int i = 0; i < s.length(); i++) {
            curStr += s.substring(i, i + 1);
            cur++;
            if (cur % 8 == 0) {
                System.out.println(curStr);
                cur = 0;
                curStr = "";
            }
        }
        if (!curStr.isEmpty()) {
            int strLength = curStr.length();
            for (int i = 0; i < 8 - strLength; i++) {
                curStr += "0";
            }
            System.out.println(curStr);
        }
    }
}
