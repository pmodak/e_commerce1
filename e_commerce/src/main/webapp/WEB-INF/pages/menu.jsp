<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>   
 <!DOCTYPE html>
 
<div class="menu-container">
  
   <a href="${pageContext.request.contextPath}/">Home</a>
    |
   <a href="${pageContext.request.contextPath}/productList">
      Product List
   </a>
   |
   <a href="${pageContext.request.contextPath}/shoppingCart">
      My Cart
   </a>
   |
   <security:authorize  access="hasRole('ROLE_ADMIN')">
     <a href="${pageContext.request.contextPath}/orderList">
         Order List
     </a>
     |
   </security:authorize>
   
   <security:authorize  access="hasRole('ROLE_CUSTOMER')">
     <a href="${pageContext.request.contextPath}/customerorderList">
         Order List
     </a>
     |
   </security:authorize>
   <security:authorize  access="hasRole('ROLE_ADMIN')">
         <a href="${pageContext.request.contextPath}/product">
                        Create Product
         </a>
         |
   </security:authorize>
</div>