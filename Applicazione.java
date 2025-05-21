import java.util.Scanner;
import java.io.*;

public class Applicazione {

	public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
		int sceltaUtente;
		double altezzaUtente;
		int anniUtente;
		String genereUtente;
		double pesoUtente = 0;
		double kcalGiornaliere = 0;
		String inizialeGenere;

		try {
			File fileDati = new File("dati.txt");

			if (!fileDati.exists()) {
				fileDati.createNewFile();

				do {
					System.out.print("Inserire la propria altezza (in cm): ");
					altezzaUtente = Double.parseDouble(scanner.nextLine());
					if (altezzaUtente < 0 || altezzaUtente > 270) {
						System.out.println("Inserire un valore valido!");
					}
				} while (altezzaUtente < 0 || altezzaUtente > 270);

				do {
					System.out.print("Inserire la propria età: ");
					anniUtente = Integer.parseInt(scanner.nextLine());
					if (anniUtente < 0) {
						System.out.println("Inserire un valore valido!");
					}
				} while (anniUtente < 0);

				do {
					System.out.print("Inserire sesso (maschio / donna): ");
					genereUtente = scanner.nextLine();
					inizialeGenere = genereUtente.substring(0, 1);
					if (!inizialeGenere.equalsIgnoreCase("m") && !inizialeGenere.equalsIgnoreCase("d")) {
						System.out.println("Inserire un valore valido!");
					}
					if (inizialeGenere.equalsIgnoreCase("m")) {
						genereUtente = "maschio";
					}
					if (inizialeGenere.equalsIgnoreCase("d")) {
						genereUtente = "donna";
					}
				} while (!inizialeGenere.equalsIgnoreCase("m") && !inizialeGenere.equalsIgnoreCase("d"));

			} else {
				Scanner scanDati = new Scanner(new File("dati.txt"));
				String linea;
				String[] datiLetti = new String[5];

				for (int i = 0; i < datiLetti.length; i++) {
					linea = scanDati.nextLine();
					String[] split = linea.split(":");
					datiLetti[i] = split[split.length - 1];
				}

				altezzaUtente = Double.parseDouble(datiLetti[0]);
				anniUtente = Integer.parseInt(datiLetti[1]);
				genereUtente = datiLetti[2];
				pesoUtente = Double.parseDouble(datiLetti[3]);
				kcalGiornaliere = Double.parseDouble(datiLetti[4]);
				scanDati.close();
			}

			do {
				System.out.println();
				System.out.println("INFORMAZIONI:");
				System.out.println("Altezza: " + altezzaUtente + "\nEtà: " + anniUtente + "\nSesso: " + genereUtente + "\nPeso: " + pesoUtente + "\nFabbisogno: " + kcalGiornaliere);
				System.out.println();

				System.out.print("Scegliere operazione da eseguire: \n1. Calcolo peso corporeo \n2. Fabbisogno calorico giornaliero \n3. Controllo giornaliero delle calorie \n4. Reset giornaliero \n5. Cambia informazioni \n6. Esci \nScelta: ");
				sceltaUtente = Integer.parseInt(scanner.nextLine());

				if (sceltaUtente == 1) {
					pesoUtente = calcolaPesoIdeale(altezzaUtente, anniUtente, genereUtente);
				}

				if (sceltaUtente == 2 && pesoUtente != 0) {
					kcalGiornaliere = calcolaFabbisognoCalorico(pesoUtente, anniUtente, genereUtente);
				} else {
					if (sceltaUtente == 2 && pesoUtente == 0) {
						System.out.println();
						System.out.println("Calcolo del peso non effettuato impossibile eseguire l'operazione!");
					}
				}

				if (sceltaUtente == 3 && kcalGiornaliere != 0) {
					tracciaCalorieGiornaliere(kcalGiornaliere);
				} else {
					if (sceltaUtente == 3 && kcalGiornaliere == 0) {
						System.out.println();
						System.out.println("Calcolo del fabbisogno calorico giornaliero non effettuato!");
					}
				}

				if (sceltaUtente == 4) {
					try {
						FileWriter fw = new FileWriter("daily.csv", false);
						fw.write("");
						fw.close();
					} catch (IOException e) {
						System.out.println("C'e stato un errore! Errore: " + e.getMessage());
					}
					System.out.println("Giornaliero Svuotato con successo!");
				}

				if (sceltaUtente == 5) {
					do {
						System.out.print("Inserire la propria altezza (in cm): ");
						altezzaUtente = Double.parseDouble(scanner.nextLine());
						if (altezzaUtente < 0 || altezzaUtente > 270) {
							System.out.println("Inserire un valore valido!");
						}
					} while (altezzaUtente < 0 || altezzaUtente > 270);

					do {
						System.out.print("Inserire la propria età: ");
						anniUtente = Integer.parseInt(scanner.nextLine());
						if (anniUtente < 0) {
							System.out.println("Inserire un valore valido!");
						}
					} while (anniUtente < 0);

					do {
						System.out.print("Inserire sesso (maschio / donna): ");
						genereUtente = scanner.nextLine();
						inizialeGenere = genereUtente.substring(0, 1);
						if (!inizialeGenere.equalsIgnoreCase("m") && !inizialeGenere.equalsIgnoreCase("d")) {
							System.out.println("Inserire un valore valido!");
						}
						if (inizialeGenere.equalsIgnoreCase("m")) {
							genereUtente = "maschio";
						}
						if (inizialeGenere.equalsIgnoreCase("d")) {
							genereUtente = "donna";
						}
					} while (!inizialeGenere.equalsIgnoreCase("m") && !inizialeGenere.equalsIgnoreCase("d"));

					kcalGiornaliere = 0;
					pesoUtente = 0;

					System.out.println();
					System.out.println("Informazioni aggiornate con successo!");
					System.out.println();
				}

				if (sceltaUtente == 6) {
					FileWriter fw = new FileWriter("dati.txt");
					fw.write("Altezza:" + altezzaUtente);
					fw.write(System.lineSeparator());
					fw.write("Età:" + anniUtente);
					fw.write(System.lineSeparator());
					fw.write("Sesso:" + genereUtente);
					fw.write(System.lineSeparator());
					fw.write("Peso:" + pesoUtente);
					fw.write(System.lineSeparator());
					fw.write("Fabbisogno:" + kcalGiornaliere);
					fw.write(System.lineSeparator());
					fw.close();
				}

				if (sceltaUtente < 1 || sceltaUtente > 6) {
					System.out.println("Inserire un valore valido!");
				}

			} while (sceltaUtente != 6);

		} catch (IOException e) {
			System.out.println("C'e stato un errore! Errore: " + e.getMessage());
		}
	}


	public static double calcolaFabbisognoCalorico(double pesoKg, int anni, String genere) {
		double kcal;
		String inizialeGenere = genere.substring(0, 1);

		if (inizialeGenere.equalsIgnoreCase("m")) {
			if (anni >= 18 && anni <= 29) {
				kcal = 15.3 * pesoKg + 679;
			} else if (anni >= 30 && anni <= 59) {
				kcal = 11.6 * pesoKg + 879;
			} else if (anni >= 60 && anni <= 74) {
				kcal = 11.9 * pesoKg + 700;
			} else {
				kcal = 8.4 * pesoKg + 819;
			}
		} else {
			if (anni >= 18 && anni <= 29) {
				kcal = 14.7 * pesoKg + 496;
			} else if (anni >= 30 && anni <= 59) {
				kcal = 8.7 * pesoKg + 829;
			} else if (anni >= 60 && anni <= 74) {
				kcal = 9.2 * pesoKg + 688;
			} else {
				kcal = 9.8 * pesoKg + 624;
			}
		}

		kcal = Math.floor(kcal * 10) / 10;

		System.out.println();
		System.out.println("Fabbisogno calorico giornaliero: " + kcal + " kcal");
		System.out.println();

		return kcal;
	}

	public static void tracciaCalorieGiornaliere(double fabbisogno) {
		String linea, alimento;
		int quantita;
		double kcalAlimento = 0;
		double kcalTotali = 0;
		String continua = "s";

		try {
			FileWriter writer = new FileWriter("daily.csv", true);

			Scanner scanGiorno = new Scanner(new File("daily.csv"));
			while (scanGiorno.hasNextLine()) {
				linea = scanGiorno.nextLine();
				String[] split = linea.split("\\,");
				kcalTotali += Double.parseDouble(split[split.length - 1]);
			}
			scanGiorno.close();

			while (continua.equalsIgnoreCase("s") && kcalTotali < fabbisogno) {
				boolean trovato = false; // Spostato qui per ogni ciclo
				Scanner scanAlimenti = new Scanner(new File("calories.csv"));
				scanAlimenti.nextLine();

				System.out.print("Inserire il nome del cibo mangiato: ");
				alimento = scanner.nextLine();
				alimento = alimento.toLowerCase().replaceAll("[^a-z]", "");

				do {
					System.out.print("Inserire la quantità (in grammi) di cibo mangiata: ");
					quantita = Integer.parseInt(scanner.nextLine());
					if (quantita < 0) {
						System.out.println("Inserire un valore valido!");
					}
				} while (quantita < 0);

                int grammiAlim = 0; 
				while (scanAlimenti.hasNextLine() && !trovato) {
					linea = scanAlimenti.nextLine();
					String[] split = linea.split("\\,");
					split[0] = split[0].toLowerCase().replaceAll("[^a-z]", "");
					if (split[0].equals(alimento)) {
						kcalAlimento = Double.parseDouble(split[split.length - 1]);
                        grammiAlim = Integer.parseInt(split[split.length-2]);
						trovato = true;
					}
				}

				if (!trovato) {
					System.out.println("Cibo non trovato nel database!");
				} else {
					kcalAlimento = (quantita/grammiAlim)*kcalAlimento;
					kcalTotali += kcalAlimento;
					writer.write(alimento + "," + quantita + " grammi," + kcalAlimento+ " kcal");
					writer.write(System.lineSeparator());
				}

				if (kcalTotali < fabbisogno) {
					System.out.print("Vuoi continuare? (si / no): ");
					continua = scanner.nextLine();
					continua = continua.substring(0, 1);
				}

				scanAlimenti.close();
			}

			System.out.println();
			System.out.println("Hai consumato un totale di: " + kcalTotali + " kcal!");

			if (kcalTotali > fabbisogno) {
				System.out.println();
				System.out.println("Hai superato il fabbisogno calorico giornaliero di " + (kcalTotali - fabbisogno) + " kcal!");
			} else {
				System.out.println("Ti rimangono " + (fabbisogno - kcalTotali) + " kcal per raggiungere il fabbisogno calorico giornaliero!");
			}

			writer.close();

			Scanner scanGiorno2 = new Scanner(new File("daily.csv"));
			System.out.println();
			System.out.println("Hai mangiato: ");
			System.out.println();

			while (scanGiorno2.hasNextLine()) {
				linea = scanGiorno2.nextLine();
				System.out.println(linea);
			}
			scanGiorno2.close();

		} catch (IOException e) {
			System.out.println("C'e stato un errore! Errore: " + e.getMessage());
		}
	}
	public static double calcolaPesoIdeale(double altezzaCm, int anni, String genere) {
		String inizialeGenere = genere.substring(0, 1);
		double[] pesi = new double[9];

		if (inizialeGenere.equalsIgnoreCase("m")) {
			pesi[0] = altezzaCm - 100 - (altezzaCm - 150) / 4;
			pesi[1] = altezzaCm - 100;
			pesi[2] = (altezzaCm - 150) * 0.75 + 50;
			pesi[5] = Math.pow((altezzaCm / 100), 2) * 22.1;
			pesi[8] = (1.40 * Math.pow(altezzaCm / 10.0, 3)) / 100.0;
		} else {
			pesi[0] = altezzaCm - 100 - (altezzaCm - 150) / 2;
			pesi[1] = altezzaCm - 104;
			pesi[2] = (altezzaCm - 150) * 0.6 + 50;
			pesi[5] = Math.pow((altezzaCm / 100), 2) * 20.6;
			pesi[8] = (135 * Math.pow(altezzaCm / 10.0, 3)) / 100.0;
		}
		pesi[3] = 0.8 * (altezzaCm - 100) + anni / 2;
		pesi[4] = altezzaCm - 100 + anni / 10 * 0.9;
		pesi[6] = (1.012 * altezzaCm) - 107.5;
		pesi[7] = Math.pow((2.37 * (altezzaCm / 100)), 3);

		for (int i = 0; i < pesi.length; i++) {
			pesi[i] = Math.floor(pesi[i] * 10) / 10;
		}

		System.out.println();
		System.out.println("1. Peso secondo Lorenz: " + pesi[0] + "\n2. Peso secondo Broca: " + pesi[1] + "\n3. Peso secondo Wan der Vael: " + pesi[2] + "\n4. Peso secondo Berthean: " + pesi[3] + "\n5. Peso secondo Perrault: " + pesi[4] + "\n6. Peso secondo Keys: " + pesi[5] + "\n7. Peso secondo travia: " + pesi[6] + "\n8. Peso secondo Livi: " + pesi[7] + "\n9. Peso secondo Buffon,Roher e Bardeen: " + pesi[8]);
		System.out.println();

		int sceltaPeso;
		do {
			System.out.print("Quale peso corporeo vuoi considerare (1-9)?: ");
			sceltaPeso = Integer.parseInt(scanner.nextLine());
			if (sceltaPeso < 1 || sceltaPeso > 9) {
				System.out.println("Inserire un valore valido!");
			}
		} while (sceltaPeso < 1 || sceltaPeso > 9);

		return pesi[sceltaPeso - 1];
	}

}
