package com.jonssonyan.基础算法.斐波那契数列;

public class Solution4 {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.print(solution4(i) + " ");
        }
    }

    static int solution4(int a) {
        if (a == 0 || a == 1) return 1;
        int pre = 1;
        int curr = 1;
        int sum;
        for (int i = 1; i < a; i++) {
            sum = pre + curr;
            pre = curr;
            curr = sum;
        }
        return curr;
    }
}
