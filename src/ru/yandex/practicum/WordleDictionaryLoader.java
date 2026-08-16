package ru.yandex.practicum;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    public WordleDictionaryLoader() {

    }

    public WordleDictionary load(String filePath) throws IOException {
        List<String> words = Files.readAllLines(Path.of("words_ru.txt"), StandardCharsets.UTF_8);

        List<String> fiveLetterWords = new ArrayList<>();

        for (String word : words) {
            if (word.length() == 5) {
                fiveLetterWords.add(word);
            }
        }
        return new WordleDictionary(fiveLetterWords);
    }


}
