import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Query {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/javaFinal";
    private static final String USER = "javaUser";
    private static final String PASS = "java";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Location Query");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel label = new JLabel("Location Name:");
        label.setBounds(10, 20, 100, 25);
        panel.add(label);

        JTextField textField = new JTextField(20);
        textField.setBounds(120, 20, 165, 25);
        panel.add(textField);

        JButton button = new JButton("Search");
        button.setBounds(10, 60, 80, 25);
        panel.add(button);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 100, 365, 150);
        panel.add(scrollPane);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String location = textField.getText();
                if (!location.isEmpty()) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        Statement stmt = conn.createStatement();

                        String sql = "SELECT min(price) as avg_price FROM Rental WHERE location = '" + location + "'";
                        ResultSet rs = stmt.executeQuery(sql);

                        textArea.setText("");
                        while (rs.next()) {
                            String result = rs.getString("avg_price");
                            textArea.append(result + "\n");
                        }

                        rs.close();
                        stmt.close();
                        conn.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        textArea.setText("Query failed, please check the database connection information and query statement.");
                    }
                } else {
                    textArea.setText("Please enter the location name.");
                }
            }
        });
    }
}
