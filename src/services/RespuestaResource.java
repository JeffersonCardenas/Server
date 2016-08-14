package services;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import data.Respuesta;
import database.DAO;

@Path("/respuesta")
public class RespuestaResource {
	
	private DAO dao;
	
	public RespuestaResource(){}
	
	public RespuestaResource(DAO d){
		this.dao=d;
	}
	
	/**
	 * Devuelve un String que representa un objecto en formato xml
	 * @param r Respuesta
	 * @return String que representa un objeto serializado en xml
	 */
	private String objectToXml(Respuesta r){
		return "<respuesta>"+
				"<id_respuesta>"+r.getId_respuesta()+"</id_respuesta>"+
				"<es_correcta>"+r.getEs_correcta()+"</es_correcta>"+
      			"<enunciado>" + r.getEnunciado() + "</enunciado>"+
      			"<id_pregunta>" + r.getId_pregunta() + "</id_pregunta>"+
      		"</respuesta>";
	}
	
	/**
	 * Devuelve un String que representa una lista de Respuestas en formato xml
	 * @param l Lista de Respuestas
	 * @return String que representa una serie de Respuestas serializadas en xml
	 */
	private String listToXml(List<Respuesta> l){
		String result = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>"+"<respuestas>";
		
		Iterator<Respuesta> it = l.iterator();
		while (it.hasNext()){
			result+=objectToXml(it.next());
		}
		result+="</respuestas>";
		return result;
		
	}
	
	@GET
	@Path("{pregunta}")
	@Produces("application/xml")
	public String getListaRespuestasFromPregunta(@PathParam("pregunta") int pregunta){
		List<Respuesta> l = dao.getListaRespuestasFromPregunta(pregunta);
		if (l!=null){
			String resul = listToXml(l);
			return resul;
		}
		else throw new WebApplicationException(Response.Status.NOT_FOUND);
	}

}
