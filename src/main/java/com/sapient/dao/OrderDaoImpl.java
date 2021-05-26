package com.sapient.dao;

import com.sapient.enums.Enums.OrderStatus;
import com.sapient.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapient.entity.*;

public class OrderDaoImpl implements OrderDao {

	public Boolean addNewOrder(Order order) throws DaoException {
		// String sql = "INSERT INTO `ORDER` (order_id, user_id, service_id, address_id,
		// timestamp, status, amount) VALUES (?,?,?,?,?,?,?)";
		String sql = "INSERT INTO `ORDER` (user_id, service_id, address_id, timestamp, status, amount) VALUES (?,?,?,?,?,?)";
		try (Connection conn = DbUtil.createConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			// stmt.setInt(1, order.getOrderId());
			stmt.setInt(1, order.getUserId());
			stmt.setInt(2, order.getServiceId());
			stmt.setInt(3, order.getAdressId());
			stmt.setDate(4, order.getTimestamp());
			stmt.setInt(5, order.getOrderStatus());
			stmt.setDouble(6, order.getAmount());

			stmt.executeUpdate();

			ResultSet key = stmt.getGeneratedKeys();
			if (key.next()) {
				int id = key.getInt(1); // or "order_id"
				System.out.println("new order added" + id);
			}
			return true;

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List<Map<String, String>> returnAllOrders(Integer userId) throws DaoException {

		List<Map<String, String>> orders = new ArrayList<Map<String, String>>();
		String sql = "select o.order_id,o.user_id,o.service_id,s.service_name,o.address_id,o.timestamp,o.amount,o.status from `ORDER` o,`SERVICE` s where o.user_id=? and o.service_id=s.service_id group by o.order_id";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					do {
						Map<String, String> order = new HashMap<String, String>();
						order.put("order_id", rs.getString("order_id"));
						order.put("user_id", rs.getString("user_id"));
						order.put("service_id", rs.getString("service_id"));
						order.put("service_name", rs.getString("service_name"));
						order.put("address_id", rs.getString("address_id"));
						order.put("timestamp", rs.getString("timestamp"));
						order.put("amount", rs.getString("amount"));
						order.put("status", OrderStatus.values()[rs.getInt("status")].name());
						orders.add(order);
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
		return orders;

	}

	public List<Order> returnAllOrdersForProvider(Integer provId) throws DaoException {

		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT order_id,user_id,SERVICE.service_id,SERVICE.address_id,amount,timestamp,status FROM `ORDER` JOIN SERVICE ON `ORDER`.service_id = SERVICE.service_id WHERE provider_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, provId);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					do {
						Order order = getOrderObj(rs);
						orders.add(order);
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
		return orders;

	}

	private Order getOrderObj(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setOrderId(rs.getInt("order_id"));
		order.setUserId(rs.getInt("user_id"));
		order.setServiceId(rs.getInt("service_id"));
		order.setAdressId(rs.getInt("address_id"));
		order.setAmount(rs.getDouble("amount"));
		order.setTimestamp(rs.getDate("timestamp"));
		order.setOrderStatus(rs.getInt("status"));
		return order;
	}

	public List<Order> returnAllIncompleteOrders(Integer userId) throws DaoException {

		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM `ORDER` WHERE user_id =  ? AND STATUS = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, userId);
			stmt.setInt(2, OrderStatus.CONFIRMED.ordinal());
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					do {
						Order order = getOrderObj(rs);
						orders.add(order);
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
		return orders;

	}

	public List<Order> returnAllRequestedOrders(Integer userId) throws DaoException {

		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM `ORDER` WHERE user_id =  ? AND status = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, userId);
			stmt.setInt(2, OrderStatus.REQUESTED.ordinal());
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					do {
						Order order = getOrderObj(rs);
						orders.add(order);
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
		return orders;

	}

	public List<Order> returnAllCancelledOrders(Integer userId) throws DaoException {

		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM `ORDER` WHERE user_id =  ? AND status = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, userId);
			stmt.setInt(2, OrderStatus.CANCELLED.ordinal());
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					do {
						Order order = getOrderObj(rs);
						orders.add(order);
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
		return orders;

	}

	public List<Order> returnAllRejectedOrders(Integer userId) throws DaoException {

		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM `ORDER` WHERE user_id =  ? AND status = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, userId);
			stmt.setInt(2, OrderStatus.REJECTED.ordinal());
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					do {
						Order order = getOrderObj(rs);
						orders.add(order);
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
		return orders;

	}

	public List<Order> returnAllPastCompletedOrders(Integer userId) throws DaoException {

		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM `ORDER` WHERE user_id =  ? AND status = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, userId);
			stmt.setInt(2, OrderStatus.COMPLETED.ordinal());
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					do {
						Order order = getOrderObj(rs);
						orders.add(order);
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
		return orders;

	}

	public Map<String, String> returnSpecificOrder(Integer orderId, Integer userId) throws DaoException {

		String sql = "select o.order_id,o.user_id,u.name,o.service_id,s.service_name,s.provider_id,o.address_id,a.latitude,a.longitude,o.timestamp,o.amount,o.status from `ORDER` o, `USER` u,`SERVICE` s,`ADDRESS` a where s.provider_id=u.user_id and o.service_id=s.service_id and o.address_id=a.address_id and o.order_id=? and o.user_id=?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, orderId);
			stmt.setInt(2, userId);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					Map<String, String> order = new HashMap<>();
					order.put("order_id", rs.getString("order_id"));
					order.put("user_id", rs.getString("user_id"));
					order.put("provider_name", rs.getString("name"));
					order.put("provider_id", rs.getString("provider_id"));
					order.put("service_id", rs.getString("service_id"));
					order.put("service_name", rs.getString("service_name"));
					order.put("address_id", rs.getString("address_id"));
					order.put("latitude", rs.getString("latitude"));
					order.put("longitude", rs.getString("longitude"));
					order.put("timestamp", rs.getString("timestamp"));
					order.put("amount", rs.getString("amount"));
					order.put("status", OrderStatus.values()[rs.getInt("status")].name());
					return order;
				} else {
					System.out.println("No data found!");
				}

			} catch (Exception e) {
				throw new DaoException(e);
			}

		} catch (Exception e) {
			throw new DaoException(e);
		}
		return null;

	}

