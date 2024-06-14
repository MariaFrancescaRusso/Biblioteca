package service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

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
import persistence.DAOFactory;
import persistence.ILibroDAO;
import persistence.LibroDAO;

@Path("books")
public class LibroService {
		
//	public LibroService() {
//		System.out.println("---------- Libro Service --------------------");
//	}

//	@GET
////	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response read_all() {
//		System.out.println("--------- get Books -------------"); 
//		ILibroDAO libroDAO = new LibroDAO(); 
//		Set<Libro> libri = libroDAO.read_all();
//  
//		return Response.ok(libri).build();
//	}
	
	@GET
//	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Libro> read_all() {
		System.out.println("--------- get Books -------------"); 
		DAOFactory factoryInstance = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
		ILibroDAO libroDAO = factoryInstance.getLibroDAO();
		Set<Libro> libri = libroDAO.read_all();
  
		return libri;
	}
	 	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(Libro libro) throws URISyntaxException {
		System.out.println("INSERT");
		ILibroDAO libroDAO = new LibroDAO();
		libroDAO.create(libro);
		System.out.println("libro inserito");
		return Response.created(new URI("api/books" + libro.getIdLibro())).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response read_by_id(@PathParam("{id}") int id) {
		System.out.println("get-by-id");
		ILibroDAO libroDAO = new LibroDAO();
		Libro libro = libroDAO.read(id);
		
		return Response.ok(libro).build();
	}
	
	@PUT
	public Response update(Libro libro) {
		System.out.println("UPDATE");
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("{id}") int id) {
		System.out.println("DELETE");
		return Response.noContent().build();
	}
}
