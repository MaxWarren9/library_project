package org.example.service;

import org.example.exception.BookNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.model.entity.Book;
import org.example.model.repository.BookRepository;
import org.example.model.repository.UserRepository;
import org.example.utils.Utils;

import java.util.Scanner;

public class Library {
    BookRepository bookRepository = new BookRepository();
    UserRepository userRepository = new UserRepository();
    Scanner scanner = new Scanner(System.in);

    public void addBook() {
        System.out.println("Введите через запятую название книги, имя автора, год издания, количество копий и количество доступных копий");
        String bookData = scanner.nextLine();
        bookRepository.add(bookData);
        nextMove();
    }

    public void getAllBooks() {
        bookRepository.getAll().values().forEach(System.out::println);
        nextMove();
    }

    public void getBookByID() {
        System.out.println("Введите ID книги: ");
        try {
            int bookId = Utils.parseNumber(scanner.nextLine());
            Book book = bookRepository.getById(bookId);
            System.out.println(book);
        } catch (BookNotFoundException | IllegalArgumentException e) {
            printError(e);
        }
        nextMove();
    }

    public void getBookByTitle() {
        System.out.println("Введите название книги: ");
        String title = scanner.nextLine().trim();
        try {
            bookRepository.getByTitle(title).forEach(System.out::println);
        } catch (BookNotFoundException e) {
            printError(e);
        }
        nextMove();
    }

    public void getBookByAuthor() {
        System.out.println("Введите имя или фамилию автора: ");
        String author = scanner.nextLine().trim();
        try {
            bookRepository.getByAuthor(author).forEach(System.out::println);
        } catch (BookNotFoundException e) {
            printError(e);
        }
        nextMove();
    }

    public void getBookByYear() {
        System.out.println("Введите год издания: ");
        try {
            int year = Utils.parseNumber(scanner.nextLine());
            bookRepository.getByYear(year).forEach(System.out::println);
        } catch (BookNotFoundException | IllegalArgumentException e) {
            printError(e);
        }
        nextMove();
    }

    public void addUser() {
        System.out.println("Введите через запятую имя и почту пользователя: ");
        try {
            String userData = scanner.nextLine();
            userRepository.add(userData);
        } catch (IllegalArgumentException e) {
            printError(e);
        }
        nextMove();
    }

    public void getAllUsers() {
        userRepository.getAll().values().forEach(System.out::println);
        nextMove();
    }

    public void getUserByID() {
        System.out.println("Введите ID пользователя: ");
        try {
            int userId = Utils.parseNumber(scanner.nextLine());
            System.out.println(userRepository.getById(userId));
        } catch (UserNotFoundException | IllegalArgumentException e) {
            printError(e);
        }
        nextMove();
    }

    public void getUserByName() {
        System.out.println("Введите имя пользователя: ");
        String name = scanner.nextLine().trim();
        try {
            userRepository.getByName(name).forEach(System.out::println);
        } catch (UserNotFoundException e) {
            printError(e);
        }
        nextMove();
    }

    public void getUserByEmail() {
        System.out.println("Введите почту пользователя: ");
        String email = scanner.nextLine().trim();
        try {
            userRepository.getByEmail(email).forEach(System.out::println);
        } catch (UserNotFoundException e) {
            printError(e);
        }
        nextMove();
    }

    void nextMove() {
        System.out.println("Выберите следующее действие: ");
    }

    void printError(Exception e) {
        System.out.println("Ошибка: " + e.getMessage());
    }
}
