package com.sapient.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sapient.entity.Address;
import com.sapient.utils.DbUtil;

public class AddressDao {
    
    public Boolean addNewAddress(Address address)
			throws DaoException {

		String sql = "INSERT INTO address (ADDRESSID, USERID, LATITUDE, LONGITUDE) VALUES (?,?,?,?)";
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

		String sql = "UPDATE address SET ADDRESSID = ?, USERID = ?, LATITUDE = ?, LONGITUDE = ?";
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

}
