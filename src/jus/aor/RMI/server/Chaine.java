package jus.aor.RMI.server;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jus.aor.RMI.common.Hotel;
import jus.aor.RMI.common._Chaine;

public class Chaine extends UnicastRemoteObject implements _Chaine{
	private List<Hotel> hotels = new ArrayList<Hotel>();

	protected Chaine(String fichier) throws RemoteException {
		/*
		 * récupération des hôtels de la chaîne dans le fichier xml passé en 1er
		 * argument
		 */
		DocumentBuilder docBuilder = null;
		org.w3c.dom.Document doc = null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			doc = docBuilder.parse(new File(fichier));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String name, localisation;
		NodeList list = doc.getElementsByTagName("Hotel");
		NamedNodeMap attrs;
		/* acquisition de toutes les entrées de la base d'hôtels */
		for (int i = 0; i < list.getLength(); i++) {
			attrs = list.item(i).getAttributes();
			name = attrs.getNamedItem("name").getNodeValue();
			localisation = attrs.getNamedItem("localisation").getNodeValue();
			hotels.add(new Hotel(name, localisation));
		}
	}

	public List<Hotel> get(String localisation) {
		List<Hotel> list = new ArrayList<Hotel>();

		for (Hotel hotel : this.hotels) {
			if (localisation.equals(hotel.localisation)) {
				list.add(hotel);
			}
		}
		return list;
	}
}