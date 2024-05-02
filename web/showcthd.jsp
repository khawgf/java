<%-- 
    Document   : showcthd
    Created on : Oct 25, 2023, 10:10:39 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" href="fontawesome-free-5.15.4-web/fontawesome-free-5.15.4-web/css/all.min.css">
    </head>
    <body>
   <div class="apps" >
        <div id="preLoader"></div>

                 <jsp:include page="headeradmin.jsp" />  

        <div class="apps__container">

          <div class="apps__content">
               <div class="apps__content-headerCTHD">
            <div class="apps__content-heading">
                <a href="adminhome.jsp">Quay lại trang chủ</a>
            </div>
         
            <div class="apps__content-add-product">
                <button id="exportButton"><i class="fas fa-cart-plus"></i> Export</button>
            </div>
        </div>
              
    <div class="apps__content-header">
        <div class="apps__content-headingCTHDLeft">
            <p class="apps__content-heading-content">Số điện thoại: ${cthdList[0].phoneKH}</p>
        </div>

        <div class="apps__content-headingCTHDLeft">
            <p class="apps__content-heading-content">Address: ${cthdList[0].address}</p>
        </div>
    </div>

    <div class="apps__content-header">
        <div class="apps__content-headingCTHDLeft">
            <p class="apps__content-heading-content">Tên khách hàng: ${cthdList[0].namekh}</p>
        </div>
        
        <div class="apps__content-headingCTHDLeft">
            <p class="apps__content-heading-content">Tổng giá trị đơn hàng: 
        <c:choose>
            <c:when test="${not empty cthdList}">
                <c:set var="totalPrice" value="0" scope="page"/>
                <c:forEach items="${cthdList}" var="cthd">
                    <c:set var="totalPrice" value="${totalPrice + (cthd.price * cthd.quantity)}" scope="page"/>
                </c:forEach>
                ${totalPrice}
            </c:when>
            <c:otherwise>
                0
            </c:otherwise>
        </c:choose>
        </p>
        </div>
    </div>
</div>
        </div>


        <div class="apps__content-container">
            <div class="apps__content-container-title hide-on-mobile">
                <div class="apps__content-container-title hide-on-mobile"> 
                    <div class="apps__content-title">

                        <div class="apps__content-title-user">
                            <p>Tên sản phẩm</p>
                        </div>

                        <div class="apps__content-title-img">
                            <p>Ảnh</p>
                        </div>
                        
                        <div class="apps__content-title-name">
                            <p>Số lượng</p>
                        </div>

                        <div class="apps__content-title-prices">
                            <p>Đơn giá</p>
                        </div>

                  <div class="apps__content-title-prices">
                            <p>Thành tiền</p>
                        </div>
                    </div></div>

            </div>

            <div class="apps__content-container-show hide-on-mobile-admin">
                <c:forEach items="${cthdList}" var="cthd">
                    <div class="apps__content-view">

                        <div class="apps__content-view-name">
                            <p>${cthd.namesp}</p>
                        </div>
                        
                        
                        <div class="apps__content-view-img">
                               <img src="${cthd.img}" alt="alt"/>
                        </div>
                        
                    <div class="apps__content-view-user">
                         <p>${cthd.quantity}</p>
                        </div>
                        
                        <div class="apps__content-view-prices">
                            <p>${cthd.price}</p>
                        </div>
                          <div class="apps__content-view-prices">
                            <p>${cthd.price * cthd.quantity}</p>
                        </div>
                       
                    </div>
                </c:forEach>
            </div>
</div>
        </div>
                
        <script>
               document.getElementById("exportButton").addEventListener("click", function () {
                   // Gọi Servlet và yêu cầu xuất PDF
                   var xhr = new XMLHttpRequest();
                   xhr.open("GET", "cthdController?id=${param.id}&exportPDF=true");
                   xhr.responseType = "blob"; // Để xử lý tệp PDF

                   xhr.onload = function () {
                       if (xhr.status === 200) {
                           // Tạo một liên kết để tải xuống tệp PDF
                           var blob = xhr.response;
                           var link = document.createElement("a");
                           link.href = window.URL.createObjectURL(blob);
                           link.download = "cthduser.pdf";
                           link.style.display = "none";
                           document.body.appendChild(link);
                           link.click();
                           document.body.removeChild(link);
                       }
                   };

                   xhr.send();
               });
           </script>

    </body>
</html>
