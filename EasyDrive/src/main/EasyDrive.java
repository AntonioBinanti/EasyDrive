package main;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EasyDrive {
	
	private static EasyDrive easyDrive;
	private Map<String, Lezione> elencoLezioni;
	private Lezione lezioneCorrente;
	private ArrayList<Argomento> listaArgomenti;
	private Map<String, Cliente> listaClienti;
	private Cliente clienteCorrente;
	
	private EasyDrive() {
		this.elencoLezioni = new HashMap<>();
		this.listaArgomenti = new ArrayList<>();
		this.listaClienti = new HashMap<>();
		loadArgomenti();
	}
	
	public static EasyDrive getInstance() {
		if (easyDrive == null)
			easyDrive = new EasyDrive();
		else
			System.out.println("Istanza già creata");

		return easyDrive;
	}
	
	public void addCliente(String codiceFiscale, String nome, String cognome, Date dataNascita, String numeroTelefono, String email, String indirizzo) {
		Cliente c = new Cliente(codiceFiscale, nome, cognome, dataNascita, numeroTelefono, email, indirizzo);
		this.listaClienti.put(codiceFiscale, c);
		System.out.println("Cliente "+ c.getNome() + " " + c.getCognome() + " inserito correttamente");
	}
	
	public Cliente getCliente(String codiceFiscale) {
		Cliente c = this.listaClienti.get(codiceFiscale);
		if(c == null) {
			System.out.println("Nessun cliente trovato con il codice fiscale selezionato");
			return null;
		}else {
		return c;
		}
	}
	
	public void removeCliente(String codiceFiscale) {
		this.listaClienti.remove(codiceFiscale);
	}
	
	public void addLezione(Date data, Time ora, Argomento argomentoTrattato) {
		Lezione l = new Lezione(data, ora, argomentoTrattato);
		this.elencoLezioni.put(l.getCodice(), l);
		System.out.println("Lezione inserita correttamente in data: " + l.getData().toString() + " e ora: " + l.getOra().toString());
	}
	
	public Lezione getLezione(Date data, Time ora) {
		String dataOra = data.toString() + ora.toString();
		Lezione l = this.elencoLezioni.get(dataOra);
		if(l == null) {
			System.out.println("Nessun cliente trovato con il codice fiscale selezionato");
			return null;
		}else {
		return l;
		}
	}
	
	public void removeLezione(Date data, Time ora) {
		String dataOra = data.toString() + ora.toString();
		this.elencoLezioni.remove(dataOra);
	}
	
	public void aggiornaFrequenzaClienti(Date data, Time ora) {
		String dataOra = data.toString() + ora.toString();
		Lezione l = this.elencoLezioni.get(dataOra);
		lezioneCorrente = l;
	}
	
	public void inserisciClienteFrequentante(String codiceFiscale) {
		if(this.lezioneCorrente != null) {
			this.clienteCorrente = this.listaClienti.get(codiceFiscale);
		}	
	}
	
	public void confermaInserimento() {
		if(this.clienteCorrente != null) {
			Argomento argomento = this.lezioneCorrente.getArgomentoTrattato();
			int numArgomentiTotali = this.listaArgomenti.size();
			this.clienteCorrente.incrementaFrequenzaLezioni(argomento, numArgomentiTotali);
		}
	}
	
	public void loadArgomenti() {
		Argomento a1 = new Argomento("Segnali di pericolo");
		Argomento a2 = new Argomento("Segnali di divieto");
		Argomento a3 = new Argomento("Segnali di obbligo");
		Argomento a4 = new Argomento("Segnali di precedenza");
		Argomento a5 = new Argomento("Semafori");
		Argomento a6 = new Argomento("Distanza di sicurezza");
		Argomento a7 = new Argomento("Norme di circolazione dei veicoli");
		Argomento a8 = new Argomento("Precedenza negli incroci");
		Argomento a9 = new Argomento("Norme di sorpasso");
		Argomento a10 = new Argomento("Cinture di sicurezza e altri dispositivi");
		this.listaArgomenti.add(a1);
		this.listaArgomenti.add(a2);
		this.listaArgomenti.add(a3);
		this.listaArgomenti.add(a4);
		this.listaArgomenti.add(a5);
		this.listaArgomenti.add(a6);
		this.listaArgomenti.add(a7);
		this.listaArgomenti.add(a8);
		this.listaArgomenti.add(a9);
		this.listaArgomenti.add(a10);
		System.out.println("Caricamento argomenti completato");
	}

	public Map<String, Lezione> getElencoLezioni() {
		return elencoLezioni;
	}

	public Lezione getLezioneCorrente() {
		return lezioneCorrente;
	}

	public ArrayList<Argomento> getListaArgomenti() {
		return listaArgomenti;
	}

	public Map<String, Cliente> getListaClienti() {
		return listaClienti;
	}

	public Cliente getClienteCorrente() {
		return clienteCorrente;
	}
	
}
