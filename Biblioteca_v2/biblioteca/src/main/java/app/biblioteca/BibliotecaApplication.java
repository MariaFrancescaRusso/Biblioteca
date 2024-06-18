package app.biblioteca;

import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("api")
//path: http://localhost:8080/biblioteca/api
public class BibliotecaApplication extends ResourceConfig {
	
	public BibliotecaApplication() {	
		System.out.println("---------------- Bilbioteca Application ----------------");		
		packages("service");
	}
}
