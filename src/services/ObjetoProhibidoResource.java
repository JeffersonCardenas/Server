package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import data.ObjetoProhibido;
import database.DAO;

@Path("/prohibido")
public class ObjetoProhibidoResource {
	
	private DAO dao;
	
	public ObjetoProhibidoResource(){}
	
	public ObjetoProhibidoResource(DAO d){
		this.dao=d;
	}
	
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public String getObjetoProhibido(@PathParam("id") int id){
		final ObjetoProhibido objeto = dao.getObjetoProhibido(id);
		if (objeto==null) throw new WebApplicationException(Response.Status.NOT_FOUND);
		else return objetoProhibidoToXml(objeto);
	}
	
	/**
	 * Devuelve una representacion en formato xml de un Objeto Prohibido
	 * @param objeto ObjetoProhibido
	 * @return String que representa el xml de un objeto prohibido
	 */
	private String objetoProhibidoToXml(ObjetoProhibido objeto) {
		return "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>"+
				"<objeto_prohibido>"+
				"<id_objeto>"+objeto.getId_objeto()+"</id_objeto>"+
      			"<nombre>" + objeto.getNombre() + "</nombre>"+
      			"<posx>" + objeto.getPosx() + "</posx>"+
      			"<posy>" + objeto.getPosy() + "</posy>"+
      			"<alto>" + objeto.getAlto() + "</alto>"+
      			"<ancho>" + objeto.getAncho() + "</ancho>"+
      			"<id_arma>" + objeto.getId_arma() + "</id_arma>"+
      		"</objeto_prohibido>";
	}

}
