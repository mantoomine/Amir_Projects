package JSONToDatabase;

import java.sql.SQLException;

public class MainForCreatingDatabaseWithoutConstraint {
 public static void main(String[] args) throws SQLException {
   var create = new CreateSQLTablesWithoutConstraints();
   create.createDatabase();
   create.createSubredditTable();
   create.createLinkTable();
   create.createCommentTable();
   System.out.println("Database and Tables Created Without any Constraint");

 }
}
