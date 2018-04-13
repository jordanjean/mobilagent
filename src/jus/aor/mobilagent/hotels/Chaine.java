package jus.aor.mobilagent.hotels;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jus.aor.mobilagent.kernel._Service;

public class Chaine implements _Service<Collection<Hotel>> {

	private Collection<Hotel> hotels = new LinkedList<Hotel>();

	public Chaine(Object... args) {
		String pathFile = (String) args[0];
		/*
		 * Lecture de la chaine d'hotels dans le fichier xml
		 */
		DocumentBuilder docbuilder = null;
		Document doc = null;
		String name, localisation;
		NodeList hlist = null;
		NamedNodeMap attrs;
		try {
			docbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = docbuilder.parse(new File(pathFile));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		hlist = doc.getElementsByTagName("Hotel");

		/* Lecture des hotels */
		for (int i = 0; i < hlist.getLength(); i++) {
			attrs = hlist.item(i).getAttributes();
			name = attrs.getNamedItem("name").getNodeValue();
			localisation = attrs.getNamedItem("localisation").getNodeValue();
			this.hotels.add(new Hotel(name, localisation));
		}
	}

	@Override
	public Collection<Hotel> call(Object... params) throws IllegalArgumentException {
		if (params.length != 1)
			throw new IllegalArgumentException();

		String localisation = (String) params[0];
		Collection<Hotel> hotelsInLocalisation = new ArrayList<Hotel>();
		for (Hotel h : this.hotels)
			if (h.localisation.equals(localisation))
				hotelsInLocalisation.add(h);

		return hotelsInLocalisation;
	}
}
