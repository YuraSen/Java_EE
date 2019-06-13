import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yurii Senin
 * Testing class DataBaseTest for testing method of DataBase class
 */
class DataBaseTest {

    /**
     * Connection of DataBase
     */
    DataBase dataBase;
    /**
     * Object of Notebook Class for testing
     */
    Notebook firstNotebook;
    /**
     * Object of Notebook Class for testing
     */
    Notebook secondNotebook;

    /**
     * Initialization data for testing
     */
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        firstNotebook = new Notebook("HP", "Intel i5", "AMD", 432372);
        secondNotebook = new Notebook("Acer", "Intel i7", "GForse", 111223);
        dataBase = new DataBase();
    }

    /**
     * Test method Insert
     *
     * @result add some information in data base
     */
    @org.junit.jupiter.api.Test
    void insert() throws SQLException, ClassNotFoundException {
        try (Connection connection = dataBase.connect();
             Statement statement = connection.createStatement()) {
            System.out.println("Connected successfully!" + "\n");
            dataBase.create(statement, connection);
            dataBase.Insert(firstNotebook);
            dataBase.Insert(secondNotebook);
            ResultSet resultSet = dataBase.Select("Brand", firstNotebook.getBrand());
            while (resultSet.next()) {
                assertEquals(firstNotebook.getBrand(), resultSet.getString("Brand"));
                assertEquals(firstNotebook.getProcessor(), resultSet.getString("Processor"));
                assertEquals(firstNotebook.getVideocard(), resultSet.getString("Videocard"));
                assertEquals(firstNotebook.getSeries(), resultSet.getInt("Series"));
            }
        }
    }

    /**
     * Test method Select
     *
     * @result return needed information from data base
     */
    @org.junit.jupiter.api.Test
    void select() throws SQLException, ClassNotFoundException {
        try (Connection connection = dataBase.connect();
             Statement statement = connection.createStatement()) {
            System.out.println("Connected successfully!" + "\n");
            dataBase.create(statement, connection);
            dataBase.Insert(secondNotebook);
            ResultSet resultSet = dataBase.Select("Brand", secondNotebook.getBrand());
            while (resultSet.next()) {
                assertEquals(secondNotebook.getVideocard(), resultSet.getString("Videocard"));
                assertEquals(secondNotebook.getSeries(), resultSet.getInt("Series"));
                assertEquals(secondNotebook.getBrand(), resultSet.getString("Brand"));
                assertEquals(secondNotebook.getProcessor(), resultSet.getString("Processor"));
            }
        }
    }

    /**
     * Test method Update
     *
     * @result update some information in data base
     */
    @org.junit.jupiter.api.Test
    void update() throws SQLException, ClassNotFoundException {
        try (Connection connection = dataBase.connect();
             Statement statement = connection.createStatement()) {
            System.out.println("Connected successfully!" + "\n");
            dataBase.create(statement, connection);
            dataBase.Insert(secondNotebook);
            secondNotebook.setBrand("Mac");
            dataBase.Update(secondNotebook);
            ResultSet resultSet = dataBase.Select("Brand", secondNotebook.getBrand());
            while (resultSet.next()) {
                assertEquals(secondNotebook.getBrand(), resultSet.getString("Brand"));
            }
        }
    }

    /**
     * Test method Delete
     *
     * @result delete some information in data base
     */
    @org.junit.jupiter.api.Test
    void delete() throws SQLException, ClassNotFoundException {
        try (Connection connection = dataBase.connect();
             Statement statement = connection.createStatement()) {
            System.out.println("Connected successfully!" + "\n");
            dataBase.create(statement, connection);
            dataBase.Insert(secondNotebook);
            dataBase.Insert(firstNotebook);
            dataBase.Delete(1);
            ResultSet resultSet = dataBase.Select("Brand", firstNotebook.getBrand());
            int countWinners = 0;
            while (resultSet.next()) {
                countWinners++;
            }
            assertEquals(countWinners, 1);
        }
    }
}