package ru.yandex.practicum.Exception;

public class WordNotInDictionaryException extends RuntimeException {
    public WordNotInDictionaryException(String guess) {
        super("Слово отсутствует в словаре:" + guess);
    }
}
