package app.biblioteca;

import model.Autore;
import model.Libro;
import persistence.DAOFactory;
import persistence.IAutoreDAO;
import persistence.ILibroDAO;

public class Main {
	
	/* choose DB */
	public static final int FACTORY = DAOFactory.POSTGRESQL;
	
	public static void main(String[] args) {
		
		/* factory for DB chosen */
		DAOFactory factoryInstance = DAOFactory.getDAOFactory(FACTORY);
		
		/* authors */
		/* - CREATE/DROP table */
		IAutoreDAO autoriDAO = factoryInstance.getAutoreDAO();
		autoriDAO.dropTable();
		autoriDAO.createTable();
		/* - INSERT of entries */
		Autore autore1 = new Autore();
		autore1.setIdAutore(1);
		autore1.setNome("Oscar");
		autore1.setCognome("Wilde");
		autoriDAO.create(autore1);
		
		/* books */
		/* - CREATE/DROP table */
		ILibroDAO libriDAO = factoryInstance.getLibroDAO();
		libriDAO.dropTable();
		libriDAO.createTable();
		/* - INSERT of entries */
		Libro libro1 = new Libro();
		libro1.setIdLibro(1);
		libro1.setTitolo("Il gigante egoista e altri racconti");
		libro1.setIdAutore(1);
		libro1.setLingua("italiano");
		libriDAO.create(libro1);
		
		/* Test Query */
		
	}
}
