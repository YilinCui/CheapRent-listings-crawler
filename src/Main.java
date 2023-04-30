import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JTextArea textArea;
    private JButton button;

    static final int MAX_T = 8;
    static ArrayList<WebCrawler> tasks = new ArrayList<>();

    public Main() {
        // Setup the JFrame
        setTitle("Web Crawler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);

        // Create the JTextField
        textField = new JTextField("https://www.apartments.com/");
        add(textField, BorderLayout.NORTH);

        // Create the JTextArea
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        textArea.append("Click the Button to start");
        // Create the JButton
        button = new JButton("Start Crawling");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCrawling();
            }
        });
        add(button, BorderLayout.SOUTH);

        // Show the JFrame
        setVisible(true);
    }

    public void startCrawling() {
        // Call your crawling method here and update the JTextArea
        // Example:
        String baseURL = textField.getText().trim();
        appendToTextArea("Crawling: " + baseURL + "\n");
        initDB();

        String area;
        int pageNum;

//		area = "new-york-ny";
//		pageNum = 28;
//		createTasks(baseURL + area, area, pageNum);
		
//        pageNum = 28;
//        area = "brooklyn-ny";
//        createTasks(baseURL + area, area, pageNum);
////      873
//        
//        pageNum = 28;
//        area = "queens-ny";
//        createTasks(baseURL + area, area, pageNum);
////      902
//        
//        pageNum = 23;
//        area = "flushing-ny";
//        createTasks(baseURL + area, area, pageNum);
////      548
//		
	    pageNum = 28;
	    area = "jersey-city-nj";
	    createTasks(baseURL + area, area, pageNum);
//	    1592
//	      
//	    pageNum = 8;
//	    area = "hoboken-nj";
//	    createTasks(baseURL + area, area, pageNum);
////      310
//		
//	    pageNum = 10;
//	    area = "mountain-view-ca";
//	    createTasks(baseURL + area, area, pageNum);
////      735
//	      
//        pageNum = 14;
//	    area = "sunnyvale-ca";
//	    createTasks(baseURL + area, area, pageNum);
////      1158
//		  
//	    pageNum = 28;
//	    area = "san-jose-ca";
//	    createTasks(baseURL + area, area, pageNum);
////	    2566

        // Start crawling in a separate thread to avoid blocking the Event Dispatch Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
                for (Runnable task : tasks)
                    pool.execute(task);

                pool.shutdown();

                try {
                    pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                } catch (InterruptedException e) {

                }
                printResult();
            }
        }).start();
    }


    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new Main();
//            }
//        });
    	new Main();
    }

    private void createTasks(String url, String area, int pageNum) {
        url = url + "/";
        for (int i = 1; i <= pageNum; i++) {
            WebCrawler r = new WebCrawler(url + String.valueOf(i), i, area, textArea);
            tasks.add(r);
        }
    }

    private void initDB() {
        String DB_URL = "jdbc:mysql://localhost:3306/";
        String USER = "javaUser";
        String PASS = "java";
        Connection conn;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS javaFinal";
            stmt.executeUpdate(sql);
            appendToTextArea("Database created successfully!\n");

            DB_URL = "jdbc:mysql://localhost:3306/javaFinal";
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // Check and drop the table if it exists
            sql = "DROP TABLE IF EXISTS rental";
            stmt.executeUpdate(sql);
            // Create the table
            sql = "CREATE TABLE IF NOT EXISTS rental \n"
                    + "(\n"
                    + "  id MEDIUMINT NOT NULL AUTO_INCREMENT,\n"
                    + "  link VARCHAR(2083),\n"
                    + "  suite VARCHAR(20),\n"
                    + "  price INT,\n"
                    + "  location VARCHAR(20),\n"
                    + "  PRIMARY KEY (id)\n"
                    + ");\n"
                    + "";
            stmt.executeUpdate(sql);
            appendToTextArea("Table created successfully!\n");
            sql = "truncate table rental";
            stmt.executeUpdate(sql);
            appendToTextArea("Initialized successfully!\n");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            appendToTextArea("Error initializing the database: " + e.getMessage() + "\n");
        }
    }

    private void printResult() {
        String DB_URL = "jdbc:mysql://localhost:3306/";
        String USER = "javaUser";
        String PASS = "java";
        Connection conn;
        try {
            DB_URL = "jdbc:mysql://localhost:3306/javaFinal";
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            stmt = conn.createStatement();
            String sql;
            sql = "select * from rental";
            ResultSet rs = stmt.executeQuery(sql);
            int cnt = 0;
            while (rs.next())
                cnt++;
            appendToTextArea("Total rentals: " + cnt + "\n");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            appendToTextArea("Error printing the results: " + e.getMessage() + "\n");
        }
    }

    private void appendToTextArea(String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textArea.append(text);
            }
        });
    }
}

