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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.Certificados;
import database.DAO;

@Path("/certificados")
public class CertificadoResource {
	
	private DAO dao=new DAO();
	private static final Logger logger = LogManager.getLogger(CertificadoResource.class);
	
	@GET
	@Produces("application/xml")
	public String getCertificados(){
		String result = "<certificados>";
		List<Certificados> lista = dao.getCertificados();
		if (!lista.isEmpty()){
			Iterator<Certificados> it = lista.iterator();
			while (it.hasNext()){
				result+=objectToXml(it.next());
			}
			result+="</certificados>";
			return result;
		}
		else throw new WebApplicationException(Response.Status.NOT_FOUND);
	}
	
	
	private String objectToXml(Certificados c){
		return "<certificado>"+
				"<nivel>"+c.getNivel()+"</nivel>"+
      			"<limite>" + c.getLimite() + "</limite>"+
      		"</certificado>";
		
	}

}
