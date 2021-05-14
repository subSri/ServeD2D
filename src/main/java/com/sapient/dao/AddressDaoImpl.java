package com.sapient.dao;

import java.sql.*;

import com.sapient.entity.Address;
import com.sapient.utils.DbUtil;

public class AddressDaoImpl implements AddressDao{
    
    public Boolean addNewAddress(Address address)
			throws DaoException {

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

    public Boolean updateAddress(Address address)
			throws DaoException {

		String sql = "UPDATE ADDRESS SET address_id = ?, user_id = ?, latitude = ?, longitude = ?";
		try (Connection conn = DbUtil.createConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, address.getAddressid());
			stmt.setInt(2, address.getUserId());
			stmt.setDouble(3, address.getLat());
			stmt.setDouble(4, address.getLongi());

			stmt.executeUpdate();
			System.out.println(" address updated");
			return true;

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
    
    public Address getAddress(Integer userId) throws DaoException 
    {
		String sql = "SELECT * FROM ADDRESS WHERE user_id = ?";
		try (Connection conn = DbUtil.createConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sql);
				) 
		{
			
		    stmt.setInt(1,userId);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					Address address = new Address();
					address.setAddressid(rs.getInt("address_id"));
					address.setLat(rs.getDouble("latitude"));
					address.setLongi(rs.getDouble("longitude"));
					address.setUserId(userId);
					return address;
				} 
			   else {
					System.out.println("No such user");
					return null;
				}
		   }
		}
		catch (Exception e) {
			throw new DaoException(e);
		}
		
	}
    
    

}
