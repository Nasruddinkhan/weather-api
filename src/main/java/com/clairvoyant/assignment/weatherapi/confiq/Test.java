package com.clairvoyant.assignment.weatherapi.confiq;

public class Test {

    private static void main(String[] args) {
        int max = 400100;
        int min = 400000;
        int random_int = (int) (Math.random() * (max-min+1) + min);
        System.out.println(random_int);
    }
}
