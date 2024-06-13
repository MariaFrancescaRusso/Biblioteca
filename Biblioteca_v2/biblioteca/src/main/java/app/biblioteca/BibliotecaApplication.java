package app.biblioteca;

import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class BibliotecaApplication extends ResourceConfig {

	public BibliotecaApplication() {	
		System.out.println("---------------- Bilbioteca Application ------------");
		packages("service");
	}
}
