package services;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ApplicationPath("/services")
public class ServerApplication extends Application{
	
	private Set<Object> singletons = new HashSet<Object>();
	private static final Logger logger = LogManager.getLogger(ServerApplication.class);
	
	public ServerApplication(){
		this.singletons.add(new UserResource());
		this.singletons.add(new CertificadoResource());
		this.singletons.add(new ExamenResource());
		logger.info("Entramos en ServerApplication");
	}
	
	public Set<Object> getSingletons(){
		return this.singletons;
	}
	
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		set.add(UserResource.class);
		set.add(CertificadoResource.class);
		set.add(ExamenResource.class);
		return set;
	}

}
