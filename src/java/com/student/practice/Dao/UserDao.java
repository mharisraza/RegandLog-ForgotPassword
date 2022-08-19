package com.student.practice.Dao;

import com.student.practice.entities.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    
    private Connection con; // con value isn't null, check ConnectionProvider class to get verification. make it final.

    public UserDao(Connection con) {        
        this.con = con;
    }

    /* Creating a method to insert data to the database */
    public boolean saveUser(Users users) { // getting users information through Users class 
        
        boolean f = false;
        try {
            
            String query = "insert into users(name,email,password) values (?,?,?)";
            
            PreparedStatement pstmt = con.prepareStatement(query);
            
            pstmt.setString(1, users.getName());
            pstmt.setString(2, users.getEmail());
            pstmt.setString(3, users.getPassword());
            
            pstmt.executeUpdate();
            
            f = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
        
    }

    public Users getUserByEmailAndPassword(String email, String password) {
        Users users = null;
        
        try {
            
            String query = "select * from users where email=? and password=?";
            
            PreparedStatement pstmt = con.prepareStatement(query);
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            ResultSet set = pstmt.executeQuery();
            
            if (set.next()) {
                users = new Users();
                
                users.setName(set.getString("name"));
                users.setId(set.getInt("id"));
                users.setEmail(set.getString("email"));
                users.setPassword(set.getString("password"));
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
        
    }
    
}
