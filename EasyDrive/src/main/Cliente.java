package main;

import java.util.ArrayList;
import java.util.Date;

public class Cliente {
	
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private Date dataNascita;
	private String numeroTelefono;
	private String email;
	private String indirizzo;
	private float frequenzaLezioni;
	private ArrayList<Argomento> argomentiSeguiti;
	
	public Cliente(String codiceFiscale, String nome, String cognome, Date dataNascita, String numeroTelefono,
			String email, String indirizzo) {
		super();
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.numeroTelefono = numeroTelefono;
		this.email = email;
		this.indirizzo = indirizzo;
		this.argomentiSeguiti = new ArrayList<>();
		this.frequenzaLezioni = 0;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public ArrayList<Argomento> getArgomentiSeguiti() {
		return argomentiSeguiti;
	}

	public void setArgomentiSeguiti(ArrayList<Argomento> argomentiSeguiti) {
		this.argomentiSeguiti = argomentiSeguiti;
	}

	public float getFrequenzaLezioni() {
		return frequenzaLezioni;
	}
	
	public void aggiornaFrequenzaLezioni(int numArgomentiSeguiti, int numArgomentiTotali) {
		this.frequenzaLezioni = (float) numArgomentiSeguiti/numArgomentiTotali*100;
	}
	
	public void incrementaFrequenzaLezioni(Argomento argomento, int numArgomentiTotali) {
		boolean b;
		int numArgomentiSeguiti;
		b= this.argomentiSeguiti.contains(argomento);
		if(b == false) {
			this.argomentiSeguiti.add(argomento);
			numArgomentiSeguiti = this.argomentiSeguiti.size();
			this.aggiornaFrequenzaLezioni(numArgomentiSeguiti, numArgomentiTotali);
		}
	}

	@Override
	public String toString() {
		return "Cliente [codiceFiscale=" + codiceFiscale + ", nome=" + nome + ", cognome=" + cognome + ", dataNascita="
				+ dataNascita + ", numeroTelefono=" + numeroTelefono + ", email=" + email + ", indirizzo=" + indirizzo
				+ ", frequenzaLezioni=" + frequenzaLezioni + ", argomentiSeguiti=" + argomentiSeguiti + "]";
	}
	
	
}
