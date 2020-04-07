package de.p7s1.qa.sevenfacette.database;

import static org.junit.jupiter.api.Assertions.assertTrue;
import de.p7s1.qa.sevenfacette.db.DbStatements;
import de.p7s1.qa.sevenfacette.db.config.DConfig;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JDatabaseTest {

  static DConfig dbConfig;
  static DbStatements createStatements;
  static DbStatements selectStatements;
  static DbStatements deleteStatements;
  private List<Map<String, Object>> dbResultSet;

  @BeforeAll
  static void setUp() {
    dbConfig = new DConfig();
    dbConfig.setDbDriver(System.getenv("DB_DRIVER"));
    dbConfig.setDbUrl(System.getenv("DB_URL"));
    dbConfig.setDbUser(System.getenv("DB_USER"));
    dbConfig.setDbPW(System.getenv("DB_PW"));
    createStatements = new DbStatements();
    selectStatements = new DbStatements();
    deleteStatements = new DbStatements();
  }
  @Test
  void connectDB() {
    createStatements.add("Statement 1;");
    createStatements.add("Statement 2;");

    System.out.println(createStatements.contains("Statement 2;"));
    System.out.println(createStatements.get(0));
    System.out.println(createStatements.size());

    dbConfig.executeQuery(createStatements);

    selectStatements.add("SELECT * FROM table;");

    dbResultSet = dbConfig.executeQuery(selectStatements);

    deleteStatements.add("Statement 1;");
    deleteStatements.add("DELETE FROM table WHERE ...;");

    dbConfig.executeQuery(deleteStatements);
  }
}
