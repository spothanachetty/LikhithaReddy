package com.prokarma.Week8;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Subtask extends Maintask {
	private static Logger logger = Logger.getLogger(Subtask.class);

	public Subtask(String name, int age) {
		super(name, age);

	}

	int hrs;
	String sname;
	Scanner s = new Scanner(System.in);

	public int task(int id) throws IOException {
		int sid = -1;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@172.16.203.151:1521:traindb",
					"spothanachetty", "prokarma");
			final String sub = "{call  insert_subtask2(?,?,?,?)}";
			CallableStatement stmt3 = con.prepareCall(sub);
			

			logger.info("Enter number of Subtasks:");
			int n = s.nextInt();

			for (i = 1; i <= n; i++) {
				logger.info("enter SubTask name:");
				String sname = s.next();

				logger.info("Enter Estimated hours:");
				Integer hrs = s.nextInt();

				stmt3.registerOutParameter(1, java.sql.Types.NUMERIC);
				stmt3.setString(2, sname);
				stmt3.setInt(3, hrs);
				stmt3.setInt(4, id);
			}
			stmt3.execute();
			con.close();
		} catch (SQLException e) {

			logger.error(e);
		}
		return sid;

	}
}
