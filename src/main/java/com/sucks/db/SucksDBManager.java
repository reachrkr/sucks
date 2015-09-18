package com.sucks.db;

import com.sucks.dto.SucksDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swachtest on 29/04/15.
 */
public class SucksDBManager {

    public static void insertSuckData(SucksDto suckData) {
        Connection connection = null;
        try {
            connection = getConnection();

            Statement stmt = connection.createStatement();
            int res1 = stmt.executeUpdate(" CREATE TABLE IF NOT EXISTS sucks (ID serial PRIMARY KEY NOT NULL, verb text,message text )");

            if( res1 == 0 ) System.out.println(" Table Exists");
            stmt.close();


            if(suckData == null) {
                return;
            }
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sucks(verb,message) VALUES (?,?)");


            preparedStatement.setString(1, suckData.getVerb());
            preparedStatement.setString(2, suckData.getMessage());


            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try{connection.close();} catch(SQLException e){}
        }
    }

    private static Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        int port = dbUri.getPort();

        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + port + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }



    public static List <SucksDto> getSucksData()
            throws Exception {
        List<SucksDto> sucksList = new ArrayList<SucksDto>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null,rs1=null;

        try {
            connection = getConnection();

            stmt = connection.createStatement();

            rs = stmt.executeQuery(" SELECT verb,message from sucks ");

            while (rs.next()) {

                SucksDto SucksDto = new SucksDto();
                SucksDto.setVerb(rs.getString("verb"));
                SucksDto.setMessage(rs.getString("message"));

          ;
                sucksList.add(SucksDto);

                //  out += "Read from tweets DB: " + rs.getString("tweettext") +"  Location: "+rs.getString("location") + "  username: "+rs.getString("username") +"\n";

            }

            return sucksList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            rs.close();
            stmt.close();


            if (connection != null) try{connection.close();} catch(SQLException e){}
        }
    }
}
