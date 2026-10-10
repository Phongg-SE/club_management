/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.finance;

import DAO.Finance.FinanceDAO;
import java.util.ArrayList;
import java.util.List;
import model.entity.Finance;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 *
 * @author Dell
 */
public class FinanceService {

    private FinanceDAO financeDAO;

    public FinanceService() {
        this.financeDAO = new FinanceDAO();
    }

    public List<Finance> getFinanceByClubId(int clubID) {
        return financeDAO.getFinanceByClubId(clubID);
    }

    public Finance getFinanceById(int financeId) {
        return financeDAO.getFinanceById(financeId);
    }

    public boolean updateFinance(Finance finance) {
        return financeDAO.updateFinance(finance);
    }

    public boolean addFinance(Finance finance) {
        if (financeDAO.isFinanceExist(finance.getFinance_name(), finance.getClubId())) {
            return false;
        }
        return financeDAO.addFinance(finance);
    }

    public List<Finance> listFinanceByFilter(int clubId, String isExpense, String status, String search, int page) throws SQLException {
        return financeDAO.listFinanceByFilter(clubId, isExpense, status, search, page);
    }

    public int getTotalPages(int clubId, String isExpense, String status, String search) throws SQLException {
        return financeDAO.getTotalPages(clubId, isExpense, status, search);
    }

}
