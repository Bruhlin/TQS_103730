package TQS.lab5.ex2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSearchSteps {
    Library library = new Library();
    List<Book> result = new ArrayList<>();

	@ParameterType(".*")
	public LocalDateTime localDateTime(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
		return LocalDate.parse(date, formatter).atStartOfDay();
	}

	@ParameterType("\\d{4}")
	public LocalDateTime year(String year) {
		return LocalDateTime.of(Integer.parseInt(year), 1, 1, 0, 0);
	}
    
    @Given("a book with the title {string}, written by {string}, published in {localDateTime}")
	public void addNewBook(final String title, final String author, final LocalDateTime published) {
		Book book = new Book(title, author, published);
		library.addBook(book);
	}

	@When("the customer searches for books published between {year} and {year}")
	public void setSearchParameters(final LocalDateTime from, LocalDateTime to) {
		result = library.findBooks(from, to);
	}

	@Then("{int} books should have been found")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertThat(result.size(), equalTo(booksFound));
	}

	@Then("Book {int} should have the title {string}")
	public void verifyBookAtPosition(final int position, final String title) {
		assertThat(result.get(position - 1).getTitle(), equalTo(title));
	}
}
