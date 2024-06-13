package persistence;

import java.util.Set;

import model.Autore;

public interface IAutoreDAO {
	/* CRUD Methods */
	public void create(Autore autore);
	public  Set<Autore> read_all();
	public Autore read(int id);
	public boolean update(Autore autore);
	public boolean delete(int id);
	
	/* other methods */
	public boolean dropTable();
	public boolean createTable();
}
