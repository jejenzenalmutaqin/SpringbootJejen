package com.springmvc.springbootapi.repository;

import com.springmvc.springbootapi.model.Book;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface BookRepository extends JpaRepository <Book,Long>{

	@Query(value="SELECT * FROM books WHERE STATUS = ?1 AND title LIKE %?2%   LIMIT ?3 OFFSET ?4 \n#sort ",nativeQuery=true)
	List<Book> getBookBystatusBytitleSort(String filterStatus, String title , Long limit , Long offset, Sort sort);
}
