package JSONToDatabase;

import java.sql.SQLException;

public class CreateSQLTablesWithConstraints {

  public void createCommentTable() throws SQLException {
    String createString =
        "USE reddit3;" +
            "DROP TABLE IF EXISTS `Comment`;" +
            " create table Comment (" +
            "`id` varchar(11) NOT NULL, " +
            "`name` varchar(40) NOT NULL, " +
            "`author` varchar(20) NOT NULL, " +
            "`score` int(11) NOT NULL, " +
            "`body` MEDIUMTEXT NOT NULL, " +
            "`subreddit_id` varchar(8) NOT NULL, " +
            "`parent_id` varchar(10) NOT NULL, " +
            "`created_utc` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
            "`link_id` varchar(8) NOT NULL, " +
            "PRIMARY KEY (`id`), " +
            "UNIQUE KEY `id_UNIQUE` (`id`), " +
            "KEY `FK_COMMENT_SUBREDDIT_ID` (`subreddit_id`), " +
            "KEY `FK_COMMENT_LINK_ID` (`link_id`), " +
            "CONSTRAINT `FK_COMMENT_LINK` FOREIGN KEY (`link_id`) REFERENCES reddit3.`Link` (`id`) ON DELETE CASCADE ON UPDATE CASCADE, " +
            "CONSTRAINT `FK_COMMENT_SUBREDDIT` FOREIGN KEY (`subreddit_id`) REFERENCES reddit3.`Subreddit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

    connectionInstance(createString);
  }

  public void createLinkTable() throws SQLException {
    String createString =
        "USE reddit3;" +
            "DROP TABLE IF EXISTS `Link`; " +
            "CREATE TABLE `Link` (" +
            "`id` varchar(11) NOT NULL, " +
            "`name` varchar(40) NOT NULL, " +
            "`subreddit_id` varchar(8) NOT NULL, " +
            "PRIMARY KEY (`id`), " +
            "UNIQUE KEY `id_UNIQUE` (`id`), " +
            "UNIQUE KEY `name_UNIQUE` (`name`), " +
            "KEY `FK_SUBREDDIT_ID_ID` (`subreddit_id`), " +
            "CONSTRAINT `FK_SUBREDDIT_ID` FOREIGN KEY (`subreddit_id`) REFERENCES reddit3.`Subreddit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

    connectionInstance(createString);
  }

  public void createSubredditTable() throws SQLException {
    String createString =
        "USE reddit3;" +
            "DROP TABLE IF EXISTS `Subreddit`; " +
            "CREATE TABLE `Subreddit` ( " +
            "`id` varchar(11) NOT NULL, " +
            "`name` varchar(40) NOT NULL, " +
            "PRIMARY KEY (`id`), " +
            "UNIQUE KEY `name_UNIQUE` (`name`), " +
            "UNIQUE KEY `id_UNIQUE` (`id`) " +
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
    CreateSQLTablesWithoutConstraints.connectionInstances(createString);
  }
}
