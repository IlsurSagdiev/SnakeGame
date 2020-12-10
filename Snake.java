package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<GameObject> snakeParts = new ArrayList<>();  // части тела которые будут в списке
    private final static String HEAD_SIGN = "\uD83D\uDC7E";   // отрисовка головы
    private final static String BODY_SIGN = "\u26AB";         // отрисовка туловища

    public boolean isAlive = true;                            // Эй, Горыныч, ты еще жив?))))

    private Direction direction = Direction.LEFT;             // на старте движ идет в ЛЕВО)


    public void setDirection(Direction direction) {           // сеттер для ЕНАМЧИКА, с проверкой что мы не поворачиваем на 180 град
        if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT)
                && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        }
        if ((this.direction == Direction.UP || this.direction == Direction.DOWN) &&
                snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        }


        if (this.direction == Direction.UP && direction == Direction.DOWN) return;
        else if (this.direction == Direction.DOWN && direction == Direction.UP) return;
        else if (this.direction == Direction.LEFT && direction == Direction.RIGHT) return;
        else if (this.direction == Direction.RIGHT && direction == Direction.LEFT) return;
        this.direction = direction;
    }

    public Snake(int x, int y) {                              // объект змея
        GameObject gameObject1 = new GameObject(x, y);
        GameObject gameObject2 = new GameObject(x + 1, y);
        GameObject gameObject3 = new GameObject(x + 2, y);
        snakeParts.add(gameObject1);
        snakeParts.add(gameObject2);
        snakeParts.add(gameObject3);
    }

    public int getLength() {                                  // размер змеи
        return snakeParts.size();
    }

    public GameObject createNewHead() {                     // изменения головы во время движения
        GameObject oldHead = snakeParts.get(0);
        if (direction == Direction.LEFT) {
            return new GameObject(oldHead.x - 1, oldHead.y);
        } else if (direction == Direction.RIGHT) {
            return new GameObject(oldHead.x + 1, oldHead.y);
        } else if (direction == Direction.UP) {
            return new GameObject(oldHead.x, oldHead.y - 1);
        } else {
            return new GameObject(oldHead.x, oldHead.y + 1);
        }
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    } // удаление хвоста после движения

    public void move(Apple apple) {                                // движ змеи
        GameObject newHead = createNewHead();           // содаем новую голову
        if (checkCollision(newHead)) {                  // чекаем самосъедение)))
            isAlive = false;
            return;
        }

        if (apple.x == newHead.x && apple.y == newHead.y) {         // если яблоко и змея на одних координатах , то яблоко RIP
            apple.isAlive = false;
            ;
        }
        if (newHead.x >= SnakeGame.WIDTH                // если выходим за пределы поля, то змея RIP
                || newHead.x < 0
                || newHead.y >= SnakeGame.HEIGHT
                || newHead.y < 0) {
            isAlive = false;
            return;
        }
        snakeParts.add(0, newHead);                  // добавляем голову на верх списка
        if (apple.isAlive == true) {
            removeTail();                                   // ремувим хвост
        }
    }

    public void draw(Game game) {                               // метод прорисовки
        Color color = (isAlive ? Color.BLACK : Color.RED);      // цвет нашей змейки, если жива то Черная) если RIP то Красная
        for (int i = 0; i < snakeParts.size(); i++) {           // перебираем части тела
            GameObject part = snakeParts.get(i);                // создаем часть тела
            String sign = (i != 0) ? BODY_SIGN : HEAD_SIGN;     // если часть тела первая в списке, то присваиваем ей голову,иначе присваиваем тело)
//            game.setCellValue(part.x, part.y, sign);            // рисуем змейку)
            game.setCellValueEx(part.x, part.y, Color.NONE, sign, color, 75);
        }
    }

    public boolean checkCollision(GameObject gameObject) {      // чек на самосъедение)))
        boolean check = false;
        for (GameObject sp : snakeParts) {
            if (sp.x == gameObject.x && sp.y == gameObject.y) {
                check = true;
            }
        }
        return check;
    }
}
