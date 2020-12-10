package com.javarush.games.snake;

import com.javarush.engine.cell.*; // пакет Движуха


public class SnakeGame extends Game {               // отрисовка игры
    public static final int WIDTH = 15;             // размеры поля
    public static final int HEIGHT = 15;            // размеры поля
    private Snake snake;                            // Змей Горыныч!)))
    private int turnDelay;                          // таймер хода
    private Apple apple;                            // яблоко
    private boolean isGameStopped;                  // хранение состояние игры
    private final static int GOAL = 28;             // Победа
    private int score;                              // подсчет очков


    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        score = 0;
        setScore(score);
        turnDelay = 300;                                   // задаем скорость движа
        setTurnTimer(turnDelay);
        snake = new Snake(WIDTH / 2, HEIGHT / 2);    // создаем змейку
        createNewApple();
        isGameStopped = false;                             // игра остановлена?
        drawScene();
    }

    private void drawScene() {                             // метод прорисовки игрового поля
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createNewApple() {                       // рандомное появление яблока
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            apple = new Apple(x, y);
        } while (snake.checkCollision(apple));             // не дадим создаться новому яблоку на теле змия)

    }

    private void gameOver() {                       // GAME OVER!
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.RED, "Rest In Peace DUDE!!!", Color.WHITE, 70);
    }

    private void win() {                             //WIN Message
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "YOU WIN BROOOOO!!!", Color.YELLOW, 70);

    }

    @Override
    public void onTurn(int step) {                       // движем змея) Описываем логику
        snake.move(apple);                               // змей шагнул
        if (!apple.isAlive) {                            // если яблоко RIP то
            score += 5;                                  // увеличиваем очки
            setScore(score);
            turnDelay -= 10;                             // уменьшаем обновление экрана
            setTurnTimer(turnDelay);
        }
        if (!apple.isAlive) {                            // если яблоко RIP, создаем новое)
            createNewApple();
        }
        if (snake.isAlive == false) {                    // если змея RIP, то GAME OVER DUDE!!!
            gameOver();
        }
        if (snake.getLength() > GOAL) {                   // если мы набрали кучу яблок, то WIN
            win();
        }

        drawScene();                                  // перерисовка экрана
    }

    @Override
    public void onKeyPress(Key key) {               // метод определения направления движений
        if (key == key.UP) {
            snake.setDirection(Direction.UP);
        } else if (key == key.LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (key == key.DOWN) {
            snake.setDirection(Direction.DOWN);
        } else if (key == key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (key == key.SPACE && isGameStopped == true) {         // если SPACE и игра стопнутая то создаем новую игру
            createGame();
        }
    }
}