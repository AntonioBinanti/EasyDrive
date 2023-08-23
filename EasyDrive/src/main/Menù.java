package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class Menù {

	public static void main(String[] args) {
		EasyDrive easyDrive = EasyDrive.getInstance();
		Scanner tastiera = new Scanner(System.in);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		DateTimeFormatter localDateFormat =  DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
		int scelta = 0;
		do {
			System.out.println("\nSeleziona un'operazione: \n1)Gestisci un cliente \n2)Gestisci la programmazione di un esame teorico \n3)Prenota un esame teorico "
					+ "\n4)Prenota un esame finale \n5)Gestisci programmazione lezioni \n6)Aggiorna frequenza clienti \n7)Gestisci programmazione guide \n8)Prenota guida"
					+ "\n9)Aggiorna numero guide sostenute \n10)Pubblica esiti esame teorico \n11)Pubblica esiti esame finale \n12)Gestisci programmazione esame finale");
			try {
                scelta = tastiera.nextInt();
            } catch (NumberFormatException n) {
                scelta = 0;
            } catch (Exception e) {
                System.out.println(e);
            }
			
			switch(scelta) {
				case 0:{
					System.out.println("Uscita");
					break;
				}
				case 1:{ //Gestisci un cliente
					int scelta2 = 0;
					System.out.println("\nGestisci cliente: \n1)Inserisci cliente \n2)Cerca cliente \n3)Rimuovi cliente \n0)Torna al menù");
					try {
		                scelta2 = tastiera.nextInt();
		            } catch (NumberFormatException n) {
		                scelta2 = 0;
		            } catch (Exception e) {
		                System.out.println(e);
		            }
					
					switch(scelta2) {
						case 0:
							break;
						case 1: //Inserisci cliente 
							boolean corretto = true;
							String codiceFiscale, nome, cognome, numeroTelefono, email, indirizzo;
							Date dataNascita = null;
							
							System.out.println("Inserisci il codice fiscale del cliente");
							codiceFiscale = tastiera.next();
							System.out.println("Inserisci il nome del cliente");
							nome = tastiera.next();
							System.out.println("Inserisci il cognome del cliente");
							cognome = tastiera.next();
							do {
								corretto = true;
								System.out.println("Inserisci la data di nascita del cliente (yyyy/mm/dd)");
								String data = tastiera.next();
								try {
									dataNascita = dateFormat.parse(data);
								} catch (ParseException e) {
									//e.printStackTrace();
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							do {
								try {
									System.out.println("Inserisci il numero di telefono del cliente");
									numeroTelefono = tastiera.next();
									if(!numeroTelefono.matches("[0-9]+")) {
										corretto = false ;
										System.out.println("Numero di telefono non valido, riprova");
									}else corretto = true;
								}catch(PatternSyntaxException  e) {
									System.out.println("Numero di telefono non valido, riprova");
									corretto = false;
								}
							}while(!corretto);
							System.out.println("Inserisci l'email del cliente");
							email = tastiera.next();
							System.out.println("Inserisci l'indirizzo del cliente");
							indirizzo = tastiera.next();
							easyDrive.addCliente(codiceFiscale, nome, cognome, dataNascita, cognome, email, indirizzo);
							System.out.println("Il cliente " + nome + " " + cognome + " è stato inserito corretammente");
							break;
							
						case 2: //Cerca cliente
							Cliente c;
							System.out.println("Inserisci il codice fiscale del cliente da cercare (Premere '0' per tornare indietro)");
							codiceFiscale = tastiera.next();
							if(codiceFiscale.equals("0")) {
								break;
							}else {
								try {
									c = easyDrive.getCliente(codiceFiscale);
									System.out.println(c.toString());
								}catch(Exception e) {
									System.out.println(e.getMessage());
								}
								break;
							}
						case 3: //Rimuovi cliente
							System.out.println("Inserisci il codice fiscale del cliente da rimuovere (Premere '0' per tornare indietro)");
							codiceFiscale = tastiera.next();
							if(codiceFiscale.equals("0")){
								break;
							}else {
								try {
									easyDrive.removeCliente(codiceFiscale);
								} catch (Exception e) {
									e.printStackTrace();
								}
								break;
							}
					}
				}
				case 2:{ //Gestisci un esame teorico
					int scelta2 = 0;
					System.out.println("\nGestisci esame teorico: \n1)Inserisci esame teorico \n2)Cerca esame teorico \n3)Rimuovi esame teorico \n0)Torna al menù");
					try {
		                scelta2 = tastiera.nextInt();
		            } catch (NumberFormatException n) {
		                scelta2 = 0;
		            } catch (Exception e) {
		                System.out.println(e);
		            }
					
					switch(scelta2) {
					case 0:
						break;
					case 1: //Inserisci esame teorico 
						boolean corretto = true;
						String data, ora;
						LocalDate localData = null;
						LocalTime localOra = null;
						do {
							corretto = true;
							System.out.println("Inserisci la data dell'esame teorico (yyyy/MM/dd)");
							data = tastiera.next();
							try {
								localData = LocalDate.parse(data, localDateFormat);
							} catch (DateTimeParseException e) {
								//e.printStackTrace();
								System.out.println("Data non valida, riprova");
								corretto = false;
							}
						}while(!corretto);
						do {
							corretto = true;
							System.out.println("Inserisci l'ora dell'esame teorico (HH:mm)");
							ora = tastiera.next();
							try {
								localOra = LocalTime.parse(ora);
							} catch (DateTimeParseException  e) {
								//e.printStackTrace();
								System.out.println("Ora non valida, riprova");
								corretto = false;
							}
						}while(!corretto);
						System.out.println("L'esame teorico è stato inserito correttamente con data " + data + " " + ora);
						break;
						
					/*case 2: //Cerca cliente
						Cliente c;
						System.out.println("Inserisci il codice fiscale del cliente da cercare (Premere '0' per tornare indietro)");
						codiceFiscale = tastiera.next();
						if(codiceFiscale.equals("0")) {
							break;
						}else {
							try {
								c = easyDrive.getCliente(codiceFiscale);
								System.out.println(c.toString());
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						}
					case 3: //Rimuovi cliente
						System.out.println("Inserisci il codice fiscale del cliente da rimuovere (Premere '0' per tornare indietro)");
						codiceFiscale = tastiera.next();
						if(codiceFiscale.equals("0")){
							break;
						}else {
							try {
								easyDrive.removeCliente(codiceFiscale);
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}*/
					}
				}
			}
		}while (scelta != 0);
	}

}
