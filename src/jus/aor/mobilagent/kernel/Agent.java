package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jus.aor.mobilagent.kernel._Service;

public class Agent implements _Agent {

    /**
     * 
     */
    private static final long serialVersionUID = -2664530419256779025L;
    /**
     * 
     */
    protected transient AgentServer agentServer;
    protected transient String serverName;
    private Route route;
    private transient Logger logger;
    
    @Override
    public void run() {
	logger.log(Level.INFO, "agent lancé");
	if(route.hasNext){
	    // exécution de l'action sur le serveur actuel
	    _Action action = route.next().action;
	    logger.log(Level.INFO, "exécution de l'action " + action + "sur le serveur " + serverName);
	    action.execute();
	    
	    // déplacement de l'agent vers le prochain serveur
	    if(route.hasNext){
		URI prochainServer = route.next().server;
		Socket s;
		try {
		    s = new Socket(prochainServer.getHost(), prochainServer.getPort());
		    ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		    BAMAgentClassLoader loader = (BAMAgentClassLoader) this.getClass().getClassLoader();
		    Jar baseCode = loader.extractCode();
		    os.writeObject(baseCode);
		    os.writeObject(this);
		    os.close();
		    logger.log(Level.INFO, "agent déplacé");
		} catch (UnknownHostException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	    }
	}else{
	    logger.log(Level.INFO, "feuille de route épuisé");
	}
    }

    @Override
    public void init(AgentServer agentServer, String serverName) {
	this.agentServer = agentServer;
	this.serverName = serverName;
	this.route = new Route(new Etape(agentServer.site(), _Action.NIHIL));
	try {
	    logger = Logger.getLogger("jus.aor.mobilagent." + InetAddress.getLocalHost().getHostName() + "." + serverName);
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void reInit(AgentServer server, String serverName) {
	this.agentServer = server;
	this.serverName = serverName;
	try {
	    logger = Logger.getLogger("jus.aor.mobilagent." + InetAddress.getLocalHost().getHostName() + "." + serverName);
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void addEtape(Etape etape) {
	route.add(etape);
    }

    protected _Action retour() {
	return this.route.retour.action;
    }
    
	protected _Service<?> getService(String nomService){
		return agentServer.getService(nomService);
	}

}
