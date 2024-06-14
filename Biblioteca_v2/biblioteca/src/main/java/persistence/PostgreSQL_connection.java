package persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSQL_connection extends DAOFactory {
	/* connection elements */
	private static String server = "localhost";
	private static String port = "5432";
	private static String db = "biblioteca";
//	public static final String DBURL = "jdbc:postgresql://localhost:5432/biblioteca";
	public static final String DBURL = "jdbc:postgresql://" + server + ":" + port + "/" + db;
	public static final String USERNAME = "postgres";
	public static final String PASSWORD = "4824";
	
	/* connection creation */
	public static Connection createConnection() {
		try { 
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
		} 
		catch (Exception e) {
			System.err.println(PostgreSQL_connection.class.getName() + ".createConnection(): failed creating connection");
			System.err.println(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	/* connection closure */
	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} 
		catch (Exception e) {
			System.err.println(PostgreSQL_connection.class.getName() + ".closeConnection(): failed closing connection");
			System.err.println(e.toString());
			e.printStackTrace();
		}
	}
	
	/* -------------------------------------------- */
	@Override
	public IAutoreDAO getAutoreDAO() {
		return new AutoreDAO();
	}

	@Override
	public ILibroDAO getLibroDAO() {
		return new LibroDAO();
	}	
}
