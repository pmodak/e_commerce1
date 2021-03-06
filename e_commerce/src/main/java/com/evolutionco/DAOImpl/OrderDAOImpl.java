package com.evolutionco.DAOImpl;

import java.util.Date;

import java.util.List;
import java.util.UUID;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.evolutionco.DAO.OrderDAO;
import com.evolutionco.DAO.ProductDAO;
import com.evolutionco.entity.Order;
import com.evolutionco.entity.OrderDetails;
import com.evolutionco.entity.Product;
import com.evolutionco.model.CartInfo;
import com.evolutionco.model.CartLineInfo;
import com.evolutionco.model.CustomerInfo;
import com.evolutionco.model.OrderDetailInfo;
import com.evolutionco.model.OrderInfo;
import com.evolutionco.model.PaginationResult;
//Transaction for hibernate
@Transactional
//bean to tell that it is a repository
@Repository
public class OrderDAOImpl implements OrderDAO{
	
	@Autowired
    private SessionFactory sessionFactory;
 
    @Autowired
    private ProductDAO productDAO;
 
    private int getMaxOrderNum() {
        String sql = "Select max(o.orderNum) from " + Order.class.getName() + " o ";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        Integer value = (Integer) query.uniqueResult();
        if (value == null) {
            return 0;
        }
        return value;
    }
	
	
	@Override
	public void saveOrder(CartInfo cartInfo) {
		 Session session = sessionFactory.getCurrentSession();
		 
	        int orderNum = this.getMaxOrderNum() + 1;
	        Order order = new Order();
	 
	        order.setId(UUID.randomUUID().toString());
	        order.setOrderNum(orderNum);
	        order.setOrderDate(new Date());
	        order.setAmount(cartInfo.getAmountTotal());
	 
	        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
	        order.setCustomerName(customerInfo.getName());
	        order.setCustomerEmail(customerInfo.getEmail());
	        order.setCustomerPhone(customerInfo.getPhone());
	        order.setCustomerAddress(customerInfo.getAddress());
	 
	        session.persist(order);
	 
	        List<CartLineInfo> lines = cartInfo.getCartLines();
	 
	        for (CartLineInfo line : lines) {
	            OrderDetails detail = new OrderDetails();
	            detail.setId(UUID.randomUUID().toString());
	            detail.setOrder(order);
	            detail.setAmount(line.getAmount());
	            detail.setPrice(line.getProductInfo().getPrice());
	            detail.setQuanity(line.getQuantity());
	 
	            String code = line.getProductInfo().getCode();
	            Product product = this.productDAO.findProduct(code);
	            detail.setProduct(product);
	 
	            session.persist(detail);
	        }
	 
	        // Set OrderNum for report.
	        //Set OrderNum to notify the user
	        cartInfo.setOrderNum(orderNum);
		
	}
	// @page = 1, 2, ...
	@Override
	public PaginationResult<OrderInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage) {
		 String sql = "Select new " + OrderInfo.class.getName()//
	                + "(ord.id, ord.orderDate, ord.orderNum, ord.amount, "
	                + " ord.customerName, ord.customerAddress, ord.customerEmail, ord.customerPhone) " + " from "
	                + Order.class.getName() + " ord "//
	                + " order by ord.orderNum desc";
	        Session session = this.sessionFactory.getCurrentSession();
	 
	        Query query = session.createQuery(sql);
	 
	        return new PaginationResult<OrderInfo>(query, page, maxResult, maxNavigationPage);
	}
	 public Order findOrder(String orderId) {
	        Session session = sessionFactory.getCurrentSession();
	        Criteria crit = session.createCriteria(Order.class);
	        crit.add(Restrictions.eq("id", orderId));
	        return (Order) crit.uniqueResult();
	    }

	@Override
	public OrderInfo getOrderInfo(String orderId) {
		 Order order = this.findOrder(orderId);
	        if (order == null) {
	            return null;
	        }
	        return new OrderInfo(order.getId(), order.getOrderDate(), //
	                order.getOrderNum(), order.getAmount(), order.getCustomerName(), //
	                order.getCustomerAddress(), order.getCustomerEmail(), order.getCustomerPhone());
	    }

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDetailInfo> listOrderDetailInfos(String orderId) {
		 String sql = "Select new " + OrderDetailInfo.class.getName() //
	                + "(d.id, d.product.code, d.product.name , d.quanity,d.price,d.amount) "//
	                + " from " + OrderDetails.class.getName() + " d "//
	                + " where d.order.id = :orderId ";
	 
	        Session session = this.sessionFactory.getCurrentSession();
	        Query query = session.createQuery(sql);
	        query.setParameter("orderId", orderId);
	        return query.list();
	}

}
