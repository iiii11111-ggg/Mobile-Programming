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
boolean successInsert = false;

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
    String selected_id = (String) jsonRequest.get("selectedExerciseId");

   Class.forName("com.mysql.cj.jdbc.Driver"); 
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthplanner?useUnicode=true&characterEncoding=utf8", "healthuser", "Mjc0203!");

    // ===== 1. 오늘 날짜의 recordId가 존재하는지 확인 =====
    String recordId = null;
    String sql_recordId = "SELECT recordId FROM exercise_record WHERE userId = ? AND recordDate = CURDATE()";
    pstmt = conn.prepareStatement(sql_recordId);
    pstmt.setString(1, pr_id);
    rs = pstmt.executeQuery();

    if (rs.next()) {
        recordId = rs.getString("recordId");
    } else {
        // ===== 2. recordId 없으면 새로 INSERT =====
        rs.close();
        pstmt.close();

        String sql_insert_record = "INSERT INTO exercise_record (userId, recordDate) VALUES (?, CURDATE())";
        pstmt = conn.prepareStatement(sql_insert_record);
        pstmt.setString(1, pr_id);
        pstmt.executeUpdate();
        pstmt.close();

        // 다시 recordId 조회
        pstmt = conn.prepareStatement(sql_recordId);
        pstmt.setString(1, pr_id);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            recordId = rs.getString("recordId");
        } else {
            jsonResponse.put("Insert", false);
            jsonResponse.put("insertMessage", "기록 생성 후 recordId 조회 실패");
            out.println(jsonResponse.toJSONString());
            return;
        }
    }

    // ===== 3. exercise_list 에 운동 추가 =====
    try {
        String sql_insert = "INSERT INTO exercise_list (exerciseId, recordId) VALUES (?, ?)";
        pstmt.close();
        pstmt = conn.prepareStatement(sql_insert);
        pstmt.setString(1, selected_id);
        pstmt.setString(2, recordId);

        int result = pstmt.executeUpdate();
        if (result == 1) {
            successInsert = true;
            jsonResponse.put("Insert", true);
            jsonResponse.put("insertMessage", "운동이 리스트에 추가되었습니다.");
        } else {
            jsonResponse.put("Insert", false);
            jsonResponse.put("insertMessage", "운동 추가에 실패했습니다.");
        }
    } catch (SQLIntegrityConstraintViolationException e) {
        jsonResponse.put("Insert", false);
        jsonResponse.put("insertMessage", "해당 운동은 이미 오늘 기록에 추가되어 있습니다.");
    }

    // ===== 4. 오늘날짜 운동 전체 목록 반환 =====
    pstmt.close();
    String sql = "SELECT el.exerciseId, el.setCount FROM exercise_record er " +
                 "JOIN exercise_list el ON er.recordId = el.recordId " +
                 "WHERE er.userId = ? AND er.recordDate = CURDATE()";
    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, pr_id);
    rs = pstmt.executeQuery();

    if (rs.next()) {
        success = true;
        do {
            exerciseIdArray.add(rs.getString("exerciseId"));
            setCountArray.add(rs.getString("setCount"));
        } while (rs.next());

        jsonResponse.put("exerciseIdArray", exerciseIdArray);
        jsonResponse.put("setCountArray", setCountArray);
        jsonResponse.put("retrieveMessage", "오늘 운동 리스트를 성공적으로 조회했습니다.");
    } else {
        jsonResponse.put("retrieveMessage", "오늘 운동 리스트가 없습니다.");
    }

    jsonResponse.put("success", success);

} catch (Exception e) {
    jsonResponse.put("success", false);
    jsonResponse.put("message", "ERROR: " + e.getMessage());
    e.printStackTrace();
} finally {
    if (rs != null) try { rs.close(); } catch (Exception ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (Exception ignored) {}
    if (conn != null) try { conn.close(); } catch (Exception ignored) {}
}

out.println(jsonResponse.toJSONString());
%>
