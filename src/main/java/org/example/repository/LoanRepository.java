package org.example.repository;

import org.example.exception.BookNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.model.entity.Book;
import org.example.model.entity.Loan;
import org.example.model.entity.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.utils.Utils;
import org.example.utils.Utils.*;

public class LoanRepository implements Repository<Loan>{

    Map<Integer, Loan> loans = new HashMap<>();
    BookRepository bookRepository;
    UserRepository userRepository;
    Utils utils = new Utils();

    private int nextId = 1;

    public LoanRepository(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;

        Loan loan1 = new Loan(1, 1, LocalDate.of(2024, 1, 1), null);
        loan1.setId(nextId);
        loans.put(nextId, loan1);
        User user = userRepository.getById(1);
        user.addLoan(loan1);
        Book book = bookRepository.getById(1);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        nextId++;
    }

    @Override
    public void add(String s) {
        String[] str = s.split(",");
        if (str.length != 2) {
            throw new IllegalArgumentException("Введите 2 параметра - id книги и id пользователя");
        }
        int bookId = utils.parseNumber(str[0]);
        int userId = utils.parseNumber(str[1]);
        Book book = bookRepository.getById(bookId);
        if (book == null) {
            throw new BookNotFoundException("Книга не найдена.");
        }
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalArgumentException("Доступных книг для выдачи не найдено");
        }
        User user = userRepository.getById(userId);
        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        if (user.getCurrentLoans().size() >= 3) {
            throw new IllegalStateException("Нельзя брать больше 3 книг!");
        }
        if (user.getCurrentLoans().stream()
                .anyMatch(loan -> loan.getBookId() == bookId && loan.getReturnDate() == null)) {
            throw new IllegalArgumentException("Нельзя брать две одинаковые книги в одни руки");
        }
        Loan loan = new Loan(bookId, userId, LocalDate.now(), null);
        loan.setId(nextId);
        loans.put(nextId, loan);
        nextId++;

        user.addLoan(loan);
        System.out.println("Пользователю " + user + " добавлена книга " + book.getTitle() + ".");
        book.setAvailableCopies(book.getAvailableCopies() - 1);
    }

    @Override
    public Map<Integer, Loan> getAll() {
        return loans;
    }

    @Override
    public Loan getById(int id) {
        return loans.get(id);
    }

    public List<Loan> getByUserId(int userId) {
        return loans.values().stream()
                .filter(l -> l.getUserId() == userId)
                .toList();
    }

    public List<Loan> getByBookId(int bookId) {
        return loans.values().stream()
                .filter(loan->loan.getBookId() == bookId)
                .toList();
    }

    public void returnBook(String s) {
        String[] str = s.split(",");
        if (str.length != 2) {
            throw new IllegalArgumentException("Введите 2 параметра - id книги и id пользователя");
        }
        int bookId = utils.parseNumber(str[0]);
        int userId = utils.parseNumber(str[1]);
        User user = userRepository.getById(userId);
        if (user == null) {
            throw new UserNotFoundException("Пользователь с ID " + userId + " не найден");
        }
        Book book = bookRepository.getById(bookId);
        if (book == null) {
            throw new BookNotFoundException("Книга с ID " + bookId + " не найдена");
        }
        Loan loan = loans.values().stream()
                .filter(l -> l.getBookId() == bookId && l.getUserId() == userId && l.getReturnDate() == null)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Активная выдача для этой книги и пользователя не найдена"));

        loan.returnBook();
        user.removeLoan(loan);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
    }

    public List<Loan> collectOverdue() {
        return loans.values().stream()
                .filter(Loan::isOverdue)
                .toList();
    }
}
