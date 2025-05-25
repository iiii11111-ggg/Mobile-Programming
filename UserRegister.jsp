<%@page import="java.sql.*"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // JSON 응답을 위한 객체
    JSONObject jsonResponse = new JSONObject();
    boolean success = false; // 기본적으로 실패

    Connection conn = null;
    PreparedStatement prepared_stat = null;

    String url="jdbc:mysql://127.0.0.1:3306/healthPlanner";
    String user="root";
    String password="1234";
    try {
        Class.forName("com.mysql.jdbc.Driver"); 
        conn = DriverManager.getConnection(url, user, password);

        String userID = request.getParameter("userID");
        String userPasswd = request.getParameter("userPassword");
        String userGender = request.getParameter("userGender");
        String userName = request.getParameter("userName");
        String userAgeStr = request.getParameter("userAge");
        String userHeightStr = request.getParameter("userHeight");
        String userWeightStr = request.getParameter("userWeight");

        int userAge = 0;
        int userHeight = 0;
        int userWeight = 0;

        if (userID == null || userID.trim().isEmpty() ||
            userPasswd == null || userPasswd.trim().isEmpty() ||
            userGender == null || userGender.trim().isEmpty() ||
            userName == null || userName.trim().isEmpty() ||
            userAgeStr == null || userAgeStr.trim().isEmpty() ||
            userHeightStr == null || userHeightStr.trim().isEmpty() ||
            userWeightStr == null || userWeightStr.trim().isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "모든 정보를 입력해주세요.");
        } else {
            try {
                userAge = Integer.parseInt(userAgeStr);
                userHeight = Integer.parseInt(userHeightStr);
                userWeight = Integer.parseInt(userWeightStr);
                

                String sql = "INSERT INTO user (userID, userPassword, userName, userGender, userAge, userHeight, userWeight) VALUES (?, ?, ?, ?, ?, ?, ?)";
                prepared_stat = conn.prepareStatement(sql);
                prepared_stat.setString(1, userID);
                prepared_stat.setString(2, userPasswd);
                prepared_stat.setString(3, userName);
                prepared_stat.setString(4, userGender);
                prepared_stat.setInt(5, userAge);
                prepared_stat.setInt(6, userHeight);
                prepared_stat.setInt(7, userWeight);

                // 6. 쿼리 실행 및 결과 확인
                int rs = prepared_stat.executeUpdate(); // 쿼리 실행 후 영향을 받은 행의 수 반환

                if (rs > 0){
                    success = true;
                    jsonResponse.put("message", "회원 등록에 성공했습니다.");
                } else {
                    jsonResponse.put("message", "회원 등록에 실패했습니다.");
                }

            } catch (NumberFormatException e) {
                // 숫자로 파싱할 수 없는 경우 (예: 나이에 문자가 입력됨)
                jsonResponse.put("success", false);
                jsonResponse.put("message", "나이, 키, 몸무게는 숫자여야 합니다.");
            } catch (SQLIntegrityConstraintViolationException e) {
                 // MySQL에서 Primary Key (userID) 중복 등의 제약 조건 위반 시
                 jsonResponse.put("success", false);
                 jsonResponse.put("message", "이미 존재하는 아이디입니다.");
                 System.err.println("SQL Integrity Constraint Violation: " + e.getMessage());
            }
        }

    } catch (ClassNotFoundException e) {
        // JDBC 드라이버를 찾지 못했을 때
        jsonResponse.put("success", false);
        jsonResponse.put("message", "JDBC 드라이버를 찾을 수 없습니다.");
        System.err.println("ClassNotFoundException: " + e.getMessage());
    } catch (SQLException e) {
        // SQL 관련 다른 예외 발생 시
        jsonResponse.put("success", false);
        String errorMessage = "데이터베이스 오류가 발생했습니다.";
        int errorCode = e.getErrorCode(); // MySQL 에러 코드
        String originalMessage = e.getMessage(); // 원래 JDBC 예외 메시지

        // MySQL 오류 코드와 메시지 내용을 통해 상세 메시지 생성
        switch (errorCode) {
            case 3819: // Check constraint violation (CHECK 제약조건 위반)
                // user_chk_1, user_chk_2 등 MySQL이 자동으로 생성하는 제약조건 이름으로 판단
                if (originalMessage.contains("user_chk_1")) { errorMessage = "아이디는 5~20자로 입력해야 합니다."; }
                else if (originalMessage.contains("user_chk_2")) { errorMessage = "비밀번호는 5~20자로 입력해야 합니다."; }
                else if (originalMessage.contains("user_chk_3")) { errorMessage = "이름은 2~5자로 입력해야 합니다."; }
                else if (originalMessage.contains("user_chk_4")) { errorMessage = "나이는 0~150 사이의 숫자로 입력해야 합니다."; }
                else if (originalMessage.contains("user_chk_5")) { errorMessage = "성별은 'male' 또는 'female'로 입력해야 합니다."; }
                else if (originalMessage.contains("user_chk_6")) { errorMessage = "키는 50~300 사이의 숫자로 입력해야 합니다."; }
                else if (originalMessage.contains("user_chk_7")) { errorMessage = "몸무게는 10~500 사이의 숫자로 입력해야 합니다."; }
                else { errorMessage = "입력한 정보가 유효하지 않습니다. 다시 확인해주세요. (상세: " + originalMessage + ")"; }
                break;
            case 1406: // Data too long for column (컬럼의 VARCHAR/CHAR 길이 초과)
                if (originalMessage.contains("Data too long for column")) {
                    int startIndex = originalMessage.indexOf("column '") + "column '".length();
                    int endIndex = originalMessage.indexOf("'", startIndex);
                    if (startIndex != -1 && endIndex != -1) {
                        String columnName = originalMessage.substring(startIndex, endIndex);
                        
                        // 사용자 친화적인 컬럼명으로 매핑 (선택 사항)
                        switch (columnName) {
                            case "userId":
                                errorMessage = "아이디가 너무 깁니다. 최대 20자까지 가능합니다.";
                                break;
                            case "userPassword":
                                errorMessage = "비밀번호가 너무 깁니다. 최대 20자까지 가능합니다.";
                                break;
                            case "userName":
                                errorMessage = "이름이 너무 깁니다. 최대 5자까지 가능합니다.";
                                break;
                            case "userGender":
                                errorMessage = "성별 입력값이 너무 깁니다. ('male' 또는 'female'만 가능)";
                                break;
                            default:
                                errorMessage = "'" + columnName + "' 항목의 입력값이 너무 깁니다. 길이를 줄여주세요.";
                                break;
                        }
                    } else {
                        errorMessage = "일부 입력값이 너무 깁니다. 길이를 줄여주세요."; // 컬럼명 추출 실패 시 일반 메시지
                    }
                } else {
                    errorMessage = "입력 값 길이 오류: " + originalMessage; // 예상치 못한 1406 에러 메시지
                }
                break;

            case 1292: // Incorrect datetime value, Truncated incorrect DOUBLE value 등 (데이터 타입 불일치)
                errorMessage = "입력 데이터의 형식이 올바르지 않습니다. (예: 나이, 키, 몸무게에 숫자가 아닌 값)";
                break;

            default: // 기타 알 수 없는 SQL 예외
                errorMessage = "알 수 없는 데이터베이스 오류: " + originalMessage;
                break;
        }
        jsonResponse.put("message", errorMessage);
        System.err.println("SQLException: " + e.getMessage()); 
    } finally {
        // 7. 자원 해제 
        try {
            if (prepared_stat != null) prepared_stat.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("자원 해제 중 오류 발생: " + e.getMessage());
        }
    }

    // 8. 최종 성공 여부 설정 및 클라이언트에 JSON 응답 전송
    jsonResponse.put("success", success); // 최종 성공 여부를 jsonResponse에 반영
    out.print(jsonResponse.toJSONString());
    out.flush(); // 버퍼 비우기
%>