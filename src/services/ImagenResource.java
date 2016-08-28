package services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

import data.Imagen;
import database.DAO;
import servlet.UserServlet;

@Path("/imagen")
public class ImagenResource {
	
	private DAO dao;
	private static String ROOT = UserServlet.getPath();
	
	public ImagenResource(){}
	
	public ImagenResource(DAO d){
		this.dao=d;
	}
	
	@POST
	@Path("/{tipo}")
	@Produces("image/png")
	public byte[] getImage(@PathParam("tipo") String tipo,
			@FormParam("examen") int examen,
			@FormParam("id") int id){
		final Imagen imagen = dao.getImagen(id, examen);
		byte[] fileBytes = null;
		String path = null;
		if (imagen!=null){
			switch(tipo){
				case "normal": path = imagen.getNormal(); break;
				case "organico": path = imagen.getOrganico(); break;
				case "inorganico": path = imagen.getInorganico(); break;
				case "bn": path = imagen.getBn(); break;
				default: path = imagen.getNormal(); break;
			}
			try{
				//El path debe ser /img/id_examen/id_imagen/{tipo}.png
				//Ejemplo /img/2/1/bn.png
				File file = new File(ROOT+path);
				if (file.exists()){
					FileInputStream fis = new FileInputStream(file);
		            BufferedInputStream inputStream = new BufferedInputStream(fis);
		            fileBytes = new byte[(int) file.length()];
		            inputStream.read(fileBytes);
		            inputStream.close();
				}
				else System.out.println("File not found: "+path);
				
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		return fileBytes;
	}
	
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public String getImageFromId(@PathParam("id") int id){
		final Imagen image = dao.getImagenFromId(id);
		if (image==null) throw new WebApplicationException(Response.Status.NOT_FOUND);
		else return this.objectToXml(image);
	}
	
	@GET
	@Path("/imagenes/{examen}")
	@Produces("application/xml")
	public String getListImagesFromExam(@PathParam("examen") int examen){
		List<Imagen> l = dao.getListaImagenesFromExamen(examen);
		if (!l.isEmpty()){
			String resul = this.listToXml(l);
			return resul;
		}
		else throw new WebApplicationException(Response.Status.NOT_FOUND);
	}
	
	/**
	 * Devuelve un objeto en formato xml
	 * @param im objeto de tipo imagen
	 * @return string que representa un objeto de tipo imagen
	 */
	private String objectToXml(Imagen im){
		return "<imagen>"+
				"<id_imagen>"+im.getId_imagen()+"</id_imagen>"+
      			"<normal>" + im.getNormal() + "</normal>"+
      			"<organico>" + im.getOrganico() + "</organico>"+
      			"<inorganico>" + im.getInorganico() + "</inorganico>"+
      			"<bn>" + im.getBn() + "</bn>"+
      			"<id_objeto>" + im.getId_objeto() + "</id_objeto>"+
      			"<id_examen>" + im.getId_examen() + "</id_examen>"+
      		"</imagen>";
	}
	
	/**
	 * Devuelve una lista de imagenes
	 * @param l lista de objetos de tipo imagen
	 * @return String que representa una lista de imagenes en formato xml
	 */
	private String listToXml(List<Imagen> l){
		String result = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>"+"<imagenes>";
		
		Iterator<Imagen> it = l.iterator();
		while (it.hasNext()){
			result+=objectToXml(it.next());
		}
		result+="</imagenes>";
		return result;
		
	}

}
