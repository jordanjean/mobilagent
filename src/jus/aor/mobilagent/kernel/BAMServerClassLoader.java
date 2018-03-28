package jus.aor.mobilagent.kernel;

import java.net.URL;
import java.net.URLClassLoader;

public class BAMServerClassLoader extends URLClassLoader {

    public BAMServerClassLoader(URL[] urls, ClassLoader parent) {
	super(urls, parent);
    }
    
    @Override
    /*
     * (non-Javadoc)
     * @see java.net.URLClassLoader#addURL(java.net.URL)
     */
    public void addURL(URL url){
	super.addURL(url);
    }
}
