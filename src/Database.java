import java.sql.* ;  // for standard JDBC programs

import org.apache.commons.dbcp2.BasicDataSource;

import java.math.* ; // for BigDecimal and BigInteger support

public class Database {
	private Connection conn;
	private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
	private static String URL = "jdbc:mysql://localhost:3306/apartmentDB";
	private static String USER = "root";
	private static String PASS = "1234";

	public static String getURL() {
		return URL;
	}

	public static String getUSER() {
		return USER;
	}

	public static String getPASS() {
		return PASS;
	}

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
    
    private void close() {
        try {
            if (preparedStatement != null)
            	preparedStatement.close();
            
            if (conn != null)
            	conn.close();
  
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}