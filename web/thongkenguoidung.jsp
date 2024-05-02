<%-- 
    Document   : thongkenguoidung
    Created on : Oct 30, 2023, 8:42:20 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" href="fontawesome-free-5.15.4-web/fontawesome-free-5.15.4-web/css/all.min.css">
        <title>JSP Page</title>
    </head>
 <body>
          <div class="apps" >

                  <jsp:include page="headeradmin.jsp" />  

        <div class="apps__container">

            <div class="apps__content" >
                
                                  <div class="btn_back">
            <a href="adminhome.jsp">Quay về trang chủ</a>
        </div>
                
     <div class="apps__content-header">
            <div class="apps__content-heading">
                <p class="apps__content-heading-content">Thống kê người dùng</p>
            </div>
         
            <div class="apps__content-add-product" onclick="showModalAdd();">
                <button id="exportButton"><i class="fas fa-cart-plus"></i> Export</button>
            </div>
        </div>
                
                
                       <form id="datetime_form">
        <input type="datetime-local" id="datetime" name="datetimeStart">
        <input type="datetime-local" id="datetime" name="datetimeEnd">
        <button class="header__search-btn_datetime" type="submit">
                    <i class="search-btn fas fa-search icon-search"></i>
                </button>
    </form>
                
                <div class="apps__content-container">
                    <div class="apps__content-container-titleTKSP hide-on-mobile"> <div class="apps__content-title">
                    <div class="apps__content-titleTKND-phone">
                            <p>Số điện thoại</p>
                        </div>

                        <div class="apps__content-titleTKND-name">
                           <p>Tên người dùng</p>
                        </div>

                        <div class="apps__content-titleTKND-orderquantity">
                            <p>Số đơn đặt hàng</p>
                        </div>
                            
                               <div class="apps__content-titleTKND-total">
                            <p>Tổng tiền</p>
                        </div>

</div></div>

                    <div class="apps__content-container-show hide-on-mobile-admin">
                      <c:forEach items="${thongkeNDList}" var="thongkend">
                        <div class="apps__content-view">
                            <div class="apps__content-viewTKND-phoneND">
                            <p>${thongkend.phoneUser}</p>
                        </div>

                            <div class="apps__content-viewTKND-nameND">
                                <p>${thongkend.nameUser}</p><p>
                            </p></div>

                            <div class="app__content-viewTKND-totalOrder">
                                    <p>${thongkend.totalOrder}</p>
                                </div>
                            
                            <div class="apps__content-viewTKND-totalPrices">
                               <p>${thongkend.totalCash}</p>
                            </div>
                            
                        </div> 
                 </c:forEach>
                                </div>
                    
                      
                </div>
        </div>
        </div>
        </div>
                
            <script>
               document.getElementById("exportButton").addEventListener("click", function () {
                   // Gọi Servlet và yêu cầu xuất PDF
                   var xhr = new XMLHttpRequest();
                   xhr.open("GET", "thongkeuserController?exportPDF=true&datetimeStart=${param.datetimeStart}&datetimeEnd=${param.datetimeEnd}");
                   xhr.responseType = "blob"; // Để xử lý tệp PDF

                   xhr.onload = function () {
                       if (xhr.status === 200) {
                           // Tạo một liên kết để tải xuống tệp PDF
                           var blob = xhr.response;
                           var link = document.createElement("a");
                           link.href = window.URL.createObjectURL(blob);
                           link.download = "thongkeuser_${param.datetimeStart}-${param.datetimeEnd}.pdf";
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
