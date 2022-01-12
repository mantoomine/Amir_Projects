package JSONToDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

  public Properties getConnectionData() {

    Properties properties = new Properties();

    String fileName = "src/Assignment2/db.properties";

    try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
      properties.load(fileInputStream);
    } catch (IOException ex) {
      Logger logger = Logger.getLogger(DBConnection.class.getName());
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
    return properties;
  }
}