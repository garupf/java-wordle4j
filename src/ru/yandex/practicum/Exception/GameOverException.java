package ru.yandex.practicum.Exception;

public class GameOverException extends WordleException {
    public GameOverException() {
        super("Игра окончена");
    }
}
