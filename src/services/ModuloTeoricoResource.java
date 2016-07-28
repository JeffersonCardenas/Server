package services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import database.DAO;

@Path("/teoria")
public class ModuloTeoricoResource {
	
	private DAO dao;
	
	public ModuloTeoricoResource(){}
	
	public ModuloTeoricoResource(DAO d){
		this.dao=d;
	}
	
	@GET
	@Path("/download/{nivel}/{modulo}")
	public byte[] getModulo(@PathParam("nivel") int nivel,@PathParam("modulo") int modulo){
		/*URL uri = ModuloTeoricoResource.class.getResource("/teoria/1.pdf");
		System.out.println(uri.toString());
		File file=null;
		try{
			file = new File(uri.toURI());
		}
		catch(URISyntaxException u){
			//u.printStackTrace();
			System.err.println("El fichero no existe");
		}
		        
        if (!file.exists())
        {
            System.out.println("El path no existe");
            return null;
        }

        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte fileContent[] = new byte[(int) file.length()];

        try {
            fin.read(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = javax.xml.bind.DatatypeConverter.printBase64Binary(fileContent);
        return result;*/
		
		//String filePath = "e:/Test/Server/Download/" + fileName;
		String filePath = ModuloTeoricoResource.class.getResource("/teoria/1.pdf").toString();
        System.out.println("Sending file: " + filePath);
        byte[] fileBytes = null;
         
        try {
            File file = new File(ModuloTeoricoResource.class.getResource("/teoria/1.pdf").toURI());
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
        return fileBytes;
		
	}

}
