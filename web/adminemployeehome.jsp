<%-- 
    Document   : admincategory
    Created on : Oct 21, 2023, 1:45:32 PM
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
                <a href="quanlyspcController" id="managerProduct"><i class="fab fa-product-hunt"></i> <span>Quản lý sản phẩm</span></a>
                <a href="quanlynccController" id="managerNCC"><i class="fas fa-handshake"></i> <span>Quản lý nhà cung cấp</span></a>
                <a href="quanlybrandController" id="managerTypeProduct"><i class="fas fa-layer-group"></i> <span>Quản lý loại sản phẩm</span></a>
            </div>
            <div class="row">
                <a href="quanlyhoadonController" id="managerOrder"><i class="fas fa-cart-arrow-down"></i> <span>Quản lý hóa đơn</span></a>
                <a href="quanlymggController" id="managerDiscount"><i class="fas fa-ticket-alt"></i> <span>Quản lý mã giảm giá</span></a>
                <a href="adminthongkehome.jsp" id="managerStatistic"><i class="fas fa-chart-bar"></i> <span>Thống kê doanh thu</span></a>
            </div>
        </div>
    </div>
    
    </div>
         <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    </body>
</html>
