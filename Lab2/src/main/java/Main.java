import java.sql.*;

/**
 * @author Yurii Senin
 * Main Class that realized lab work
 */
public class Main {
    /**
     * Main function
     *
     * @param args arguments of main function
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DataBase dataBase = new DataBase();
        try (Connection connection = dataBase.connect();
             Statement statement = connection.createStatement()) {
            System.out.println("Connected successfully!" + "\n");
            dataBase.create(statement, connection);
            Notebook firstNotebook = new Notebook("HP", "Intel i5", "AMD", 432372);
            Notebook secondNotebook = new Notebook("Acer", "Intel i7", "GForse", 111223);
            dataBase.Insert(firstNotebook);
            dataBase.Insert(secondNotebook);
            secondNotebook.setProcessor("Intel i5 543");
            dataBase.Update(secondNotebook);
            dataBase.Delete(1);
        }
    }
}
