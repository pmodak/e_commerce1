package com.evolutionco.DAO;

import java.util.List;
import com.evolutionco.model.*;

public interface CustomerOrderDAO {
	 public void saveOrder(CartInfo cartInfo);
	 
	    public PaginationResult<OrderInfo> listOrderInfo(int page,
	            int maxResult, int maxNavigationPage);
	    
	    public OrderInfo getOrderInfo(String orderId);
	    
	    public List<OrderDetailInfo> listOrderDetailInfos(String orderId);
}
