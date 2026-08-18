package ru.yandex.practicum;

import ru.yandex.practicum.Exception.GameOverException;
import ru.yandex.practicum.Exception.InvalidWordLengthException;
import ru.yandex.practicum.Exception.WordNotInDictionaryException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.yandex.practicum.LetterResult.*;

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

    private final String answer;
    private final WordleDictionary dictionary;

    private final int maxSteps;
    private int currentStep;

    private final List<String> guesses;
    private final List<GuessResult> results;

    public WordleGame(String answer, int steps, WordleDictionary dictionary) {
        this.answer = answer;
        this.maxSteps = steps;
        this.dictionary = dictionary;
        this.currentStep = 0;
        this.guesses = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    public static class GuessResult {
        private final String guess;
        private final LetterResult[] result;

        public GuessResult(String guess, LetterResult[] result) {
            this.guess = guess;
            this.result = result;
        }

        public String getGuess() {
            return guess;
        }

        public LetterResult[] getResult() {
            return result;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            for (LetterResult letterResult : result) {
                if (letterResult == LetterResult.CORRECT){
                    stringBuilder.append('+');
                } else if (letterResult == LetterResult.PRESENT){
                    stringBuilder.append('^');
                } else if (letterResult == LetterResult.ABSENT)
                    stringBuilder.append('-');
            }
            return guess + " " + stringBuilder;
        }
    }

    public GuessResult makeGuess(String guess) {

        guess = guess.toLowerCase();
        guess = guess.replace("ё", "е");

        if (isGameOver()) {
            throw new GameOverException();
        }

        if (guess.length() != answer.length()) {
            throw new InvalidWordLengthException();
        }

        if (!dictionary.contains(guess)) {
            throw new WordNotInDictionaryException(guess);
        }

        GuessResult result = analyzeGuess(guess);

        guesses.add(guess);
        results.add(result);
        currentStep++;

        return result;
    }

    private GuessResult analyzeGuess(String guess) {
        int length = answer.length();
        LetterResult[] result = new LetterResult[length];
        boolean[] used = new boolean[length];

        for (int i = 0; i < length; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                result[i] = CORRECT;
                used[i] = true;
            }
        }

        for (int i = 0; i < length; i++) {
            if (result[i] != null) continue;

            char c = guess.charAt(i);
            boolean found = false;

            for (int j = 0; j < result.length; j++) {

                if (!used[j] && c == answer.charAt(j)) {
                    result[i] = LetterResult.PRESENT;
                    used[j] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                result[i] = LetterResult.ABSENT;
            }
        }
        return new GuessResult(guess, result);
    }

    public String suggestWord() {
        for (String guess : dictionary.getWords()) {
            if (matchesHistory(guess)) {
                return guess;
            }

        }
        return null;
    }

    private boolean matchesHistory(String word) {

        for (int i = 0; i < guesses.size(); i++) {

            String guess = guesses.get(i);
            GuessResult expected = results.get(i);

            GuessResult simulated =
                    analyzeGuessForWord(word, guess);

            if (!Arrays.equals(
                    simulated.getResult(),
                    expected.getResult()
            )) {
                return false;
            }
        }

        return true;
    }

    private GuessResult analyzeGuessForWord(String answerCandidate, String guess) {

        int length = answerCandidate.length();

        LetterResult[] result = new LetterResult[length];
        boolean[] used = new boolean[length];

        for (int i = 0; i < length; i++) {
            if (guess.charAt(i) == answerCandidate.charAt(i)) {
                result[i] = CORRECT;
                used[i] = true;
            }
        }

        for (int i = 0; i < length; i++) {

            if (result[i] != null) continue;

            char c = guess.charAt(i);
            boolean found = false;

            for (int j = 0; j < length; j++) {

                if (!used[j] && c == answerCandidate.charAt(j)) {
                    result[i] = LetterResult.PRESENT;
                    used[j] = true;
                    found = true;
                    break;
                }
            }

            if (!found) {
                result[i] = LetterResult.ABSENT;
            }
        }

        return new GuessResult(guess, result);
    }

    public boolean isWin() {
        return guesses.contains(answer);
    }

    public boolean isGameOver() {
        return isWin() || currentStep >= maxSteps;
    }

    public String getSecretWord() {
        return answer;
    }

    public List<GuessResult> getGameState() {
        return results;
    }
}
