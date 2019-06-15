package docker.demo.controller;

import docker.demo.model.Book;
import docker.demo.model.BookRepository;
import docker.demo.business.BookDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class BooksController {
	@Autowired
	private BookRepository bookRepository;

	private final String NEW_BOOK_ID = "newBook";

	@GetMapping
	public String showBooksList(Model model) {
		model.addAttribute("books", bookRepository.findAll()); //= "SELECT * FROM books"

		model.addAttribute(NEW_BOOK_ID, new BookDTO());
		return "books-ui";
	}

	@PostMapping("/add")
	public String addBook(Model model, @ModelAttribute(NEW_BOOK_ID) BookDTO neu) {
		try {
			if (StringUtils.isBlank(neu.getText())) throw new Exception("Book title must not be empty.");
			Book bookModel = new Book();
			bookModel.setText(neu.getText());

			bookRepository.save(bookModel); //Inster ...

			model.addAttribute("success", "Book created.");
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return showBooksList(model);
	}

	@PostMapping("/{id}/delete")
	public String delete(Model model, @PathVariable("id") Long id) {
		try {
			Book bookToDelete = bookRepository.findById(id).get(); // = SELECT * FROM books WHERE id=123
			bookRepository.delete(bookToDelete);
		} catch (Exception e) {
			model.addAttribute("error", "Invalid bookID.");
		}

		model.addAttribute("success", "Book deleted.");
		return showBooksList(model);
	}

}
