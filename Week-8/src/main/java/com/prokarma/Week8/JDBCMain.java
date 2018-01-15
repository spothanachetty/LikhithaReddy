package com.prokarma.Week8;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.log4j.Logger;


public class JDBCMain {
	private static Logger logger = Logger.getLogger(JDBCMain.class);
	public static void main(String[] args) throws IOException {
	
		int i, x = 1;

		Maintask mt = new Maintask(null, 0);
		Subtask st = new Subtask(null, 0);
		Scanner in = new Scanner(System.in);

		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@172.16.203.151:1521:traindb", "spothanachetty",
					"prokarma");

			while (x == 1) {

				logger.info("Menu: \n 1. Add MainTask\n 2. Print Existing Data\n 3.Update Data\n 4.Exit");
				int num = in.nextInt();
				logger.info("Enter your name:");
				String name = in.next();
				logger.info("Enter your age:");
				int age = in.nextInt();
				final String que = "{call insert_person(?,?,?)}";
				CallableStatement stmt = con.prepareCall(que);
				stmt.registerOutParameter(1, java.sql.Types.NUMERIC);
				stmt.setString(2, name);
				stmt.setInt(3, age);
				logger.info("Person details are entered");
				stmt.execute();
				int id = stmt.getInt(1);

				switch (num) {
				case 1:

					if (age > 30) {
						int mid = mt.task(id);
						logger.info("Enter number of Subtasks:");
						int n = in.nextInt();

						final String sub = "{call  insert_subtask(?,?,?,?,?)}";
						CallableStatement stmt2 = con.prepareCall(sub);

						stmt2.setInt(1, mid);
						stmt2.registerOutParameter(2, java.sql.Types.NUMERIC);

						stmt2.setInt(5, id);

						for (i = 1; i <= n; i++) {
							logger.info("enter SubTask name:");
							String sname = in.next();
							stmt2.setString(3, sname);
							logger.info("Enter Estimated hours:");
							Integer hrs = in.nextInt();
							stmt2.setInt(4, hrs);
						}
						stmt2.execute();
						
						logger.info("SubTask details are entered");
					} else if (age > 20 && age <= 30) {
						st.task(id);

					} else {
						try {
							throw new InvalidAgeException();
						} catch (InvalidAgeException e) {
							logger.error(e);
						}
					}
					break;
				case 2:
					logger.info("The details of Person,MainTasks,SubTasks");
					Statement stm1 = con.createStatement();
					ResultSet rs1 = stm1.executeQuery("select * from Person");
					rs1.next();
					Statement stm2 = con.createStatement();
					ResultSet rs2 = stm2.executeQuery("select * from MainTask");
					rs2.next();
					Statement stm3 = con.createStatement();
					ResultSet rs3 = stm3.executeQuery("select * from SubTask");
					rs3.next();

					ResultSetMetaData rsmd = rs1.getMetaData();
					int coloumnsNumber = rsmd.getColumnCount();
					while (rs1.next()) {
						for (int j = 1; j <= coloumnsNumber; j++) {
							if (j > 1)
								System.out.println(", ");
							String coloumnValue = rs1.getString(j);
							logger.info(coloumnValue+ " " + rsmd.getColumnName(j));
						}
						logger.info("");
					}
					

					ResultSetMetaData rsmd2 = rs2.getMetaData();
					int coloumnsNumber1 = rsmd2.getColumnCount();
					while (rs2.next()) {
						for (int j = 1; j <= coloumnsNumber1; j++) {
							if (j > 1)
								logger.info(", ");
							String coloumnValue = rs2.getString(j);
							logger.info(coloumnValue+ " " + rsmd2.getColumnName(j));
						}
						logger.info("");
					}

					ResultSetMetaData rsmd3 = rs3.getMetaData();
					int coloumnsNumber2 = rsmd3.getColumnCount();
					while (rs3.next()) {
						for (int j = 1; j <= coloumnsNumber2; j++) {
							if (j > 1)
								logger.info(", ");
							String coloumnValue = rs3.getString(j);
							logger.info(coloumnValue+ " " + rsmd3.getColumnName(j));
						}
						logger.info("");
					}

					break;
				case 3:
					logger.info("Enter Maintask name");
					String mname = in.next();
					logger.info("Enter Maintask ID which youn want to update");
					int mid = in.nextInt();
					PreparedStatement stm = con.prepareStatement("update MainTask set mt_name=? where mt_id=? ");
					stm.setString(1, mname);
					stm.setInt(2, mid);
                    stm.execute();
					break;
				case 4:
					logger.info("Exit");
					break;
				default:
					logger.info("Invalid");
					break;
				}
			}
			con.close();

		} catch (SQLException e) {
			logger.error(e);
		}

	}

}
