package com.prokarma.Week8;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.log4j.Logger;

;

public class Maintask extends Person {
	private static Logger logger = Logger.getLogger(Maintask.class);
	public Maintask(String name, int age) {
		super(name, age);
	}

	int i;
	String title;
	String des;
	Scanner s = new Scanner(System.in);

	public int task(int id) throws IOException {
		Connection con;
		int mid = -1;
		try {

			con = DriverManager.getConnection("jdbc:oracle:thin:@172.16.203.151:1521:traindb", "spothanachetty",
					"prokarma");
			logger.info("Enter title of MainTask:");
			title = s.next();

			logger.info("Enter Description:");
			des = s.next();

			final String main = "{call  insert_maintask(?,?,?,?)}";
			CallableStatement stmt1 = con.prepareCall(main);
			stmt1.registerOutParameter(1, java.sql.Types.NUMERIC);
			stmt1.setString(2, title);
			stmt1.setString(3, des);
			stmt1.setInt(4, id);
			logger.info("MainTask details are entered");
			stmt1.execute();
			mid = stmt1.getInt(1);
			con.close();

		} catch (SQLException e) {

			logger.error(e);
		}
		return mid;

	}

}
