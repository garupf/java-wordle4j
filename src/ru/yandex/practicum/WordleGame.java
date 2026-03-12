package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;
    private WordleDictionary dictionary;

    private int maxSteps;
    private int currentStep;

    private List<String> guesses;
    private List<GuessResult> results;

    public WordleGame(String answer, int steps, WordleDictionary dictionary) {
        this.answer = answer;
        this.dictionary = dictionary;
        this.currentStep = 0;
        this.guesses = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    public GuessResult makeGuess(String guess) {

        checkGameOver();
        checkWordLength(guess);
        checkDictionary(guess);

        GuessResult result = analyzeGuess(guess);

        guesses.add(guess);
        results.add(result);

        currentStep++;
        return result;
    }

    private GuessResult analyzeGuess(String guess);

    public enum LetterResult {
        CORRECT,
        PRESENT,
        ABSENT
    }

    public class suggestWord {

    }

    public boolean isGameOver() {
        return currentStep >= maxSteps || isWin();
    }
}
