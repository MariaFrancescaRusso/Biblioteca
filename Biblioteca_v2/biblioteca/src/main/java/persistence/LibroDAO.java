package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
//import java.util.logging.Logger;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Libro;

public class LibroDAO implements ILibroDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(LibroDAO.class.getName());		
//	Logger logger = Logger.getLogger(getClass().getCanonicalName());
	
	/* Literal constants */
	static final String TABLE = "libri";
	static final String ID = "id";
	static final String TITLE = "titolo";
	static final String AUTHOR = "idAutore";
	static final String LANGUAGE = "lingua";

	/* Statement SQL */
	/* --- INSERT INTO TABLE (ID, ...) VALUES (?, ...); --- */
//	static final String insert = "INSERT INTO " + TABLE + "(" + ID + ", " + TITLE + ", " + AUTHOR + ", " + LANGUAGE + ") VALUES (?,?,?,?)";
	static final String insert = "INSERT INTO " + TABLE + "(" + TITLE + ", " + AUTHOR + ", " + LANGUAGE + ") VALUES (?,?,?)";
	/* --- SELECT * FROM TABLE; --- */
	static String read_all = "SELECT * FROM " + TABLE;
	/* --- SELECT * FROM TABLE WHERE ID = ?; --- */
	static String read_by_id = "SELECT * FROM " + TABLE + " WHERE " + ID + " = ?";
	/* --- UPDATE TABLE SET column = ?, ... WHERE ID = ?; --- */
	static String update = "UPDATE " + TABLE + " SET " + TITLE + " = ?, " + AUTHOR + " = ?, " + LANGUAGE + " = ? WHERE " + ID + " = ?";
	/* --- DELETE FROM TABLE WHERE ID = ?; --- */
	static String delete = "DELETE FROM " + TABLE + " WHERE " + ID + " = ?";
	/* --- DROP TABLE --- */
	static String drop = "DROP TABLE " + TABLE;
	/* --- CREATE TABLE (columns... ) --- */
	static String create = "CREATE TABLE " + TABLE + " ( " + 
