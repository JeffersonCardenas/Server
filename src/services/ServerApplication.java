package services;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import database.DAO;

@ApplicationPath("/services")
public class ServerApplication extends Application{
	
	private Set<Object> singletons = new HashSet<Object>();
	private DAO dao=new DAO();
	private static final Logger logger = LogManager.getLogger(ServerApplication.class);
	
	public ServerApplication(){
		this.singletons.add(new UserResource(dao));
		this.singletons.add(new CertificadoResource(dao));
		this.singletons.add(new ExamenTeoricoResource(dao));
		this.singletons.add(new ExamenPracticoResource(dao));
		this.singletons.add(new ModuloTeoricoResource(dao));
		this.singletons.add(new PreguntaResource(dao));
		this.singletons.add(new RespuestaResource(dao));
		logger.info("Entramos en ServerApplication");
	}
	
	public Set<Object> getSingletons(){
		return this.singletons;
	}
	
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		set.add(UserResource.class);
		set.add(CertificadoResource.class);
		set.add(ExamenTeoricoResource.class);
		set.add(ExamenPracticoResource.class);
		set.add(ModuloTeoricoResource.class);
		set.add(PreguntaResource.class);
		set.add(RespuestaResource.class);
		return set;
	}

}
