<%@page import="java.sql.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>



<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.json.simple.*"%>

<%
    String pr_id = request.getParameter("id");
    String userID=null;
    Connection conn=null;
 
    PreparedStatement prepared_stat = null;
    ResultSet rs = null;

    String url="jdbc:mysql://localhost:3306/db_2021081073";
    String user="root";
    String password="ionsis0325!";

    try{

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url,user,password);

        String sql = "select userID from MEMBER where userID ='" + pr_id + "'";
        prepared_stat = conn.prepareStatement(sql);

        rs = prepared_stat.executeQuery();

        while(rs.next()){
            userID = rs.getString("userID");
        }

        JSONObject json=new JSONObject();
        json.put("userID",userID);

        if(userID!=null) json.put("newID",false);
        else json.put("newID",true);

        out.println(json.toString());
    } catch(SQLException ex){
        out.println("SQLExeption:"+ex.getMessage());
    }finally {
        if(rs!=null) rs.close();
        if(prepared_stat!=null) prepared_stat.close();
        if(conn!=null) conn.close();
    }

%>