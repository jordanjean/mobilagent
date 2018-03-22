package jus.aor.RMI.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import jus.aor.RMI.common.Hotel;
import jus.aor.RMI.common._Annuaire;
import jus.aor.RMI.common._Chaine;

public class LookForHotel {
	/** le critère de localisaton choisi */
	private String localisation;
	private int port = 1099;
	private _Annuaire annuaire;
	private int nbChaines = 6;
	private List<Hotel> hotels = new ArrayList<Hotel>();

	// ...
	/**
	 * Définition de l'objet représentant l'interrogation.
	 * 
	 * @param args
	 *            les arguments n'en comportant qu'un seul qui indique le
	 *            critère de localisation
	 */
	public LookForHotel(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		if (args.length != 1) {
			System.out.println("Error: please select a valid location");
			System.exit(1);
		}
		System.out.println("Location: " + args[0]);
		localisation = args[0];
	}

	/**
	 * réalise une interrogation
	 * 
	 * @return la durée de l'interrogation
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public long call() throws RemoteException, NotBoundException {
		long td = System.currentTimeMillis();

		Registry registre = null;

		// On parcourt les chaines hotelières et on regarde dans leur
		// registre à leur port les hotels qu'elle possède à la localisation
		for (int i = 1; i <= this.nbChaines; i++) {
			registre = LocateRegistry.getRegistry(this.port + i);
			this.hotels.addAll((List<Hotel>) ((_Chaine) registre.lookup("chaine" + i)).get(this.localisation));
		}

		this.annuaire = (_Annuaire) registre.lookup("annuaire");

		// On arrête le timer ici, l'affichage ne fait pas partie du temps de
		// recherche
		long tf = System.currentTimeMillis();

		// Affichage des hotels trouvés
		System.out.println("Hotels :");
		for (Hotel h : hotels) {
			System.out.println(h.toString());
		}

		return tf - td;
	}

	public static void main(String[] args) throws RemoteException, NotBoundException {
		LookForHotel lfh = new LookForHotel(args);
		long time = lfh.call();
		System.out.println("Research done in " + time + " milliseconds");
	}
}
