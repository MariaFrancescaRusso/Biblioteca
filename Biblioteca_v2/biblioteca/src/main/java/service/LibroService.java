package service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Libro;
//import persistence.DAOFactory;
import persistence.ILibroDAO;
import persistence.LibroDAO;

@Path("books")
public class LibroService {
	
	private static final Logger logger = LoggerFactory.getLogger(LibroService.class.getName());
//	DAOFactory factoryInstance = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
		
	@GET
	//path: http://localhost:8080/biblioteca/api/books
//	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Libro> read_all() {
//	public Response read_all() {
		logger.info("read_all() --> SELECT * FROM libri");
//		ILibroDAO libroDAO = factoryInstance.getLibroDAO();
		ILibroDAO libroDAO = new LibroDAO();
		Set<Libro> libri = libroDAO.read_all();
				
		logger.debug("SELECT executed: " + libri);
		Response.ok(libri.toString()).build();
		return libri;
//		return Response.ok(libri.toString()).build();
	}
	 	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(Libro libro) throws URISyntaxException {
		logger.info("insert() --> INSERT INTO libri(TITLE, AUTHOR, LANGUAGE) VALUES ({},{},{})", libro.getTitolo(), libro.getIdAutore(), libro.getLingua());
		ILibroDAO libroDAO = new LibroDAO();
		libroDAO.create(libro);
		
		logger.debug("INSERT executed");
		return Response.created(new URI("api/books" + libro.getIdLibro())).build();
	}
	
	@GET
	@Path("{id}")
	//path: http://localhost:8080/biblioteca/api/books/{id}
	@Produces(MediaType.APPLICATION_JSON)
	public Response read_by_id(@PathParam("id") int id) {
		logger.info("read_by_id() --> SELECT * FROM libri WHERE ID = " + id);
		ILibroDAO libroDAO = new LibroDAO();
		Libro libro = libroDAO.read(id);
		
		if(libro == null) {
			logger.debug("SELECT by ID not executed: ID={} not found");
//			return Response.noContent().entity("ID not found").build();
//			return Response.noContent().entity("ID not found").type(MediaType.TEXT_HTML).build();
			return Response.noContent().build();			
		}
		else {
			logger.debug("SELECT by ID executed: " + libro);
			return Response.ok(libro).build();
		}
	}
	
	@PUT
	public Response update(Libro libro) {
		logger.info("update() --> UPDATE libri SET TITLE = {}, AUTHOR = {}, LANGUAGE = {} WHERE ID = {}", libro.getTitolo(), libro.getIdAutore(), libro.getLingua(), libro.getIdLibro());
		ILibroDAO libroDAO = new LibroDAO();
		boolean result = libroDAO.update(libro);
		
		if(result) {
			logger.debug("UPDATE executed");
			return Response.ok(libro).build();
		}
		else {
			logger.debug("UPDATE not executed");
			return Response.notModified().build();
		}
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		logger.info("delete() --> DELETE FROM libri WHERE ID = " + id);
		ILibroDAO libroDAO = new LibroDAO();
		boolean result = libroDAO.delete(id);
		
		if(result) {
			logger.debug("UPDATE executed");
			return Response.ok(id).build();
		}
		else {
			logger.debug("UPDATE not executed");
			return Response.notModified().build();
		}
	}
}
