package com.jonssonyan.数据结构.数组.两个数组的交集;

public class Solution2 {
    public static void main(String[] args) {
        int[] array1 = {1, 2, 2, 3};
        int[] array2 = {1, 2};
        solution2(array1, array2);
    }

    public static void solution2(int[] array1, int[] array2) {

        // 初始化指针
        int pointer1 = 0;
        int pointer2 = 0;

        while (pointer1 < array1.length && pointer2 < array2.length) {
            int temp1 = array1[pointer1];
            int temp2 = array2[pointer2];

            if (temp1 == temp2) {
                // 打印
                System.out.println(temp1);
                pointer1++;
                pointer2++;
            } else if (temp1 < temp2) { // 如果array1中元素比array2中元素小，则array1指针往后移一位
                pointer1++;
            } else {
                pointer2++;
            }
        }
    }
}
