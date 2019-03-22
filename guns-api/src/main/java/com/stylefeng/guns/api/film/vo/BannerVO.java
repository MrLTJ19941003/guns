package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by liutj on 2019/3/6.
 */
@Data
public class BannerVO implements Serializable{

    private String bannerId;
    private String bannerAddress;
    private String bannerUrl;


//    public static void main(String[] args) {
//        int[] arr= new int[]{9,3,4,8,6,2,1};
////        3,4,8,6,2,1,9
////        3,4,6,2,1,8,9
////        3,4,2,1,6,8,9
////        3,2,1,4,6,8,9
////        2,1,3,4,6,8,9
////        1,2,3,4,6,8,9
//        for(int i= 1;i<arr.length;i++){
//            System.out.println(i);
//            for(int j = 1;j<arr.length-i+1;j++){
//                if(arr[j-1]>arr[j]) {
//                    int temp = arr[j-1];
//                    arr[j-1] = arr[j];
//                    arr[j] = temp;
//                }
//            }
//        }
//
//        for(int is : arr){
//            System.out.print(is + " ");
//        }
//    }

}
