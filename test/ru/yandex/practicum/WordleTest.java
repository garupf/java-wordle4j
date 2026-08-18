package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

class WordleTest {
    @Test
    void shouldReturnCorrectResultForCorrectGuess() {
        List<String> words = List.of("паста", "кошка", "банан");
        WordleDictionary dictionary = new WordleDictionary(words);
        WordleGame game = new WordleGame("паста", 6, dictionary);

        WordleGame.GuessResult result = game.makeGuess("паста");

        assertEquals("паста +++++", result.toString());
    }
}
