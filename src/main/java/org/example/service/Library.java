package org.example.service;

import org.example.model.repository.BookRepository;
import org.example.model.repository.UserRepository;
import org.example.ui.ConsoleMenu;

public class Library {
    BookRepository bookRepository = new BookRepository();
    UserRepository userRepository = new UserRepository();
    ConsoleMenu consoleMenu = new ConsoleMenu();

    public void exit() {
        consoleMenu.flag = false;
        System.out.println("Приложение завершило работу");
    }
}
