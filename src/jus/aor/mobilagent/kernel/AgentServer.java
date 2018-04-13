package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Le server qui supporte le modèle du bus à agents mobiles "mobilagent".
 * Lorsqu'un agent se présente, le serveur charge son codebase et l'objet
 * représentant cet agent, puis il active cet objet qui exécute l'action que
 * l'agent a à réaliser sur ce serveur.
 * 
 * @author Morat
 */
public final class AgentServer implements Runnable {
    /** le logger de ce serveur */
    private Logger logger;
    /** La table des services utilisables sur ce serveur */
    private Map<String, _Service<?>> services;
    /** Le port auquel est attaché le serveur */
    private int port;
    /** l'état du serveur */
    private boolean running;
    /** la socket de communication du bus */
    private ServerSocket s;
    /** le nom logique de ce serveur */
    private String name;

    /**
     * L'initialisation du server
     * 
     * @param port
     *            the port où est attaché le srvice du bus à agents mobiles
     * @param name
     *            le nom du serveur
     * @throws Exception
     *             any exception
     */
    AgentServer(int port, String name) throws Exception {
	this.name = name;
	logger = Logger.getLogger("jus.aor.mobilagent." + InetAddress.getLocalHost().getHostName() + "." + name);
	this.port = port;
	services = new HashMap<String, _Service<?>>();
	s = new ServerSocket(port);
    }

    /**
     * le lancement du serveur
     * 
     */
    public void run() {
	// A COMPLETER
	// boucle de réception des agents
	running = true;
	try{
        	while (running) {
        	    // en attente de connexion
        	    Socket client = s.accept();
        	    logger.log(Level.INFO, "Connexion établie");
        	    BAMAgentClassLoader cl = new BAMAgentClassLoader(this.getClass().getClassLoader());
        	    
        	    InputStream is = client.getInputStream();
//        	    ObjectInputStream ais = new ObjectInputStream(is);
        	    AgentInputStream ais = new AgentInputStream(is, cl);

        	    // récupération du code de l'agent (le jar)
        	    Jar jar = (Jar) ais.readObject();
        	    logger.log(Level.INFO, "jar réceptionné");
        	    cl.integrateCode(jar);
        
        	    // récupération de l'agent et lancement
        	    _Agent agent = (_Agent)ais.readObject();
        	    agent.reInit(this, name);
        	    ais.close();
        	    client.close();
        	    new Thread(agent).start();
        	}
	}catch(Exception e){
	    System.out.println(e);
	    e.printStackTrace();
	}
    }

    /**
     * ajoute un service au serveur
     * 
     * @param name
     *            le nom du service
     * @param service
     *            le service
     */
    void addService(String name, _Service<?> service) {
	services.put(name, service);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	try {
	    return "mobilagent://" + InetAddress.getLocalHost().getHostName() + ":" + port;
	} catch (UnknownHostException e) {
	    return "mobilagent://";
	}
    }

    /**
     * restitue le service de nom name ou null si celui-ci n'est pas attaché au
     * serveur.
     * 
     * @param name
     * @return le service souhaité ou null
     */
    _Service<?> getService(String name) {
	return services.get(name);
    }

    /**
     * restitue l'URI de ce serveur qui est de la forme :
     * "mobilagent://<host>:<port>" ou null si cette opération échoue.
     * 
     * @return l'URI du serveur
     */
    URI site() {
	try {
	    return new URI("mobilagent://" + InetAddress.getLocalHost().getHostName() + ":" + port);
	} catch (Exception e) {
	    return null;
	}
    }

    public void terminate() {
	this.running = false;
    }
}

/**
 * ObjectInputStream spécifique au bus à agents mobiles. Il permet d'utiliser le
 * loader de l'agent.
 * 
 * @author Morat
 */
class AgentInputStream extends ObjectInputStream {
    /**
     * le classLoader à utiliser
     */
    BAMAgentClassLoader loader;

    AgentInputStream(InputStream is, BAMAgentClassLoader cl) throws IOException {
	super(is);
	loader = cl;
    }

    protected Class<?> resolveClass(ObjectStreamClass cl) throws IOException, ClassNotFoundException {
	return loader.loadClass(cl.getName());
    }
}