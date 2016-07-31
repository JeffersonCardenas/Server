package database;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import data.*;

public class DAO {
	
	private Connection connection;
	
	public DAO(){
		
	}
	
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
				resul.add(new User(dni,f,name,p,tipo));
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
	
	
	public ExamenPractico getExamenPractico(int level) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		ExamenPractico result	= null;
		String query = null;
		try{
			this.connection = DBConnection.getConnection();
			query = "select Id_Examen,NumImagenes from EXAMEN_PRACTICO where Nivel=?";
			pst = this.connection.prepareStatement(query);
			pst.setInt(1, level);
			rs = pst.executeQuery();
			if (rs.next()){
				int id = rs.getInt("Id_Examen");
				int n = rs.getInt("NumImagenes");
				result = new ExamenPractico(id,level,n);
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
	
		
		public ExamenTeorico getExamenTeorico(int level) {
			PreparedStatement pst = null;
			ResultSet rs = null;
			ExamenTeorico result	= null;
			String query = null;
			try{
				this.connection = DBConnection.getConnection();
				query = "select Id_Examen,Nombre,Descripcion,Tiempo_Examen,numPreguntas from EXAMEN_TEORICO where Nivel=?";
				pst = this.connection.prepareStatement(query);
				pst.setInt(1, level);
				rs = pst.executeQuery();
				if (rs.next()){
					int id = rs.getInt("Id_Examen");
					String name = rs.getString("Nombre");
					String d = rs.getString("Descripcion");
					int tiempo = rs.getInt("Tiempo_Examen");
					int num = rs.getInt("numPreguntas");
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


}
