<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="UTF-8" %>
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

    String pr_id = (String) jsonRequest.get("userID");
    String select_id = (String) jsonRequest.get("exerciseId"); 
    String setCount = (String) jsonRequest.get("setCount"); 

    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthPlanner", "root", "1234");

    // 삭제
    String sql= "update exercise_list Set setCount = ? where exerciseId = ? AND recordId = (select recordId from exercise_record where userId = ? AND recordDate = CURDATE());";
    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, setCount);
    pstmt.setString(2,select_id);
    pstmt.setString(3,pr_id);
    int result = pstmt.executeUpdate();

    if(result == 1)
    {
        success = true;
    }
    else
    {
        success = false;
        jsonResponse.put("message", "ERROR");
    }

    jsonResponse.put("success", success);


} catch (Exception e) {
    // 예상치 못한 다른 일반적인 오류 처리
    jsonResponse.put("success", false);
    jsonResponse.put("message", "ERROR: " + e.getMessage());
    System.err.println("General error in RecordList.jsp: " + e.getMessage());
    e.printStackTrace(); // 개발 중에는 항상 스택 트레이스를 출력하여 상세 오류 확인
} finally {
    // 모든 자원 닫기 (오류 발생 여부와 상관없이)
    if (rs != null) try { rs.close(); } catch (Exception ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (Exception ignored) {}
    if (conn != null) try { conn.close(); } catch (Exception ignored) {}
}

out.println(jsonResponse.toJSONString());
%>