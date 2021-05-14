package com.sapient.dao;

import java.sql.SQLException;
import java.util.List;

import com.sapient.entity.*;

public interface UserDao {
	
	public Boolean verifyUserCreds(User user) throws DaoException;
	public Boolean addNewUser(User user) throws DaoException ;
	public Double getBalance(Integer userId) throws DaoException, ClassNotFoundException, SQLException ;
	public Boolean addToWallet(Integer userId, Double amount) throws DaoException ;
	public Boolean withdrawFromWallet(Integer userId, Double amount) throws DaoException;
	public List<Service> getTopRatedNServicesWithinACategory(String category, Integer n) throws DaoException;
	public List<Service> findNearByServicesSortedByDistance(String category, Integer n, Address userAddress) throws DaoException;
	

}
