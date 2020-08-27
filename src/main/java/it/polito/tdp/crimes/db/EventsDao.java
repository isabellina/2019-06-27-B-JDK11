package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.crimes.model.Arco;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getCategoria(){
		String sql = "select  distinct e.`offense_category_id` as cat " + 
				"from events as e " + 
				"order by e.`offense_category_id` ASC" ;
		
		List<String> categorie = new LinkedList<String>();
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				categorie.add(res.getString("cat"));
			}
			conn.close();
			return categorie;
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> getYear(){
		String sql = "select  distinct Month(e.`reported_date`) as mese " + 
				"from events as e " + 
				"order by Month(e.`reported_date`) ASC" ;
		
		List<Integer> mesi = new LinkedList<Integer>();
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				mesi.add(res.getInt("mese"));
			}
			conn.close();
			return mesi;
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getVertex(String s, int a){
		String sql = "select distinct  e.`offense_type_id` as tipi " + 
				"from events as e " + 
				"where e.`offense_category_id` = ? and Month(e.`reported_date`)= ?" ;
		
		
		List<String> listaTipi = new LinkedList<String>();
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, s);
			st.setInt(2, a);
			
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				listaTipi.add(res.getString("tipi"));
			}
			conn.close();
			return listaTipi;
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	
	public List<Arco> getArchi(String c, int m){
		String sql = " select e.`offense_type_id`as id  , e1.`offense_type_id` as id1 , count(distinct e.`neighborhood_id`) as cnt " + 
				"from events as e , events as e1 " + 
				"where e.`neighborhood_id` = e1.`neighborhood_id` and e.`offense_type_id`!= e1.`offense_type_id` and e.`offense_category_id` =? and e1.`offense_category_id` = ? and Month(e.`reported_date`) =  ? and Month(e1.`reported_date`) =? " + 
				"group by e.`offense_type_id` , e1.`offense_type_id` " ;
		
		List<Arco> listaArchi = new LinkedList<Arco>();
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, c);
			st.setString(2, c);
			st.setInt(3, m);
			st.setInt(4, m);
			
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Arco a = new Arco(res.getString("id"), res.getString("id1"), res.getInt("cnt")) ;
				listaArchi.add(a);
			}
			conn.close();
			return listaArchi;
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

}
