package ru.yandex.practicum;

import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words; // список всех слов словаря

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public int size() { //количество слов
        return words.size();
    }

    public boolean contains(String word) { // есть ли слово в словаре
        return words.contains(word);
    }

    public String getRandomWord() { // получить случайное слово
        Random rand = new Random();
        int index = rand.nextInt(words.size());
        return words.get(index);
    }

    public List<String> getWords() { // вернуть список слов
        return words;
    }


}
