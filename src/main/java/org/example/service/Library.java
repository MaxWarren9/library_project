package org.example.service;

import org.example.exception.BookNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.model.entity.Book;
import org.example.repository.BookRepository;
import org.example.repository.LoanRepository;
import org.example.repository.UserRepository;

import java.util.Scanner;

import org.example.utils.Utils;
import org.example.utils.Utils.*;

public class Library {
    BookRepository bookRepository = new BookRepository();
    UserRepository userRepository = new UserRepository();
    LoanRepository loanRepository = new LoanRepository(bookRepository, userRepository);
    Scanner scanner = new Scanner(System.in);
    Utils utils = new Utils();

    public void addBook() {
        System.out.println("Введите через запятую название книги, имя автора, год издания, количество копий и количество доступных копий");
        String bookData = scanner.nextLine();
        try {
            bookRepository.add(bookData);
        } catch (IllegalArgumentException e) {
            printError(e);
        }
        nextMove();
    }

    public void getAllBooks() {
        bookRepository.getAll().values().forEach(System.out::println);
        nextMove();
    }

    public void getBookByID() {
        System.out.println("Введите ID книги: ");
        try {
            int bookId = utils.parseNumber(scanner.nextLine());
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
            int year = utils.parseNumber(scanner.nextLine());
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
            int userId = utils.parseNumber(scanner.nextLine());
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

    public void issueBook() {
        System.out.println("Введите id книги и id пользователя для выдачи книги: ");
        String info = scanner.nextLine().trim();
        try {
            loanRepository.add(info);
        } catch (UserNotFoundException | BookNotFoundException | IllegalArgumentException | IllegalStateException e) {
            printError(e);
        }
        nextMove();
    }

    public void getAllLoans() {
        loanRepository.getAll().values().forEach(System.out::println);
        nextMove();
    }

    public void returnBook() {
        System.out.println("Введите id книги и id пользователя для возврата книги: ");
        String info = scanner.nextLine().trim();
        try {
            loanRepository.returnBook(info);
            System.out.println("Книга успешно возвращена!");
        } catch (UserNotFoundException | BookNotFoundException | IllegalArgumentException e) {
            printError(e);
        }
        nextMove();
    }

    public void getLoansByUser() {
        System.out.println("Введите id пользователя для просмотра выданных книг: ");
        String info = scanner.nextLine().trim();
        int id = utils.parseNumber(info);
        if (loanRepository.getByUserId(id).isEmpty()) {
            System.out.println("История выдачи для пользователя не найдена");
        }
        loanRepository.getByUserId(id).forEach(System.out::println);
        nextMove();
    }

    public void getLoansByBook() {
        System.out.println("Введите id книги для просмотра истории выдачи: ");
        String info = scanner.nextLine().trim();
        int id = utils.parseNumber(info);
        loanRepository.getByBookId(id).forEach(System.out::println);
        nextMove();
    }

    public void getOverdueBooks() {
        if (loanRepository.collectOverdue().isEmpty()) {
            System.out.println("Просроченных книг не найдено");
        } else {
            System.out.println("Список книг с просроченным сроком возврата:");
            loanRepository.collectOverdue().forEach(System.out::println);
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
