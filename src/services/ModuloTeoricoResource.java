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

import org.apache.commons.codec.binary.Base64;

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
	@Produces("application/pdf")
	public byte[] getPDF(@PathParam("nivel") int nivel,@PathParam("modulo") int modulo){
        byte[] fileBytes = null;
        ModuloTeorico mod = dao.getModuloTeorico(nivel, modulo);
        if (mod!=null){
        	if (mod.getPdf()==null){
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
        	else fileBytes = mod.getPdf();
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
		
		if (mod == null){
			imageBytes = Base64.decodeBase64(file);
				
			System.out.println("String descodificado");
	        		
		    if (imageBytes!=null) mod = new ModuloTeorico(modulo,nivel,imageBytes);
		    else mod = new ModuloTeorico(modulo,nivel);
	        
	        if (dao.insertModuloTeorico(mod)>0) return Response.status(Response.Status.OK).build();
	        else return Response.status(Response.Status.NOT_FOUND).build();
		}
		else return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	@GET
	@Path("{nivel}/{modulo}")
	@Produces("application/xml")
	public String getModuloTeorico(@PathParam("nivel") int nivel,@PathParam("modulo") int modulo){
		ModuloTeorico mod = dao.getModuloTeorico(nivel, modulo);
		if (mod==null)	throw new WebApplicationException(Response.Status.NOT_FOUND);
		else return this.moduloToXml(mod);	
	}
	
	@GET
	@Path("{nivel}")
	@Produces("application/xml")
	public String getModulos(@PathParam("nivel") int nivel){
		List<ModuloTeorico> listaMod = dao.getListModTeorico(nivel);
		if (listaMod!=null){
			String result = "<modulos_teoricos>";
			Iterator<ModuloTeorico> it = listaMod.iterator();
			while (it.hasNext()){
				ModuloTeorico m = it.next();
				result+="<modulo_teorico>"+
						"<id_modulo>"+m.getId_modulo()+"</id_modulo>"+
						"<nivel>" + m.getNivel() + "</nivel>";
				if (m.getPdf()!=null) result+="<pdf>"+new String(Base64.encodeBase64(m.getPdf()))+"</pdf>";
				else result+="<pdf></pdf>";
				result+="</modulo_teorico>";
			}
			return result+="</modulos_teoricos>";
		}
		else throw new WebApplicationException(Response.Status.NOT_FOUND);		
	}
	
	private String moduloToXml(ModuloTeorico m){
		String result = "<modulo_teorico>"+
								"<id_modulo>"+m.getId_modulo()+"</id_modulo>"+
								"<nivel>" + m.getNivel() + "</nivel>";
		if (m.getPdf()!=null) result+="<pdf>"+new String(Base64.encodeBase64(m.getPdf()))+"</pdf>";
					else result+="<pdf></pdf>";
      	return result+=	"</modulo_teorico>";
	}

}