	public void cancelOrder(Integer orderId) throws DaoException {

		String sql = "UPDATE `ORDER` SET status = ? WHERE order_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, OrderStatus.CANCELLED.ordinal());
			stmt.setInt(2, orderId);
			stmt.executeUpdate();

		} catch (Exception e) {
			throw new DaoException(e);
		}

	}

	public void rejectOrder(Integer orderId) throws DaoException {

		String sql = "UPDATE `ORDER` SET status = ? WHERE order_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, OrderStatus.REJECTED.ordinal());
			stmt.setInt(2, orderId);
			stmt.executeUpdate();

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void acceptOrder(Integer orderId) throws DaoException {

		String sql = "UPDATE `ORDER` SET status = ? WHERE order_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, OrderStatus.CONFIRMED.ordinal());
			stmt.setInt(2, orderId);
			stmt.executeUpdate();

		} catch (Exception e) {
			throw new DaoException(e);
		}

	}

	public void completeOrder(Integer orderId) throws DaoException {

		String sql = "UPDATE `ORDER` SET status = ? WHERE order_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, OrderStatus.COMPLETED.ordinal());
			stmt.setInt(2, orderId);
			stmt.executeUpdate();

		} catch (Exception e) {
			throw new DaoException(e);
		}

	}

	public Boolean updateCompletedOrders(Integer serviceId) throws DaoException {

		String sql = "UPDATE SERVICE SET completed_orders = completed_orders + ? WHERE service_id = ?";
		// what to do about user id generation?
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, 1);
			stmt.setInt(2, serviceId);

			stmt.executeUpdate();
			System.out.println("Count of completed orders increased");
			return true;

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

}
