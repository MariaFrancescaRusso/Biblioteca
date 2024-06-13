package persistence;

import java.util.Set;

import model.Libro;

public interface ILibroDAO {
	/* CRUD Methods */
	public void create(Libro libro);
	public  Set<Libro> read_all();
	public  Libro read(int id);
	public boolean update(Libro libro);
	public boolean delete(int id);
	
	/* other methods */
	public boolean dropTable();
	public boolean createTable();
}
