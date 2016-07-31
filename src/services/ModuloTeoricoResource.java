package services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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
	@Path("{nivel}/{modulo}")
	public byte[] getModulo(@PathParam("nivel") int nivel,@PathParam("modulo") int modulo){
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

}
