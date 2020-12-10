package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class Apple extends GameObject {                            //объект яблоко

    private static final String APPLE_SIGN = "\uD83C\uDF4E";       // отрисовка яблока

    public boolean isAlive = true;                                 // меня еще не съели

    public Apple(int x, int y) {
        super(x, y);
    }

    public void draw(Game game) {                   //метод прорисовки яблока
        game.setCellValueEx(x, y, Color.NONE, APPLE_SIGN, Color.DARKRED,75);
    }



}