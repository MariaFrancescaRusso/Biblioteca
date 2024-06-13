package service;

import java.net.URI;
import java.net.URISyntaxException;

import jakarta.validation.constraints.AssertFalse.List;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import model.Libro;
import persistence.ILibroDAO;

@Path("books")
public class LibroService {
	
	/*
	 * @GET public List read() {
	 * System.out.println("--------- get Books -------------"); ILibroDAO libroDAO =
	 * null; Libro libro = libroDAO.;
	 * 
	 * return Response.ok(libro).build(); }
	 */
	
	@POST
	public Response insert(Libro libro) throws URISyntaxException {
		System.out.println("INSERT");
		ILibroDAO libroDAO = null;
		libroDAO.create(libro);
		System.out.println("libro inserito");
		return Response.created(new URI("api/books" + libro.getIdLibro())).build();
	}
	
	@GET
	public Response read_by_id(@PathParam("{id}") int id) {
		System.out.println("get-by-id");
		ILibroDAO libroDAO = null;
		Libro libro = libroDAO.read(id);
		
		return Response.ok(libro).build();
	}
	
	@PUT
	public Response update(Libro libro) {
		System.out.println("UPDATE");
		return Response.noContent().build();
	}
	
	@DELETE
	public Response delete(@PathParam("{id}") int id) {
		System.out.println("DELETE");
		return Response.noContent().build();
	}
}
