package persistence;

import model.Libro;

public interface ILibroDAO {
	/* CRUD Methods */
	public void create(Libro libro);
	public Libro read(int id);
	public boolean update(Libro libro);
	public boolean delete(int id);
	
	/* other methods */
	public boolean dropTable();
	public boolean createTable();
}
