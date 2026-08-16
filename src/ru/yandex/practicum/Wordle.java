package ru.yandex.practicum;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
@SuppressWarnings("ALL")
public class Wordle {

    private static final Logger logger = Logger.getLogger(Wordle.class.getName());

    public static void main(String[] args) {
        setupLogger();

        logger.info("Запуск приложения Wordle");

        // 1. Создаём загрузчик словаря
        WordleDictionaryLoader loader = new WordleDictionaryLoader();

        // 2. Загружаем словарь
        WordleDictionary dictionary;
        try {
            dictionary = loader.load("words.txt");
        } catch (IOException e) {
            logger.severe("Ошибка загрузки словаря: " + e.getMessage());
            System.out.println("Не удалось загрузить словарь.");
            return;
        }

        String answer = dictionary.getRandomWord();
        int steps = 6;

        // 3. Создаём игру
        WordleGame game = new WordleGame(answer, steps, dictionary);

        // 4. Запускаем игровой цикл
        runGameLoop(game);

        logger.info("Приложение завершено");
    }

    private static void setupLogger() {
        try {
            FileHandler fileHandler = new FileHandler("wordle.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.out.println("Не удалось создать лог-файл");
        }
    }

    private static void runGameLoop(WordleGame game) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в Wordle!");

        while (!game.isGameOver()) {
            System.out.println("Введите слово:");

            String input = scanner.nextLine();

            try {
                game.makeGuess(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                continue;
            }

            // Вывод состояния игры
            System.out.println(game.getGameState());
        }

        // Финальный результат
        if (game.isWin()) {
            System.out.println("Вы выиграли!");
        } else {
            System.out.println("Вы проиграли!");
            System.out.println("Загаданное слово: " + game.getSecretWord());
        }
    }
}