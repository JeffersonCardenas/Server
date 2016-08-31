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
import javax.ws.rs.core.Response;

import data.Pregunta;
import database.DAO;

@Path("/pregunta")
public class PreguntaResource {
	
	private DAO dao;
	
	public PreguntaResource(){}
	
	public PreguntaResource(DAO d){
		this.dao=d;
	}
	
	@GET
	@Path("{examen}")
	@Produces("application/xml")
	public String getListaPreguntas(@PathParam("examen") int examen){
		List<Pregunta> list = dao.getListaPreguntasFromExamen(examen);
		if (!list.isEmpty()){
			String output = listToXml(list);
			return output;
		}
		else throw new WebApplicationException(Response.Status.NOT_FOUND);
	}
	
	@POST
	@Path("/insert")
	@Produces("application/xml")
	public String insertaPregunta(@FormParam("enunciado") String enunciado, @FormParam("examen") int examen){
		int idPregunta = dao.insertaPregunta(enunciado, examen);
		if (idPregunta!=0) return "<pregunta><id_pregunta>"+idPregunta+"</id_pregunta></pregunta>";
		else return "<pregunta><id_pregunta>0</id_pregunta></pregunta>";
	}
	
	/**
	 * Devuelve la representacion de una Preguna en formato XML
	 * @param p Pregunta
	 * @return xml que representa una pregunta
	 */
	private String objectToXml(Pregunta p){
		return "<pregunta>"+
				"<id_pregunta>"+p.getId_pregunta()+"</id_pregunta>"+
      			"<enunciado>" + p.getEnunciado() + "</enunciado>"+
      			"<id_examen>" + p.getId_examen() + "</id_examen>"+
      		"</pregunta>";
	}
	
	/**
	 * Representa un conjunto de preguntas
	 * @param l Lista de Preguntas
	 * @return String que reoresenta un xml con todas las preguntas del listado
	 */
	private String listToXml(List<Pregunta> l){
		String result = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>"+"<preguntas>";
		
		Iterator<Pregunta> it = l.iterator();
		while (it.hasNext()){
			result+=objectToXml(it.next());
		}
		result+="</preguntas>";
		return result;
	}

}
