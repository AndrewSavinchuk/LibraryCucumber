package com.library.step_definitions;

import com.library.utilities.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

public class UsersStepDefs {
    @Given("Establish the database connection")
    public void establish_the_database_connection() {
        System.out.println("Database Connection is don inside the Hooks");
    }

    List<String> actualIDs;

    @When("Execute query to get all IDs from users")
    public void execute_query_to_get_all_i_ds_from_users() {
        String query = "SELECT id\n" +
                "FROM users";
        DB_Util.runQuery(query);
        actualIDs = DB_Util.getColumnDataAsList(1);

    }

    @Then("verify all users has unique ID")
    public void verify_all_users_has_unique_id() {
        String query = "SELECT DISTINCT id\n" +
                "FROM users";
        DB_Util.runQuery(query);
        List<String> expectedIDs = DB_Util.getColumnDataAsList(1);
        Assert.assertEquals(expectedIDs, actualIDs);

    }

    List<String> actulColumns; //null

    @When("Execute query to get all columns")
    public void execute_query_to_get_all_columns() {
        String query = "SELECT * FROM users";
        DB_Util.runQuery(query);
        actulColumns = DB_Util.getAllColumnNamesAsList();

    }

    @Then("verify the below columns are listed in result")
    public void verify_the_below_columns_are_listed_in_result(List<String> expectedColumns) {
        Assert.assertEquals(expectedColumns, actulColumns);

    }

    String actualPopularUser;

    @When("I execute query to find most popular user")
    public void i_execute_query_to_find_most_popular_user() {
        String query = "SELECT full_name, COUNT(*) AS countofreadbooks\n" +
                "FROM users u\n" +
                "         INNER JOIN book_borrow bb ON u.id = bb.user_id\n" +
                "GROUP BY full_name\n" +
                "ORDER BY countofreadbooks DESC";
        DB_Util.runQuery(query);
        actualPopularUser = DB_Util.getFirstRowFirstColumn();
        // DB_Util.getCellValue(1, 1);
    }

    @Then("verify {string} is the user who reads the most")
    public void verify_is_the_user_who_reads_the_most(String expectedPopularUser) {
        Assert.assertEquals(expectedPopularUser, actualPopularUser);
    }
}
