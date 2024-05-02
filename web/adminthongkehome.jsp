<%-- 
    Document   : adminthongkehome
    Created on : Oct 30, 2023, 1:16:13 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" href="fontawesome-free-5.15.4-web/fontawesome-free-5.15.4-web/css/all.min.css">
    </head>
 <body>
           <div class="apps" onload="hideAdminBar();">
        <div id="preLoader"></div>

            <jsp:include page="headeradmin.jsp" />  

       <div class="menu-container">
        <div class="menu">
            <div class="row">
                <a href="thongkespController" id="managerProduct"><i class="fab fa-product-hunt"></i> <span>Thống kê sản phẩm</span></a>
                <a href="thongkedoanhthuController" id="managerNCC"><i class="fas fa-dollar-sign"></i> <span>Thống kê doanh thu</span></a>
                <a href="thongkeuserController" id="managerUser"><i class="fas fa-users"></i> <span>Thống kê người dùng</span></a>
            </div>
            
        </div>
    </div>
    
    </div>
         <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        
    </body>
</html>
