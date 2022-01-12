package JSONToDatabase;

import java.sql.SQLException;

public class MainForCreatingDatabaseWithConstraint {
 public static void main(String[] args) throws SQLException {
   var create = new CreateSQLTablesWithConstraints();
   create.createDatabase();
   create.createSubredditTable();
   create.createLinkTable();
   create.createCommentTable();
   System.out.println("Database and Tables Created With Full Constraint");

 }
}
