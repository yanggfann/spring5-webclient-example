package com.example.demo.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class BatchGenerateSql {

  @Test
  public void generateSql() throws FileNotFoundException {
    PrintWriter writer = new PrintWriter("insertTestTable.sql");
    for (int i = 0; i <= 2; i++) {
      String s = "insert into test (id, location, age) values ('%s', %f, %d);";
      writer.println(String.format(s, UUID.randomUUID(), i * 0.12, 100+i));
    }
    writer.close();
  }

}
