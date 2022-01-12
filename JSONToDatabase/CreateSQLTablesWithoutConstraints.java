package JSONToDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CreateSQLTablesWithoutConstraints {

  public void createCommentTable() throws SQLException {
    String createString =
        "USE reddit3;" +
            "DROP TABLE IF EXISTS `Comment`;" +
            " create table Comment (" +
            "`id` varchar(11) , " +
            "`name` varchar(40) , " +
            "`author` varchar(20) , " +
            "`score` int(11) , " +
            "`body` MEDIUMTEXT , " +
            "`subreddit_id` varchar(8) , " +
            "`parent_id` varchar(10) , " +
            "`created_utc` timestamp , " +
            "`link_id` varchar(8)  " +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

    connectionInstance(createString);
  }

  public void createLinkTable() throws SQLException {
    String createString =
        "USE reddit3;" +
            "DROP TABLE IF EXISTS `Link`; " +
            "CREATE TABLE `Link` (" +
            "`id` varchar(11) , " +
            "`name` varchar(40) , " +
            "`subreddit_id` varchar(8)  " +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

    connectionInstance(createString);
  }

  public void createSubredditTable() throws SQLException {
    String createString =
        "USE reddit3;" +
            "DROP TABLE IF EXISTS `Subreddit`; " +
            "CREATE TABLE `Subreddit` ( " +
            "`id` varchar(11) , " +
            "`name` varchar(40)  " +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

    connectionInstance(createString);
  }

  public void createDatabase() throws SQLException {
    String createString =
        "DROP DATABASE reddit3;" +
            "CREATE DATABASE  IF NOT EXISTS reddit3 ";

    connectionInstance(createString);
  }

  private void connectionInstance(String createString) {
    connectionInstances(createString);
  }

  static void connectionInstances(String createString) {
    DBConnection dbConnection = new DBConnection();
    Properties properties = dbConnection.getConnectionData();

    String url = properties.getProperty("db.url");
    String user = properties.getProperty("db.user");
    String password = properties.getProperty("db.passwd");
    try (Connection connection = DriverManager.getConnection(url, user, password)) {
      Statement statement;
      statement = connection.createStatement();
      statement.executeUpdate(createString);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
