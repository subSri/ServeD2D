package com.sapient.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sapient.entity.Address;
import com.sapient.entity.Service;
import com.sapient.utils.DbUtil;

public class ServiceDaoImpl implements ServiceDao {

	public Boolean addNewService(Service service) throws DaoException {
//		String sql = "INSERT INTO SERVICE (service_id, provider_id, address_id, is_approved, category, description, image_url, price, service_radius, rating_count, completed_orders) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		String sql = "INSERT INTO SERVICE (provider_id, address_id, is_approved, category, description, image_url, price, service_radius, rating_count, completed_orders) VALUES (?,?,?,?,?,?,?,?,?,?)";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
//			stmt.setInt(1, service.getServiceId());
			stmt.setInt(1, service.getProviderId());
			stmt.setInt(2, service.getAddressId());
			stmt.setBoolean(3, service.getIsApproved());
			stmt.setString(4, service.getCategory());
			stmt.setString(5, service.getDescription());
			stmt.setString(6, service.getImageUrl());
			stmt.setDouble(7, service.getPrice());
			stmt.setDouble(8, service.getServiceRadius());
			stmt.setInt(9, service.getRatingCount());
			stmt.setInt(10, service.getCompletedOrders());

			stmt.executeUpdate();

			ResultSet key = stmt.getGeneratedKeys();
			if (key.next()) {
				int id = key.getInt(1); // or "service_id"
				System.out.println("new service added" + id);
			}
			return true;

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List<Service> returnAllService() throws DaoException {

		List<Service> services = new ArrayList<Service>();
		String sql = "SELECT * FROM SERVICE";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					do {
						Service service = getServiceObj(rs);
						services.add(service);
					} while (rs.next());

				} else {
					System.out.println("No data found!");
				}

			} catch (Exception e) {
				throw new DaoException(e);
			}

		} catch (Exception e) {
			throw new DaoException(e);
		}
		return services;

	}

	private Service getServiceObj(ResultSet rs) throws SQLException {
		Service service = new Service();
		service.setServiceId(rs.getInt("service_id"));
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
		service.setName(rs.getString("name"));
		service.setRatings();
		return service;
	}

	public List<Service> returnAllServiceOfACategory(String category) throws DaoException {

		List<Service> serviceList = new ArrayList<Service>();
		String sql = "SELECT SERVICE.*,USER.name FROM SERVICE INNER JOIN USER ON SERVICE.provider_id=USER.user_id WHERE SERVICE.category=?";
		// String sql = "SELECT * FROM SERVICE WHERE category = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setString(1, category);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					do {
						Service service = getServiceObj(rs);
						serviceList.add(service);
					} while (rs.next());

				} else {
					System.out.println("No SERVICE of given category found");
				}

			} catch (Exception e) {
				throw new DaoException(e);
			}

		} catch (Exception e) {
			throw new DaoException(e);
		}
		return serviceList;

	}

	public List<String> returnAllCategories() throws DaoException {

		List<String> categories = new ArrayList<String>();
		String sql = "SELECT DISTINCT(category) FROM SERVICE";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					do {
						categories.add(rs.getString("category"));
					} while (rs.next());

				} else {
					System.out.println("No catgories found");
				}

			} catch (Exception e) {
				throw new DaoException(e);
			}

		} catch (Exception e) {
			throw new DaoException(e);
		}
		return categories;

	}

	public Service returnASpecificService(Integer serviceId) throws DaoException {

		Service service = new Service();
		String sql = "SELECT * FROM SERVICE WHERE service_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, serviceId);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					service = getServiceObj(rs);
				} else {
					System.out.println("No data found!");
				}

			} catch (Exception e) {
				throw new DaoException(e);
			}

		} catch (Exception e) {
			throw new DaoException(e);
		}
		return service;

	}

	public Boolean updateAService(Service service) throws DaoException {
		// how to authenticate the provider here
//		String sql = "UPDATE SERVICE SET address_id = ?, is_approved = ?, category = ?, description = ?, image_url = ?, price = ?, service_radius = ?, rating_count = ?, completed_orders = ? WHERE service_id = ? AND provider_id = ?";
		String sql = "UPDATE SERVICE SET address_id = ?, is_approved = ?, category = ?, description = ?, image_url = ?, price = ?, service_radius = ?, rating_count = ?, completed_orders = ? WHERE provider_id = ?";

		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, service.getAddressId());
			stmt.setBoolean(2, service.getIsApproved());
			stmt.setString(3, service.getCategory());
			stmt.setString(4, service.getDescription());
			stmt.setString(5, service.getImageUrl());
			stmt.setDouble(6, service.getPrice());
			stmt.setDouble(7, service.getServiceRadius());
			stmt.setInt(8, service.getRatingCount());
			stmt.setInt(9, service.getCompletedOrders());
//			stmt.setInt(10, service.getServiceId());
			stmt.setInt(10, service.getProviderId());

			stmt.executeUpdate();

			ResultSet key = stmt.getGeneratedKeys();
			if (key.next()) {
				int id = key.getInt(1); // or "review_id"
				System.out.println("service updated" + id);
			}
			return true;

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List<Service> getTopRatedNServicesWithinACategory(String category, Integer n) throws DaoException {
		String sql = "SELECT * FROM USER JOIN SERVICE ON USER.user_id = SERVICE.provider_id WHERE SERVICE.category = ? ORDER BY (SERVICE.rating_count/SERVICE.completed_orders) DESC LIMIT ?";
		List<Service> topServices = new ArrayList<Service>();
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setString(1, category);
			stmt.setInt(2, n);
			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					Service service = getServiceObj(rs);
					topServices.add(service);
				}
			} catch (Exception e) {
				throw new DaoException(e);
			}
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return topServices;
	}

	public List<Service> findNearByServicesSortedByDistance(String category, Integer n, Address userAddress)
			throws DaoException {

		String sql = "SELECT *,(3959 * acos (cos ( radians(?) )* cos( radians( ADDRESS.latitude ) )* cos( radians( ADDRESS.longitude ) - radians(?) )+ sin ( radians(?) )* sin( radians( ADDRESS.latitude ) )))"
				+ "AS distance FROM USER JOIN ADDRESS ON USER.user_id = ADDRESS.user_id WHERE USER.user_id IN (SELECT provider_id FROM SERVICE WHERE category = ? ) ORDER BY distance DESC LIMIT ?";
		List<Service> topServiceProviders = new ArrayList<Service>();
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setDouble(1, userAddress.getLat());
			stmt.setDouble(2, userAddress.getLongi());
			stmt.setDouble(3, userAddress.getLat());
			stmt.setString(4, category);
			stmt.setInt(5, n);
			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					Service service = getServiceObj(rs);
					topServiceProviders.add(service);
				}
			} catch (Exception e) {
				throw new DaoException(e);
			}
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return topServiceProviders;
	}

}
