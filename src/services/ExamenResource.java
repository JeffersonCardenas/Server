package services;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import javax.ws.rs.GET;
import data.ExamenPractico;
import data.ExamenTeorico;
import database.DAO;

@Path("/examen")
public class ExamenResource {
	
	private DAO dao = new DAO();
	
	@GET
	@Path("/teorico/{nivel}")
	@Produces("application/xml")
	public String getExamenTeorico(@PathParam("nivel") int nivel){
		final ExamenTeorico teorico = dao.getExamenTeorico(nivel);
		if (teorico==null) throw new WebApplicationException(Response.Status.NOT_FOUND);
		else return this.examenTeoricoToXml(teorico);
	}
	
	

	@GET
	@Path("/practico/{nivel}")
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



	private String examenTeoricoToXml(ExamenTeorico t) {
		/*byte[] bytes = t.getDescripcion().getBytes( Charset.forName("UTF-8" ));
		String desc = new String( bytes, Charset.forName("UTF-8") );*/
		return "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>"+
				"<examen_teorico>"+
				"<id_examen>"+t.getIdTeorico()+"</id_examen>"+
      			"<nombre>" + t.getNombre() + "</nombre>"+
      			"<descripcion>" + t.getDescripcion() + "</descripcion>"+
      			"<tiempo_examen>" + t.getTiempo() + "</tiempo_examen>"+
      			"<numpreguntas>" + t.getNumPreguntas() + "</numpreguntas>"+
      			"<nivel>" + t.getNivel() + "</nivel>"+
      		"</examen_teorico>";
	}

}
