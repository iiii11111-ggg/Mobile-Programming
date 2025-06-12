 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 <%@page import="java.sql.*"%>
 <%@page import="java.sql.ResultSet"%>
 <%@page import="java.sql.PreparedStatement"%>
 <%@page import="java.sql.DriverManager"%>
 <%@page import="java.sql.Connection"%>
 <%@page import="org.json.simple.*"%>
 <%
 String pr_id = request.getParameter("userID");
 String userID=null;
 Connection conn=null;
    PreparedStatement prepared_stat=null;
 ResultSet rs=null;
 try{
       Class.forName("com.mysql.cj.jdbc.Driver"); 
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthplanner?useUnicode=true&characterEncoding=utf8", "healthuser", "Mjc0203!");
  String query="select userId from user where userId='"+pr_id+ "'";
  prepared_stat=conn.prepareStatement(query);
  rs=prepared_stat.executeQuery();
  while(rs.next()){
   userID=rs.getString("userID");
  }
  JSONObject json = new JSONObject();
  json.put("userID",userID);

  if (userID!=null) json.put("newID",false);
  else json.put("newID",true);

  out.println(json.toString());

 } catch (SQLException ex) { out.println("SQLException:"+ex.getMessage());
 } finally { 
  if (rs!=null) rs.close();
  if (prepared_stat!=null) prepared_stat.close();
  if (conn!=null) conn.close();
 }
 %>