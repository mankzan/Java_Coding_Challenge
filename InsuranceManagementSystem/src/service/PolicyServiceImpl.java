package service;

import java.util.Collection;
import dao.PolicyDaoImpl;
import entity.Policy;
import exceptions.PolicyNotFoundException;

public class PolicyServiceImpl implements IPolicyService {

    private PolicyDaoImpl dao;

    public PolicyServiceImpl() {
        dao = new PolicyDaoImpl();
    }

    @Override
    public boolean createPolicy(Policy policy) {

        if (validatePolicy(policy)) {
            return dao.createPolicy(policy);
        }
        return false; 
    }

    @Override
    public Policy getPolicy(int policyId) throws PolicyNotFoundException {
        Policy policy = dao.getPolicy(policyId);
        if (policy == null) {
            throw new PolicyNotFoundException("Policy with ID " + policyId + " does not exist.");
        }
        return policy;
    }

    @Override
    public Collection<Policy> getAllPolicies() {
        return dao.getAllPolicies();
    }

    @Override
    public boolean updatePolicy(Policy policy) {
        if (validatePolicy(policy)) {
            return dao.updatePolicy(policy);
        }
        return false; 
    }

    @Override
    public boolean deletePolicy(int policyId) {
        return dao.deletePolicy(policyId);
    }

    private boolean validatePolicy(Policy policy) {
        boolean isValid = true;
        if (policy.getPolicyId() <= 0 || policy.getPolicyName() == null || policy.getPolicyName().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }
}
