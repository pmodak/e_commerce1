<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"  %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product List</title>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
 
</head>
<body>
 
   <jsp:include page="header.jsp" />
   <jsp:include page="menu.jsp" />
 
   <fmt:setLocale value="en_US" scope="session"/>
  
   <div class="page-title">Order List</div>
 <security:authorize  access="hasRole('ROLE_CUSTOMER')">
   <div>Total Order Count: ${paginationResult.totalRecords}</div>
  
   <table border="1" style="width:100%">
       <tr>
           <th>Order Num</th>
           <th>Order Date</th>
           <th>Customer Name</th>
           <th>Customer Address</th>
           <th>Customer Email</th>
           <th>Amount</th>
           <th>View</th>
       </tr>
       
       <c:forEach items="${paginationResult.list}" var="orderInfo">
           <tr>
               <td>${orderInfo.orderNum}</td>
               <td>
                  <fmt:formatDate value="${orderInfo.orderDate}" pattern="dd-MM-yyyy HH:mm"/>
               </td>
               <td>${orderInfo.customerName}</td>
               <td>${orderInfo.customerAddress}</td>
               <td>${orderInfo.customerEmail}</td>
               <td style="color:red;">
                  <fmt:formatNumber value="${orderInfo.amount}" type="currency" currencySymbol="Rs." currencyCode="INR"/>
               </td>
               <td><a href="${pageContext.request.contextPath}/order?orderId=${orderInfo.id}">
                  View</a></td>
           </tr>
       </c:forEach>
   </table>
</security:authorize>
   <c:if test="${paginationResult.totalPages > 1}">
       <div class="page-navigator">
          <c:forEach items="${paginationResult.navigationPages}" var = "page">
              <c:if test="${page != -1 }">
                <a href="orderList?page=${page}" class="nav-item">${page}</a>
              </c:if>
              <c:if test="${page == -1 }">
                <span class="nav-item"> ... </span>
              </c:if>
          </c:forEach>
       </div>
   </c:if>
   <jsp:include page="footer.jsp" />
 
</body>
</html>