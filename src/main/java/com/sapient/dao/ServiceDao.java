package com.sapient.dao;

import java.util.List;

import com.sapient.entity.Service;

public interface ServiceDao {
	 public Boolean addNewService(Service service) throws DaoException;
	 public List<Service> returnAllService() throws DaoException;
	 public List<Service> returnAllServiceOfACategory(String category) throws DaoException;
	 public List<String> returnAllCategories() throws DaoException;
	 public Service returnASpecificService(Integer serviceId) throws DaoException ;
	 public Boolean updateAService(Service service) throws DaoException;
	 

}
