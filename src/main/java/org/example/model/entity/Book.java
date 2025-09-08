package org.example.model.entity;

import java.util.Objects;

public class Book {
    int id;
    String title;
    String author;
    int year;
    int totalCopies;
    int availableCopies;

    public Book(String title, String author, int year, int totalCopies, int availableCopies) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название книги не может быть пустым");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Автор не может быть пустым");
        }
        if (year < 1450 || year > java.time.Year.now().getValue() + 1) {
            throw new IllegalArgumentException("Некорректный год издания книги");
        }
        if (totalCopies < 0 || availableCopies < 0) {
            throw new IllegalArgumentException("Количество копий и доступных должно быть >= 0");
        }
        if (totalCopies < availableCopies) {
            throw new IllegalArgumentException("Количество копий не может быть меньше числа доступных");
        }

        this.title = title;
        this.author = author;
        this.year = year;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public String toString() {
        return String.format(
                "[ID=%d] \"%s\", автор: %s, %d г., доступно: %d/%d",
                id, title, author, year, availableCopies, totalCopies
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return year == book.year &&
                title.equalsIgnoreCase(book.title) &&
                author.equalsIgnoreCase(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), author.toLowerCase(), year);
    }
}
