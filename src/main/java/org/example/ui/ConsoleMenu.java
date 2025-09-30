package org.example.ui;

import org.example.service.Library;

import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    Library library = new Library();

    public void start() {
        greeting();
        printMenu();
        while (true) {
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "0" -> System.exit(0);
                case "1" -> library.addBook();
                case "2" -> library.getAllBooks();
                case "3" -> library.getBookByID();
                case "4" -> library.getBookByTitle();
                case "5" -> library.getBookByAuthor();
                case "6" -> library.getBookByYear();
                case "7" -> library.addUser();
                case "8" -> library.getAllUsers();
                case "9" -> library.getUserByID();
                case "10" -> library.getUserByName();
                case "11" -> library.getUserByEmail();
                default -> defaultCommand();
            }
        }
    }

    void greeting() {
        System.out.println("Добро пожаловать в библиотеку!");
    }

    void printMenu() {
        System.out.println("""
                Выбери интересующую тебя функцию:
                1. Добавить новую книгу
                2. Просмотреть все книги
                3. Найти книгу по id
                4. Найти книгу по названию
                5. Найти книгу по автору
                6. Найти книгу по году
                7. Добавить нового пользователя
                8. Просмотреть всех пользователей
                9. Найти пользователя по ID
                10. Найти пользователя по имени
                11. Найти пользователя по email
                0. Выйти
                """);
    }

    void defaultCommand() {
        System.out.println("Команда не распознана. Выберите команду из списка");
        printMenu();
    }
}
