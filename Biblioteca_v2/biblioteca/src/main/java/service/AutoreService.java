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
//path: http://localhost:8080/biblioteca/api/authors
public class AutoreService {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoreService.class.getName());
		
	@GET
	//path: http://localhost:8080/biblioteca/api/authors
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Autore> read_all() {
//	public Response read_all() {
		logger.info("read_all() --> SELECT * FROM autori");
		IAutoreDAO autoreDAO = new AutoreDAO();
		Set<Autore> autori = autoreDAO.read_all();
		
		logger.debug("SELECT executed: " + autori);
		Response.ok(autori.toString()).build();
		return autori;
//		return Response.ok(autori.toString()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(Autore autore) throws URISyntaxException {
		logger.info("insert() --> INSERT INTO autori(ID, NAME, SURNAME) VALUES ({},{},{})", autore.getIdAutore(), autore.getNome(), autore.getCognome());
		IAutoreDAO autoreDAO = new AutoreDAO();
		autoreDAO.create(autore);
		
		logger.debug("INSERT executed");
		return Response.created(new URI("api/authors" + autore.getIdAutore())).build();
	}
	
	@GET
	@Path("{id}")
	//path: http://localhost:8080/biblioteca/api/authors/{id}
	@Produces(MediaType.APPLICATION_JSON)
	public Response read_by_id(@PathParam("id") int id) {
		logger.info("read_by_id() --> SELECT * FROM autori WHERE ID = " + id);
		IAutoreDAO autoreDAO = new AutoreDAO();
		Autore autore = autoreDAO.read(id);
		
		if(autore == null) {
			logger.debug("SELECT by ID not executed: ID={} not found");
//			return Response.noContent().entity("ID not found").build();
			return Response.noContent().build();
		}
		else {
			logger.debug("SELECT by ID executed: " + autore);
			return Response.ok(autore).build();
		}		
	}
	
	@PUT
	public Response update(Autore autore) {
		logger.info("update() --> UPDATE autori SET NAME = {}, SURNAME = {} WHERE ID = {}", autore.getNome(), autore.getCognome(), autore.getIdAutore());
		IAutoreDAO autoreDAO = new AutoreDAO();
		boolean result = autoreDAO.update(autore);
		
		if(result) {
			logger.debug("UPDATE executed");
			return Response.ok(autore).build();
		}
		else {
			logger.debug("UPDATE not executed");
			return Response.notModified().build();
		}
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		logger.info("delete() --> DELETE FROM autori WHERE ID = " + id);
		IAutoreDAO autoreDAO = new AutoreDAO();
		boolean result = autoreDAO.delete(id);
		
		if(result) {
			logger.debug("DELETE executed");
			return Response.ok(id).build();
		}
		else {
			logger.debug("DELETE not executed");
			return Response.notModified().build();
		}
	}
}
