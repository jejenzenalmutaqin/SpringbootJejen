package com.springmvc.springbootapi.dao;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springmvc.springbootapi.model.Book;
import com.springmvc.springbootapi.repository.BookRepository;

@Service
public class BookDAO {
	
	@Autowired
	BookRepository bookRepository;
	
	/* to save a book */
	public Book save (Book book) {
		return bookRepository.save(book);
	}
	
	/* search all book */
	public List<Book> findAll() {
		return bookRepository.findAll();
	}
	
	/* get a book */
	public Book findOne(Long bookid) {
		return bookRepository.findOne(bookid);
	}

	/* delete a book */
	public void delete(Book book) {
		bookRepository.delete(book);
	}
	
    public List<Book> findAllSortBy(String sort) {
        return bookRepository.findAll(sortByIdAsc(sort));
    }

    private Sort sortByIdAsc(String sort) {
        return new Sort(Sort.Direction.ASC, sort);
    }
    
    public List<Book> getBookBystatusBytitleSort (String filterStatus, String title, Long limit, Long offset, Sort sort) {
    	return bookRepository.getBookBystatusBytitleSort(filterStatus, title,limit,offset, sort);
    }
    
}
