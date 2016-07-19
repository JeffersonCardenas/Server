package services;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.*;
import data.User;
import database.DAO;

@Path("/user")
public class UserResource {
	
	private DAO dao=new DAO();
	private static final Logger logger = LogManager.getLogger(UserResource.class);
	
	@POST
	@Path("{dni}-{pass}")
	@Produces("application/xml")
	public String getUserApp(@PathParam("dni") String dni,@PathParam("pass") String pass){
		final User result = dao.getUser(dni, pass);
		if (result==null){
			logger.info("El usuario no existe",this);
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		else return this.userToXml(result);
	}
	
	@GET
	@Path("users")
	@Produces("application/xml")
	public String getListUsers(){
		String result = "<usuarios>";
		List<User> lista = dao.getUserList();
		if (!lista.isEmpty()){
			Iterator<User> it = lista.iterator();
			while (it.hasNext()){
				result+=userToXml(it.next());
			}
			result+="</usuarios>";
			return result;
		}
		else throw new WebApplicationException(Response.Status.NOT_FOUND);
		
	}
	
	protected String userToXml(User u){
		return "<usuario>"+
					"<dni>"+u.getDNI()+"</dni>"+
	      			"<nombre_completo>" + u.getNombre() + "</nombre_completo>"+
	      			"<fecha>" + u.getFecha() + "</fecha>"+
	      			"<tipo>" + u.getTipo() + "</tipo>"+
	      			"<pass>" + u.getPass() + "</pass>"+
	      		"</usuario>";
	}	

}
