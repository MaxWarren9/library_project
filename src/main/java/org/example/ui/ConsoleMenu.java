package org.example.ui;

import org.example.exception.BookNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.model.entity.Book;
import org.example.model.repository.BookRepository;
import org.example.model.repository.UserRepository;
import org.example.service.Library;
import org.example.util.Utils;

import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    BookRepository bookRepository = new BookRepository();
    UserRepository userRepository = new UserRepository();
    Library library = new Library();
    public boolean flag = true;

    public void start() {
        greeting();
        printMenu();
        while (flag) {
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "0":
                    flag = false;
                    System.out.println("Приложение завершило работу");
                    break;
                case "1":
                    System.out.println("Введите через запятую название книги, имя автора, год издания, количество копий и количество доступных копий");
                    String bookData = scanner.nextLine();
                    bookRepository.add(bookData);
                    nextMove();
                    break;
                case "2":
                    bookRepository.getAll().values().forEach(System.out::println);
                    nextMove();
                    break;
                case "3":
                    System.out.println("Введите ID книги: ");
                    int bookId = Utils.parseNumber(scanner.nextLine());
                    try {
                        Book book = bookRepository.getById(bookId);
                        System.out.println(book);
                    } catch (BookNotFoundException e) {
                        printError(e);
                    }
                    nextMove();
                    break;
                case "4":
                    System.out.println("Введите название книги: ");
                    String title = scanner.nextLine().trim();
                    try {
                        bookRepository.getByTitle(title).forEach(System.out::println);
                    } catch (BookNotFoundException e) {
                        printError(e);
                    }
                    nextMove();
                    break;
                case "5":
                    System.out.println("Введите имя или фамилию автора: ");
                    String author = scanner.nextLine().trim();
                    try {
                        bookRepository.getByAuthor(author).forEach(System.out::println);
                    } catch (BookNotFoundException e) {
                        printError(e);
                    }
                    nextMove();
                    break;
                case "6":
                    System.out.println("Введите год издания: ");
                    int year = Utils.parseNumber(scanner.nextLine());
                    try {
                        bookRepository.getByYear(year).forEach(System.out::println);
                    } catch (BookNotFoundException e) {
                        printError(e);
                    }
                    nextMove();
                    break;
                case "7":
                    System.out.println("Введите через запятую имя и почту пользователя: ");
                    String userData = scanner.nextLine();
                    userRepository.add(userData);
                    nextMove();
                    break;
                case "8":
                    userRepository.getAll().values().forEach(System.out::println);
                    nextMove();
                    break;
                case "9":
                    System.out.println("Введите ID пользователя: ");
                    int userId = Utils.parseNumber(scanner.nextLine());
                    try {
                        System.out.println(userRepository.getById(userId));
                    } catch (UserNotFoundException e) {
                        printError(e);
                    }
                    nextMove();
                    break;
                case "10":
                    System.out.println("Введите имя пользователя: ");
                    String name = scanner.nextLine().trim();
                    try {
                        userRepository.getByName(name).forEach(System.out::println);
                    } catch (UserNotFoundException e) {
                        printError(e);
                    }
                    nextMove();
                    break;
                case "11":
                    System.out.println("Введите почту пользователя: ");
                    String email = scanner.nextLine().trim();
                    try {
                        userRepository.getByEmail(email).forEach(System.out::println);
                    } catch (UserNotFoundException e) {
                        printError(e);
                    }
                    nextMove();
                    break;
                default:
                    System.out.println("Команда не распознана. Выберите команду из списка");
                    printMenu();
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

    void nextMove() {
        System.out.println("Выберите следующее действие: ");
    }

    void printError(Exception e) {
        System.out.println("Ошибка" + e.getMessage());
    }

}
