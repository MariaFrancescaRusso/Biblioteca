package service;

import java.net.URI;
import java.net.URISyntaxException;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import model.Autore;
import persistence.IAutoreDAO;

@Path("authors")
public class AutoreService {
	
	@POST
	public Response insert(Autore autore) throws URISyntaxException {
		System.out.println("INSERT");
		IAutoreDAO autoreDAO = null;
		autoreDAO.create(autore);
		System.out.println("autore inserito");
		return Response.created(new URI("api/authors" + autore.getIdAutore())).build();
	}
	
//	@GET
//	public Response read_by_id(@PathParam("{id}") int id) {
//		ILibroDAO libroDAO = null;
//		Libro libro = libroDAO.read(id);
//		
//		return Response.
//	}
	
	@PUT
	public Response update(Autore autore) {
		return Response.noContent().build();
	}
	
	@DELETE
	public Response delete(@PathParam("{id}") int id) {
		System.out.println("DELETE");
		return Response.noContent().build();
	}
}
