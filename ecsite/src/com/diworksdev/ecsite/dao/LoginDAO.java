package com.diworksdev.ecsite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.diworksdev.ecsite.dto.LoginDTO;
import com.diworksdev.ecsite.util.DBConnector;

public class LoginDAO {

    private DBConnector dbConnector = new DBConnector();
    private Connection connection = dbConnector.getConnection();

    public LoginDTO getLoginUserInfo(String loginUserId, String loginPassword) {

        LoginDTO loginDTO = new LoginDTO();

        String sql = "SELECT * FROM login_user_transaction WHERE login_id = ? AND login_pass = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, loginUserId);
            preparedStatement.setString(2, loginPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                loginDTO.setLoginId(resultSet.getString("login_id"));
                loginDTO.setLoginPassword(resultSet.getString("login_pass"));
                loginDTO.setUserName(resultSet.getString("user_name"));
                loginDTO.setLoginFlg(true);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close(); // 必要に応じて

        } catch (Exception e) {
            e.printStackTrace();
        }

        return loginDTO;
    }
}
