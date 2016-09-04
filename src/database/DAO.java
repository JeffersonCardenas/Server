package database;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.Statement;

import data.*;

public class DAO {
	
	private Connection connection;
	
	public DAO(){ }
	
	/**
	 * Accede a BBDD y devuelve un usuario
	 * @param dni String
	 * @param pass String
	 * @return objeto de tipo User, o null si no existe
	 */
	public User getUser(String dni, String pass){
		PreparedStatement pst = null;
		ResultSet rs = null;
		User result	= null;
		String query = null;
		try{
			this.connection = DBConnection.getConnection();
			query = "select DNI,Fecha,Nombre_Completo,Tipo,Pass from USUARIO where DNI=? and PASS=?";
			pst = this.connection.prepareStatement(query);
			pst.setString(1, dni);
			pst.setString(2, pass);
			rs = pst.executeQuery();
			if (rs.next()){
				String dniP = rs.getString("DNI");
				String name = rs.getString("Nombre_Completo");
				String p = rs.getString("Pass");
				String f = rs.getDate("Fecha").toString();
				int tipo = rs.getInt("Tipo");
				result = new User(dniP,p,name,f,tipo);
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();
			} catch (Exception e) {}
		}
		return result;		
	}
	
	/**
	 * Inserta un usuario en BBDD
	 * @param name String
	 * @param dni String
	 * @param pass String
	 * @param f Date
	 * @return 1 si se ha podido insertar el usuario correctamente, 0 en caso contrario
	 */
	public int insertaUsuario(String name, String dni, String pass, Date f) {
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		try{
			this.connection = DBConnection.getConnection();
			query = "insert into USUARIO (DNI,Fecha,Nombre_Completo,Tipo,Pass) values (?,?,?,?,?)";
			pst = this.connection.prepareStatement(query);
			pst.setString(1, dni);
			pst.setDate(2, f);
			pst.setString(3, name);
			pst.setInt(4, 0);
			pst.setString(5, pass);
			resul = pst.executeUpdate();			
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return resul;
	}
	
	/**
	 * Elimina un usuario de la BBDD
	 * @param dni String del alumno
	 * @return 1 si se ha borrado correctamente, 0 en otro caso
	 */
	public int deleteUsuario(String dni) {
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		try{
			this.connection = DBConnection.getConnection();
			query = "delete from usuario where dni=?";
			pst = this.connection.prepareStatement(query);
			pst.setString(1, dni);
			resul = pst.executeUpdate();			
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return resul;
	}
	
	/**
	 * Comprueba si un usuario se encuentra en BBDD
	 * @param dni String
	 * @return true si existe el usuario, false en caso contrario
	 */
	public boolean existeUsuario(String dni) {
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		boolean resul = false;
		try{
			this.connection = DBConnection.getConnection();
			query = "select nombre_completo from usuario where dni=?";
			pst = this.connection.prepareStatement(query);
			pst.setString(1, dni);
			rs = pst.executeQuery();
			resul = rs.next();
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return resul;
	}
	
	/**
	 * Actualiza los datos de un usuario en BBDD
	 * @param dni String
	 * @param name String
	 * @param pass String
	 * @return 1 si se han actualizado correctamente los datos, 0 en caso contrario
	 */
	public int updateUsuario(String dni, String name, String pass) {
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		try{
			this.connection = DBConnection.getConnection();
			query = "update usuario set nombre_completo=?,pass=? where dni=?";
			pst = this.connection.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, pass);
			pst.setString(3, dni);
			resul = pst.executeUpdate();			
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return resul;
	}
	
	/**
	 * Devuelve una lista con los usuarios del sistema
	 * @return List de objetos de tipo User
	 */
	public List<User> getUserList() {
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<User> resul = new LinkedList<User>();
		try{
			con = DBConnection.getConnection();
			String sql = "SELECT * FROM usuario";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next() && rs.getInt("Tipo")==0) {
				String dni = rs.getString("DNI");
				String name = rs.getString("Nombre_Completo");
				String p = rs.getString("Pass");
				String f = rs.getDate("Fecha").toString();
				int tipo = rs.getInt("Tipo");
				resul.add(new User(dni,p,name,f,tipo));
			}
			return resul;
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return null;
	}
	
	/**
	 * Comprueba si el alumno tiene aprobado un examen teorico
	 * @param dni del alumno
	 * @param idExamen examen teorico
	 * @return devuelve 1 si el alumno ha aprobado el examen teorico, 0 en caso contrario
	 */
	public int tieneAprobadoTeorico(String dni, int idExamen) {
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		try{
			this.connection = DBConnection.getConnection();
			query = "select dni from aprueba_teorico a join examen_teorico b where "
					+ "a.Id_Examen_Teorico=b.Id_Examen and a.Id_Examen_Teorico=? and a.DNI=?";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, idExamen);
			pst.setString(2, dni);
			rs = pst.executeQuery();
			if (rs.next()) resul=1;
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return resul;
	}
	
	/**
	 * Comprueba si un usuario tiene un examen practico aprobado previamente
	 * @param dni String
	 * @param idPractico int
	 * @return 1 si el usuario ha aprobado el practico, 0 en caso contrario
	 */
	public int tieneAprobadoPractico(String dni, int idPractico){
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		try{
			this.connection = DBConnection.getConnection();
			query = "select DNI from aprueba_practico where Id_Examen_Practico=? and DNI=?";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, idPractico);
			pst.setString(2, dni);
			rs = pst.executeQuery();
			if (rs.next()) resul=1;
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return resul;
	}
	
	/**
	 * Devuelve todas las Certificaciones de la BBDD
	 * @return List de objetos de tipo Certificados
	 */
	public List<Certificados> getCertificados() {
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<Certificados> resul = new LinkedList<Certificados>();
		try{
			con = DBConnection.getConnection();
			String sql = "SELECT * FROM certificacion";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				int t = rs.getInt("Nivel");
				resul.add(new Certificados(t));
			}
			return resul;
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return null;
	}
	
	/**
	 * Devuelve los certificados obtenidos por un usuario
	 * @param dni del alumno
	 * @return Lista de certificados obtenidos por el alumno
	 */
	public List<Certificados> getCertificadosFromUser(String dni) {
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<Certificados> resul = new LinkedList<Certificados>();
		try{
			con = DBConnection.getConnection();
			String sql = "select nivel from tiene where dni=?";
			pst = con.prepareStatement(sql);
			pst.setString(1, dni);
			rs = pst.executeQuery();
			while (rs.next()) {
				int t = rs.getInt("Nivel");
				resul.add(new Certificados(t));
			}
			return resul;
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return null;
	}
	
	/**
	 * Devuelve el Examen Practico de un determinado nivel de Certificacion
	 * @param level int
	 * @return Examen Practico
	 */
	public ExamenPractico getExamenPractico(int level) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		ExamenPractico result	= null;
		String query = null;
		try{
			this.connection = DBConnection.getConnection();
			query = "select Id_Examen,num_imagenes,Tiempo_Examen from EXAMEN_PRACTICO where Nivel=?";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, level);
			rs = pst.executeQuery();
			if (rs.next()){
				int id = rs.getInt("Id_Examen");
				int n = rs.getInt("num_imagenes");
				int t = rs.getInt("Tiempo_Examen");
				result = new ExamenPractico(id,level,n,t);
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();
			} catch (Exception e) {}
		}
		return result;
	}
	
	/**
	 * Devuelve un Examen Teorico de una determinada Certificacion	
	 * @param level int
	 * @return Examen Teorico
	 */
	public ExamenTeorico getExamenTeorico(int level) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		ExamenTeorico result	= null;
		String query = null;
		try{
			this.connection = DBConnection.getConnection();
			query = "select Id_Examen,Nombre,Descripcion,Tiempo_Examen,num_preguntas from EXAMEN_TEORICO where Nivel=?";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, level);
			rs = pst.executeQuery();
			if (rs.next()){
				int id = rs.getInt("Id_Examen");
				String name = rs.getString("Nombre");
				String d = rs.getString("Descripcion");
				int tiempo = rs.getInt("Tiempo_Examen");
				int num = rs.getInt("num_preguntas");
				result = new ExamenTeorico(id,name,d,tiempo,num,level);
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();
			} catch (Exception e) {}
		}
		return result;
	}
		
