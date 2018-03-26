package jus.aor.mobilagent.kernel;

import com.sun.xml.internal.bind.v2.TODO;

public class Agent implements _Agent {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private AgentServer agentServer;
    protected String serverName;
    private Route route;
    
    @Override
    public void run() {
	if(route.hasNext){
	    route.next().action.execute();
	}else{
	    System.out.println("Route vide");
	}
    }

    @Override
    public void init(AgentServer agentServer, String serverName) {
	this.agentServer = agentServer;
	this.serverName = serverName;
	this.route = new Route(new Etape(agentServer.site(), _Action.NIHIL));
    }

    @Override
    public void reInit(AgentServer server, String serverName) {
	this.agentServer = server;
	this.serverName = serverName;
    }

    @Override
    public void addEtape(Etape etape) {
	route.add(etape);
    }

    protected _Action retour() {
	return this.route.retour.action;
    }

}
