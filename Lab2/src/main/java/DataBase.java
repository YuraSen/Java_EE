import java.sql.*;

/**
 * @author Yurii Senin
 * Class DataBase that realized basic operation of DataBase database
 */
public class DataBase {
    private final String url = "jdbc:mysql://localhost:3306/JDBC?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    /**
     * Login for connection to database
     */
    private final String user = "root";
    /**
     * Password for connection to database
     */
    private final String password = "0302";
    /**
     * Name driver for connection to database
     */
    private final String nameDriver = "com.mysql.cj.jdbc.Driver";


    /**
     * Connection of DataBase database
     */
    private Connection connection;
    /**
     * Constructor for creating object about plane
     */
    public DataBase() {
    }

    /**
     * Method for creating database
     *
     * @param statement  - statement of my database
     * @param connection - Connection of DataBase database
     */

    public void create(Statement statement, Connection connection) throws SQLException {
        this.connection = connection;
        statement.executeUpdate("DROP TABLE Notebooks;");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Notebooks (id INT NOT NULL AUTO_INCREMENT, Brand VARCHAR (30) NOT NULL, Processor VARCHAR (30) NOT NULL , Videocard VARCHAR (30) NOT NULL, Series INT NOT NULL, PRIMARY KEY(id));");

    }

    /**
     * Connection to database
     *
     * @return connection to database
     */
    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(nameDriver);
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Operation INSERT for MYSQL database
     *
     * @param notebook - notebook
     */
    public void Insert(Notebook notebook) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Notebooks (Brand, Processor, Videocard, Series) VALUES (?,?,?,?);");
        preparedStatement.setString(1, notebook.getBrand());
        preparedStatement.setString(2, notebook.getProcessor());
        preparedStatement.setString(3, notebook.getVideocard());
        preparedStatement.setInt(4, notebook.getSeries());
        preparedStatement.executeUpdate();
    }


    /**
     * Operation SELECT for MYSQL database
     *
     * @param column     - column of notebook
     * @param selectName - name of notebook that selected
     * @return information of chosen notebook
     */
    public ResultSet Select(String column, String selectName) throws SQLException {
        String querry = String.format("SELECT * FROM Notebooks WHERE %s = ?;", column);
        PreparedStatement preparedStatement = connection.prepareStatement(querry);
        preparedStatement.setString(1, selectName);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    /**
     * Operation UPDATE for MYSQL database
     *
     * @param notebook - notebook
     */
    public void Update(Notebook notebook) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Notebooks SET Brand = ?, Processor = ?, Videocard = ? WHERE Series = ?;");
        preparedStatement.setString(1, notebook.getBrand());
        preparedStatement.setString(2, notebook.getProcessor());
        preparedStatement.setString(3, notebook.getVideocard());
        preparedStatement.setInt(4, notebook.getSeries());
        preparedStatement.executeUpdate();
    }

    /**
     * Operation DELETE for MYSQL database
     *
     * @param id - id of notebook
     */
    public void Delete(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Notebooks WHERE ID = ?;");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
}
