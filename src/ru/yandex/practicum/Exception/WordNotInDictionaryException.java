package ru.yandex.practicum.Exception;

public class WordNotInDictionaryException extends WordleException {
    public WordNotInDictionaryException(String guess) {
        super("Слово отсутствует в словаре:" + guess);
    }
}
