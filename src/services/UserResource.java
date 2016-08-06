package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.*;
import data.User;
import database.DAO;

@Path("/user")
public class UserResource {
	
	private DAO dao;
	private static final Logger logger = LogManager.getLogger(UserResource.class);
	
	public UserResource(){	}
	
	public UserResource(DAO d){
		this.dao=d;
	}
	
	@POST
	@Path("get")
	@Produces("application/xml")
	public String getUserApp(@FormParam("dni") String dni,@FormParam("pass") String pass){
		final User result = dao.getUser(dni, pass);
		if (result==null){
			logger.info("El usuario no existe",this);
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		else {
			logger.info("El usuario "+result.getNombre()+" existe", this);
			return this.userToXml(result);
		}
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
	
	@POST
	@Path("add")
	@Produces(MediaType.TEXT_HTML)
	public Response insertUser(@FormParam("nombre") String nombre, 
			@FormParam("dni") String dni,@FormParam("pass") String pass){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String fecha = dateFormat.format(cal.getTime());
		java.util.Date date = null;
		int success = 0;
		try{
			date = dateFormat.parse(fecha);			
		}
		catch(ParseException p){
			p.printStackTrace();
		}		
		java.sql.Date fechaInsert = new java.sql.Date(date.getTime());
		success = dao.insertaUsuario(nombre, dni, pass, fechaInsert);
		String msg = "Insertado: "+nombre;
		if (success>0) return Response.ok(msg).entity(msg).build();
		else throw new WebApplicationException(Response.Status.NOT_MODIFIED);
		
	}
	
	@DELETE
	@Path("{dni}")
	public Response deleteUser(@PathParam("dni") String dni){
		int resul = dao.deleteUsuario(dni);
		String msg = "Usuario "+dni+" borrado";
		if(resul>0) return Response.status(204).entity(msg).build();
		else return Response.status(Response.Status.NOT_FOUND)
				.entity("No se ha podido eliminar el usuario "+dni).build();
	}
	
	@GET
	@Path("{dni}")
	public Response checkUser(@PathParam("dni") String dni){
		if (dao.existeUsuario(dni)) return Response.status(Response.Status.OK).build();
		else return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@POST
	@Path("update/{dni}")
	public Response updateUser(@PathParam("dni") String dni,@FormParam("nombre") String nombre,
			@FormParam("pass") String pass){
		int resul = dao.updateUsuario(dni,nombre,pass);
		if(resul>0) return Response.status(Response.Status.NO_CONTENT).build();
		else return Response.status(Response.Status.NOT_FOUND)
				.entity("No se ha podido actualizar el usuario "+dni).build();
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
