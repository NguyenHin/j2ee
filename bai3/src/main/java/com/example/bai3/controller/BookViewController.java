package com.example.bai3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bai3.model.Book;
import com.example.bai3.service.BookService;

@Controller
@RequestMapping("/books")
public class BookViewController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";   // books.html
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";     // add.html
    }

    @PostMapping("/add")
    public String add(Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "edit-book";    // edit.html
    }

    @PostMapping("/edit")
    public String edit(Book book) {
        bookService.updateBook(book.getId(), book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
