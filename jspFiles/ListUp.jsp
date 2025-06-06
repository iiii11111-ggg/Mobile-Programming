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
    JSONArray exerciseIds = (JSONArray) jsonRequest.get("exerciseIds");

    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthPlanner", "root", "1234");

    // 오늘 날짜의 기록이 있는지 체크
    String sql = "select recordId from exercise_record where userId = ? and recordDate = CURDATE()";
    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, pr_id);
    rs = pstmt.executeQuery();

    String recordId = null;
    if (rs.next()) {
        recordId = rs.getString("recordId");
    }
    rs.close();
    pstmt.close();

    // 기록이 없으면 새로 insert 후 다시 recordId 조회
    if (recordId == null) {
        sql = "insert into exercise_record (userId) values (?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, pr_id);
        int insertCount = pstmt.executeUpdate();
        pstmt.close();

        if (insertCount == 1) {
            sql = "select recordId from exercise_record where userId = ? and recordDate = CURDATE()";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pr_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                recordId = rs.getString("recordId");
            }
            rs.close();
            pstmt.close();
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "RECORD INSERT FAILED");
            out.println(jsonResponse.toJSONString());
            return;
        }
    }

    // exercise_list에 운동 아이디들 insert
    sql = "insert into exercise_list (exerciseId, recordId) values (?, ?)";
    pstmt = conn.prepareStatement(sql);

    for (int i = 0; i < exerciseIds.size(); i++) {
        String id = (String) exerciseIds.get(i);
        pstmt.setString(1, id);
        pstmt.setString(2, recordId);
        int result = pstmt.executeUpdate();

        if (result != 1) {
            jsonResponse.put("message", "LIST INSERT FAILED at exerciseId: " + id);
            success = false;
            break;
        } else {
            success = true;
        }
    }
    pstmt.close();

    jsonResponse.put("success", success);

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
