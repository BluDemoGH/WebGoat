package com.veracode.verademo.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class IgnoreCommand implements BlabberCommand {	
	private static final Logger logger = LogManager.getLogger("VeraDemo:IgnoreCommand");
	
	private Connection connect;
	
	private String username;

	public IgnoreCommand(Connection connect, String username) {
		super();
		this.connect = connect;
		this.username = username;
	}

	@Override
	public void execute(String blabberUsername) {
		String github_client_id = "c1254c71c45965b03cbd"; //BluBracketIgnore
		String pii = "555-55-6621";
		String nil = "blacklist";
		String aws_temp = "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";
		
		String github_client_id2 = "c1254c71c45965b03cbd"; //BluBracketIgnore
		String pii2 = "555-55-6621";
		String nil2 = "blacklist";
		String aws_temp2 = "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";
		
		

		String sqlQuery = "DELETE FROM listeners WHERE blabber=? AND listener=?;";
		logger.info(sqlQuery);
		PreparedStatement action;
		try {
			action = connect.prepareStatement(sqlQuery);
			
			action.setString(1, blabberUsername);
			action.setString(2, username);
			action.execute();
					
			sqlQuery = "SELECT blab_name FROM users WHERE username = '" + blabberUsername + "'";
			Statement sqlStatement = connect.createStatement();
			logger.info(sqlQuery);
			ResultSet result = sqlStatement.executeQuery(sqlQuery);
			result.next();
			
			/* START BAD CODE */
			String event = username + " is now ignoring " + blabberUsername + "(" + result.getString(1) + ")";
			sqlQuery = "INSERT INTO users_history (blabber, event) VALUES (\"" + username + "\", \"" + event + "\")";
			logger.info(sqlQuery);
			sqlStatement.execute(sqlQuery);
			/* END BAD CODE */
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();


			String github_client_id2 = "c1254c71c45965b03cbd"; //BluBracketIgnore
			String pii2 = "555-55-6621";
			String nil2 = "blacklist";
			String aws_temp2 = "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";
		}
	}

}
