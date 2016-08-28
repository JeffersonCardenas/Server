package services;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import data.ExamenPractico;
import database.DAO;
import tools.Tools;

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
	
	@POST
	@Path("/aprueba")
	@Produces(MediaType.TEXT_HTML)
	public Response insertaAprobadoPractico(@FormParam("dni") String dni, @FormParam("id") int id){
		if (dao.insertaAprobadoPractico(dni, id, Tools.getDate())>0) return Response.status(Response.Status.OK).build();
		else return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	/**
	 * Devuelve una representacion en formato xml de un ExamenPractico
	 * @param p ExamenPractico
	 * @return String que representa un Examen Practico en xml
	 */
	private String examenPracticoToXml(ExamenPractico p) {
		return "<examen_practico>"+
				"<id_examen>"+p.getIdPractico()+"</id_examen>"+
      			"<nivel>" + p.getNivel() + "</nivel>"+
      			"<num_imagenes>" + p.getNumImagenes() + "</num_imagenes>"+
      			"<tiempo_examen>"+ p.getTiempo_examen()+"</tiempo_examen>"+
      		"</examen_practico>";
	}

}
