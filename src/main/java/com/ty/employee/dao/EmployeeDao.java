package com.ty.employee.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.ty.employee.helper.CheckDatabase;

public class EmployeeDao {

	public static Connection connectionObject() {
		String url = "jdbc:postgresql://localhost:5432/employee";
		String user = "postgres";
		String password = "root";

		try {
			Class.forName("org.postgresql.Driver");

			return DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Connection failed, wrong input path !!");
		}
		return null;
	}

	public static void save(Scanner sc) {
		Connection con = connectionObject();
		if (con != null) {
			String sql = "insert into employee values(?,?,?,?,?)";
			try {
				PreparedStatement pstm = con.prepareStatement(sql);
				System.out.println("Enter eid, firstname, lastname, designation, salary");
				pstm.setString(1, sc.next());
				pstm.setString(2, sc.next());
				pstm.setString(3, sc.next());
				pstm.setString(4, sc.next());
				pstm.setInt(5, sc.nextInt());

				if (pstm.executeUpdate() == 1) {
					System.out.println("Inserted Successfully!!");
					System.out.println("Do you want to insert again. Enter 'yes' or 'no'.");
					if (sc.next().equals("yes"))
						save(new Scanner(System.in));
				}

				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void update(Scanner sc) {
		Connection con = connectionObject();
		if (con != null) {
			PreparedStatement pstm = null;
			try {
				if (CheckDatabase.isTableFilled()) {
					System.out.println("Press 1 to update salary, Press 2 to update designation");
					int choice = sc.nextInt();
					if (choice == 1) {
						System.out.println("Enter updated salary & for which id");
						String sql = "Update employee set salary = ? where eid = ?";
						pstm = con.prepareStatement(sql);
						pstm.setInt(1, sc.nextInt());
						pstm.setString(2, sc.next());
					} else if (choice == 2) {
						System.out.println("Enter updated designation & for which id");
						String sql = "Update employee set designation = ? where eid = ?";
						pstm = con.prepareStatement(sql);
						pstm.setString(1, sc.next());
						pstm.setString(2, sc.next());
					}
					if (pstm.executeUpdate() == 1)
						System.out.println("Data updated Successfully!!");
				} else
					System.out.println("No record present in the table to update.");
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void delete(Scanner sc) {
		Connection con = connectionObject();
		if (con != null) {
			if (CheckDatabase.isTableFilled()) {
				System.out.println("Enter employee id to be deleted.");
				String sql = "delete from employee where eid = ?";
				try {
					PreparedStatement pstm = con.prepareStatement(sql);
					pstm.setString(1, sc.next());
					if (pstm.executeUpdate() == 1)
						System.out.println("Data deleted successfully!!!");
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else
				System.out.println("No record present in the table to delete.");
		}
	}

	public static void find(Scanner sc) {
		Connection con = connectionObject();
		if (con != null) {
			if (CheckDatabase.isTableFilled()) {
				System.out.println(
						"Press 1 to find employee based on firstname, " + " Press 2 to find employee based on salary");
				String sql = "Select * from employee";
				Statement stm;
				try {
					stm = con.createStatement();
					ResultSet rs = stm.executeQuery(sql);
					int choice = sc.nextInt();
					boolean found = false;
					if (choice == 1) {
						System.out.println("Enter employee first name");
						String firstName = sc.next();
						while (rs.next()) {
							if (rs.getString("firstname").equals(firstName)) {
								System.out.println(
										rs.getString("firstname") + " having employee id: " + rs.getString("eid"));
								found = true;
							}
						}
						if (!found) {
							System.out.println("Employee with " + firstName + " does not exist..");
						}
					} else if (choice == 2) {
						System.out.println("Enter employee salary range");
						int a = sc.nextInt();
						int b = sc.nextInt();
						while (rs.next()) {
							if (rs.getInt("salary") >= a && rs.getInt("salary") <= b) {
								System.out.println(
										rs.getString("firstname") + " having employee id: " + rs.getString("eid"));
								found = true;
							}
						}
						if (!found) {
							System.out.println("Employee within salary range does not exist..");
						}
					}
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else
				System.out.println("No record present in the table to find.");
		}
	}
}
