package services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
				System.out.println("El path es: "+ROOT+path);
				File file = new File(ROOT+path);
				if (file.exists()){
					System.out.println("File found at: "+path);
					FileInputStream fis = new FileInputStream(file);
		            BufferedInputStream inputStream = new BufferedInputStream(fis);
		            fileBytes = new byte[(int) file.length()];
		            inputStream.read(fileBytes);
		            inputStream.close();
				}
				else System.out.println("File not found");
				
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		return fileBytes;
	}

}
