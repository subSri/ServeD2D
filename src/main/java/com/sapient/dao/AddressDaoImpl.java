package com.sapient.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sapient.entity.Address;
import com.sapient.utils.DbUtil;

public class AddressDaoImpl implements AddressDao{
    
    public Boolean addNewAddress(Address address, Integer userId)
			throws DaoException {
				
		if (address.getUserId() == userId){
			String sql = "INSERT INTO ADDRESS (address_id, user_id, latitude, longitude) VALUES (?,?,?,?)";
			try (Connection conn = DbUtil.createConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
					stmt.setInt(1, address.getAddressid());
					stmt.setInt(2, address.getUserId());
					stmt.setDouble(3, address.getLat());
					stmt.setDouble(4, address.getLongi());
			
					stmt.executeUpdate();
				System.out.println("new address added");
				return true;

			} catch (Exception e) {
				throw new DaoException(e);
			}
		}
		else{
			throw new DaoException("Requested User Not Logged In");
		}

	}


    public Boolean updateAddress(Address address, Integer userId)
			throws DaoException {
		if (address.getUserId() == userId){
			String sql = "UPDATE ADDRESS SET latitude = ?, longitude = ? WHERE address_id = ? AND user_id = ?";
			try (Connection conn = DbUtil.createConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
					stmt.setInt(3, address.getAddressid());
					stmt.setInt(4, userId);
					stmt.setDouble(1, address.getLat());
					stmt.setDouble(2, address.getLongi());
			
					stmt.executeUpdate();
				System.out.println(" address updated");
				return true;

			} catch (Exception e) {
				throw new DaoException(e);
			}
		}
		throw new DaoException("Requested user not logged in");
	}
    
    public List<Address> getAddress(Integer userId) throws DaoException 
    {
		String sql = "SELECT * FROM ADDRESS WHERE user_id = ?";
		List<Address> addresses = new ArrayList<Address>();
		try (Connection conn = DbUtil.createConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
				) 
		{
			
			
		    stmt.setInt(1,userId);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					do{
					Address address = setAddressObj(userId, rs);
					addresses.add(address);
					}while(rs.next());

				} 
			   else {
					System.out.println("No such user");
				}
		   }
		}
		catch (Exception e) {
			throw new DaoException(e);
		}
		return addresses;
		
	}

	private Address setAddressObj(Integer userId, ResultSet rs) throws SQLException {
		Address address = new Address();
		address.setAddressid(rs.getInt("address_id"));
		address.setLat(rs.getDouble("latitude"));
		address.setLongi(rs.getDouble("longitude"));
		address.setUserId(userId);
		return address;
	}

	public Address getAddressFromId(Integer addressId) throws DaoException 
    {
		String sql = "SELECT * FROM ADDRESS WHERE address_id = ?";
		Address address;
		try (Connection conn = DbUtil.createConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
				) 
		{
			
			
		    stmt.setInt(1,addressId);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					
					address = setAddressObj(addressId, rs);
					return address;
					

				} 
			   else {
					System.out.println("No such address");
					throw new DaoException("No such address");
				}
		   }
		}
		catch (Exception e) {
			throw new DaoException(e);
		}
		
		
		
	}

    
    

}
