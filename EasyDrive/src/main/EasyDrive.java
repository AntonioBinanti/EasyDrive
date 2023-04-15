package main;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
	private Map<String, EsameTeorico> elencoEsamiTeorici;
	private EsameTeorico esameTeoricoCorrente;
	
	private EasyDrive() {
		this.elencoLezioni = new HashMap<>();
		this.listaArgomenti = new ArrayList<>();
		this.listaClienti = new HashMap<>();
		this.elencoEsamiTeorici = new HashMap<>();
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
		if(this.listaClienti.remove(codiceFiscale) != null){
			System.out.println("Cliente eliminato correttamente");
		}else {
			System.out.println("Impossibile rimuovere il cliente con il codice fiscale selezionato");
		}
	}
	
	public void addLezione(/*Date data, Time ora*/ LocalDate data, LocalTime ora, Argomento argomentoTrattato) {
		Lezione l = new Lezione(/*data, ora*/ data, ora, argomentoTrattato);
		this.elencoLezioni.put(l.getCodice(), l);
		System.out.println("Lezione inserita correttamente in data: " + l.getData().toString() + " e ora: " + l.getOra().toString());
	}
	
	public Lezione getLezione(LocalDate data, LocalTime ora) {
		//String dataOra = data.toString() + ora.toString();
		String dataOra = LocalDateTime.of(data, ora).toString();
		Lezione l = this.elencoLezioni.get(dataOra);
		if(l == null) {
			System.out.println("Nessuna lezione trovata con la data e ora selezionate");
			return null;
		}else {
		return l;
		}
	}
	
	public void removeLezione(LocalDate data, LocalTime ora) {
		//String dataOra = data.toString() + ora.toString();
		String dataOra = LocalDateTime.of(data, ora).toString();
		if(this.elencoLezioni.remove(dataOra) != null) {
			System.out.println("Lezione eliminata correttamente");
		}else {
			System.out.println("Impossibile rimuovere la lezione con la data e l'ora selezionate");
		}
	}
	
	public void aggiornaFrequenzaClienti(LocalDate data, LocalTime ora) {
		//String dataOra = data.toString() + ora.toString();
		String dataOra = LocalDateTime.of(data, ora).toString();
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
	
	public void addEsameTeorico(LocalDate data, LocalTime ora) {
		EsameTeorico e = new EsameTeorico(data, ora);
		this.elencoEsamiTeorici.put(e.getCodice(), e);
		System.out.println("Esame teorico fissato correttamente per la data: " + e.getData().toString() + " e ora: " + e.getOra().toString());
	}
	
	public EsameTeorico getEsameTeorico(LocalDate data, LocalTime ora) {
		String dataOra = LocalDateTime.of(data, ora).toString();
		EsameTeorico e = this.elencoEsamiTeorici.get(dataOra);
		if(e == null) {
			System.out.println("Nessun esame teorico trovato con la data e ora selezionate");
			return null;
		}else {
		return e;
		}
	}
	
	public void removeEsameTeorico(LocalDate data, LocalTime ora) {
		String dataOra = LocalDateTime.of(data, ora).toString();
		if(this.elencoEsamiTeorici.remove(dataOra) != null) {
			System.out.println("Esame teorico eliminato correttamente");
		}else {
			System.out.println("Impossibile rimuovere l'esame teorico con la data e l'ora selezionate");
		}
	}
	
	public ArrayList<EsameTeorico> prenotaEsameTeorico() {
		ArrayList<EsameTeorico> esamiTeoriciDisponibili = new ArrayList<>();
		for (Map.Entry<String, EsameTeorico> entry : elencoEsamiTeorici.entrySet()) {
            if(entry.getValue().isDisponibile()){
                esamiTeoriciDisponibili.add(entry.getValue());
            }
        }
		return esamiTeoriciDisponibili;
	}
	
	public void selezionaEsame(LocalDate data, LocalTime ora) {
		this.esameTeoricoCorrente = this.getEsameTeorico(data, ora);
	}
	
	public void inserisciCliente(String codiceFiscale) {
		if(this.esameTeoricoCorrente != null) {
			this.clienteCorrente = this.getCliente(codiceFiscale);
		}
	}
	
	public void confermaPrenotazione() {
		if(this.clienteCorrente != null) {
			this.esameTeoricoCorrente.prenotaCliente(this.clienteCorrente);
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

	public void setLezioneCorrente(Lezione lezioneCorrente) {
		this.lezioneCorrente = lezioneCorrente;
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
	
	public void setClienteCorrente(Cliente clienteCorrente) {
		this.clienteCorrente = clienteCorrente;
	}
	
	public Cliente getClienteCorrente() {
		return clienteCorrente;
	}

	public EsameTeorico getEsameTeoricoCorrente() {
		return esameTeoricoCorrente;
	}

	public void setEsameTeoricoCorrente(EsameTeorico esameTeoricoCorrente) {
		this.esameTeoricoCorrente = esameTeoricoCorrente;
	}

	public Map<String, EsameTeorico> getElencoEsamiTeorici() {
		return elencoEsamiTeorici;
	}
}
