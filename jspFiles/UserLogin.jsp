<%@page import="java.sql.*"%>
 <%@page import="java.sql.ResultSet"%>
 <%@page import="java.sql.PreparedStatement"%>
 <%@page import="java.sql.DriverManager"%>
 <%@page import="java.sql.Connection"%>
 <%@ page language = "java" contentType = "text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
 <%@page import="org.json.simple.*"%>
 <%
 String pr_id = request.getParameter("userID");
 String pr_passwd = request.getParameter("userPassword");

 String id,name,age,gender,height,weight;

 boolean success=false;
 Connection conn=null;
    PreparedStatement prepared_stat=null;
 ResultSet rs=null;
 String url="jdbc:mysql://127.0.0.1:3306/healthPlanner";
 String user="root";
 String password="1234";

 JSONObject jsonResponse=new JSONObject();

 try{
        Class.forName("com.mysql.jdbc.Driver");
  conn=DriverManager.getConnection(url,user,password);
  
   String sql = "select userId from user where userId = ? AND userPassword = ?";
   prepared_stat = conn.prepareStatement(sql);
   prepared_stat.setString(1,pr_id);
   prepared_stat.setString(2,pr_passwd);

   rs = prepared_stat.executeQuery();

  if(rs.next()){
   success = true;
  }
  else{
   jsonResponse.put("message", "ID AND PASSWORD IS WRONG");
   }

  jsonResponse.put("success", success);

  if(success)
  {
   sql = "select * from user where userId = ? AND userPassword = ?";
   prepared_stat = conn.prepareStatement(sql);
   prepared_stat.setString(1,pr_id);
   prepared_stat.setString(2,pr_passwd);

   rs = prepared_stat.executeQuery();

   if(rs.next()){
      id = pr_id;
      name = rs.getString("userName");
      age = rs.getString("userAge");
      gender = rs.getString("userGender");
      height = rs.getString("userHeight");
      weight = rs.getString("userWeight");

      jsonResponse.put("id",id);
      jsonResponse.put("name",name);
      jsonResponse.put("age",age);
      jsonResponse.put("gender",gender);
      jsonResponse.put("height",height);
      jsonResponse.put("weight",weight);
  }
  else{
   jsonResponse.put("message", "ID AND PASSWORD IS WRONG SECOND");
   }

  }
  out.println(jsonResponse.toString());

 } catch (SQLException ex) {
    out.println("SQLException:"+ex.getMessage());
    success = false;
        jsonResponse.put("success", success);
        jsonResponse.put("message", "데이터베이스 오류: " + ex.getMessage());
 } finally { 
  if (rs!=null) rs.close();
  if (prepared_stat!=null) prepared_stat.close();
  if (conn!=null) conn.close();
 }
 %>