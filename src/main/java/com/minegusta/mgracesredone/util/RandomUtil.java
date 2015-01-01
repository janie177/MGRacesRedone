package com.minegusta.mgracesredone.util;


import java.util.Random;

public class RandomUtil
{
    private static Random rand = new Random();

    public static boolean chance(int percentage)
    {
        return rand.nextInt(100) > percentage;
    }

    public static boolean fiftyfifty()
    {
        return rand.nextBoolean();
    }

    public static int randomNumber(int max)
    {
        return rand.nextInt(max) + 1;
    }
}
