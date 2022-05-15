package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public class Bill {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3308/billing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertProject(String bname, String price, String bdes) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into Billservice(`id`, `bname`, `price`, `bdes`)"
					+ " values (  ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, bname);
			preparedStmt.setString(3, price);
			preparedStmt.setString(4, bdes);
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newProject = readProject();
			output = "{\"status\":\"success\", \"data\": \"" + newProject + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the project.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readProject() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\'1\'><tr><th> Name </th><th> Price </th><th> Description </th><th> Update </th><th> Delete </th></tr>";
			String query = "select * from Billservice";

			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String id = Integer.toString(rs.getInt("id"));
				String bName = rs.getString("bname");
				String bPrice = rs.getString("price");
				String bDes = rs.getString("bDes");

				// Add into the html table
				output += "<tr><td><input id=\'hidProjectIDUpdate\' name=\'hidProjectIDUpdate\' type=\'hidden\' value=\'"
						+ id + "'>" + bName + "</td>";
				output += "<td>" + bPrice + "</td>";
				output += "<td>" + bDes + "</td>";

				
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-billid='"
						+ id + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the projects.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updateProject(String id, String bname, String price, String bdes) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE Billservice SET bname=?,price=?,bdes=? WHERE id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, bname);
			preparedStmt.setString(2, price);
			preparedStmt.setString(3, bdes);
			preparedStmt.setInt(4, Integer.parseInt(id));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newProject = readProject();
			output = "{\"status\":\"success\", \"data\": \"" + newProject + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the project.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteProject(String id) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from Billservice where id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newProject = readProject();
			output = "{\"status\":\"success\", \"data\": \"" + newProject + "\"}";
		} catch (Exception e) {
			output = "Error while deleting the project.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
