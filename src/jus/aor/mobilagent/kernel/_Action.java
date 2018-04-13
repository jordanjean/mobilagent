/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */
package jus.aor.mobilagent.kernel;

import java.io.Serializable;

/**
 * Définit une action à exécuter par un agent.
 * 
 * @author Morat
 */
public interface _Action extends Serializable {
    /** l'action vide */
    public static final _Action NIHIL = new _Action() {

	private static final long serialVersionUID = 1L;

	@Override
	/*
	 * (non-Javadoc)
	 * @see jus.aor.mobilagent.kernel._Action#execute()
	 */
	public void execute() {
	    System.out.println("action vide");
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
	    return "action vide";
	}
    };

    /**
     * Exécute l'action
     */
    public void execute();
}
