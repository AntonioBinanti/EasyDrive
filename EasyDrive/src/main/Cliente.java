package main;

import java.time.LocalDate;
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
	private boolean foglioRosa;
	private int numeroBocciature;
	private int numeroGuide;
	private boolean patente;
	private int numeroBocciatureEsameFinale;
	
	public Cliente(String codiceFiscale, String nome, String cognome, Date dataNascita, String numeroTelefono,
			String email, String indirizzo) {
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.numeroTelefono = numeroTelefono;
		this.email = email;
		this.indirizzo = indirizzo;
		this.argomentiSeguiti = new ArrayList<>();
		this.frequenzaLezioni = 0;
		this.patente=false;
		this.numeroBocciatureEsameFinale=0;
	}
	
	public void aggiornaFrequenzaLezioni(int numArgomentiSeguiti, int numArgomentiTotali) {
		this.frequenzaLezioni = (float) numArgomentiSeguiti/numArgomentiTotali*100;
	}
	
	public void incrementaFrequenzaLezioni(Argomento argomento, int numArgomentiTotali) throws Exception {
		boolean b;
		int numArgomentiSeguiti;
		b= this.argomentiSeguiti.contains(argomento);
		if(b == false) {
			this.argomentiSeguiti.add(argomento);
			numArgomentiSeguiti = this.argomentiSeguiti.size();
			this.aggiornaFrequenzaLezioni(numArgomentiSeguiti, numArgomentiTotali);
			System.out.println("La frequenza lezioni è stata aggiornata"); // la frequenza viene aggiornata solo se l'argomento non è stato seguito
		}else {
			System.out.println("L'argomento: " + argomento.getDescrizione() + " è stato già seguito, la frequenza lezioni non è stata aggiornata");
			throw new Exception("L'argomento è stato già seguito, la frequenza lezioni non è stata aggiornata");
		}
	}
	
	public void incrementaNumeroBocciature() {
		this.numeroBocciature ++;
		System.out.println("Numero bocciature aggiornate");
	}
	
	public void aggiornaNumeroGuide() {
		this.numeroGuide ++;
		System.out.println("Numero guide aggiornate");
	}

	public int incrementaNumeroBocciatureEsameFinale() {
		this.numeroBocciatureEsameFinale ++;
		System.out.println("Numero bocciature esame finale aggiornate");
		return this.numeroBocciatureEsameFinale;
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
	
	public void setFrequenzaLezioni(float frequenzaLezioni) {
		this.frequenzaLezioni = frequenzaLezioni;
	}

	public boolean getFoglioRosa() {
		return foglioRosa;
	}

	public void setFoglioRosa(boolean foglioRosa) {
		this.foglioRosa = foglioRosa;
	}

	public int getNumeroBocciature() {
		return numeroBocciature;
	}

	public void setNumeroBocciature(int numeroBocciature) {
		this.numeroBocciature = numeroBocciature;
	}
	
	public int getNumeroGuide() {
		return numeroGuide;
	}

	public void setNumeroGuide(int numeroGuide) {
		this.numeroGuide = numeroGuide;
	}

	public boolean getPatente() {
		return patente;
	}

	public void setPatente(boolean patente) {
		this.patente = patente;
	}

	public int getNumeroBocciatureEsameFinale() {
		return numeroBocciatureEsameFinale;
	}

	public void setNumeroBocciatureEsameFinale(int numeroBocciatureEsameFinale) {
		this.numeroBocciatureEsameFinale = numeroBocciatureEsameFinale;
	}

	@Override
	public String toString() {
		return "Cliente [codiceFiscale=" + codiceFiscale + ", nome=" + nome + ", cognome=" + cognome + ", dataNascita="
				+ dataNascita + ", numeroTelefono=" + numeroTelefono + ", email=" + email + ", indirizzo=" + indirizzo
				+ ", frequenzaLezioni=" + frequenzaLezioni + ", argomentiSeguiti=" + argomentiSeguiti + ", foglioRosa="
				+ foglioRosa + ", numeroBocciature=" + numeroBocciature + ", numeroGuide=" + numeroGuide + ", patente="
				+ patente + ", numeroBocciatureEsameFinale=" + numeroBocciatureEsameFinale + "]";
	}

	
	
}
