<%-- 
    Document   : thongkedoanhthu
    Created on : Oct 31, 2023, 10:09:55 AM
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
                <p class="apps__content-heading-content">Thống kê doanh thu</p>
            </div>
         
            <div class="apps__content-add-product">
                <button id="exportButton"><i class="fas fa-cart-plus"></i> Export</button>
            </div>
        </div>
                
                
                       <form action="thongkedoanhthuController" method="get" id="datetime_form">
        <input type="datetime-local" id="datetime" name="datetimeStart">
        <input type="datetime-local" id="datetime" name="datetimeEnd">
        <button class="header__search-btn_datetime" type="submit">
                    <i class="search-btn fas fa-search icon-search"></i>
                </button>
    </form>
                
                <div class="apps__content-container">
                    <div class="apps__content-container-titleTKSP hide-on-mobile"> <div class="apps__content-title">
                    <div class="apps__content-titleTKDT-nhaphang">
                            <p>Số tiền nhập hàng</p>
                        </div>

                        <div class="apps__content-titleTKND-banhang">
                           <p>Số tiền bán ra</p>
                        </div>
                            
                             <div class="apps__content-titleTKND-equal">
                           <p>Tổng kết</p>
                        </div> 

</div>
                    </div>

                    <div class="apps__content-container-show hide-on-mobile-admin">
                        <c:forEach items="${thongkedtList}" var="thongkedt">
                        <div class="apps__content-view">
                            <div class="apps__content-viewTKDT-nhaphang">
                            <p>${thongkedt.total_nhap}</p>
                        </div>

                            <div class="apps__content-viewTKDT-banhang">
                                <p>${thongkedt.total_ban}</p>
                            </div>
                            
                            <div class="apps__content-viewTKDT-equal">
                                <p>${thongkedt.total_ketqua}</p>
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
        xhr.open("GET", "thongkedoanhthuController?exportPDF=true&datetimeStart=${param.datetimeStart}&datetimeEnd=${param.datetimeEnd}");
        xhr.responseType = "blob"; // Để xử lý tệp PDF

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Tạo một liên kết để tải xuống tệp PDF
                var blob = xhr.response;
                var link = document.createElement("a");
                link.href = window.URL.createObjectURL(blob);
                link.download = "thongkedoanhthu_${param.datetimeStart}-${param.datetimeEnd}.pdf";
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
