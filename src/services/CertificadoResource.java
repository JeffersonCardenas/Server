package services;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import data.Certificados;
import database.DAO;

@Path("/certificados")
public class CertificadoResource {
	
	private DAO dao;
	
	public CertificadoResource(){}
	
	public CertificadoResource(DAO d){
		this.dao=d;
	}
	
	@GET
	@Produces("application/xml")
	public String getCertificados(){
		String result;
		List<Certificados> lista = dao.getCertificados();
		if (!lista.isEmpty()){
			result = listToXml(lista);
			return result;
		}
		else throw new WebApplicationException(Response.Status.NOT_FOUND);
	}
	
	@GET
	@Path("{dni}")
	@Produces("application/xml")
	public String getCertificadosFromDNI(@PathParam("dni") String dni){
		List<Certificados> list = dao.getCertificadosFromUser(dni);
		String resul;
		if (!list.isEmpty()){
			resul = listToXml(list);
			return resul;
		}
		else throw new WebApplicationException(Response.Status.NOT_FOUND);
	}
	
	@POST
	@Path("/obtiene")
	@Produces(MediaType.TEXT_HTML)
	public Response insertaCertificacion(@FormParam("nivel") int nivel,@FormParam("dni") String dni){
		if (dao.obtieneCertificacion(nivel, dni)>0) return Response.status(Response.Status.OK).build();
		else return Response.status(Response.Status.NOT_FOUND).build();		
	}
	
	
	private String objectToXml(Certificados c){
		return "<certificado>"+
				"<nivel>"+c.getNivel()+"</nivel>"+
      		   "</certificado>";		
	}
	
	/**
	 * Convierte una lista de Certificados a String que representa un xml
	 * @param c Lista de Certificados
	 * @return String que representa un conjunto de Certificados en xml
	 */
	private String listToXml(List<Certificados> c){
		String result = "<certificados>";
		
		Iterator<Certificados> it = c.iterator();
		while (it.hasNext()){
			result+=objectToXml(it.next());
		}
		result+="</certificados>";
		return result;		
	}

}
