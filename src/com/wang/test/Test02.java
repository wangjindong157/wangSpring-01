package com.wang.test;

import java.util.Scanner;

public class Test02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int sum = 1;
        System.out.println(n + "=" + n);
        for (int i = 2; i < n / 2 + 1; i++) {
            if (i % 2 == 1) {
                if (n % i == 0 && (n / i) - i / 2 > 0) {
                    int start = (n / i) - i / 2, end = (n / i) + i / 2;
                    String res = n + "=";
                    for (int j = start; j < end; j++) {
                        res += j + "+";
                    }
                    sum++;
                    res += end;
                    System.out.println(res);
                }
            } else {
                if ((n * 2) % i == 0 && n % i != 0) {
                    int start = n / i - i / 2 + 1, end = n / i + 1 + i / 2 - 1;
                    if (start < 1) {
                        continue;
                    }
                    String res = n + "=";
                    for (int j = start; j < end; j++) {
                        res += j + "+";
                    }
                    res += end;
                    sum++;
                    System.out.println(res);
                }
            }
        }
        System.out.println("Result:" + sum);
    }
}
