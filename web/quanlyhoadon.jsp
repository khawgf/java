<%-- 
    Document   : quanlyhoadon
    Created on : Oct 14, 2023, 2:00:06 PM
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
      <div class="apps" onload="hideAdminBar();">
        <div id="preLoader"></div>

             <jsp:include page="headeradmin.jsp" />  

        <div class="apps__container">

            <div class="apps__content">
                
                                  <div class="btn_back">
            <a href="adminhome.jsp">Quay về trang chủ</a>
        </div>
                
                          <div class="apps__content-header">
                    <div class="apps__content-heading">
                        <p class="apps__content-heading-content">Danh sách</p>
                    </div>
                              
                </div>
        
                 .<div class="hoadon_tool">
                     <form action="quanlyhoadonController" method="get" id="datetime_form">
                       <input type="hidden" placeholder="Search something........." value="${param.searchUser}" class="search-input" id="txtsearch" name="searchUser">
        <input type="datetime-local" id="datetime" name="datetimeStart" >
        <input type="datetime-local" id="datetime" name="datetimeEnd">
        <button class="header__search-btn_datime_order" type="submit">
                    <i class="search-btn fas fa-search icon-search"></i>
                </button>
    </form>
        
                   <form action="quanlyhoadonController" method="get" id="searchForm">
                 <input type="text" placeholder="Search something........." class="search-input" id="txtsearch" name="searchUser">
                        <input type="hidden" id="datetime" name="datetimeStart" value="${param.datetimeStart}" >
                        <input type="hidden" id="datetime" name="datetimeEnd" value="${param.datetimeEnd}">
                 <button class="header__search-btn" type="submit">
                    <i class="search-btn fas fa-search icon-search"></i>
                </button>
            </form>
        
        <div class="apps__content-export">
                <button id="exportButton"><i class="fas fa-cart-plus"></i> Export</button>
            </div>
                 </div>
        
                <div class="apps__content-container">
                    <div class="apps__content-container-title hide-on-mobile">
                       <div class="apps__content-container-title hide-on-mobile"> 
                        <div class="apps__content-title">
                            <div class="apps__content-title-id">
                                <p>ID</p>
                            </div>

                            <div class="apps__content-title-user">
                                <p>Số điện thoại</p>
                            </div>

                            <div class="apps__content-title-name">
                                <p>Trạng thái</p>
                            </div>


                            <div class="apps__content-title-prices">
                                <p>Hóa đơn</p>
                            </div>

                            <div class="apps__content-title-tools">
                               <p>Công cụ</p>
                            </div>
                        </div></div>
                        
                    </div>

                    <div class="apps__content-container-show hide-on-mobile-admin">
                    <c:forEach items="${hoadonList}" var="hoadon">
                        <div class="apps__content-view">
                            <div class="apps__content-view-id">
                                <p>${hoadon.id}</p>
                            </div>

                            <div class="apps__content-view-user">
                                <p>${hoadon.phone}</p>
                            </div>

                            <div class="apps__content-view-name">
                                <p>${hoadon.status}</p>
                            </div>
                            <div class="apps__content-view-prices">
                                <a href="cthdController?id=${hoadon.id}">
                                    <button >Xem chi tiết</button> 
                                </a>
                            </div>
                            <div class="apps__content-view-tools">
                                <c:if test="${hoadon.status ne 'Đã giao'}">
                                <div class="apps__content-view-tools-update" onclick="showModalUpdate(${hoadon.id},'${hoadon.phone}','${hoadon.address}')">
                                    <i class="fas fa-pen"></i>
                                </div>
                            </c:if>
                                <div class="apps__content-view-tools-delete" onclick="confirmDelete(${hoadon.id});">
                                    <i class="fas fa-trash-alt"></i>
                                </div>
                            </div>
                        </div>
                                        </c:forEach>
                    </div>
                                       
                    
                                 <div class="pagination">
                <div class="pagination__list">
                    <!-- Liên kết đến trang chính -->
                    <a href="quanlyhoadonController" class="pagination__list-link">
                        <div class="pagination__list-item">Trang chủ</div>
                    </a>

                    <!-- Liên kết trang trước -->
                    <c:if test="${currentPage > 1}">
                        <a href="quanlyhoadonController?page=${currentPage - 1}&searchUser=${param.searchUser}&datetimeStart=${param.datetimeStart}&datetimeEnd=${param.datetimeEnd}" id="prePage" class="pagination__list-link">
                            <div class="pagination__list-item">Trước</div>
                        </a>
                    </c:if>

                    <!-- Liên kết số trang -->
                    <c:forEach begin="1" end="${totalPages}" varStatus="pageVar">
                        <c:choose>
                            <c:when test="${pageVar.index == currentPage}">
                                <div class="pagination__list-link">
                                    <div class="pagination__list-item active">${pageVar.index}</div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a href="quanlyhoadonController?page=${pageVar.index}&searchUser=${param.searchUser}&datetimeStart=${param.datetimeStart}&datetimeEnd=${param.datetimeEnd}" class="pagination__list-link">
                                    <div class="pagination__list-item">${pageVar.index}</div>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <!-- Liên kết trang tiếp theo -->
                    <c:if test="${currentPage < totalPages}">
                        <a href="quanlyhoadonController?page=${currentPage + 1}&searchUser=${param.searchUser}&datetimeStart=${param.datetimeStart}&datetimeEnd=${param.datetimeEnd}" id="nextPage" class="pagination__list-link">
                            <div class="pagination__list-item">Tiếp theo</div>
                        </a>
                    </c:if>
                </div>
            </div>
                    
            <form action="quanlyhoadonController" method="post">
                <input type="hidden" name="action" value="update">
                     <div class="modal__add-product" style="display: none;">
            <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>
            <div class="modal__add-product-content">
                <div class="modal__add-product-header">
                    <p class="modal__add-product-header-heading">Cập nhật hóa đơn</p>
                </div>
                <div class="modal__add-Order-id"> <input type="text" value="" readonly="" name="txtadminid" id="txtadminid"></div>
                  <div class="modal__add-Order-phone"> <input type="text" placeholder="Số điện thoại"  value="" name="txtOrderPhone" id="txtOrderPhone"></div>
                <div class="modal__add-Order-adress"> <input type="text" placeholder="Địa chỉ" value="" name="txtOrderAddress" id="txtOrderAddress"></div>

                 <div class="modal__add-NCC-activity " style="display: block;"><select name="selectOrderActivity" id="selectOrderActivity">
                                <c:forEach items="${orderStatusList}" var="orderStatus">
                                    <option value="${orderStatus.id}">${orderStatus.name}</option>
                                </c:forEach>
                    </select>
                </div>
                <div class="modal__add-product-btn" style="display: block;" id="btnadminupdateOrder">
                    <button>Cập nhật</button>
                </div>

                <div class="modal__add-product-btn-close" onclick="hideModalAdd();">
                    <i class="fas fa-window-close"></i>
                </div>
            </div>
        </div>
            </form>
                </div>
                </div>
            </div>
          </div>
                  
                    <%
            String successMessage = (String) request.getAttribute("successMessage");
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (successMessage != null) {
            %>
            <script>
                alert("<%= successMessage %>");
                window.location.href = window.location.href; // Tải lại trang hiện tại
            </script>
            <%
            } else if (errorMessage != null) {
            %>
            <script>
                alert("<%= errorMessage %>");
                 window.location.href = window.location.href; 
            </script>
            <%
            }
            %>
                      <script>
            
           function hideModalAdd()
       {
           document.querySelector('.modal__add-product').style.display = "none";

       }  
                    function showModalUpdate(id,phone,adress)
    {
        document.querySelector('.modal__add-product').style.display = "block";
           document.getElementById("txtadminid").value = id;
            document.getElementById("txtOrderPhone").value = phone;
                        document.getElementById("txtOrderAddress").value = adress;
    }  
                    function confirmDelete(id) {
                            var confirmDelete = confirm("Bạn có chắc chắn muốn xóa đơn hàng này?");
                            if (confirmDelete) {
                                deleteOrder(id);
                                }
                            }

                            function deleteOrder(id) {
                                            var xhr = new XMLHttpRequest();
                                            xhr.open("POST", "quanlyhoadonController", true);
                                            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                            var data = "action=delete&id=" + id;

                                            xhr.onreadystatechange = function () {
                                                if (xhr.readyState === 4) {
                                                    if (xhr.status === 200) {
                                                        alert("Hóa đơn đã được xóa thành công.");
                                                        // Cập nhật giao diện hoặc thực hiện bất kỳ thay đổi nào khác tùy theo yêu cầu.
                                                        location.reload();
                                                    } else if (xhr.status === 500) {
                                                        alert("Lỗi server: Không thể xóa hóa đơn.");
                                                    }
                                                }
                                            };

                                            xhr.send(data);
                                        }
                                        
//                                          function showcthd(id) {
//                                            var xhr = new XMLHttpRequest();
//                                            xhr.open("POST", "quanlyhoadonController", true);
//                                            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//                                            var data = "action=show&id=" + id;
//
//                                            xhr.onreadystatechange = function () {
//                                                if (xhr.readyState === 4) {
//                                                    if (xhr.status === 200) {
//                                                        location.reload();
//                                                    } else if (xhr.status === 500) {
//                                                    }
//                                                }
//                                            };
//
//                                            xhr.send(data);
//                                        }

    document.getElementById("exportButton").addEventListener("click", function () {
        // Gọi Servlet và yêu cầu xuất PDF
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "quanlyhoadonController?exportPDF=true&searchUser=${param.searchUser}&datetimeStart=${param.datetimeStart}&datetimeEnd=${param.datetimeEnd}");
        xhr.responseType = "blob"; // Để xử lý tệp PDF

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Tạo một liên kết để tải xuống tệp PDF
                var blob = xhr.response;
                var link = document.createElement("a");
                link.href = window.URL.createObjectURL(blob);
                link.download = "thongkehoadon_${param.datetimeStart}-${param.datetimeEnd}.pdf";
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
