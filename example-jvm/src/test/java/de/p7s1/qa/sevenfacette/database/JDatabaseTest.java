package de.p7s1.qa.sevenfacette.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import de.p7s1.qa.sevenfacette.db.DbStatements;
import de.p7s1.qa.sevenfacette.db.config.DConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JDatabaseTest {

  static DConfig dbConfig;
  static DbStatements dbPreparationStatements;
  static DbStatements dbTearDownStatements;
  static DbStatements insertStatements;
  static DbStatements updateStatements;
  static DbStatements selectStatements;
  static DbStatements deleteStatements;


  /*
  hbm2ddl closes the connection after creating the table, so h2 discards it.
  We have our connection-url configured like this: jdbc:h2:mem:test
  the content of the database is lost at the moment the last connection is closed.
  To keep our content we have to configure the url like this: jdbc:h2:mem:test;DB_CLOSE_DELAY=-1
  H2 will keep its content as long as the jvm lives.
  Notice the semicolong (;) rather than colon (:).
   */
  @BeforeAll
  static void setUp() {
    dbConfig = new DConfig();
    dbConfig.setDbDriver("org.h2.Driver");
    dbConfig.setDbUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

    // Create table
    dbPreparationStatements = new DbStatements();
    dbPreparationStatements.add("create table customer(id integer, name varchar(10))");
    dbConfig.executeQuery(dbPreparationStatements);


    updateStatements = new DbStatements();
    selectStatements = new DbStatements();
    deleteStatements = new DbStatements();
  }

  @BeforeEach
  void prepareData() {
    insertStatements = new DbStatements();
    insertStatements.add("insert into customer values (1, 'Thomas')");
    insertStatements.add("insert into customer values (2, 'Tobias')");
    insertStatements.add("insert into customer values (3, 'Christoph')");
    dbConfig.executeQuery(insertStatements);
  }

  @AfterEach
  void tearDown() {
    dbTearDownStatements = new DbStatements();
    dbTearDownStatements.add("DELETE FROM customer;");
    dbConfig.executeQuery(dbTearDownStatements);

    selectStatements.add("SELECT * FROM customer");
    List<Map<String, Object>> dbResultSet = dbConfig.executeQuery(selectStatements);
  }

  @Test
  @DisplayName("Insert data to database and validates the count")
  void insertData() {
    selectStatements.add("SELECT * FROM customer");
    List<Map<String, Object>> dbResultSet = dbConfig.executeQuery(selectStatements);
    assertNotNull(dbResultSet);
    assertEquals(3, dbResultSet.size());
  }

  @Test
  @DisplayName("Update data and validates it")
  void updateData() {
    selectStatements.add("SELECT * FROM customer");
    List<Map<String, Object>> dbResultSet = dbConfig.executeQuery(selectStatements);
    updateStatements.add("UPDATE customer SET name = 'Manuel' WHERE name = 'Christoph' ");
    dbConfig.executeQuery(updateStatements);
    dbResultSet = dbConfig.executeQuery(selectStatements);
    assertNotNull(dbResultSet);
    assertEquals(3, dbResultSet.size());

    assertEquals(3, Integer.parseInt(dbResultSet.get(2).get("ID").toString()));
    assertEquals("Manuel", dbResultSet.get(2).get("NAME"));
  }

  @Test
  @DisplayName("Delete data and validate it")
  void deleteData() {
    deleteStatements.add("DELETE FROM customer WHERE ID = 2");
    dbConfig.executeQuery(deleteStatements);

    selectStatements.add("SELECT * FROM customer");
    List<Map<String, Object>> dbResultSet = dbConfig.executeQuery(selectStatements);

    assertNotNull(dbResultSet);
    assertEquals(2, dbResultSet.size());
  }

  @Test
  @DisplayName("Validate all data in db with list")
  void validateAllData() {

    selectStatements.add("SELECT * FROM customer");
    List<Map<String, Object>> dbResultSet = dbConfig.executeQuery(selectStatements);

    Map<String, Object> validationMap = new HashMap<>();
    validationMap.put("1", "Thomas");
    validationMap.put("2", "Tobias");
    validationMap.put("3", "Christoph");

    dbResultSet.forEach(result -> {
      result.entrySet().stream().allMatch(entry -> entry.getValue().equals(validationMap.get(
        entry.getKey())));
    });
  }
}