//							ID + " INT PRIMARY KEY, " + 
							ID + " SERIAL PRIMARY KEY, " + 
							TITLE + " VARCHAR NOT NULL, " + 
							AUTHOR + " INT REFERENCES " + AutoreDAO.TABLE + "(" + AutoreDAO.ID + "), " + 
							LANGUAGE + " VARCHAR NOT NULL )";
	
	 
	/*CRUD Methods*/	
	@Override
	public void create(Libro libro) {
		logger.info("create() --> INSERT: " + insert);
		
		/* connection creation */
		Connection connection = PostgreSQL_connection.createConnection();
		
		try {
			/* statement execution */
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.clearParameters();
//			statement.setInt(1, libro.getIdLibro());
//			statement.setString(2, libro.getTitolo());
//			statement.setInt(3, libro.getIdAutore());
//			statement.setString(4, libro.getLingua());
			statement.setString(1, libro.getTitolo());
			statement.setInt(2, libro.getIdAutore());
			statement.setString(3, libro.getLingua());
			statement.executeUpdate();
			logger.debug("INSERT executed");
			
			statement.close();
		} 
		catch (Exception e) {
			logger.warn("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			/* connection closure */
			PostgreSQL_connection.closeConnection(connection);
		}
	}
	
	@Override
	public Set<Libro> read_all() {
		logger.info("read_all() --> SELECT: " + read_all);
		
		Set<Libro> result = new HashSet<Libro>();
		
		/* connection creation */
		Connection connection = PostgreSQL_connection.createConnection();
		
		try {
			/* statement execution */
			Statement statement = connection.createStatement();
			ResultSet res =statement.executeQuery(read_all);		
			
			
			while(res.next()) {
				Libro entry = new Libro();
				entry.setIdLibro(res.getInt(ID));
				entry.setTitolo(res.getString(TITLE));
				entry.setIdAutore(res.getInt(AUTHOR));
				entry.setLingua(res.getString(LANGUAGE));
				
				result.add(entry);
				logger.debug("SELECT executed");
			}
			
			res.close();
			statement.close();
		} 
		catch (Exception e) {
			logger.warn("read(): failed to read entry: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			/* connection closure */
			PostgreSQL_connection.closeConnection(connection);
		}
		
		return result;
	}
	
	@Override
	public Libro read(int id) {
		logger.info("read_by_id() --> SELECT: " + read_by_id);
		
		Libro result = null;
		
		/* connection creation */
		Connection connection = PostgreSQL_connection.createConnection();
		
		try {
			/* statement execution */
			PreparedStatement statement = connection.prepareStatement(read_by_id);
			statement.clearParameters();
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			
			if(res.next()) {
				Libro entry = new Libro();
				entry.setIdLibro(res.getInt(ID));
				entry.setTitolo(res.getString(TITLE));
				entry.setIdAutore(res.getInt(AUTHOR));
				entry.setLingua(res.getString(LANGUAGE));
				
				result = entry;
				logger.debug("SELECT executed");
			}
			
			res.close();
			statement.close();
		} 
		catch (Exception e) {
			logger.warn("read(): failed to read entry with id = " + id + ": " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			/* connection closure */
			PostgreSQL_connection.closeConnection(connection);
		}
		
		return result;
	}

	@Override
	public boolean update(Libro libro) {
		logger.info("update() --> UPDATE: " + update);
		
		boolean result = false;
		
		/* connection creation */
		Connection connection = PostgreSQL_connection.createConnection();
		
		try {
			/* statement execution */
			PreparedStatement statement = connection.prepareStatement(update);
			statement.clearParameters();
			statement.setInt(1, libro.getIdLibro());
			statement.setString(2, libro.getTitolo());
			statement.setInt(3, libro.getIdAutore());
			statement.setString(4, libro.getLingua());
			statement.executeUpdate();
			
			result = true;
			logger.debug("UPDATE executed");
			
			statement.close();
		} 
		catch (Exception e) {
			logger.warn("update(): failed to update entry: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			/* connection closure */
			PostgreSQL_connection.closeConnection(connection);
		}
		
		return result;
	}

	@Override
	public boolean delete(int id) {
		logger.info("delete() --> DELETE: " + delete);
		
		boolean result = false;
		
		/* connection creation */
		Connection connection = PostgreSQL_connection.createConnection();
		
		try {
			/* statement execution */
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.clearParameters();
			statement.setInt(1, id);
			statement.executeUpdate();
			
			result = true;
			logger.debug("DELETE executed");
			
			statement.close();
		} 
		catch (Exception e) {
			logger.warn("delete(): failed to delete entry with id = " + id + ": " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			/* connection closure */
			PostgreSQL_connection.closeConnection(connection);
		}
		
		return result;
	}
	
	/* other methods */
	@Override
	public boolean dropTable() {
		logger.info("dropTable() --> DROP: " + drop);
		
		boolean result = false;
		
		/* connection creation */
		Connection connection = PostgreSQL_connection.createConnection();
		
		try {
			/* statement execution */
			Statement statement = connection.createStatement();
			statement.execute(drop);
			
			result = true;
			logger.debug("DROP executed");
			
			statement.close();
		} 
		catch (Exception e) {
			logger.warn("dropTable(): failed to drop table " + TABLE + ": " + e.getMessage());
//			e.printStackTrace();
		}
		finally {
			/* connection closure */
			PostgreSQL_connection.closeConnection(connection);
		}
		
		return result;
	}

	@Override
	public boolean createTable() {
		logger.info("createTable() --> CREATE: " + create);
		
		boolean result = false;
		
		/* connection creation */
		Connection connection = PostgreSQL_connection.createConnection();
		
		try {
			/* statement execution */
			Statement statement = connection.createStatement();
			statement.execute(create);
			
			result = true;
			logger.debug("CREATE executed");
			
			statement.close();
		} 
		catch (Exception e) {
			logger.warn("createTable(): failed to create table " + TABLE + ": " + e.getMessage());
//			e.printStackTrace();
		}
		finally {
			/* connection closure */
			PostgreSQL_connection.closeConnection(connection);
		}
		
		return result;
	}
}
