package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Exception.GameOverException;
import ru.yandex.practicum.Exception.InvalidWordLengthException;
import ru.yandex.practicum.Exception.WordNotInDictionaryException;

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

    @Test
    void shouldThrowInvalidWordLengthException() {
        List<String> words = List.of("паста", "кошка", "банан");
        WordleDictionary dictionary = new WordleDictionary(words);
        WordleGame game = new WordleGame("паста", 6, dictionary);
        assertThrows(InvalidWordLengthException.class, () -> game.makeGuess("кот"));
    }

    @Test
    void shouldThrowWordNotInDictionaryException() {
        List<String> words = List.of("паста", "кошка", "банан");
        WordleDictionary dictionary = new WordleDictionary(words);
        WordleGame game = new WordleGame("паста", 6, dictionary);
        assertThrows(WordNotInDictionaryException.class, () -> game.makeGuess("топот"));
    }

    @Test
    void shouldThrowGameOverException () {
        List<String> words = List.of("паста", "кошка", "банан");
        WordleDictionary dictionary = new WordleDictionary(words);
        WordleGame game = new WordleGame("паста", 1, dictionary);
        game.makeGuess("кошка");
        assertThrows(GameOverException.class, () -> game.makeGuess("банан"));
    }

    @Test
    void shouldThrowGameIsWin () {
        List<String> words = List.of("паста", "кошка", "банан");
        WordleDictionary dictionary = new WordleDictionary(words);
        WordleGame game = new WordleGame("паста", 6, dictionary);
        game.makeGuess("паста");
        assertTrue(game.isWin());
    }

    @Test
    void shouldAnalyzeGuessCorrectly() {
        List<String> words = List.of("паста", "табор", "банан");
        WordleDictionary dictionary = new WordleDictionary(words);
        WordleGame game = new WordleGame("паста", 6, dictionary);
        WordleGame.GuessResult result = game.makeGuess("табор");

        LetterResult[] expected = {
                LetterResult.PRESENT,
                LetterResult.CORRECT,
                LetterResult.ABSENT,
                LetterResult.ABSENT,
                LetterResult.ABSENT
        };
        assertArrayEquals(expected, result.getResult());
    }

    @Test
    void shouldRepeatedLettersCorrectly() {
        List<String> words = List.of("паста", "ааабб");
        WordleDictionary dictionary = new WordleDictionary(words);
        WordleGame game = new WordleGame("паста", 6, dictionary);
        WordleGame.GuessResult result = game.makeGuess("ааабб");

        LetterResult[] expected = {
                LetterResult.PRESENT,
                LetterResult.CORRECT,
                LetterResult.ABSENT,
                LetterResult.ABSENT,
                LetterResult.ABSENT
        };
        assertArrayEquals(expected, result.getResult());
    }

    @Test
    void shouldReturnWordFromDictionary() {
        List<String> words = List.of("паста", "табор", "банан");
        WordleDictionary dictionary = new WordleDictionary(words);
        WordleGame game = new WordleGame("паста", 6, dictionary);
        String result = game.suggestWord();
        assertTrue(words.contains(result));
    }

    @Test
    void shouldSuggestWordBasedOnPreviousGuess() {
        List<String> words = List.of("табор", "паста");
        WordleDictionary dictionary = new WordleDictionary(words);
        WordleGame game = new WordleGame("паста", 6, dictionary);

        game.makeGuess("табор");

        String result = game.suggestWord();
        assertEquals("паста", result);
    }

    @Test
    void shouldReturnNull () {
        List<String> words = List.of("табор");
        WordleDictionary dictionary = new WordleDictionary(words);
        WordleGame game = new WordleGame("паста", 6, dictionary);

        game.makeGuess("табор");
        String result = game.suggestWord();
        assertNull(result);
    }

    @Test
    void shouldReturnGameState () {
        List<String> words = List.of("паста", "табор", "банан");
        WordleDictionary dictionary = new WordleDictionary(words);
        WordleGame game = new WordleGame("паста", 6, dictionary);

        game.makeGuess("табор");
        game.makeGuess("паста");

        List<WordleGame.GuessResult> state = game.getGameState();

        assertEquals(2, state.size());
        assertEquals(state.get(0).getGuess(), "табор");
        assertEquals(state.get(1).getGuess(), "паста");
    }

    @Test
    void shouldContainsWordInDictionary () {
        List<String> words = List.of("паста", "табор", "банан");
        WordleDictionary dictionary = new WordleDictionary(words);

        assertTrue(dictionary.contains("паста"));
        assertFalse(dictionary.contains("топот"));
    }

    @Test
    void shouldReturnDictionarySize() {
        List<String> words = List.of("паста", "табор", "банан");
        WordleDictionary dictionary = new WordleDictionary(words);
        assertEquals(3, dictionary.size());
    }

    @Test
    void shouldReturnRandomWord() {
        List<String> words = List.of("паста", "табор", "банан");
        WordleDictionary dictionary = new WordleDictionary(words);
        String result = dictionary.getRandomWord();;
        assertTrue(words.contains(result));
    }
}
