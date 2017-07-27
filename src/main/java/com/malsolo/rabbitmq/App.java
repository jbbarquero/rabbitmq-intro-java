package com.malsolo.rabbitmq;

import java.util.Arrays;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n*2)
                .limit(2)
                .forEach(System.out::println);
    }
}
