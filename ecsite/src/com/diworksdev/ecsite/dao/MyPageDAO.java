package com.diworksdev.ecsite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.diworksdev.ecsite.dto.MyPageDTO;
import com.diworksdev.ecsite.util.DBConnector;

public class MyPageDAO {

    public ArrayList<MyPageDTO> getMyPageUserInfo(String item_transaction_id,
            String user_master_id) throws SQLException {

        ArrayList<MyPageDTO> myPageDTOList = new ArrayList<>();

        String sql =
                "SELECT ubit.id, iit.item_name, ubit.total_price, ubit.total_count, "
                + "ubit.pay, ubit.insert_date "
                + "FROM user_buy_item_transaction ubit "
                + "LEFT JOIN item_info_transaction iit "
                + "ON ubit.item_transaction_id = iit.id "
                + "WHERE ubit.item_transaction_id = ? "
                + "AND ubit.user_master_id = ? "
                + "ORDER BY ubit.insert_date DESC";

        try (Connection connection = new DBConnector().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, item_transaction_id);
            pstmt.setString(2, user_master_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                MyPageDTO dto = new MyPageDTO();
                dto.setId(rs.getString("id"));
                dto.setItemName(rs.getString("item_name"));
                dto.setTotalPrice(rs.getString("total_price"));
                dto.setTotalCount(rs.getString("total_count"));
                dto.setPayment(rs.getString("pay"));
                dto.setInsert_date(rs.getString("insert_date"));
                myPageDTOList.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return myPageDTOList;
    }


    public int buyItemHistoryDelete(String item_transaction_id,
            String user_master_id) throws SQLException {

        String sql =
                "DELETE FROM user_buy_item_transaction "
                + "WHERE item_transaction_id = ? AND user_master_id = ?";

        int result = 0;

        try (Connection connection = new DBConnector().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, item_transaction_id);
            pstmt.setString(2, user_master_id);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
