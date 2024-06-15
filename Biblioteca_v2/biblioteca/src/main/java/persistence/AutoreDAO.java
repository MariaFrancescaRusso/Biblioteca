package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.logging.Logger;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.persistence.sessions.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Autore;

public class AutoreDAO implements IAutoreDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoreDAO.class.getName());
//	Logger logger = Logger.getLogger(getClass().getCanonicalName());
	
	/* Literal constants */
	static final String TABLE = "autori";
	static final String ID = "id";
	static final String NAME = "nome";
	static final String SURNAME = "cognome";

	/* Statement SQL */
	/* --- INSERT INTO TABLE (ID, ...) VALUES (?, ...); --- */
	static final String insert = "INSERT INTO " + TABLE + "(" + ID + ", " + NAME + ", " + SURNAME + ") VALUES (?,?,?)";
	/* --- SELECT * FROM TABLE; --- */
	static String read_all = "SELECT * FROM " + TABLE;
	/* --- SELECT * FROM TABLE WHERE ID = ?; --- */
	static String read_by_id = "SELECT * FROM " + TABLE + " WHERE " + ID + " = ?";
	/* --- UPDATE TABLE SET column = ?, ... WHERE ID = ?; --- */
	static String update = "UPDATE " + TABLE + " SET " + NAME + " = ?, " + SURNAME + " = ? WHERE " + ID + " = ?";
	/* --- DELETE FROM TABLE WHERE ID = ?; --- */
	static String delete = "DELETE FROM " + TABLE + " WHERE " + ID + " = ?";
	/* --- DROP TABLE --- */
	static String drop = "DROP TABLE " + TABLE + " CASCADE";
	/* --- CREATE TABLE (columns... ) --- */
	static String create = "CREATE TABLE " + TABLE + " ( " + 
							ID + " INT PRIMARY KEY, " + 
							NAME + " VARCHAR NOT NULL, " + 
							SURNAME + " VARCHAR NOT NULL )";
	
	/*CRUD Methods*/
	@Override
	public void create(Autore autore) {
		logger.info("create() --> INSERT: " + insert);
		
		/* connection creation */
		Connection connection = PostgreSQL_connection.createConnection();
		
		try {
			/* statement execution */
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.clearParameters();
			statement.setInt(1, autore.getIdAutore());
			statement.setString(2, autore.getNome());
			statement.setString(3, autore.getCognome());
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
	public Set<Autore> read_all() {
		logger.info("read_all() --> SELECT: " + read_all);
		
		Set<Autore> result = new HashSet<Autore>();
		
		/* connection creation */
		Connection connection = PostgreSQL_connection.createConnection();
		
		try {
			/* statement execution */
			Statement statement = connection.createStatement();
			ResultSet res =statement.executeQuery(read_all);		
			
			
			while(res.next()) {
				Autore entry = new Autore();
				entry.setIdAutore(res.getInt(ID));
				entry.setNome(res.getString(NAME));
				entry.setCognome(res.getString(SURNAME));
				
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
	public Autore read(int id) {
		logger.info("read_by_id() --> SELECT: " + read_by_id);
		
		Autore result = null;
		
		/* connection creation */
		Connection connection = PostgreSQL_connection.createConnection();
		
		try {
			/* statement execution */
			PreparedStatement statement = connection.prepareStatement(read_by_id);
			statement.clearParameters();
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			
			if(res.next()) {
				Autore entry = new Autore();
				entry.setIdAutore(res.getInt(ID));
				entry.setNome(res.getString(NAME));
				entry.setCognome(res.getString(SURNAME));
				
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
	public boolean update(Autore autore) {
		logger.info("update() --> UPDATE: " + update);
		
		boolean result = false;
		
		/* connection creation */
		Connection connection = PostgreSQL_connection.createConnection();
		
		try {
			/* statement execution */
			PreparedStatement statement = connection.prepareStatement(update);
			statement.clearParameters();
			statement.setInt(1, autore.getIdAutore());
			statement.setString(2, autore.getNome());
			statement.setString(3, autore.getCognome());
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
