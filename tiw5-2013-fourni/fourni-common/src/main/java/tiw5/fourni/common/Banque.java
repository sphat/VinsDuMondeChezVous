package tiw5.fourni.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "banque")
public class Banque {
	public static final String BANQUE_XML = "mabanque.xml";
	private static final Logger LOG = LoggerFactory.getLogger(Banque.class);

	@XmlElement(name = "compte")
	public List<Compte> comptes = new ArrayList<Compte>();

	public Compte getCompte(long num) {
		for (Compte c : comptes) {
			if (c.getNumeroCarte() == num) {
				return c;
			}
		}
		return null;
	}

	public void addCompte(Compte c) {
		comptes.add(c);
	}

	public void saveXml() throws JAXBException {
		File f = new File(BANQUE_XML);
		JAXBContext.newInstance(Banque.class).createMarshaller().marshal(this, f);
	}

	public static Banque fromXml() throws JAXBException {
		File f = new File(BANQUE_XML);
		if (!f.isFile()) {
			LOG.info("{} non trouvé,  utilise une banque vide", BANQUE_XML);
			return new Banque();
		}
		return (Banque) JAXBContext.newInstance(Banque.class)
				.createUnmarshaller().unmarshal(f);
	}
}
