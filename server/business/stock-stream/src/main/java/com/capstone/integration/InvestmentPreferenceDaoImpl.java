package com.capstone.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.capstone.exceptions.DatabaseException;
import com.capstone.models.IncomeCategory;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.InvestmentPurpose;
import com.capstone.models.InvestmentYear;
import com.capstone.models.RiskTolerance;

public class InvestmentPreferenceDaoImpl implements InvestmentPreferenceDoa {

	DataSource dataSource;

	public InvestmentPreferenceDaoImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public InvestmentPreference getInvestmentPreference(String clientId) {

		if (clientId == null || clientId.isBlank()) {
			throw new IllegalArgumentException("client id cannot be null or blank");
		}

		InvestmentPreference investmentPreference = null;

		try {
			Connection connection = dataSource.getConnection();

			investmentPreference = getInvestmentPreference(connection, clientId);

			return investmentPreference;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DatabaseException();
		}
	}

	private InvestmentPreference getInvestmentPreference(Connection connection, String clientId) throws SQLException {

		String getInvestmentPreferenceQuery = """
				SELECT
				client_id,
				investment_purpose,
				investment_purpose_description,
				risk_tolerance,
				income_category,
				investment_year,
				is_robo_advisor_terms_accepted
				FROM investment_preferences
				WHERE client_id=?
				""";

		try (PreparedStatement statement = connection.prepareStatement(getInvestmentPreferenceQuery)) {

			statement.setString(1, clientId);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				InvestmentPurpose investmentPurpose = InvestmentPurpose.of(rs.getString("investment_purpose"));
				String investmentPurposeDescription = rs.getString("investment_purpose_description");
				RiskTolerance riskTolerance = RiskTolerance.of(rs.getString("risk_tolerance"));
				IncomeCategory incomeCategory = IncomeCategory.of(rs.getString("income_category"));
				InvestmentYear investmentYear = InvestmentYear.of(rs.getString("investment_year"));

				boolean isRoboAdvisorTermsAccepted = false;
				if (rs.getInt("is_robo_advisor_terms_accepted") == 1) {
					isRoboAdvisorTermsAccepted = true;
				}

				InvestmentPreference investmentPreference = new InvestmentPreference(clientId, investmentPurpose,
						investmentPurposeDescription, riskTolerance, incomeCategory, investmentYear,
						isRoboAdvisorTermsAccepted);

				return investmentPreference;
			} else {
				throw new DatabaseException("No data found for clientId : " + clientId);
			}

		}

	}

	@Override
	public InvestmentPreference addInvestmentPreference(InvestmentPreference investmentPreference) {

		if (investmentPreference == null) {
			throw new IllegalArgumentException("Investment preference cannot be null..");
		}

		InvestmentPreference newInvestmentPreference;

		try {
			Connection connection = dataSource.getConnection();

			newInvestmentPreference = addInvestmentPreference(connection, investmentPreference);

			return newInvestmentPreference;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		}

	}

	private InvestmentPreference addInvestmentPreference(Connection connection,
			InvestmentPreference investmentPreference) throws SQLException {

		String insertPreferenceQuery = """
				INSERT INTO investment_preferences
					(client_id,
					investment_purpose,
					investment_purpose_description,
					risk_tolerance,
					income_category,
					investment_year,
					is_robo_advisor_terms_accepted)
					VALUES
					(?,?,?,?,?,?,?)
									""";

		try (PreparedStatement statement = connection.prepareStatement(insertPreferenceQuery)) {

			statement.setString(1, investmentPreference.getClientId());
			statement.setString(2, investmentPreference.getInvestmentPurpose().getName());

			if (investmentPreference.getInvestmentPurposeDescription() == null
					|| investmentPreference.getInvestmentPurposeDescription().isBlank()) {
				investmentPreference
						.setInvestmentPurposeDescription(investmentPreference.getInvestmentPurpose().getDescription());
			}

			statement.setString(3, investmentPreference.getInvestmentPurposeDescription());
			statement.setString(4, investmentPreference.getRiskTolerance().getName());
			statement.setString(5, investmentPreference.getIncomeCategory().getName());
			statement.setString(6, investmentPreference.getInvestmentYear().getName());

			int roboAdvisorBoolean = investmentPreference.isRoboAdvisorTermsAccepted() ? 1 : 0;

			statement.setInt(7, roboAdvisorBoolean);

			if (statement.executeUpdate() == 0) {
				throw new DatabaseException("No new rows where inserted..");
			}

			return investmentPreference;

		}

	}

	@Override
	public InvestmentPreference updateInvestmentPreference(InvestmentPreference investmentPreference) {

		if (investmentPreference == null) {
			throw new IllegalArgumentException("investment preference cannot be null");
		}

		try {
			Connection connection = dataSource.getConnection();

			InvestmentPreference updatedInvestmentPreference = updateInvestmentPreference(connection,
					investmentPreference);

			return updatedInvestmentPreference;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new DatabaseException(e.getErrorCode() + e.getMessage());
		}

	}

	private InvestmentPreference updateInvestmentPreference(Connection connection,
			InvestmentPreference investmentPreference) throws SQLException {
		String updateInvestmentPreferenceQuery = """
				UPDATE investment_preferences
				SET
				client_id=?,
				investment_purpose=?,
				investment_purpose_description=?,
				risk_tolerance=?,
				income_category=?,
				investment_year=?,
				is_robo_advisor_terms_accepted=?
				WHERE client_id = ?
				""";

		try (PreparedStatement statement = connection.prepareStatement(updateInvestmentPreferenceQuery)) {
			statement.setString(1, investmentPreference.getClientId());
			statement.setString(2, investmentPreference.getInvestmentPurpose().getName());

			if (investmentPreference.getInvestmentPurposeDescription() == null
					|| investmentPreference.getInvestmentPurposeDescription().isBlank()) {
				investmentPreference
						.setInvestmentPurposeDescription(investmentPreference.getInvestmentPurpose().getDescription());
			}

			statement.setString(3, investmentPreference.getInvestmentPurposeDescription());
			statement.setString(4, investmentPreference.getRiskTolerance().getName());
			statement.setString(5, investmentPreference.getIncomeCategory().getName());
			statement.setString(6, investmentPreference.getInvestmentYear().getName());
			int roboAdvisorBoolean = investmentPreference.isRoboAdvisorTermsAccepted() ? 1 : 0;

			statement.setInt(7, roboAdvisorBoolean);
			statement.setString(8, investmentPreference.getClientId());

			if (statement.executeUpdate() == 0) {
				throw new DatabaseException("No new rows where inserted..");
			}

			return investmentPreference;

		}

	}

	@Override
	public InvestmentPreference removeInvestmentPreference(String clientId) {

		if (clientId == null || clientId.isBlank()) {
			throw new IllegalArgumentException("client id cannot be null or empty");
		}

		try {
			Connection connection = dataSource.getConnection();
			
			InvestmentPreference deletedInvestmentPreference = removeInvestmentPreference(connection,clientId);
			
			return deletedInvestmentPreference;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		}

	}

	private InvestmentPreference removeInvestmentPreference(Connection connection, String clientId)
			throws SQLException {

		String deletePreferenceQuery = """
				DELETE FROM investment_preferences WHERE client_id=?
				""";
		
		InvestmentPreference investmentPreference = getInvestmentPreference(connection, clientId);
		
		if(investmentPreference!=null) {
			try(PreparedStatement statement = connection.prepareStatement(deletePreferenceQuery)){
				statement.setString(1, investmentPreference.getClientId());
				
				if(statement.executeUpdate()==0) {
					throw new DatabaseException("No rows where deleted..");
				}
				
				return investmentPreference;
				
			}
		}else {
			throw new DatabaseException("No data exists for client id : "+clientId);
		}
		
	}

}