	/**
	 * 
	 * @param n nivel de la certificacion
	 * @param id id_modulo del Modulo Teorico
	 * @return Modulo Teorico de la BBDD
	 */
	public ModuloTeorico getModuloTeorico(int n,int id){
		PreparedStatement pst = null;
		ResultSet rs = null;
		ModuloTeorico result	= null;
		String query = null;
		try{
			this.connection = DBConnection.getConnection();
			query = "select Id_Modulo,Nivel,PDF from modulo_teorico where Nivel=? and Id_Modulo=?";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, n);
			pst.setInt(2, id);
			rs = pst.executeQuery();
			if (rs.next()){
				int idmod = rs.getInt("Id_Modulo");
				int l = rs.getInt("Nivel");
				Blob p = rs.getBlob("PDF");
				if (!rs.wasNull()){
					byte[] pd = p.getBytes(1, (int)p.length());
					result = new ModuloTeorico(idmod,l,pd);
				}
				else result = new ModuloTeorico(idmod,l);
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();
			} catch (Exception e) {}
		}
		return result;
	}
		
	/**
	 * Devuelve una lista de todos los modulos teoricos pertenecientes a un nivel
	 * @param nivel de certificacion
	 * @return Lista de Modulos Teoricos
	 */
	public List<ModuloTeorico> getListModTeorico(int nivel) {
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<ModuloTeorico> resul = new LinkedList<ModuloTeorico>();
		try{
			con = DBConnection.getConnection();
			String sql = "select Id_Modulo,PDF from modulo_teorico where Nivel=?";
			pst = con.prepareStatement(sql);
			pst.setInt(1, nivel);
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("Id_Modulo");
				Blob b = rs.getBlob("PDF");
				if (!rs.wasNull()){
					byte[] pdf = b.getBytes(1, (int)b.length());
					resul.add(new ModuloTeorico(id,nivel,pdf));
				}
				else resul.add(new ModuloTeorico(id,nivel));
			}
			return resul;
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return null;
	}
		
	/**
	 * Inserta un nuevo modulo teorico en la BBDD
	 * @param m Modulo Teorico
	 * @return
	 */
	public int insertModuloTeorico(ModuloTeorico m){
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		Blob blob = null;
		try{
			this.connection = DBConnection.getConnection();
			query = "insert into modulo_teorico (Id_Modulo,Nivel,PDF) values (?,?,?)";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, m.getId_modulo());
			pst.setInt(2, m.getNivel());
			if (m.getPdf()!=null){
				blob = this.connection.createBlob();
				blob.setBytes(1, m.getPdf());
				pst.setBlob(3, blob);
			}
			else pst.setBlob(3, blob);
			resul = pst.executeUpdate();		
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return resul;
	}
		
	/**
	 * Accede a BBDD y devuelve una lista con las preguntas pertenecientes a un examen
	 * @param idExamen
	 * @return Lista de Pregunta o null si no hay preguntas para esa id
	 */
	public List<Pregunta> getListaPreguntasFromExamen(int idExamen) {
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<Pregunta> resul = new LinkedList<Pregunta>();
		try{
			con = DBConnection.getConnection();
			String sql = "select Id_Pregunta,Enunciado from pregunta where Id_Examen=?";
			pst = con.prepareStatement(sql);
			pst.setInt(1, idExamen);
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("Id_Pregunta");
				String e = rs.getString("Enunciado");
				resul.add(new Pregunta(id,e,idExamen));
			}
			return resul;
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return null;
	}
		
	/**
	 * Accede a BBDD y devuelve una lista de Respuestas perteneciente a una pregunta
	 * @param idPregunta
	 * @return Lista de Respuestas o null si hay un error
	 */
	public List<Respuesta> getListaRespuestasFromPregunta(int idPregunta) {
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<Respuesta> resul = new LinkedList<Respuesta>();
		try{
			con = DBConnection.getConnection();
			String sql = "select Id_Respuesta,Es_Correcta,Enunciado from respuesta where Id_Pregunta=?";
			pst = con.prepareStatement(sql);
			pst.setInt(1, idPregunta);
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("Id_Respuesta");
				int c = rs.getInt("Es_Correcta");
				String e = rs.getString("Enunciado");
				resul.add(new Respuesta(id,c,e,idPregunta));
			}
			return resul;
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return null;
	}
		
	/**
	 * Inserta un nuevo aprobado en la tabla aprueba_teorico
	 * @param dni del alumno
	 * @param idExamenTeorico del Examen Teorico aprobado
	 * @param f fecha en la que se obtiene el aprobado
	 * @return 1 si se ha insertado correctamente, 0 en caso contrario
	 */
	public int insertaAprobadoTeorico(String dni, int idExamenTeorico, Date f) {
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		try{
			this.connection = DBConnection.getConnection();
			query = "insert into aprueba_teorico (Id_Examen_Teorico,DNI,Fecha) values (?,?,?)";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, idExamenTeorico);
			pst.setString(2, dni);
			pst.setDate(3, f);
			resul = pst.executeUpdate();			
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return resul;
	}

	/**
	 * Inserta en la tabla tiene una nueva certificacion para el alumno
	 * @param level nivel obtenido
	 * @param dni del alumno
	 * @return 1 si se ha podido insertar correctamente, 0 en otro caso
	 */
	public int obtieneCertificacion(int level, String dni) {
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		try{
			this.connection = DBConnection.getConnection();
			query = "insert into tiene (Nivel,DNI) values (?,?)";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, level);
			pst.setString(2, dni);
			resul = pst.executeUpdate();			
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return resul;
	}
	
	/**
	 * Accede a BBDD y devuelve una entidad de tipo Imagen
	 * @param id de la imagen
	 * @param examen id del examen al que pertenece esta imagen
	 * @return devuelve un objeto de tipo Imagen o null si no existe en BBDD
	 */
	public Imagen getImagen(int id,int examen){
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		Imagen result	= null;
		try{
			this.connection = DBConnection.getConnection();
			query = "select normal,organico,inorganico,bn,Id_Objeto "
					+ "from imagen where Id_Imagen=? and Id_Examen=?";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, id);
			pst.setInt(2, examen);
			rs = pst.executeQuery();
			if (rs.next()){
				String n = rs.getString("normal");
				String o = rs.getString("organico");
				String ino = rs.getString("inorganico");
				String bn = rs.getString("bn");
				int id_o = rs.getInt("Id_Objeto");
				result = new Imagen(id,n,o,ino,bn,id_o,examen);
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();
			} catch (Exception e) {}
		}
		return result;	
	}
	
	/**
	 * Devuelve un objeto de tipo imagen con el id pasado por parámetro
	 * @param idImagen
	 * @return Objeto Imagen o null si no existe
	 */
	public Imagen getImagenFromId(int idImagen){
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		Imagen result	= null;
		try{
			this.connection = DBConnection.getConnection();
			query = "select normal,organico,inorganico,bn,Id_Objeto,Id_Examen "
					+ "from imagen where Id_Imagen=?";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, idImagen);
			rs = pst.executeQuery();
			if (rs.next()){
				String n = rs.getString("normal");
				String o = rs.getString("organico");
				String ino = rs.getString("inorganico");
				String bn = rs.getString("bn");
				int id_o = rs.getInt("Id_Objeto");
				int id_e = rs.getInt("Id_Examen");
				result = new Imagen(idImagen,n,o,ino,bn,id_o,id_e);
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();
			} catch (Exception e) {}
		}
		return result;	
	}
	
	/**
	 * Devuelve una lista de imagenes pertenecientes a un examen
	 * @param examen id del examen practico al que pertenecen las imagenes
	 * @return List de objetos de tipo Imagen, vacia si no hay ningun elemento en BBDD
	 */
	public List<Imagen> getListaImagenesFromExamen(int examen){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<Imagen> resul = new LinkedList<Imagen>();
		try{
			con = DBConnection.getConnection();
			String sql = "select Id_Imagen,normal,organico,inorganico,bn,Id_Objeto "
					+ "from imagen where Id_Examen=?";
			pst = con.prepareStatement(sql);
			pst.setInt(1, examen);
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("Id_Imagen");
				String n = rs.getString("normal");
				String o = rs.getString("organico");
				String in = rs.getString("inorganico");
				String bn = rs.getString("bn");
				int id_o = rs.getInt("Id_Objeto");
				resul.add(new Imagen(id,n,o,in,bn,id_o,examen));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return resul;
		
	}
	
	/**
	 * Actualiza los datos de una imagen en la BBDD
	 * @param image Imagen
	 * @return int 1 si se ha actualizado correctamente la fila, 0 en caso contrario
	 */
	public int updateImagen(Imagen image){
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		try{
			this.connection = DBConnection.getConnection();
			query = "update imagen set normal=?,organico=?,inorganico=?,bn=?,Id_Objeto=? where Id_Imagen=?";
			pst = this.connection.prepareStatement(query);
			pst.setString(1, image.getNormal());
			pst.setString(2, image.getOrganico());
			pst.setString(3, image.getInorganico());
			pst.setString(4, image.getBn());
			if (image.getId_objeto()>0) pst.setInt(5, image.getId_objeto());
			else	pst.setNull(5, Types.NULL);
			pst.setInt(6, image.getId_imagen());
			resul = pst.executeUpdate();			
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return resul;
	}
	
	/**
	 * Inserta una nueva imagen sin objeto prohibido asociado
	 * @param idExamen int Id del Examen Practico al que pertenece la imagen
	 * @return int devuelve el id de la imagen insertada o 0 si no se ha podido insertar
	 */
	public int insertaImagen(int idExamen){
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		int lastid = 0;
		try{
			this.connection = DBConnection.getConnection();
			query = "insert into imagen (normal, organico, inorganico, bn, Id_Objeto, Id_Examen)"
					+ " values (NULL,NULL,NULL,NULL,NULL,?)";
			pst = this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, idExamen);
			resul = pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next() && resul>0){
				lastid = rs.getInt(1);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return lastid;
	}
	
	/**
	 * Inserta un aprobado practico
	 * @param dni String del alumno que lo ha aprobado
	 * @param idExamenPractico int
	 * @param fecha Date
	 * @return un 1 si se ha insertado correctamente los datos en la tabla Aprueba_Practico, 0 en otro caso
	 */
	public int insertaAprobadoPractico(String dni, int idExamenPractico, Date fecha){
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		try{
			this.connection = DBConnection.getConnection();
			query = "insert into aprueba_practico (Id_Examen_Practico,DNI,Fecha) values (?,?,?)";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, idExamenPractico);
			pst.setString(2, dni);
			pst.setDate(3, fecha);
			resul = pst.executeUpdate();			
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return resul;
	}
	
	/**
	 * Devuelve un Objeto Prohibido de la BBDD
	 * @param idObjeto int
	 * @return el objeto prohibido si existe ese id en BBDD, null en caso contrario
	 */
	public ObjetoProhibido getObjetoProhibido(int idObjeto){
		PreparedStatement pst = null;
		ResultSet rs = null;
		ObjetoProhibido result	= null;
		String query = null;
		try{
			this.connection = DBConnection.getConnection();
			query = "select Id_Objeto,Nombre,posx,posy,alto,ancho,Id_Arma from objeto_prohibido where Id_Objeto=?";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, idObjeto);
			rs = pst.executeQuery();
			if (rs.next()){
				int id = rs.getInt("Id_Objeto");
				String name = rs.getString("Nombre");
				int x = rs.getInt("posx");
				int y = rs.getInt("posy");
				int alt = rs.getInt("alto");
				int anc = rs.getInt("ancho");
				int arma = rs.getInt("Id_Arma");
				result = new ObjetoProhibido(id,name,x,y,alt,anc,arma);
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();
			} catch (Exception e) {}
		}
		return result;
	}
	
	/**
	 * Inserta un nuevo Objeto Prohibido en la BBDD
	 * @param prohibido ObjetoProhibido
	 * @return int devuelve el id auto generado al insertar el objeto prohibido, 0 en caso contrario
	 */
	public int insertObjetoProhibido(ObjetoProhibido prohibido){
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		int lastid=0;
		try{
			this.connection = DBConnection.getConnection();
			query = "insert into objeto_prohibido (Nombre,posx,posy,alto,ancho,Id_Arma) values (?,?,?,?,?,?)";
			pst = this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "ObjetoPeligroso");
			pst.setInt(2, prohibido.getPosx());
			pst.setInt(3, prohibido.getPosy());
			pst.setInt(4, prohibido.getAlto());
			pst.setInt(5, prohibido.getAncho());
			pst.setInt(6, prohibido.getId_arma());
			resul = pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next() && resul>0){
				lastid = rs.getInt(1);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return lastid;
	}
	
	/**
	 * Inserta una pregunta en la BBDD
	 * @param enunciado String
	 * @param idExamen int
	 * @return el id auto generado al insertar una nueva columna en la tabla Pregunta
	 */
	public int insertaPregunta(String enunciado, int idExamen) {
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		int lastid = 0;
		try{
			this.connection = DBConnection.getConnection();
			query = "insert into pregunta (Enunciado,Id_Examen) values (?,?)";
			pst = this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, enunciado);
			pst.setInt(2, idExamen);
			resul = pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next() && resul>0){
				lastid = rs.getInt(1);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return lastid;
	}
	
	/**
	 * Inserta una nueva respuesta perteneciente a una pregunta determinada
	 * @param idPregunta int
	 * @param resp String que representa el enunciado de la respuesta
	 * @param correcta int 1 si es correcta, 0 si es falsa
	 * @return int 1 si se ha insertado correctamente, 0 si ha habido algun error
	 */
	public int insertaRespuesta(int idPregunta, String resp, int correcta){
		PreparedStatement pst = null;
		ResultSet rs          = null;
		String query = null;
		int resul = 0;
		try{
			this.connection = DBConnection.getConnection();
			query = "insert into respuesta (Es_Correcta,Enunciado,Id_Pregunta) values (?,?,?)";
			pst = this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, correcta);
			pst.setString(2, resp);
			pst.setInt(3, idPregunta);
			resul = pst.executeUpdate();
			rs = pst.getGeneratedKeys();
		}
		catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();				
			} catch (Exception e) {}
		}
		return resul;
	}
	
	/**
	 * Devuelve el tipo de arma correspondiente al id pasado como parametro
	 * @param idArma int
	 * @return objeto TipoArma o null si no existe el objeto con esa id en la BBDD
	 */
	public TipoArma getTipoArma(int idArma){
		PreparedStatement pst = null;
		ResultSet rs = null;
		TipoArma result	= null;
		String query = null;
		try{
			this.connection = DBConnection.getConnection();
			query = "select Id_Arma,Descripcion from tipo_arma where Id_Arma=?";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, idArma);
			rs = pst.executeQuery();
			if (rs.next()){
				int id = rs.getInt("Id_Arma");
				String desc = rs.getString("Descripcion");
				result = new TipoArma(id,desc);
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (this.connection != null) this.connection.close();
			} catch (Exception e) {}
		}
		return result;
	}
	
	/**
	 * Devuelve todos los tipos de arma del sistema
	 * @return List<TipoArma>
	 */
	public List<TipoArma> getListTipoArma(){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<TipoArma> resul = new LinkedList<TipoArma>();
		try{
			con = DBConnection.getConnection();
			String sql = "select Id_Arma,Descripcion from tipo_arma";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				int t = rs.getInt("Id_Arma");
				String desc = rs.getString("Descripcion");
				resul.add(new TipoArma(t,desc));
			}
			return resul;
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return null;
	}


}
