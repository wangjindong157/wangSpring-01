package com.wang.test;

import com.wang.service.AppConfig;
import com.wang.service.UserInterface;
import com.wang.spring.WangApplicationContext;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        WangApplicationContext wangApplicationContext = new WangApplicationContext(AppConfig.class);

        UserInterface userService = (UserInterface) wangApplicationContext.getBean("userService");
        userService.test();
        
        //test();

        calculate();
    }

    private static void calculate() {
        String[] s = {"abch", "baz", "foo", "bar", "xtfy", "abcdef"};
        int max = 0;
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s.length; j++) {
                if (i == j) {
                    continue;
                }
                int[] mid = new int[26];
                for (int k = 0; k < s[i].length(); k++) {
                    mid[s[i].charAt(k) - 'a']++;
                }
                for (int k = 0; k < s[j].length(); k++) {
                    mid[s[j].charAt(k) - 'a']++;
                }
                boolean flag = true;
                for (int k = 0; k < 26; k++) {
                    if (mid[k] > 1) {
                        flag = false;
                    }
                }
                if (flag) {
                    int newNum = s[i].length() * s[j].length();
                    max = max > newNum ? max : newNum;
                }
            }
        }
        System.out.println(max);
    }

    private static void test() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] sum = new int[505];
        for (int i = 0; i < n; i++) {
            int num = scanner.nextInt();
            sum[num]++;
        }
        for (int i = 1; i <= 500; i++) {
            if (sum[i] > 0) {
                System.out.println(i);
            }
        }
    }

}
