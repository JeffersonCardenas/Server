package services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.jboss.resteasy.util.Base64;

import database.DAO;
import data.ModuloTeorico;

@Path("/teoria")
public class ModuloTeoricoResource {
	
	private DAO dao;
	
	public ModuloTeoricoResource(){}
	
	public ModuloTeoricoResource(DAO d){
		this.dao=d;
	}
	
	@GET
	@Path("pdf/{nivel}/{modulo}")
	public byte[] getPDF(@PathParam("nivel") int nivel,@PathParam("modulo") int modulo){
        byte[] fileBytes = null;
        ModuloTeorico mod = dao.getModuloTeorico(nivel, modulo);
        if (mod!=null){
        	try {
            	String filePath = ModuloTeoricoResource.class.getResource("/teoria/c"+nivel+"/"+modulo+".pdf").toString();
                System.out.println("Sending file: " + filePath);
            	
                File file = new File(ModuloTeoricoResource.class.getResource("/teoria/c"+nivel+"/"+modulo+".pdf").toURI());
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream inputStream = new BufferedInputStream(fis);
                fileBytes = new byte[(int) file.length()];
                inputStream.read(fileBytes);
                inputStream.close();
                
            } catch (IOException ex) {
                System.err.println(ex);
            }
            catch(URISyntaxException ur){
            	System.err.println(ur);
            }
        }        
        return fileBytes;
	}
	
	@POST
	@Path("upload/{nivel}/{modulo}")
	@Consumes("application/x-www-form-urlencoded")
	public Response uploadPDF(@PathParam("nivel") int nivel,@PathParam("modulo") int modulo,
			@FormParam("file") String file){
		ModuloTeorico mod = dao.getModuloTeorico(nivel, modulo);
		byte[] imageBytes = null;
		int exito = 0;
		if (mod == null){
	        try {
	        	System.out.println("El Modulo no está en la BBDD");
				String filePath = ModuloTeoricoResource.class.getResource("/teoria/c"+nivel+"/").toString();
				System.out.println(filePath);
				
				imageBytes = Base64.decode(file.getBytes());
	        	
	        	String fileName = filePath + modulo+".pdf";
	        	System.out.println("Insertando archivo: "+fileName);

	        	File archivo = new File(fileName);
	        	if (!archivo.exists() && archivo.createNewFile()){
	        		FileUtils.writeByteArrayToFile(archivo, imageBytes);
		            if (imageBytes!=null) mod = new ModuloTeorico(modulo,nivel,imageBytes);
		            else mod = new ModuloTeorico(modulo,nivel);
		            exito = dao.insertModuloTeorico(mod);
		            System.out.println("File Created: " + filePath);
	        	}
	            
	        } catch (IOException ex) {
	            System.err.println(ex);
	        } catch (Exception e){
	        	System.err.println(e);
	        }
	        if (exito>0) return Response.status(Response.Status.OK).build();
	        else return Response.status(Response.Status.NOT_FOUND).build();
		}
		else return Response.status(Response.Status.BAD_REQUEST).build();
		
	}
	
	@GET
	@Path("{nivel}/{modulo}")
	@Produces("application/xml")
	public String getModuloTeorico(@PathParam("nivel") int nivel,@PathParam("modulo") int modulo){
		ModuloTeorico mod = dao.getModuloTeorico(nivel, modulo);
		if (mod==null){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		else {
			return this.moduloToXml(mod);
		}		
	}
	
	@GET
	@Path("{nivel}")
	@Produces("application/xml")
	public String getModulos(@PathParam("nivel") int nivel){
		List<ModuloTeorico> listaMod = dao.getListModTeorico(nivel);
		if (listaMod!=null){
			String result = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>"+
								"<modulos_teoricos>";
			Iterator<ModuloTeorico> it = listaMod.iterator();
			while (it.hasNext()){
				ModuloTeorico m = it.next();
				result+="<modulo_teorico>"+
						"<id_modulo>"+m.getId_modulo()+"</id_modulo>"+
						"<nivel>" + m.getNivel() + "</nivel>";
				if (m.getPdf()!=null) result+="<pdf>"+m.getPdf().toString()+"</pdf>";
				else result+="<pdf>0</pdf>";
				result+="</modulo_teorico>";
			}
			return result+="</modulos_teoricos>";
		}
		else throw new WebApplicationException(Response.Status.NOT_FOUND);		
	}
	
	private String moduloToXml(ModuloTeorico m){
		String result = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>"+
							"<modulo_teorico>"+
								"<id_modulo>"+m.getId_modulo()+"</id_modulo>"+
								"<nivel>" + m.getNivel() + "</nivel>";
		if (m.getPdf()!=null) result+="<pdf>"+m.getPdf().toString()+"</pdf>";
			else result+="<pdf>0</pdf>";
      	return result+=	"</modulo_teorico>";
	}

}
