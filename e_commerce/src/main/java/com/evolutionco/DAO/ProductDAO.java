package com.evolutionco.DAO;

import com.evolutionco.entity.Product;
import com.evolutionco.model.PaginationResult;
import com.evolutionco.model.ProductInfo;

public interface ProductDAO {
	 public Product findProduct(String code);
	    
	    public ProductInfo findProductInfo(String code) ;
	  
	    
	    public PaginationResult<ProductInfo> queryProducts(int page,
	                       int maxResult, int maxNavigationPage  );
	    
	    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult,
	                       int maxNavigationPage, String likeName);
	 
	    public void save(ProductInfo productInfo);
}
