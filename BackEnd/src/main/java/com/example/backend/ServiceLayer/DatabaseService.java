package com.example.backend.ServiceLayer;

import com.example.backend.PersistanceLayer.DatabaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Autowired
    private DatabaseInitializer databaseInitializer;

    public void createTable() {
        databaseInitializer.createTable();
    }

    public void dropTable() {
        databaseInitializer.dropTable();
    }

    public void truncateTable() {
        databaseInitializer.truncateTable();
    }
}
