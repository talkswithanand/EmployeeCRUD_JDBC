package com.ty.employee.controller;

import java.util.Scanner;

import com.ty.employee.dao.EmployeeDao;

public class EmployeeController {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Press 1 to insert values\nPress 2 to update record"
				+ "\nPress 3 to delete record\nPress 4 to find records\nPress 5 to exit");

		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			EmployeeDao.save(sc);
			break;
		case 2:
			EmployeeDao.update(sc);
			break;
		case 3:
			EmployeeDao.delete(sc);
			break;
		case 4:
			EmployeeDao.find(sc);
			break;
		case 5:
			System.out.println("Exiting!!!");
		}
		sc.close();
	}
}
