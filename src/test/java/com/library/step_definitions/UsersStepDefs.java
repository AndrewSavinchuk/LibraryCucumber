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
    List<String> actulColumns;
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
}
