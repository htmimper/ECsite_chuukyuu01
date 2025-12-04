package com.diworksdev.ecsite.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.diworksdev.ecsite.dao.MyPageDAO;
import com.diworksdev.ecsite.dto.MyPageDTO;
import com.opensymphony.xwork2.ActionSupport;

public class MyPageAction extends ActionSupport implements SessionAware {

    private Map<String, Object> session;
    private MyPageDAO myPageDAO = new MyPageDAO();
    private ArrayList<MyPageDTO> myPageList = new ArrayList<MyPageDTO>();
    private String deleteFlg;
    private String message;

    public String execute() throws SQLException {

        // ログインチェック
        if (!session.containsKey("login_user_id")) {
            return ERROR;
        }

        String item_transaction_id = String.valueOf(session.get("id"));
        String user_master_id = String.valueOf(session.get("login_user_id"));

        // 削除処理
        if ("1".equals(deleteFlg)) {
            int res = myPageDAO.buyItemHistoryDelete(item_transaction_id, user_master_id);
            if (res > 0) {
                setMessage("商品情報を正しく削除しました。");
            } else {
                setMessage("商品情報の削除に失敗しました。");
            }
        }

        // 削除後も必ず最新の一覧を取得
        myPageList = myPageDAO.getMyPageUserInfo(item_transaction_id, user_master_id);

        return SUCCESS;
    }

    // getter/setter
    public ArrayList<MyPageDTO> getMyPageList() {
        return this.myPageList;
    }

    public void setDeleteFlg(String deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
