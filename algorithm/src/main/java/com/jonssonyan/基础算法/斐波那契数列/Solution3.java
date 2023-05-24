package com.jonssonyan.基础算法.斐波那契数列;

import java.util.Arrays;

public class Solution3 {
    public static void main(String[] args) {
        int[] dp = new int[20];
        dp[0] = dp[1] = 1;
        for (int i = 2; i < 20; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        System.out.println(Arrays.toString(dp));
    }
}
