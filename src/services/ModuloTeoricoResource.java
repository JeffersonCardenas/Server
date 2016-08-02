package services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

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
