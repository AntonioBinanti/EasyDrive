package main;

public class Argomento {
	
	private String descrizione;

	public Argomento(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "Argomento [descrizione=" + descrizione + "]";
	}

}
