package model;

import java.util.Objects;

public class Autore 
{
	private int idAutore;
	private String nome;
	private String cognome;
	
	public int getIdAutore() {
		return idAutore;
	}
	
	public void setIdAutore(int idAutore) {
		this.idAutore = idAutore;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cognome, idAutore, nome);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autore other = (Autore) obj;
		return Objects.equals(cognome, other.cognome) && idAutore == other.idAutore && Objects.equals(nome, other.nome);
	}
	
	@Override
	public String toString() {
		return "Autore [idAutore=" + idAutore + ", nome=" + nome + ", cognome=" + cognome + "]";
	}
}
