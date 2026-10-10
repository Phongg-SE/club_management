/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Finance;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import model.dto.DBContext;
import model.entity.Finance;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Dell
 */
public class FinanceDAO {

    public List<Finance> getFinanceByClubId(int clubID) {
        List<Finance> finances = new ArrayList<>();
        String sql = "SELECT f.finance_id, f.finance_name, f.club_id, f.event_id, e.name AS event_name, "
                + "f.member_id, u.full_name AS member_name, f.approver_id, a.full_name AS approver_name, "
                + "f.is_expense, f.amount, f.status, f.finance_date, f.last_updated, f.details "
                + "FROM finance f "
                + "LEFT JOIN event e ON f.event_id = e.event_id "
                + "JOIN user u ON f.member_id = u.user_id "
                + "LEFT JOIN user a ON f.approver_id = a.user_id "
                + "WHERE f.club_id = ?";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, clubID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int financeId = resultSet.getInt("finance_id");
                    String financeName = resultSet.getString("finance_name");
                    int eventId = resultSet.getInt("event_id");
                    String eventName = resultSet.getString("event_name");
                    int memberId = resultSet.getInt("member_id");
                    String memberName = resultSet.getString("member_name");
                    int approverId = resultSet.getInt("approver_id");
                    String approverName = resultSet.getString("approver_name");
                    String isExpense = resultSet.getString("is_expense");
                    double amount = resultSet.getDouble("amount");
                    String status = resultSet.getString("status");
                    String details = resultSet.getString("details");

                    // Chuyển đổi finance_date và last_updated thành String (yyyy-MM-dd)
                    Date financeDateRaw = resultSet.getDate("finance_date");
                    Timestamp lastUpdatedRaw = resultSet.getTimestamp("last_updated");

                    String financeDate = (financeDateRaw != null) ? dateFormat.format(financeDateRaw) : null;
                    String lastUpdated = (lastUpdatedRaw != null) ? dateTimeFormat.format(lastUpdatedRaw) : null;

                    Finance finance = new Finance(financeId, financeName, clubID, eventId, eventName, memberId, memberName,
                            approverId, approverName, isExpense, amount, status, financeDate, lastUpdated, details);
                    finances.add(finance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return finances;
    }

//    //test get list by clubid
//    public static void main(String[] args) {
//        FinanceDAO financeDAO = new FinanceDAO(); // Giả định bạn có class DAO này
//
//        int testClubId = 1; // Thay đổi ID câu lạc bộ để test
//        List<Finance> finances = financeDAO.getFinanceByClubId(testClubId);
//        if (finances.isEmpty()) {
//            System.out.println("Không có dữ liệu tài chính nào cho club_id = " + testClubId);
//        } else {
//            for (Finance finance : finances) {
//                System.out.println("Finance ID: " + finance.getFinanceId());
//                System.out.println("Finance ID: " + finance.getFinance_name());
//                System.out.println("Event ID: " + finance.getEventId());
//                System.out.println("Event Name: " + finance.getEventName());
//                System.out.println("Member ID: " + finance.getMemberId());
//                System.out.println("Member Name: " + finance.getMemberName());
//                System.out.println("Approver ID: " + finance.getApproverId());
//                System.out.println("Approver Name: " + finance.getApproverName());
//                System.out.println("Is Expense: " + finance.getIsExpense());
//                System.out.println("Amount: " + finance.getAmount());
//                System.out.println("Finance Date: " + finance.getFinanceDate());
//                System.out.println("Details: " + finance.getDetails());
//                System.out.println("-----------------------------------------");
//            }
//        }
//    }
    public Finance getFinanceById(int financeId) {
        Finance finance = null;
        String sql = "SELECT f.finance_id, f. finance_name, f.club_id, f.event_id, e.name AS event_name, "
                + "f.member_id, u.full_name AS member_name, f.approver_id, a.full_name AS approver_name, "
                + "f.is_expense, f.amount, f.status, f.finance_date, f.last_updated, f.details "
                + "FROM finance f "
                + "LEFT JOIN event e ON f.event_id = e.event_id "
                + "JOIN user u ON f.member_id = u.user_id "
                + "LEFT JOIN user a ON f.approver_id = a.user_id "
                + "WHERE f.finance_id = ?";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, financeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String financeName = resultSet.getString("finance_name");
                    int clubId = resultSet.getInt("club_id");
                    int eventId = resultSet.getInt("event_id");
                    String eventName = resultSet.getString("event_name");
                    int memberId = resultSet.getInt("member_id");
                    String memberName = resultSet.getString("member_name");
                    int approverId = resultSet.getInt("approver_id");
                    String approverName = resultSet.getString("approver_name");
                    String isExpense = resultSet.getString("is_expense");
                    double amount = resultSet.getDouble("amount");
                    String status = resultSet.getString("status");
                    String details = resultSet.getString("details");

                    // Định dạng ngày
                    Date financeDateRaw = resultSet.getDate("finance_date");
                    Timestamp lastUpdatedRaw = resultSet.getTimestamp("last_updated");

                    String financeDate = (financeDateRaw != null) ? dateFormat.format(financeDateRaw) : null;
                    String lastUpdated = (lastUpdatedRaw != null) ? dateTimeFormat.format(lastUpdatedRaw) : null;

                    finance = new Finance(financeId, financeName, clubId, eventId, eventName, memberId, memberName, approverId, approverName, isExpense, amount, status, financeDate, lastUpdated, details);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finance;
    }

//        //test get by id
//        public static void main(String[] args) {
//            FinanceDAO financeDAO = new FinanceDAO(); // Giả sử bạn có một lớp DAO để xử lý database
//            int testFinanceId = 1; // ID của bản ghi cần test
//            Finance finance = financeDAO.getFinanceById(testFinanceId);
//            if (finance != null) {
//                System.out.println("Finance ID: " + finance.getFinanceId());
//                System.out.println("Finance Name: " + finance.getFinance_name());
//                System.out.println("Club ID: " + finance.getClubId());
//                System.out.println("Event ID: " + finance.getEventId());
//                System.out.println("Event Name: " + finance.getEventName());
//                System.out.println("Member ID: " + finance.getMemberId());
//                System.out.println("Member Name: " + finance.getMemberName());
//                System.out.println("Approver ID: " + finance.getApproverId());
//                System.out.println("Approver Name: " + finance.getApproverName());
//                System.out.println("Is Expense: " + finance.getIsExpense());
//                System.out.println("Amount: " + finance.getAmount());
//                System.out.println("Finance Date: " + finance.getFinanceDate());
//                System.out.println("Details: " + finance.getDetails());
//            } else {
//                System.out.println("Không tìm thấy bản ghi với ID: " + testFinanceId);
//            }
//        }
    public boolean updateFinance(Finance finance) {
        String sql = "UPDATE finance SET finance_name = ?, event_id = ?, member_id = ?, approver_id = ?, is_expense = ?, "
                + "amount = ?, details = ?, status = ?, last_updated = CURRENT_TIMESTAMP "
                + "WHERE finance_id = ?";

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Gán giá trị cho các tham số trong câu lệnh SQL
            pstmt.setString(1, finance.getFinance_name());

            // Kiểm tra eventId
            if (finance.getEventId() == 0) {
                pstmt.setNull(2, Types.INTEGER);
            } else {
                pstmt.setInt(2, finance.getEventId());
            }

            pstmt.setInt(3, finance.getMemberId());

            // Kiểm tra approverId
            if (finance.getApproverId() == 0) {
                pstmt.setNull(4, Types.INTEGER);
            } else {
                pstmt.setInt(4, finance.getApproverId());
            }

            pstmt.setString(5, finance.getIsExpense());
            pstmt.setDouble(6, finance.getAmount());

            // ktra details
            if (finance.getDetails() == null || finance.getDetails().isEmpty()) {
                pstmt.setNull(7, Types.VARCHAR);
            } else {
                pstmt.setString(7, finance.getDetails());
            }

            pstmt.setString(8, finance.getStatus());
            pstmt.setInt(9, finance.getFinanceId());

            // Thực thi truy vấn
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi cập nhật tài chính: " + e.getMessage());
            System.out.println("Mã lỗi SQL: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
        }
        return false;
    }

//    //Test update
//    public static void main(String[] args) {
//        FinanceDAO financeDAO = new FinanceDAO();
//
//        // Tạo một đối tượng Finance hợp lệ
//        Finance finance = new Finance(
//                1, // financeId
//                "Test Name",
//                1, // clubId
//                1, // eventId
//                5, // memberId
//                0, // approverId
//                "No", // isExpense (chỉ nhận "Yes" hoặc "No")
//                50000.0, // amount (phải >= 0)
//                "Pending", // status 
//                null, // financeDate (định dạng yyyy-MM-dd)
//                null, // lastUpdated (định dạng yyyy-MM-dd)
//                null // details
//        );
//
//        // Gọi phương thức cập nhật tài chính
//        boolean result = financeDAO.updateFinance(finance);
//
//        // Kiểm tra kết quả
//        if (result) {
//            System.out.println("✅ Cập nhật tài chính thành công!");
//        } else {
//            System.out.println("❌ Cập nhật thất bại!");
//        }
//    }
    public boolean addFinance(Finance finance) {
        String sql = "INSERT INTO finance (finance_name, club_id, event_id, member_id, approver_id, is_expense, amount, finance_date, details, status, last_updated) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DBContext.getJDBCConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Gán giá trị vào câu lệnh SQL
            stmt.setString(1, finance.getFinance_name());
            stmt.setInt(2, finance.getClubId());

            // Xử lý event_id (nếu null thì set NULL)
            if (finance.getEventId() == 0) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, finance.getEventId());
            }

