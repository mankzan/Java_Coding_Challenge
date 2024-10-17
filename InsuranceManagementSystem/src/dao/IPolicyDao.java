package dao;
import entity.Policy;
import exceptions.PolicyNotFoundException;

import java.util.Collection;

public interface IPolicyDao {
	
	 boolean createPolicy(Policy policy);
	    Policy getPolicy(int policyId) throws PolicyNotFoundException;
	    Collection<Policy> getAllPolicies() throws PolicyNotFoundException;
	    boolean updatePolicy(Policy policy) throws PolicyNotFoundException;
	    boolean deletePolicy(int policyId) throws PolicyNotFoundException;
}