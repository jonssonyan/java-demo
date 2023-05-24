package com.jonssonyan.基础算法.斐波那契数列;

public class Solution2 {
    public static void main(String[] args) {
        int[] mono = new int[20];
        for (int i = 0; i < 20; i++) {
            System.out.print(solution2(mono, i) + "\t");
        }
    }

    static int solution2(int[] mono, int a) {
        if (a == 0 || a == 1) return 1;
        if (mono[a] != 0) return mono[a];
        return solution2(mono, a - 1) + solution2(mono, a - 2);
    }
}
