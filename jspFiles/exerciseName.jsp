<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.json.simple.parser.*" %>

<%
Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;
boolean success = false;
JSONObject jsonResponse = new JSONObject();
JSONArray exerciseNameArray = new JSONArray();
JSONArray exerciseTypeArray = new JSONArray();
JSONArray koreanNameArray = new JSONArray();
JSONArray exerciseId = new JSONArray();

try {
    // JSON 요청 파싱
    BufferedReader reader = request.getReader();
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
        sb.append(line);
    }
    JSONParser parser = new JSONParser();
    JSONObject jsonRequest = (JSONObject) parser.parse(sb.toString());

     Class.forName("com.mysql.cj.jdbc.Driver"); 
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthplanner?useUnicode=true&characterEncoding=utf8", "healthuser", "Mjc0203!");

    String sql = "select * from exercise_type";
    pstmt = conn.prepareStatement(sql);
    rs = pstmt.executeQuery();

    if(rs.next()){
        success = true;
        do{
            String id = rs.getString("exerciseId");
            String name = rs.getString("exerciseName");
            String part = rs.getString("exercisePart");
            String koreanName = rs.getString("koreanName");

            exerciseId.add(id);
            exerciseNameArray.add(name);
            exerciseTypeArray.add(part);
            koreanNameArray.add(koreanName);

        }while(rs.next());
        jsonResponse.put("exerciseId", exerciseId);
        jsonResponse.put("exerciseNameArray", exerciseNameArray);
        jsonResponse.put("exerciseTypeArray", exerciseTypeArray);
        jsonResponse.put("koreanNameArray", koreanNameArray);

    }else{
        success = false;
        jsonResponse.put("message", "select is wrong");
    }
    jsonResponse.put("success",success);
    

} catch (Exception e) {
    jsonResponse.put("success", false);
    jsonResponse.put("message", "ERROR: " + e.getMessage());
    e.printStackTrace();  // 로그에 스택 트레이스 출력 권장
} finally {
    if (rs != null) try { rs.close(); } catch (Exception ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (Exception ignored) {}
    if (conn != null) try { conn.close(); } catch (Exception ignored) {}
}

out.println(jsonResponse.toJSONString());
%>
