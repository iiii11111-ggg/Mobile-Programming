<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.json.simple.parser.*" %>
<%
Connection conn = null;
PreparedStatement pstmt = null;
PreparedStatement pstmt2 = null; // 두 번째 쿼리용 PreparedStatement 추가
ResultSet rs = null;
boolean found = false; // 초기값을 false로 설정
JSONObject jsonResponse = new JSONObject();
JSONArray nameList = new JSONArray();

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

    String id = (String) jsonRequest.get("userID");
    String date = (String) jsonRequest.get("date");

    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthPlanner", "root", "1234");

    // 오늘 날짜의 기록이 있는지 체크
    String sql = "select recordId from exercise_record where userId = ? and recordDate = ?";
    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, id);
    pstmt.setString(2, date);
    rs = pstmt.executeQuery();

    if(rs.next()) {
        int recordId = rs.getInt("recordId");
        String sql2 = "SELECT et.exerciseName, et.koreanName, el.setCount FROM exercise_type et INNER JOIN exercise_list el ON et.exerciseId = el.exerciseId WHERE el.recordId = ?";
        pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setInt(1, recordId);
        ResultSet rs2 = pstmt2.executeQuery(); 

        while(rs2.next()) {
            String name = rs2.getString("exerciseName");
            String koreanName = rs2.getString("koreanName");
            String setCount = rs2.getString("setCount");

            JSONObject record = new JSONObject();
            record.put("exerciseName", name);
            record.put("koreanName", koreanName);
            record.put("setCount", setCount);
            nameList.add(record); 
            found = true;
        }
        if (rs2 != null) try { rs2.close(); } catch (Exception ignored) {} //
    } else {
        found = false;
        jsonResponse.put("message", "No record found for the given user and date.");
    }

    jsonResponse.put("nameList", nameList);
    jsonResponse.put("found", found);

} catch (Exception e) {

    jsonResponse.put("found", false);
    jsonResponse.put("message", "ERROR: " + e.getMessage());
    e.printStackTrace(); 
} finally {

    if (rs != null) try { rs.close(); } catch (Exception ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (Exception ignored) {}
    if (pstmt2 != null) try { pstmt2.close(); } catch (Exception ignored) {} 
    if (conn != null) try { conn.close(); } catch (Exception ignored) {}


    out.print(jsonResponse.toJSONString());
}
%>