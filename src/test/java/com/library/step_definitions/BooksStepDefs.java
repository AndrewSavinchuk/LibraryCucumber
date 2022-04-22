package com.library.step_definitions;

import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utilities.BrowserUtil;
import com.library.utilities.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class BooksStepDefs {
    LoginPage loginPage = new LoginPage();
    DashBoardPage dashBoardPage = new DashBoardPage();
    String actualBorrowedBooksNumber;

    @Given("I am in the homepage of library app")
    public void i_am_in_the_homepage_of_library_app() {
        loginPage.login();
    }

    @When("I take borrowed books number")
    public void i_take_borrowed_books_number() {
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

}
