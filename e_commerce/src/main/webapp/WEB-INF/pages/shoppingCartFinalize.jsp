<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 
<title>Shopping Cart Finalize</title>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
 
</head>
<body>
   <jsp:include page="header.jsp" />
 
   <jsp:include page="menu.jsp" />
 
   <div class="page-title">Finalize</div>
  
   <div class="container">
       <h3><font color="blue" size="5">Thank you for Order<br/>
       Your order number is: ${lastOrderedCart.orderNum}</font></h3>
   </div>
 
   <jsp:include page="footer.jsp" />
 
</body>
</html>