package com.journaldev.spring.jdbc.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 


import javax.sql.DataSource;
 


import com.journaldev.spring.jdbc.model.Url;
 
/*public class UrlDAOImpl implements UrlDAO {
 
 /*   private DataSource dataSource;
 
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
 
    
    public void save(Url url) {
        String query = "insert into record (recordID, url) values (?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, Url.getId());
            ps.setString(2, Url.getName());
            ps.setString(3, Url.getRole());
            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Url saved with id="+Url.getId());
            }else System.out.println("Url save failed with id="+Url.getId());
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 

    public Url getById(int id) {
        String query = "select name, role from Url where id = ?";
        Url emp = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                emp = new Url();
                emp.setId(id);
                emp.setName(rs.getString("name"));
                emp.setRole(rs.getString("role"));
                System.out.println("Url Found::"+emp);
            }else{
                System.out.println("No Url found with id="+id);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return emp;
    }

    public void update(Url Url) {
        String query = "update Url set name=?, role=? where id=?";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, Url.getName());
            ps.setString(2, Url.getRole());
            ps.setInt(3, Url.getId());
            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Url updated with id="+Url.getId());
            }else System.out.println("No Url found with id="+Url.getId());
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 
  
    public void deleteById(int id) {
        String query = "delete from Url where id=?";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Url deleted with id="+id);
            }else System.out.println("No Url found with id="+id);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 

    public List<Url> getAll() {
        String query = "select id, name, role from Url";
        List<Url> empList = new ArrayList<Url>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                Url emp = new Url();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setRole(rs.getString("role"));
                empList.add(emp);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return empList;
    }
 
}*/