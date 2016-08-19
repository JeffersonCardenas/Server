package services;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import data.ExamenTeorico;
import database.DAO;
import tools.Tools;

@Path("/teorico")
public class ExamenTeoricoResource {
	
	private DAO dao;
	
	public ExamenTeoricoResource(){}
	
	public ExamenTeoricoResource(DAO d){
		this.dao=d;
	}
	
	@GET
	@Path("{nivel}")
	@Produces("application/xml")
	public String getExamenTeorico(@PathParam("nivel") int nivel){
		final ExamenTeorico teorico = dao.getExamenTeorico(nivel);
		if (teorico==null) throw new WebApplicationException(Response.Status.NOT_FOUND);
		else return this.examenTeoricoToXml(teorico);
	}
	
	@POST
	@Path("/aprueba")
	@Produces(MediaType.TEXT_HTML)
	public Response insertAprobadoTeorico(@FormParam("dni") String dni, @FormParam("id") int id){
		if (dao.insertaAprobadoTeorico(dni, id, Tools.getDate())>0) return Response.status(Response.Status.OK).build();
		else return Response.status(Response.Status.NOT_FOUND).build();
		
	}


	private String examenTeoricoToXml(ExamenTeorico t) {
		return "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>"+
				"<examen_teorico>"+
				"<id_examen>"+t.getIdTeorico()+"</id_examen>"+
      			"<nombre>" + t.getNombre() + "</nombre>"+
      			"<descripcion>" + t.getDescripcion() + "</descripcion>"+
      			"<tiempo_examen>" + t.getTiempo() + "</tiempo_examen>"+
      			"<num_preguntas>" + t.getNumPreguntas() + "</num_preguntas>"+
      			"<nivel>" + t.getNivel() + "</nivel>"+
      		"</examen_teorico>";
	}

}
