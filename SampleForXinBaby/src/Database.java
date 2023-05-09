import java.sql.* ;  // for standard JDBC programs

import org.apache.commons.dbcp2.BasicDataSource;

import java.math.* ; // for BigDecimal and BigInteger support

public class Database {
	private Connection conn;
	private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
	private static String URL = "jdbc:mysql://localhost:3306/javaFinal";
	private static String USER = "javaUser";
	private static String PASS = "java";
	private static BasicDataSource dataSource = null;
	static {
		dataSource = new BasicDataSource();
		dataSource.setUrl(URL);
		dataSource.setUsername(USER);
		dataSource.setPassword(PASS);

		dataSource.setMinIdle(5);
		dataSource.setMaxIdle(10);
		dataSource.setMaxTotal(25);

	}
	
	public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
	
	public Database() {
//		try {
//			conn = DriverManager.getConnection(URL, USER, PASS);
//			System.out.println("Connected database successfully!");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Connection Error!");
//		}
	}
	public void insertRental(String link, String suite, int price, String location) {
		try {
			conn = dataSource.getConnection();
			String strQuery = "insert into rental (link, suite, price, location) values (?, ?, ?, ?)";
			preparedStatement = conn.prepareStatement(strQuery);
			preparedStatement.setString(1, link);
			preparedStatement.setString(2, suite);
			preparedStatement.setInt(3, price);
			preparedStatement.setString(4, location);
//			preparedStatement.execute();
			int count = preparedStatement.executeUpdate();
			if (count > 0) // <-- something like this.
				System.out.println("Insert success");
			else 
				System.out.println("Insert failure");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
    public static void main(String[] args) {
    	Database d = new Database();
    	d.insertRental("http", "1b", 1450, "NYC");
    }
    
    private void close() {
        try {
            if (resultSet != null)
                resultSet.close();
            
            if (statement != null)
                statement.close();
            
            if (preparedStatement != null)
            	preparedStatement.close();
            
            if (conn != null)
            	conn.close();
  
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}

// create database javaFinal;
// CREATE USER 'javaUser'@'localhost' IDENTIFIED WITH authentication_plugin BY 'java';
// GRANT ALL PRIVILEGES ON javaFinal.* TO 'javaUser'@'localhost';