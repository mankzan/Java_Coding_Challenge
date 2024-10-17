package dao;
import Util.DBConnection;
import entity.Policy;
import java.sql.*;
import java.util.*;


public class PolicyDaoImpl implements IPolicyDao{
	
	private Connection conn;

	public PolicyDaoImpl() {

		conn = DBConnection.getDBConnection();

	}

	@Override
	public boolean createPolicy(Policy policy) {
		// TODO Auto-generated method stub
		String insert =  "INSERT INTO policy (policyId, policyName, policyType) VALUES (?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(insert);
	            pstmt.setInt(1, policy.getPolicyId());
	            pstmt.setString(2, policy.getPolicyName());
	            pstmt.setString(3, policy.getPolicyType());

	            int rowsAffected = pstmt.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }

	}

	@Override
	public Policy getPolicy(int policyId) {
		
		String insert =  "SELECT * FROM policy WHERE policyId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(insert);
	            pstmt.setInt(1, policyId);
	            ResultSet resultSet = pstmt.executeQuery();

	            if (resultSet.next()) {
	                return mapResultSetToPolicy(resultSet);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	}

	@Override
	public Collection<Policy> getAllPolicies() {
		
		List<Policy> policies = new ArrayList<>();
		String selectAll = "select * from Employees ";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(selectAll);
			ResultSet rs = pstmt.executeQuery();
			
            while (rs.next()) {
                Policy policy = mapResultSetToPolicy(rs);
                policies.add(policy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return policies;
	}

	@Override
	public boolean updatePolicy(Policy policy) {
		String update =  "UPDATE policy SET policyName = ?, policyType = ? WHERE policyId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(update);
	            pstmt.setString(1, policy.getPolicyName());
	            pstmt.setString(2, policy.getPolicyType());
	            pstmt.setInt(3, policy.getPolicyId());

	            int rowsAffected = pstmt.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	}

	@Override
	public boolean deletePolicy(int policyId) {
		String delete = "DELETE FROM policy WHERE policyId = ?";
		try {
	             PreparedStatement pstmt = conn.prepareStatement(delete);
	            pstmt.setInt(1, policyId);

	            int rowsAffected = pstmt.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	}
	private Policy mapResultSetToPolicy(ResultSet resultSet) throws SQLException {
        int policyId = resultSet.getInt("policyId");
        String policyName = resultSet.getString("policyName");
        String policyType = resultSet.getString("policyType");

        return new Policy(policyId, policyName, policyType, policyId);
    }
	
	

}