            stmt.setInt(4, finance.getMemberId());

            // Xử lý approver_id (nếu null thì set NULL)
            if (finance.getApproverId() == 0) {
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(5, finance.getApproverId());
            }

            stmt.setString(6, finance.getIsExpense());
            stmt.setDouble(7, finance.getAmount());

            // Chuyển đổi String thành SQL Date
            stmt.setDate(8, java.sql.Date.valueOf(finance.getFinanceDate()));

            // Xử lý details (có thể null)
            if (finance.getDetails() == null || finance.getDetails().isEmpty()) {
                stmt.setNull(9, Types.VARCHAR);
            } else {
                stmt.setString(9, finance.getDetails());
            }

            stmt.setString(10, finance.getStatus());

            // Thực thi câu lệnh SQL và kiểm tra kết quả
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Trả về true nếu thêm thành công

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }
    
//    //test add
//    public static void main(String[] args) {
//        FinanceDAO financeDAO = new FinanceDAO();
//
//        // Định dạng ngày tháng
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String financeDate = sdf.format(new Date());  // Ngày hiện tại
//
//        // Dữ liệu hợp lệ để thêm mới
//        Finance finance = new Finance(0, "Test add", 1, 0, 2, 0, "No", 200000.0, "Pending", financeDate, "", "Thêm giao dịch test");
//
//        // Thử thêm vào database
//        boolean result = financeDAO.addFinance(finance);
//
//        if (result) {
//            System.out.println("✅ Thêm tài chính thành công!");
//        } else {
//            System.out.println("❌ Thêm tài chính thất bại!");
//        }
//    }
    
    //filter + search + paging
    public List<Finance> listFinanceByFilter(int clubId, String isExpense, String status, String search, int page) throws SQLException {
        List<Finance> financeList = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT f.finance_id, f.finance_name, f.club_id, f.event_id, e.name AS event_name, "
                + "f.member_id, u.full_name AS member_name, f.approver_id, a.full_name AS approver_name, "
                + "f.is_expense, f.amount, f.status, f.finance_date, f.last_updated, f.details "
                + "FROM finance f "
                + "LEFT JOIN event e ON f.event_id = e.event_id "
                + "JOIN user u ON f.member_id = u.user_id "
                + "LEFT JOIN user a ON f.approver_id = a.user_id "
                + "WHERE f.club_id = ? "
        );

        List<Object> params = new ArrayList<>();
        params.add(clubId);

        if (isExpense != null && !isExpense.isEmpty()) {
            sql.append(" AND f.is_expense = ?");
            params.add(isExpense);
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND f.status = ?");
            params.add(status);
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND f.finance_name LIKE ?");
            params.add("%" + search + "%");
        }
        sql.append(" LIMIT 5 OFFSET ?");

        params.add((page - 1) * 5);

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) instanceof Integer) {
                    statement.setInt(i + 1, (Integer) params.get(i));
                } else {
                    statement.setString(i + 1, (String) params.get(i));
                }
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

                while (resultSet.next()) {
                    financeList.add(new Finance(
                            resultSet.getInt("finance_id"),
                            resultSet.getString("finance_name"),
                            resultSet.getInt("club_id"),
                            resultSet.getInt("event_id"),
                            resultSet.getString("event_name"),
                            resultSet.getInt("member_id"),
                            resultSet.getString("member_name"),
                            resultSet.getInt("approver_id"),
                            resultSet.getString("approver_name"),
                            resultSet.getString("is_expense"),
                            resultSet.getInt("amount"),
                            resultSet.getString("status"),
                            resultSet.getDate("finance_date") != null ? dateFormat.format(resultSet.getDate("finance_date")) : null,
                            resultSet.getTimestamp("last_updated") != null ? dateTimeFormat.format(resultSet.getTimestamp("last_updated")) : null,
                            resultSet.getString("details")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching finance records: " + e.getMessage());
            throw e;
        }
        return financeList;
    }

    public int getTotalPages(int clubId, String isExpense, String status, String search) throws SQLException {
        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM finance f "
                + "LEFT JOIN event e ON f.event_id = e.event_id "
                + "WHERE f.club_id = ? ");

        List<Object> params = new ArrayList<>();
        params.add(clubId);

        if (isExpense != null && !isExpense.isEmpty()) {
            countSql.append(" AND f.is_expense = ?");
            params.add(isExpense);
        }
        if (status != null && !status.isEmpty()) {
            countSql.append(" AND f.status = ?");
            params.add(status);
        }
        if (search != null && !search.isEmpty()) {
            countSql.append(" AND f.finance_name LIKE ?");
            params.add("%" + search + "%");
        }

        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(countSql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) instanceof Integer) {
                    statement.setInt(i + 1, (Integer) params.get(i));
                } else {
                    statement.setString(i + 1, (String) params.get(i));
                }
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int totalRecords = resultSet.getInt(1);
                    return (int) Math.ceil((double) totalRecords / 5);
                }
            }
        }
        return 1;
    }

    // TEST filter
    public static void main(String[] args) {
        FinanceDAO dao = new FinanceDAO();
        try {
            int clubId = 1;
            String isExpense = ""; // Hoặc null để kiểm tra tất cả
            String status = "Pending"; // Hoặc null
            String search = ""; // Hoặc null
            int page = 1;

            List<Finance> finances = dao.listFinanceByFilter(clubId, isExpense, status, search, page);
            System.out.println("Finance records found: " + finances.size());
            for (Finance fin : finances) {
                System.out.println(fin.getFinanceId() + " - " + fin.getFinance_name()+ " - " + fin.getStatus());
            }

            int totalPages = dao.getTotalPages(clubId, isExpense, status, search);
            System.out.println("Total pages: " + totalPages);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isFinanceExist(String financeName, int clubId) {
        String sql = "SELECT COUNT(*) FROM finance WHERE finance_name = ? AND club_id = ?";
        try (Connection connection = DBContext.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, financeName);
            statement.setInt(2, clubId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Trả về true nếu đã tồn tại
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
