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
		String sql = "INSERT INTO SERVICE (id, provider_id, address_id, is_approved, category, description, image_url, price, service_radius, rating_count, completed_orders) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
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

    public List<Service> returnAllSERVICE() throws DaoException {
		
		List<Service> SERVICE = new ArrayList<Service>();
		String sql = "SELECT * FROM SERVICE";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Service service = new Service();
						service.setServiceId(rs.getInt("id"));
						service.setProviderId(rs.getInt("provider_id"));
						service.setAddressId(rs.getInt("address_id"));
						service.setIsApproved(rs.getBoolean("is_approved"));
						service.setCategory(rs.getString("category"));
						service.setDescription(rs.getString("description"));
						service.setImageUrl(rs.getString("image_url"));
						service.setServiceRadius(rs.getDouble("service_radius"));
                        service.setPrice(rs.getDouble("price"));
                        service.setRatingCount(rs.getInt("rating_count"));
                        service.setCompletedOrders(rs.getInt("completed_orders"));
						SERVICE.add(service);
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
		return SERVICE;

	}

    public List<Service> returnAllSERVICEOfACategory(String category) throws DaoException {
		
		List<Service> SERVICE = new ArrayList<Service>();
		String sql = "SELECT * FROM SERVICE WHERE category = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
            stmt.setString(1, category);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Service service = new Service();
						service.setServiceId(rs.getInt("id"));
						service.setProviderId(rs.getInt("provider_id"));
						service.setAddressId(rs.getInt("address_id"));
						service.setIsApproved(rs.getBoolean("is_approved"));
						service.setCategory(rs.getString("category"));
						service.setDescription(rs.getString("description"));
						service.setImageUrl(rs.getString("image_url"));
						service.setServiceRadius(rs.getDouble("service_radius"));
                        service.setPrice(rs.getDouble("price"));
                        service.setRatingCount(rs.getInt("rating_count"));
                        service.setCompletedOrders(rs.getInt("completed_orders"));
						SERVICE.add(service);
					} while (rs.next());

				}
				else
				{
					System.out.println("No SERVICE of given category found"); 
				}
			
			}
			catch (Exception e) {
				throw new DaoException(e);
			}
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}
		return SERVICE;

	}

    public List<String> returnAllCategories() throws DaoException {
		
		List<String> categories = new ArrayList<String>();
		String sql = "SELECT DISTINCT(category) FROM SERVICE";
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
		String sql = "SELECT * FROM SERVICE WHERE id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
            stmt.setInt(1, serviceId);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {

                    
                    service.setServiceId(rs.getInt("id"));
                    service.setProviderId(rs.getInt("provider_id"));
                    service.setAddressId(rs.getInt("address_id"));
                    service.setIsApproved(rs.getBoolean("is_approved"));
                    service.setCategory(rs.getString("category"));
                    service.setDescription(rs.getString("description"));
                    service.setImageUrl(rs.getString("image_url"));
                    service.setServiceRadius(rs.getDouble("service_radius"));
                    service.setPrice(rs.getDouble("price"));
                    service.setRatingCount(rs.getInt("rating_count"));
                    service.setCompletedOrders(rs.getInt("completed_orders"));
                    


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
    String sql = "UPDATE SERVICE SET address_id = ?, is_approved = ?, category = ?, description = ?, image_url = ?, price = ?, service_radius = ?, rating_count = ?, COMPLETEDSERVICE = ? WHERE id = ? AND provider_id = ?";
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
