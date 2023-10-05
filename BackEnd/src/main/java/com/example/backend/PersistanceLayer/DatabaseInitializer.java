package com.example.backend.PersistanceLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS rental (\n"
                + "  id MEDIUMINT NOT NULL AUTO_INCREMENT,\n"
                + "  link VARCHAR(2083),\n"
                + "  suite VARCHAR(20),\n"
                + "  price INT,\n"
                + "  location VARCHAR(20),\n"
                + "  PRIMARY KEY (id)\n"
                + ");";
        jdbcTemplate.execute(createTableSQL);
        System.out.println("Table created successfully!");
    }

    public void dropTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS rental";
        jdbcTemplate.execute(dropTableSQL);
        System.out.println("Table dropped successfully!");
    }

    public void truncateTable() {
        String truncateTableSQL = "TRUNCATE TABLE rental";
        jdbcTemplate.execute(truncateTableSQL);
        System.out.println("Table truncated successfully!");
    }
}
