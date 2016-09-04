package services;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import data.TipoArma;
import database.DAO;

@Path("/tipoarma")
public class TipoArmaResource {
	
	private DAO dao;
	
	public TipoArmaResource(){}
	
	public TipoArmaResource(DAO d){
		this.dao=d;
	}
	
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public String getTipoArma(@PathParam("id") int id){
		TipoArma arma = dao.getTipoArma(id);
		if (arma!=null) return armaToXml(arma);
		else throw new WebApplicationException(Response.Status.NOT_FOUND);
	}
	
	@GET
	@Path("list")
	@Produces("application/xml")
	public String getListTipoArma(){
		List<TipoArma> list = dao.getListTipoArma();
		if (!list.isEmpty()) return listToXml(list);
		else throw new WebApplicationException(Response.Status.NOT_FOUND);
	}
	
	/**
	 * Transforma un objeto en un String que representa un xml
	 * @param arm TipoArma
	 * @return String que representa el xml
	 */
	private String armaToXml(TipoArma arm){
		return "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>"+
				"<tipo_arma>"+
      			"<id_arma>" + arm.getId_arma() + "</id_arma>"+
				"<descripcion>" + arm.getDescripcion() + "</descripcion>"+
      		"</tipo_arma>";
	}
	
	/**
	 * Transforma una lista de tipos de armas en representacion en String
	 * @param l List
	 * @return
	 */
	private String listToXml(List<TipoArma> l){
		String result = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?><tipos_armas>";
		
		Iterator<TipoArma> it = l.iterator();
		while (it.hasNext()){
			TipoArma aux = it.next();
			result+="<tipo_arma>"+
					"<id_arma>" + aux.getId_arma() + "</id_arma>"+
					"<descripcion>" + aux.getDescripcion() + "</descripcion>"+
			"</tipo_arma>";
		}
		result+="</tipos_armas>";
		return result;
		
	}

}
