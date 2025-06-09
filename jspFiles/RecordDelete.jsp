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
JSONArray exerciseIdArray = new JSONArray();
JSONArray setCountArray = new JSONArray();

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
    String delete_id = (String) jsonRequest.get("exerciseId"); 

    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthPlanner", "root", "1234");


    // 삭제
    String sql= "delete from exercise_list where exerciseId = ? AND recordId in (select recordId from exercise_record where userId = ? AND recordDate = CURDATE())";
    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, delete_id);
    pstmt.setString(2,pr_id);
    int result = pstmt.executeUpdate();

    if(result == 1)
    {
        success = true;
        // 삭제 후 리스트가 전부 비었는지 검사
        String sql_select= "select listId from exercise_list where recordId in (select recordId from exercise_record where userId = ? AND recordDate = CURDATE())";
        pstmt = conn.prepareStatement(sql_select);
        pstmt.setString(1,pr_id);
        rs = pstmt.executeQuery();

        if(!rs.next())
        {
            String sql_record_delete = "DELETE FROM exercise_record WHERE recordId = (SELECT recordId FROM (SELECT recordId FROM exercise_record WHERE userId = ? AND recordDate = CURDATE()) AS temp)";
            pstmt = conn.prepareStatement(sql_record_delete);
            pstmt.setString(1,pr_id);
            int result1 = pstmt.executeUpdate();
            if(result1 == 1){
                success = true;
                jsonResponse.put("recordDelete",true);
            }
            else
            {
                jsonResponse.put("message","레코드가 지워지지 않습니다.");
            }
        }
        else
        {
            jsonResponse.put("message","리스트가 존재합니다.");
        }
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