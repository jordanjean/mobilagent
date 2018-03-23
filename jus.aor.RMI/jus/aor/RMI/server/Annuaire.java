package jus.aor.RMI.server;

import java.io.File;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jus.aor.RMI.common.Numero;
import jus.aor.RMI.common._Annuaire;

public class Annuaire extends UnicastRemoteObject implements _Annuaire {

	private HashMap<String, Numero> annuaire = new HashMap<String, Numero>();

	protected Annuaire(String fichier) throws ParserConfigurationException, SAXException, IOException {
		/* Récupération de l'annuaire dans le fichier xml */
		DocumentBuilder docBuilder = null;
		Document doc = null;
		docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = docBuilder.parse(new File(fichier));

		String name, numero;
		NodeList list = doc.getElementsByTagName("Telephone");
		NamedNodeMap attrs;
		/* acquisition de toutes les entrées de l'annuaire */
		for (int i = 0; i < list.getLength(); i++) {
			attrs = list.item(i).getAttributes();
			name = attrs.getNamedItem("name").getNodeValue();
			numero = attrs.getNamedItem("numero").getNodeValue();
		}

		// On récupère les arguments pour la construction de Chaine
		String arguments = doc.getElementsByTagName("service").item(0).getAttributes().getNamedItem("args")
				.getNodeValue();

	}

	@Override
	public Numero get(String abonne) {
		return this.annuaire.get(abonne);
	}
}