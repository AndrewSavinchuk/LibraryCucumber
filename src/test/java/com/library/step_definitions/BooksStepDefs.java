package com.library.step_definitions;

import com.library.pages.BookPage;
import com.library.pages.CommonAreaPage;
import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utilities.BrowserUtil;
import com.library.utilities.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class BooksStepDefs {
    LoginPage loginPage;
    //LoginPage loginPage =new LoginPage();
    DashBoardPage dashBoardPage;
    String actualBorrowedBooksNumber;

    @Given("I am in the homepage of library app")
    public void i_am_in_the_homepage_of_library_app() {
        loginPage = new LoginPage();
        loginPage.login();
    }

    @When("I take borrowed books number")
    public void i_take_borrowed_books_number() {
        dashBoardPage = new DashBoardPage();
        BrowserUtil.waitFor(3);
        actualBorrowedBooksNumber = dashBoardPage.borrowedBooksNumber.getText();

    }

    @Then("borrowed books number information must match with DB")
    public void borrowed_books_number_information_must_match_with_db() {
        String query = "SELECT COUNT(*)\n" +
                "FROM book_borrow\n" +
                "WHERE is_returned = 0";
        DB_Util.runQuery(query);
        String expectedBorrowedBooksNumber = DB_Util.getFirstRowFirstColumn();
        Assert.assertEquals(expectedBorrowedBooksNumber, actualBorrowedBooksNumber);

    }

    String actualBookGenre;

    @When("I execute query to find most popular book genre")
    public void i_execute_query_to_find_most_popular_book_genre() {
        String query = "SELECT book_categories.name, COUNT(*) AS countofbookcategories\n" +
                "FROM book_borrow\n" +
                "         INNER JOIN books\n" +
                "             ON book_borrow.book_id = books.id\n" +
                "         INNER JOIN book_categories\n" +
                "             ON books.book_category_id = book_categories.id\n" +
                "GROUP BY book_categories.name\n" +
                "ORDER BY countofbookcategories DESC";

        DB_Util.runQuery(query);
        actualBookGenre = DB_Util.getCellValue(1, 1);

    }

    @Then("verify {string} is the most popular book genre.")
    public void verify_is_the_most_popular_book_genre(String expectedBookGenre) {
        Assert.assertEquals(expectedBookGenre, actualBookGenre);
    }

    @When("I navigate to {string} page")
    public void i_navigate_to_page(String module) {
        new CommonAreaPage().navigateModule(module);
    }

    String book;

    @When("I open a book called {string}")
    public void i_open_a_book_called(String bookName) {
        book = bookName;
        new BookPage().search.sendKeys(bookName);
        BrowserUtil.waitFor(3);
    }

    @When("I execute query to get the book information from books table")
    public void i_execute_query_to_get_the_book_information_from_books_table() {
        String query = "SELECT name, author, year\n" +
                "FROM books\n" +
                "WHERE name = '" + book + "'";
        DB_Util.runQuery(query);
    }

    @Then("verify book DB and UI information must match")
    public void verify_book_db_and_ui_information_must_match() {
        BookPage bookPage = new BookPage();
        //from ui
        String actualYear = bookPage.year.getText();
        String actualAuthorName = bookPage.authorName.getText();
        String actualBookName = bookPage.bookName.getText();

        // from DB
        String ecpectedBookName = DB_Util.getCellValue(1, 1);
        String ecpectedAuthorName = DB_Util.getCellValue(1, 2);
        String ecpectedYear = DB_Util.getCellValue(1, 3);
        //compare ui and DB
        Assert.assertEquals(ecpectedBookName, actualBookName);
        Assert.assertEquals(ecpectedAuthorName, actualAuthorName);
        Assert.assertEquals(ecpectedYear, actualYear);

    }

}
