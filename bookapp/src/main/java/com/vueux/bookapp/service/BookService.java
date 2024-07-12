package com.vueux.bookapp.service;


import com.vueux.bookapp.exception.RecordNotFoundException;
import com.vueux.bookapp.model.BookEntity;
import com.vueux.bookapp.model.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    public List<BookEntity> getAllBooks()
    {
        System.out.println("getAllBooks");
        List<BookEntity> result = (List<BookEntity>) repository.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<BookEntity>();
        }
    }

    public BookEntity getBookById(Long id) throws RecordNotFoundException {
        System.out.println("getBookById");
        Optional<BookEntity> book = repository.findById(id);

        if(book.isPresent()) {
            return book.get();
        } else {
            throw new RecordNotFoundException("No book record exist for given id");
        }
    }

    public BookEntity createOrUpdateBook(BookEntity entity)
    {
        System.out.println("createOrUpdateBook");
        //Create new book
        if (entity.getId() == null)
        {
            entity = repository.save(entity);

            return entity;
        }
        else
        {
            //Update existing book
            Optional<BookEntity> book = repository.findById(entity.getId());

            if (book.isPresent())
            {
                BookEntity newEntity = book.get();
                newEntity.setISBN(entity.getISBN());
                newEntity.setAuthor(entity.getAuthor());
                newEntity.setBookName(entity.getBookName());
                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public void deleteBookById(Long id) throws RecordNotFoundException
    {
        System.out.println("deleteBookById");
        Optional<BookEntity> book = repository.findById(id);

        if(book.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No book record exist for given id");
        }
    }

}
