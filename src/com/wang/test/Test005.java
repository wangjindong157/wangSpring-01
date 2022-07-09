package com.wang.test;

import java.util.Scanner;

public class Test005 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] sn = scanner.nextLine().split(" ");
        int n = Integer.valueOf(sn[0]);
        int s = Integer.valueOf(sn[1]);
        String w = scanner.nextLine();
        String[] ws = w.split(" ");

        int[][] dp = new int[n + 5][s + 5];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= s; j++) {
                Integer curW = Integer.valueOf(ws[i - 1]);
                int max = dp[i - 1][j];
                if (j - curW >= 0) {
                    max = max > dp[i - 1][j - curW] + curW ? max : dp[i - 1][j - curW] + curW;
                }
                dp[i][j] = max;
            }
        }
        String result = dp[n][s] == s ? "YES" : "NO";
        System.out.println(result);
        //7 15
        //1 4 3 4 5 2 7

        //4 38
        //20 24 24 44

        //9 81
        //8 37 29 19 35 49 9 34 8
    }
}
