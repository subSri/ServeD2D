package com.sapient.dao;

import java.util.List;

import com.sapient.entity.Address;

public interface AddressDao {
	public Boolean addNewAddress(Address address, Integer userId) throws DaoException ;
	 public Boolean updateAddress(Address address, Integer userId)throws DaoException;
	 public List<Address> getAddress(Integer userId) throws DaoException ;
	 public Address getAddressFromId(Integer addressId) throws DaoException ;
	 
}
