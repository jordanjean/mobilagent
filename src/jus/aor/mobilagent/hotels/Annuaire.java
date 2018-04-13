package jus.aor.mobilagent.hotels;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jus.aor.mobilagent.kernel.Numero;
import jus.aor.mobilagent.kernel._Service;

public class Annuaire implements _Service<Numero> {

	private Map<String, Numero> annuaire = new HashMap<String, Numero>();

	public Annuaire(Object... args) {
		String pathFile = (String) args[0];

		DocumentBuilder documentBuilder = null;
		Document document = null;
		String name = "";
		String num = "";
		NodeList telList = null;
		NamedNodeMap attributes = null;

		// Lecture de l'annuaire
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = documentBuilder.parse(new File(pathFile));

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		telList = document.getElementsByTagName("Telephone");
		for (int i = 0; i < telList.getLength(); i++) {
			attributes = telList.item(i).getAttributes();
			name = attributes.getNamedItem("name").getNodeValue();
			num = attributes.getNamedItem("numero").getNodeValue();
			this.annuaire.put(name, new Numero(num));
		}
	}

	@Override
	public Numero call(Object... params) throws IllegalArgumentException {
		if (params.length != 1) {
			throw new IllegalArgumentException();
		}
		return this.annuaire.get(params[0]);
	}
}