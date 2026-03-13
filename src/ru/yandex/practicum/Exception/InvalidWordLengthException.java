package ru.yandex.practicum.Exception;

public class InvalidWordLengthException extends WordleException {
    public InvalidWordLengthException() {
        super("Неправильная длина слова");
    }
}
