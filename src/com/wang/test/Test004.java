package com.wang.test;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Test004 {
    public static void main(String[] args) {
        //test();
    }

    private static void test() {
        Scanner scanner = new Scanner(System.in);
        int sum = scanner.nextInt();
        int num = scanner.nextInt();
        scanner.nextLine();
        String die = scanner.nextLine();
        int bu = scanner.nextInt();
        String[] dies = die.split(" ");
        int[] diesInt = new int[dies.length];
        for (int i = 0; i < dies.length; i++) {
            diesInt[i] = Integer.valueOf(dies[i]);
        }
        int[] newNum = new int[dies.length + 1];
        for (int i = 0; i <= diesInt.length; i++) {
            if (i == 0) {
                newNum[i] = diesInt[i] - 1;
            } else if (i == diesInt.length) {
                newNum[i] = sum - diesInt[i - 1];
            } else {
                newNum[i] = diesInt[i] - diesInt[i - 1] - 1;
            }
        }
        int[] qian = new int[newNum.length];
        for (int i = 0; i < newNum.length; i++) {
            if (i == 0) {
                qian[i] = newNum[i];
            } else {
                qian[i] = qian[i - 1] + newNum[i];
            }
        }
        int max = qian[bu] + bu;
        for (int i = 1; i <= qian.length - bu - 1; i++) {
            int curMax = qian[i + bu] - qian[i - 1] + bu;
            max = curMax > max ? curMax : max;
        }
        System.out.println(max);
    }
}
