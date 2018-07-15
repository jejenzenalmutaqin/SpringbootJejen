package com.springmvc.springbootapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springmvc.springbootapi.dao.BookDAO;
import com.springmvc.springbootapi.model.Book;

@RestController
public class BookController {

	@Autowired
	BookDAO bookDAO;
	
	@RequestMapping(value="/books", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getNativeBooks(
			@RequestParam("pageNumber") Long pageNumber,
			@RequestParam("pageSize") Long pageSize,
			@RequestParam("sort") String sort,
			@RequestParam("query") String query,
			@RequestParam("filterStatus") String filterStatus){
		
		Sort sort1 = new Sort(new Sort.Order(Direction.ASC, sort));
		
		List<Book> book = bookDAO.getBookBystatusBytitleSort(filterStatus, query,pageSize,pageNumber, sort1);
		
		if(book==null) {
			return ResponseEntity.notFound().build();
		}
		
		try {
			List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
			for (Book map : book) {
				Map<String, Object> mapResult = new HashMap<String, Object>();
				mapResult.put("id", map.getId());
				mapResult.put("title", map.getTitle());
				mapResult.put("price", map.getPrice());
				mapResult.put("status", map.getStatus()); 
				listResult.add(mapResult);
			}  
			List<Map<String, Object>> meta = new ArrayList<Map<String, Object>>(); 
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", pageNumber);
			map.put("size", pageSize);
			map.put("count", listResult.size());
			if (listResult.size()==pageSize) {
				map.put("totalPages", listResult.size()/pageSize);
			} else if (listResult.size()<pageSize || listResult.size()>pageSize) {
				map.put("totalPages", listResult.size()/pageSize +1);
			}
			map.put("totalData", listResult.size());
			meta.add(map);

			Map<String, Object> result = new HashMap<String, Object>();
			if(listResult.size()>0) {
				result.put("status","success");
			}else {
				result.put("status","Data Empty");
			}
			result.put("code","200");
			result.put("data",listResult);
			result.put("meta",meta);

			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		
		
	}
}
