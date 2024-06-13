package persistence;

public abstract class DAOFactory {
	/* Constant '0' for factory PostgreSQL */
	public static final int POSTGRESQL = 0;
	
	/* Given a constant Return the implementation to the relative factory */
	public static DAOFactory getDAOFactory(int factory) {
		switch(factory) {
			case POSTGRESQL: return new PostgreSQL_connection();
			default: return null;
		}
	}
	
	/* Methods to obtain a DAO for each Model class (JavaBean) and mapping */
	public abstract IAutoreDAO getAutoreDAO();
	public abstract ILibroDAO getLibroDAO();
}
