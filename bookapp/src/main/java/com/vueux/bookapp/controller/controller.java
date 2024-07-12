package com.vueux.bookapp.controller;

import com.vueux.bookapp.exception.RecordNotFoundException;
import com.vueux.bookapp.model.BookEntity;
import com.vueux.bookapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class controller {

    @Autowired
    BookService service;

    @RequestMapping
    public String getAllBooks (Model model)
    {
        System.out.println("getAllBooks");
        List<BookEntity> list = service.getAllBooks();
        model.addAttribute("books", list);
        return "list-books";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editBookById(Model model, @PathVariable("id")Optional<Long> id)
        throws RecordNotFoundException
    {
        System.out.println("editBookById");
        if (id.isPresent()) {
            BookEntity entity = service.getBookById(id.get());
            model.addAttribute("book", entity);
        } else {
            model.addAttribute("book", new BookEntity());
        }
        return "add-edit-book";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteBookById(Model model, @PathVariable("id")Long id)
        throws RecordNotFoundException
    {
        System.out.println("deleteBookById"+ id);
        service.deleteBookById(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/createBook", method = RequestMethod.POST)
    public String createOrUpdateBook(BookEntity book)
    {
        System.out.println("createOrUpdateBook");
        service.createOrUpdateBook(book);
        return "redirect:/";
    }

}
