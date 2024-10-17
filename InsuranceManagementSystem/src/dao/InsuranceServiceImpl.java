package dao;

import entity.Policy;
import exceptions.PolicyNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Util.DBConnection;

public class InsuranceServiceImpl implements IPolicyDao {
    
	private Connection conn;

	public InsuranceServiceImpl() {

		conn = DBConnection.getDBConnection();

	}
	
    @Override
    public boolean createPolicy(Policy policy){
    	String insert =   "INSERT INTO policy (policyId, policyName, policyType,coverageAmount) VALUES (?, ?, ?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(insert);
            pstmt.setInt(1, policy.getPolicyId());
            pstmt.setString(2, policy.getPolicyName());
            pstmt.setString(3, policy.getPolicyType());
            pstmt.setDouble(4, policy.getCoverageAmount());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Policy getPolicy(int policyId) throws PolicyNotFoundException {
    	String selectAll =   "SELECT * FROM policy WHERE policyId = ?";
        try {
			PreparedStatement pstmt = conn.prepareStatement(selectAll);
			ResultSet rs = pstmt.executeQuery();
            pstmt.setInt(1, policyId);

            if (rs.next()) {
                return mapResultSetToPolicy(rs);
            } else {
            	throw new PolicyNotFoundException("Policy with ID " + policyId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PolicyNotFoundException("Error while retrieving policy with ID " + policyId);
        }
    }

    @Override
    public Collection<Policy> getAllPolicies() throws PolicyNotFoundException  {
        List<Policy> policies = new ArrayList<>();

        String selectAll =   "SELECT * FROM policy";
        try {
			PreparedStatement pstmt = conn.prepareStatement(selectAll);
			ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Policy policy = mapResultSetToPolicy(rs);
                policies.add(policy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PolicyNotFoundException("Error while retrieving all policies");
        }

        return policies;
    }

    @Override	
    public boolean updatePolicy(Policy policy) {
    	String update =  "UPDATE policy SET policyName = ?, policyType = ?, coverageAmount = ? WHERE policyId = ?";
        try {
        	PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, policy.getPolicyName());
            pstmt.setString(2, policy.getPolicyType());
            pstmt.setDouble(3, policy.getCoverageAmount());
            pstmt.setInt(4, policy.getPolicyId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePolicy(int policyId) throws PolicyNotFoundException  {
    	String delete = "DELETE FROM policy WHERE policyId = ?";
        try {
        	PreparedStatement pstmt = conn.prepareStatement(delete);
            pstmt.setInt(1, policyId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            } else {
                throw new PolicyNotFoundException("Policy with ID " + policyId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PolicyNotFoundException("Error while deleting policy with ID " + policyId);
        }
    }


    private Policy mapResultSetToPolicy(ResultSet resultSet) throws SQLException {
        int policyId = resultSet.getInt("policyId");
        String policyName = resultSet.getString("policyName");
        String policyType = resultSet.getString("policyType");
        double coverageAmount = resultSet.getDouble("coverageAmount");

        return new Policy(policyId, policyName, policyType, coverageAmount);
    }

  
}