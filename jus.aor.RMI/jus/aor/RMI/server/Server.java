package jus.aor.RMI.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

	public static void main(String[] args) {

		int port = 1099;
		int nbChaines = 6;

		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("Erreur : Pas de numero de port valide.");
				System.exit(1);
			}
		}

		// installation d'un securityManager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		Registry registre;
		for (int i = 1; i <= nbChaines; i++) {
			registre = LocateRegistry.createRegistry(port + i);
			Chaine string = new Chaine("../DataStore/Hotels" + i + ".xml");
			registre.bind("chaine" + i, string);
		}
		registre = LocateRegistry.createRegistry(port + nbChaines + 1);
		Annuaire a = new Annuaire("DataStore/Annuaire.xml");
		registre.bind("annuaire", a);

		System.out.println("Démarrage du serveur effectué !");
	}

}
