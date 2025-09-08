package org.example.model.repository;

import org.example.exception.BookNotFoundException;
import org.example.model.entity.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.util.Utils.parseNumber;

public class BookRepository implements Repository<Book>{
    Map<Integer, Book> books = new HashMap<>();
    private int nextId = 1;

    public BookRepository() {
        Book book1 = new Book("Маленький принц", "А.Экзюпери", 1951, 3, 2);
        book1.setId(nextId);
        books.put(nextId, book1);
        nextId++;

        Book book2 = new Book("Гарри Поттер и Философский камень", "Д. Роулинг", 1997, 8, 4);
        book2.setId(nextId);
        books.put(nextId, book2);
        nextId++;
    }

    @Override
    public void add(String s){
        try {
            if (s == null || s.isBlank()) {
                throw new IllegalArgumentException("Пустая строка с параметрами недопустима");
            }
            String[] str = s.split(",");
            if (str.length != 5) {
                throw new IllegalArgumentException("Вы ввели некорректные параметры книги");
            }
            Book book = new Book(str[0], str[1], parseNumber(str[2]), parseNumber(str[3]), parseNumber(str[4]));
            if (books.containsValue(book)) {
                throw new IllegalArgumentException("Данная книга уже существует в базе");
            }
            book.setId(nextId);
            books.put(nextId, book);
            nextId++;
            System.out.printf("%nКнига %s добавлена в базу%n", book.getTitle());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при добавлении: " + e.getMessage());
        }
    }

    @Override
    public Map<Integer, Book> getAll() {
        return books;
    }

    @Override
    public Book getById(int id) {
        if (!books.containsKey(id) && id != -1) {
            throw new BookNotFoundException("Книга с ID = " + id + " не найдена");
            }
        return books.get(id);
    }


    public List<Book> getByTitle(String title) {
        List<Book> bookList = books.values().stream()
                .filter(book -> book.getTitle().toLowerCase()
                .contains(title.toLowerCase())).toList();
        if (bookList.isEmpty()) {
            throw new BookNotFoundException("Книги с названием '" + title + "' не найдено");
        }
            return bookList;
    }

    public List<Book> getByAuthor(String author) {
        List<Book> bookList = books.values().stream()
                .filter(book -> book.getAuthor().toLowerCase()
                        .contains(author.toLowerCase())).toList();

        if (bookList.isEmpty()) {
            throw new BookNotFoundException("Книги автора '" + author + "' не найдены");
        }
        return bookList;
    }

    public List<Book> getByYear(int year) {
        List<Book> bookList = books.values().stream()
                .filter(book -> book.getYear() == year).toList();

        if (bookList.isEmpty()) {
            throw new BookNotFoundException("Книги за " + year + " год не найдены");
        }
        return bookList;
    }
}
