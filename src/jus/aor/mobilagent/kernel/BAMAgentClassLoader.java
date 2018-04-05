package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
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
		
		// define and resolve all the classes from the jar file
		for(Entry<String, byte[]> entry : jar) {
			super.resolveClass(this.defineClass(className(entry.getKey()), entry.getValue(), 0, entry.getValue().length));	
		}
	}
	
	public String className(String name){
		//delete the .class at the end and change the / into . 
		return name.replace(".class", "").replace("/", ".");
	}
	
	public Jar extractCode(){
		return code;
	}
	
	public String toString(){
		//print the name of the jar and the classes in it
		String classesToPrint = "BAM : " + code.toString() + "\n";
		for(Iterator<Entry<String,byte[]>> iter = code.iterator(); iter.hasNext();) {
			Entry<String, byte[]> currentClass = iter.next();
			classesToPrint.concat(className(currentClass.getKey()));
		}
		
		return classesToPrint;
	}
}

