package jus.aor.RMI.client;

import java.rmi.RemoteException;

public class LookForHotel {
	/** le critère de localisaton choisi */
	private String localisation;
	private int port = 1099;

	// ...
	/**
	 * Définition de l'objet représentant l'interrogation.
	 * 
	 * @param args
	 *            les arguments n'en comportant qu'un seul qui indique le
	 *            critère de localisation
	 */
	public LookForHotel(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		if (args.length != 1) {
			System.out.println("Error: please select a valid location");
			System.exit(1);
		}
		System.out.println("Location: " + args[0]);
		localisation = args[0];
	}

	/**
	 * réalise une intérrogation
	 * 
	 * @return la durée de l'interrogation
	 * @throws RemoteException
	 */
	public long call() throws RemoteException {
		long td = System.currentTimeMillis();

		return System.currentTimeMillis() - td;
	}

	public static void main(String[] args) throws RemoteException {
		LookForHotel lfh = new LookForHotel(args);
		long time = lfh.call();
		System.out.println("Research done in " + time + " milliseconds");
	}
}
