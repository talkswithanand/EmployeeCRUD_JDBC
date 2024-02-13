package com.ty.employee.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ty.employee.dao.EmployeeDao;

public class CheckDatabase {
	
	public static boolean isTableFilled() {
		Connection con = EmployeeDao.connectionObject();
		if(con != null) {
			String sql = "select * from employee";
			PreparedStatement pstm;
			try {
				pstm = con.prepareStatement(sql);
				return pstm.executeQuery().next();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		throw new IllegalArgumentException("Connection not established.");
	}
}
