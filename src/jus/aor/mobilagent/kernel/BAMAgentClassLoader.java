package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarException;

public class BAMAgentClassLoader extends ClassLoader {
	
	private Jar code;
	public BAMAgentClassLoader(String jarName, ClassLoader parent) {
		super(parent);
		try {
			integrateCode(new Jar(jarName));
		} catch (JarException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BAMAgentClassLoader(ClassLoader parent) {
		super(parent);
	}
	
	public void integrateCode(Jar jar){
		code = jar;
		
		// define all the classes from the jar file
		for(Iterator<Map.Entry<String,byte[]>> iter = code.iterator(); iter.hasNext();) {
			Map.Entry<String, byte[]> currentClass = iter.next();
			this.defineClass(className(currentClass.getKey()), currentClass.getValue(), 0, currentClass.getValue().length);
		}
	}
	
	public String className(String name){
		//delete the .class at the end and change the / into . 
		return name.substring(0, name.length()-6).replace('/', '.');
	}
	
	public Jar extractCode(){
		return code;
	}
	
	public String toString(){
		//print the name of the jar and the classes in it
		String classesToPrint = "BAM : " + code.toString() + "\n";
		for(Iterator<Map.Entry<String,byte[]>> iter = code.iterator(); iter.hasNext();) {
			Map.Entry<String, byte[]> currentClass = iter.next();
			classesToPrint.concat(className(currentClass.getKey()));
		}
		
		return classesToPrint;
	}
}

