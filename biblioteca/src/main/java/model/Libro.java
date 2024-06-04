package model;

import java.util.Objects;

public class Libro 
{	
	private int idLibro;
	private String titolo;
	private int idAutore;
	private String lingua;
		
	public int getIdLibro() {
		return idLibro;
	}
	
	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public int getIdAutore() {
		return idAutore;
	}
	
	public void setIdAutore(int idAutore) {
		this.idAutore = idAutore;
	}
	
	public String getLingua() {
		return lingua;
	}
	
	public void setLingua(String lingua) {
		this.lingua = lingua;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idLibro, idAutore, lingua, titolo);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		return idLibro == other.idLibro && idAutore == other.idAutore && Objects.equals(lingua, other.lingua)
				&& Objects.equals(titolo, other.titolo);
	}
	
	@Override
	public String toString() {
		return "Libro [id=" + idLibro + ", titolo=" + titolo + ", idAutore=" + idAutore + ", lingua=" + lingua + "]";
	}
}
