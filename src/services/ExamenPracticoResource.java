package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import data.ExamenPractico;
import database.DAO;

@Path("/practico")
public class ExamenPracticoResource {
	
	private DAO dao;
	
	public ExamenPracticoResource(){}
	
	public ExamenPracticoResource(DAO d){
		this.dao=d;
	}
	
	@GET
	@Path("{nivel}")
	@Produces("application/xml")
	public String getExamenPractico(@PathParam("nivel") int nivel){
		final ExamenPractico practico = dao.getExamenPractico(nivel);
		if (practico==null) throw new WebApplicationException(Response.Status.NOT_FOUND);
		else return this.examenPracticoToXml(practico);
		
	}
	
	
	private String examenPracticoToXml(ExamenPractico p) {
		return "<examen_practico>"+
				"<id_examen>"+p.getIdPractico()+"</id_examen>"+
      			"<nivel>" + p.getNivel() + "</nivel>"+
      			"<numimagenes>" + p.getNumImagenes() + "</numimagenes>"+
      		"</examen_practico>";
	}

}
