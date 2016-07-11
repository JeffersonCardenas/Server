package services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.logging.log4j.*;
import data.User;
import database.DAO;

@Path("/user")
public class UserResource {
	
	private DAO dao=new DAO();
	private static final Logger logger = LogManager.getLogger(UserResource.class);
	
	@GET
	@Path("{dni}-{pass}")
	@Produces("application/xml")
	public StreamingOutput getUserApp(@PathParam("dni") String dni,@PathParam("pass") String pass){
		final User result = dao.getUser(dni, pass);
		//return result!=null ? result.toXML():"No existe el usuario";
		if (result==null){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		logger.info("El usuario es "+result.toString());
		return new StreamingOutput() {
	         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
	            outputCustomer(outputStream, result);
	         }
	      };
	}
	
	protected void outputCustomer(OutputStream os, User cust) throws IOException
	   {
	      logger.info("Entramos en outputCustomer");
		  PrintStream writer = new PrintStream(os);
	      writer.println("<usuario dni=\"" + cust.getDNI() + "\">");
	      writer.println("   <nombre_completo>" + cust.getNombre() + "</nombre_completo>");
	      writer.println("   <fecha>" + cust.getFecha() + "</fecha>");
	      writer.println("   <tipo>" + cust.getTipo() + "</tipo>");
	      writer.println("   <pass>" + cust.getPass() + "</pass>");
	      writer.println("</usuario>");
	      logger.info("Salimos de outputCustomer");
	   }
	

}
