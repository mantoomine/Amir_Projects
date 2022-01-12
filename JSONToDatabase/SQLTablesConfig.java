package JSONToDatabase;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


public class SQLTablesConfig {

  Set<String> linkSetHash = new HashSet<>();
  Set<String> SubredditHash = new HashSet<>();


  public void createTables(String fileName) throws IOException {

    File file = new File(fileName);
    String path = ".\\src\\Assignment2\\" + file;
    JSONObject jsonObject;
    int batchSize = 200;
    int count = 0;

    BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
    String line = bufferedReader.readLine();

    DBConnection dbConnection = new DBConnection();
    Properties properties = dbConnection.getConnectionData();

    String url = properties.getProperty("db.url");
    String user = properties.getProperty("db.user");
    String password = properties.getProperty("db.passwd");
    try (Connection connection = DriverManager.getConnection(url, user, password)) {
      connection.setAutoCommit(false);
      PreparedStatement subredditPreparedStatement = connection.prepareStatement(
          "INSERT INTO reddit3.subreddit(`id`, `name`) VALUES (?,?)");
      PreparedStatement link = connection.prepareStatement(
          "INSERT INTO reddit3.link(`id`, `name`, `subreddit_id`) VALUES (?,?,?)");
      PreparedStatement comment = connection.prepareStatement(
          "INSERT INTO reddit3.comment(`id`, `name`, `author`, `score`,`body`, `subreddit_id`, `parent_id`, `created_utc`, `link_id`) VALUES (?,?,?,?,?,?,?,?,?)");
      while (line != null) {
        jsonObject = linetoJSON(line);
        System.out.println("Started Inserting , PLEASE WAiT!!!");
        String subredditId = jsonObject.getString("subreddit_id");
        String linkId = jsonObject.getString("link_id");
        if (!SubredditHash.contains(subredditId)) {
          count++;
          saveSubreddit(jsonObject, subredditPreparedStatement);
          SubredditHash.add(subredditId);
        }
        if (!linkSetHash.contains(linkId)) {
          count++;
          saveLink(jsonObject, link);
          linkSetHash.add(linkId);
        }
        count++;
        saveSubreddit(jsonObject,subredditPreparedStatement);
        saveComment(jsonObject, comment);
        saveLink(jsonObject,link);
        count++;

        if (count > batchSize) {
          subredditPreparedStatement.executeBatch();
          link.executeBatch();
          comment.executeBatch();
          count = 0;
          connection.commit();
          //connection.setAutoCommit(false);
        }

        line = bufferedReader.readLine();
      }
      subredditPreparedStatement.executeBatch();
      link.executeBatch();
      comment.executeBatch();
      connection.commit();

    } catch (Throwable e) {
      System.out.println(e.getMessage());
    }
  }

  private JSONObject linetoJSON(String line) {
    return new JSONObject(line);

  }

  private void saveComment(JSONObject data, PreparedStatement preparedStatement) throws SQLException {
    preparedStatement.setString(1, data.getString("id"));
    preparedStatement.setString(2, data.getString("name"));
    preparedStatement.setString(3, data.getString("author"));
    preparedStatement.setInt(4, data.getInt("score"));
    preparedStatement.setString(5, data.getString(StringEscapeUtils.escapeEcmaScript("body")));
    preparedStatement.setString(6, data.getString("subreddit_id"));
    preparedStatement.setString(7, data.getString("parent_id"));
    preparedStatement.setString(8, String.valueOf(new Date(data.getLong("created_utc") * 1000L)));
    preparedStatement.setString(9, data.getString("link_id").split("_")[1]);
    preparedStatement.addBatch();
  }


  private void saveSubreddit(JSONObject data, PreparedStatement preparedStatement) throws SQLException {
    preparedStatement.setString(1, data.getString("subreddit_id"));
    preparedStatement.setString(2, data.getString("subreddit"));
    preparedStatement.addBatch();

  }

  private void saveLink(JSONObject data, PreparedStatement preparedStatement) throws SQLException {
    preparedStatement.setString(1, data.getString("link_id").split("_")[1]);
    preparedStatement.setString(2, data.getString("link_id"));
    preparedStatement.setString(3, data.getString("subreddit_id"));
    preparedStatement.addBatch();
  }
}
