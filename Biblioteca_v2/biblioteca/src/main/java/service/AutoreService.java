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
import model.Autore;
import persistence.AutoreDAO;
import persistence.IAutoreDAO;

@Path("authors")
public class AutoreService {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoreService.class.getName());
	
	@GET
//	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Autore> read_all() {
		logger.info("---------------- read_all Authors ----------------");
		IAutoreDAO autoreDAO = new AutoreDAO(); 
		Set<Autore> autori = autoreDAO.read_all();
  
		return autori;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(Autore autore) throws URISyntaxException {
		System.out.println("INSERT");
		IAutoreDAO autoreDAO = new AutoreDAO();
		autoreDAO.create(autore);
		System.out.println("autore inserito");
		return Response.created(new URI("api/authors" + autore.getIdAutore())).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response read_by_id(@PathParam("{id}") int id) {
		System.out.println("get-by-id");
		IAutoreDAO autoreDAO = new AutoreDAO();
		Autore autore = autoreDAO.read(id);
		
		return Response.ok(autore).build();
	}
	
	@PUT
	public Response update(Autore autore) {
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("{id}") int id) {
		System.out.println("DELETE");
		return Response.noContent().build();
	}
}
