package org.example.ui;

import org.example.service.Library;

import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    Library library = new Library();

    public void start() {
        greeting();
        printMenu();
    }

    void greeting() {
        System.out.println("Добро пожаловать в библиотеку!");
    }

    void printMenu() {
        System.out.println("""
                Выбери интересующую тебя функцию:
                1. Работа с книгами
                2. Работа с пользователем
                3. Выдача и возврат книг
                4. История и просрочки
                0. Выйти
                """);
        while (true) {
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> printBooksFunc();
                case "2" -> printUserFunc();
                case "3" -> printLoanFunc();
                case "4" -> printViewFunc();
                case "0" -> System.exit(0);
                default -> defaultCommand();
            }
        }
    }

        void printBooksFunc() {
                System.out.println("""
                        1. Добавить новую книгу
                        2. Просмотреть все книги
                        3. Найти книгу по id
                        4. Найти книгу по названию
                        5. Найти книгу по автору
                        6. Найти книгу по году
                        0. Выйти
                        """);
                while (true) {
                    String choice = scanner.nextLine().trim();
                    switch (choice) {
                        case "1" -> library.addBook();
                        case "2" -> library.getAllBooks();
                        case "3" -> library.getBookByID();
                        case "4" -> library.getBookByTitle();
                        case "5" -> library.getBookByAuthor();
                        case "6" -> library.getBookByYear();
                        case "0" -> printMenu();
                        default -> defaultCommand();
                    }
                }
            }


        void printUserFunc () {
            System.out.println("""
                    1. Добавить нового пользователя
                    2. Просмотреть всех пользователей
                    3. Найти пользователя по ID
                    4. Найти пользователя по имени
                    5. Найти пользователя по email
                    0. Выйти
                    """);
            while (true) {
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -> library.addUser();
                    case "2" -> library.getAllUsers();
                    case "3" -> library.getUserByID();
                    case "4" -> library.getUserByEmail();
                    case "0" -> printMenu();
                    default -> defaultCommand();
                }
            }
        }

        void printLoanFunc () {
            System.out.println("""
                    1. Выдать книгу пользователю
                    2. Вернуть книгу
                    0. Выйти
                    """);
            while (true) {
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -> library.issueBook();
                    case "2" -> library.returnBook();
                    case "0" -> printMenu();
                    default -> defaultCommand();
                }
            }
        }

        void printViewFunc () {
            System.out.println("""
                    1. Просмотреть историю выдачи всех книг
                    2. Просмотреть историю выдачи пользователя
                    3. Просмотреть историю выдачи книги
                    4. Просмотреть просроченные книги
                    0. Выйти
                    """);
            while (true) {
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -> library.getAllLoans();
                    case "2" -> library.getLoansByUser();
                    case "3" -> library.getLoansByBook();
                    case "4" -> library.getOverdueBooks();
                    case "0" -> printMenu();
                    default -> defaultCommand();
                }
            }
        }

        void defaultCommand () {
            System.out.println("Команда не распознана. Выберите команду из списка");
            printMenu();
        }
    }
