package JSONToDatabase;

import java.io.IOException;

public class MainForDataInserting {
  public static void main(String[] args) throws IOException {
    long startTime = System.nanoTime();

    var readFile = new SQLTablesConfig();
//     readFile.createTables("RC_2007-10.json");
  //  readFile.createTables("RC_2011-07.json");
 //   readFile.createTables("RC_2012-12.json");

    long endTime = System.nanoTime();
    long timeElapsed = endTime - startTime;
    System.out.println("Execution time in nanoseconds: " + timeElapsed);
    System.out.println("Execution time in seconds: "
        + timeElapsed / 1000000000);
  }
}
