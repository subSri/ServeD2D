package com.sapient.dao;

import com.sapient.entity.Address;

public interface AddressDao {
	public Boolean addNewAddress(Address address) throws DaoException ;
	 public Boolean updateAddress(Address address)throws DaoException;
	 public Address getAddress(Integer userId) throws DaoException ;
	 
}
