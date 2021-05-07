package com.sapient.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sapient.entity.Service;
import com.sapient.utils.DbUtil;

public class ServiceDao {
    
    public Boolean addNewService(Service service)
        throws DaoException {
		String sql = "INSERT INTO service (SERVICEID, PROVIDERID, ADDRESSID, ISAPPROVED, CATEGORY, DESCRIPTION, IMAGEURL, PRICE, SERVICERADIUS, RATINGCOUNT, COMPLETEDserviceS) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		//what to do about user id generation?
		try (Connection conn = DbUtil.createConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, service.getServiceId());
            stmt.setInt(2, service.getProviderId());
            stmt.setInt(3, service.getAddressId());
            stmt.setBoolean(4, service.getIsApproved());
			stmt.setString(5, service.getCategory());
			stmt.setString(6, service.getDescription());
			stmt.setString(7, service.getImageUrl());
			stmt.setDouble(8, service.getPrice());
            stmt.setDouble(9, service.getServiceRadius());
			stmt.setInt(10, service.getRatingCount());
			stmt.setInt(11, service.getCompletedOrders());

			stmt.executeUpdate();
			System.out.println("new service added");
			return true;

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

    public List<Service> returnAllServices() throws DaoException {
		
		List<Service> services = new ArrayList<Service>();
		String sql = "SELECT * FROM services";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Service service = new Service();
						service.setServiceId(rs.getInt("SERVICEID"));
						service.setProviderId(rs.getInt("PROVIDERID"));
						service.setAddressId(rs.getInt("ADDRESSID"));
						service.setIsApproved(rs.getBoolean("ISAPPROVED"));
						service.setCategory(rs.getString("CATEGORY"));
						service.setDescription(rs.getString("DESCRIPTION"));
						service.setImageUrl(rs.getString("IMAGEURL"));
						service.setServiceRadius(rs.getDouble("SERVICERADIUS"));
                        service.setPrice(rs.getDouble("PRICE"));
                        service.setRatingCount(rs.getInt("RATINGCOUNT"));
                        service.setCompletedOrders(rs.getInt("COMPLETEDORDERS"));
						services.add(service);
					} while (rs.next());

				}
				else
				{
					System.out.println("No data found!"); 
				}
			
			}
			catch (Exception e) {
				throw new DaoException(e);
			}
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}
		return services;

	}

    public List<Service> returnAllServicesOfACategory(String category) throws DaoException {
		
		List<Service> services = new ArrayList<Service>();
		String sql = "SELECT * FROM services WHERE CATEGORY = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
            stmt.setString(1, category);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Service service = new Service();
						service.setServiceId(rs.getInt("SERVICEID"));
						service.setProviderId(rs.getInt("PROVIDERID"));
						service.setAddressId(rs.getInt("ADDRESSID"));
						service.setIsApproved(rs.getBoolean("ISAPPROVED"));
						service.setCategory(rs.getString("CATEGORY"));
						service.setDescription(rs.getString("DESCRIPTION"));
						service.setImageUrl(rs.getString("IMAGEURL"));
						service.setServiceRadius(rs.getDouble("SERVICERADIUS"));
                        service.setPrice(rs.getDouble("PRICE"));
                        service.setRatingCount(rs.getInt("RATINGCOUNT"));
                        service.setCompletedOrders(rs.getInt("COMPLETEDORDERS"));
						services.add(service);
					} while (rs.next());

				}
				else
				{
					System.out.println("No services of given category found"); 
				}
			
			}
			catch (Exception e) {
				throw new DaoException(e);
			}
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}
		return services;

	}

    public List<String> returnAllCategories() throws DaoException {
		
		List<String> categories = new ArrayList<String>();
		String sql = "SELECT DISTINCT(CATEGORY) FROM services";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						categories.add(rs.getString(0));
					} while (rs.next());

				}
				else
				{
					System.out.println("No catgories found"); 
				}
			
			}
			catch (Exception e) {
				throw new DaoException(e);
			}
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}
		return categories;

	}

    public Service returnASpecificService(Integer serviceId) throws DaoException {
		
        Service service = new Service();
		String sql = "SELECT * FROM services WHERE SERVICEID = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
            stmt.setInt(1, serviceId);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {

                    
                    service.setServiceId(rs.getInt("SERVICEID"));
                    service.setProviderId(rs.getInt("PROVIDERID"));
                    service.setAddressId(rs.getInt("ADDRESSID"));
                    service.setIsApproved(rs.getBoolean("ISAPPROVED"));
                    service.setCategory(rs.getString("CATEGORY"));
                    service.setDescription(rs.getString("DESCRIPTION"));
                    service.setImageUrl(rs.getString("IMAGEURL"));
                    service.setServiceRadius(rs.getDouble("SERVICERADIUS"));
                    service.setPrice(rs.getDouble("PRICE"));
                    service.setRatingCount(rs.getInt("RATINGCOUNT"));
                    service.setCompletedOrders(rs.getInt("COMPLETEDORDERS"));
                    


				}
				else
				{
					System.out.println("No data found!"); 
				}
			
			}
			catch (Exception e) {
				throw new DaoException(e);
			}
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}
		return service;

	}

    public Boolean updateAService(Service service)
    throws DaoException {
        //how to authenticate the provider here
    String sql = "UPDATE services SET ADDRESSID = ?, ISAPPROVED = ?, CATEGORY = ?, DESCRIPTION = ?, IMAGEURL = ?, PRICE = ?, SERVICERADIUS = ?, RATINGCOUNT = ?, COMPLETEDSERVICE = ? WHERE SERVICEID = ? AND PROVIDERID = ?";
    //what to do about user id generation?
    try (Connection conn = DbUtil.createConnection(); 
        PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
        stmt.setInt(10, service.getServiceId());
        stmt.setInt(11, service.getProviderId());
        stmt.setInt(1, service.getAddressId());
        stmt.setBoolean(2, service.getIsApproved());
        stmt.setString(3, service.getCategory());
        stmt.setString(4, service.getDescription());
        stmt.setString(5, service.getImageUrl());
        stmt.setDouble(6, service.getPrice());
        stmt.setDouble(7, service.getServiceRadius());
        stmt.setInt(8, service.getRatingCount());
        stmt.setInt(9, service.getCompletedOrders());

        stmt.executeUpdate();
        System.out.println("service updated");
        return true;

    } catch (Exception e) {
        throw new DaoException(e);
    }
}

}
