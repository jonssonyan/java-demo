package com.jonssonyan.基础算法.斐波那契数列;

public class Solution1 {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.print(solution1(i) + ",");
        }
    }

    static int solution1(int a) {
        if (a == 0 || a == 1) return 1;
        return solution1(a - 1) + solution1(a - 2);
    }
}
