package jus.aor.RMI.server;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import jus.aor.RMI.common._Annuaire;
import jus.aor.RMI.common._Chaine;

public class Server {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		int port = 2001;
		int nbChaines = 1;

		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("Erreur : Pas de numero de port valide.");
				System.exit(1);
			}
		}

		// installation d'un securityManager
		/*
		 * if(System.getSecurityManager() == null){
		 * System.setSecurityManager(new SecurityManager()); }
		 */
		try {
			Registry registre;
			registre = LocateRegistry.createRegistry(port);
			for (int i = 1; i <= nbChaines; i++) {
				_Chaine chaine = new Chaine("DataStore/Hotels" + i + ".xml");
				registre.rebind("chaine" + i, chaine);
			}
			registre = LocateRegistry.createRegistry(port + nbChaines + 1);
			_Annuaire a = new Annuaire("DataStore/Annuaire.xml");
			try{
			registre.rebind("annuaire", a);
			} catch (Exception e){
				e.printStackTrace();
			}

			System.out.println("Démarrage du serveur effectué !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
