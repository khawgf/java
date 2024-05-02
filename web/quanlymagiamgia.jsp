<%-- 
    Document   : quanlymagiamgia
    Created on : Oct 14, 2023, 2:12:57 PM
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

            <div class="apps__content" onclick="hideAdminBar();">
                
                                  <div class="btn_back">
            <a href="adminhome.jsp">Quay về trang chủ</a>
        </div>
                
         <div class="apps__content-header">
                    <div class="apps__content-heading">
                        <p class="apps__content-heading-content">Danh sách</p>
                    </div>
             
                    <form action="quanlymggController" method="get" id="searchForm">
                       <input type="text" placeholder="Search something........." class="search-input" id="txtsearch" name="searchDiscount">
                      <button class="header__search-btn" type="submit">
                          <i class="search-btn fas fa-search icon-search"></i>
                      </button>
                  </form>
    
                    <div class="apps__content-add-product" onclick="showModalAdd();">
                        <button><i class="fas fa-cart-plus"></i> Thêm mới</button>
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
                                <p>Discount(%)</p>
                            </div>

                            <div class="apps__content-title-name">
                                <p>Mã giảm</p>
                            </div>


                            <div class="apps__content-title-prices">
                                <p>Áp dụng</p>
                            </div>

                            <div class="apps__content-title-tools">
                               <p>Công cụ</p>
                            </div>
                        </div></div>
                        
                    </div>

                    <div class="apps__content-container-show hide-on-mobile-admin">
                    <c:forEach items="${mggList}" var="mgg">
                        <div class="apps__content-view">
                            <div class="apps__content-view-id">
                                <p>${mgg.id}</p>
                            </div>

                            <div class="apps__content-view-user">
                                <p>${mgg.discount}</p>
                            </div>

                            <div class="apps__content-view-name">
                                <p>${mgg.code}</p>
                            </div>


                            <div class="apps__content-view-prices" onclick="showModalAddDiscountToProduct()">
                               <button>Áp dụng mã</button>
                            </div>

                            <div class="apps__content-view-tools">
                                <div class="apps__content-view-tools-update" onclick="showModalUpdate(${mgg.id},'${mgg.discount}','${mgg.code}')">
                                    <i class="fas fa-pen"></i>
                                </div>

                                <div class="apps__content-view-tools-delete" onclick="confirmDelete(${mgg.id})">
                                    <i class="fas fa-trash-alt"></i>
                                </div>
                            </div>
                        </div>
                             </c:forEach>
                    </div>
                                       
                    
                          <div class="pagination">
                <div class="pagination__list">
                    <!-- Liên kết đến trang chính -->
                    <a href="quanlymggController" class="pagination__list-link">
                        <div class="pagination__list-item">Trang chủ</div>
                    </a>

                    <!-- Liên kết trang trước -->
                    <c:if test="${currentPage > 1}">
                        <a href="quanlymggController?page=${currentPage - 1}&searchDiscount=${param.searchDiscount}" id="prePage" class="pagination__list-link">
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
                                <a href="quanlymggController?page=${pageVar.index}&searchDiscount=${param.searchDiscount}" class="pagination__list-link">
                                    <div class="pagination__list-item">${pageVar.index}</div>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <!-- Liên kết trang tiếp theo -->
                    <c:if test="${currentPage < totalPages}">
                        <a href="quanlymggController?page=${currentPage + 1}&searchDiscount=${param.searchDiscount}" id="nextPage" class="pagination__list-link">
                            <div class="pagination__list-item">Tiếp theo</div>
                        </a>
                    </c:if>
                </div>
            </div>
                    
                    
        <form action="quanlymggController" method="post">
        <input type="hidden" name="action" value="add">
       <div class="modal__add-product" style="display: none;">
            <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>

                    <div class="modal__add-product-content">
                
                <div class="modal__add-product-header">
                    <p class="modal__add-product-header-heading">Thêm mới mã giảm giá</p>
                </div>

                <div class="modal__add-discount-id"> <input type="text" value="${maxId}" readonly="" name="txtmggID" id="txtadminid"></div>

                <div class="modal__add-discount-percent">
                    <select id="selectDiscountPercent" name="discountPercent">
                        <option value="">Chọn % giảm giá</option>
                        <!-- Sử dụng JavaScript để tạo các tùy chọn từ 1 đến 100 -->
                    </select>
                </div>

                <div class="modal__add-discount-code">
                    <input type="text" placeholder="Mã code" value="" readonly="" name="txtDiscountCode" id="txtDiscountCode">
                </div>
                
                <div class="modal__add-product-btn" id="btnaddDiscount" style="display: block;"><button >Thêm mới</button></div>

                <div class="modal__add-product-btn-close" onclick="hideModalAdd();">
                    <i class="fas fa-window-close"></i>
                </div>
            </div>
        </div>
                 </form>
                
      <form action="quanlymggController" method="post">
        <input type="hidden" name="action" value="update">
       <div class="modal__add-product update_MGG" style="display: none;">
            <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>

             <div class="modal__add-product-content">
                
                <div class="modal__add-product-header">
                    <p class="modal__add-product-header-heading">Câp nhật mã giảm giá</p>
                </div>

                <div class="modal__add-discount-id"> <input type="text" value="" readonly="" name="txtmggUpdate" id="txtadminidUpdate"></div>

               <div class="modal__add-discount-percent">
                    <select name="updateSelectPercent" id="updateSelectPercent">
                        <option value="">Chọn % giảm giá</option>
                        <!-- Sử dụng JavaScript để tạo các tùy chọn từ 1 đến 100 -->
                    </select>
                </div>

                <div class="modal__add-discount-code">
                    <input type="text" placeholder="Mã code" value="" readonly="" name="txtDiscountCodeUpdate" id="txtDiscountCodeUpdate">
                </div>
                
                <div class="modal__add-product-btn" id="btnupdateDiscount" style="display: block;"><button >Cập nhật</button></div>

                <div class="modal__add-product-btn-close" onclick="hideModalAdd();">
                    <i class="fas fa-window-close"></i>
                </div>
            </div>
        </div>
                 </form>
                    
        <form action="quanlymggController" method="post">
        <input type="hidden" name="action" value="addDiscountToProduct">
        <div class="modal__add-productDiscount" style="display: none;">
            <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>
            <div class="modal__add-productDiscount-content">
                <div class="modal__add-productDiscount-header">
                    <p class="modal__add-productDiscount-header-heading">Thêm mã giảm giá vào sản phẩm</p>
                </div>
                 <div class="modal__add-Product-Discount-id"> <input type="text" value="${maxIdCTMGG}" readonly="" name="txtPDiscountid" id="txtPDiscountid"></div>
                <div class="modal__add-Product-Discount ">
                    <select name="selectProduct_discount" id="selectProduct_discount">
                        <option value="">Chọn sản phẩm</option>
                         <c:forEach items="${spcList}" var="spc">
                               <option value="${spc.id}">${spc.name}</option>
                        </c:forEach>
                    </select>
                </div>
                   <div class="modal__add-Discount-Product ">
                    <select name="selectDiscount_product" id="selectDiscount_product">
                        <option value="">Chọn mã giảm giá</option>
                    <c:forEach items="${mggcbbList}" var="mgg">
                        <option value="${mgg.id}">${mgg.discount}</option>
                    </c:forEach>
                    </select>
                </div>
                <div class="modal__add-product-btn" id="btnaddDiscountProduct" style="display: block;"><button onclick="actionAddProduct();">Thêm mới</button></div>
                <div class="modal__add-product-btn" style="display: none;" id="btnupdateDiscountProduct">
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
            
        function showModalAddDiscountToProduct()
       {
           document.querySelector('.modal__add-productDiscount').style.display = "block";
    }  
    
            function showModalAdd()
       {
           document.querySelector('.modal__add-product').style.display = "block";
    }  
           function hideModalAdd()
       {
           document.querySelector('.modal__add-product').style.display = "none";
           document.querySelector('.modal__add-productDiscount').style.display = "none";
           document.querySelector('.update_MGG').style.display = "none";
       }  
                    function showModalUpdate(id,discount,code)
    {
        document.querySelector('.update_MGG').style.display = "block";
           document.getElementById("txtadminidUpdate").value = id;
            document.getElementById("updateSelectPercent").value = discount;
            document.getElementById("txtDiscountCodeUpdate").value = code;
    }  

    // JavaScript code
            document.addEventListener("DOMContentLoaded", function () {
                var selectElement = document.getElementById("selectDiscountPercent");
                var codeInput = document.getElementById("txtDiscountCode");

                // Tạo tùy chọn từ 1 đến 100
                for (var i = 1; i <= 70; i++) {
                    var option = document.createElement("option");
                    option.value = i;
                    option.text = i + "% giảm giá";
                    selectElement.appendChild(option);
                }

                // Xử lý sự kiện onchange của dropdown
                selectElement.addEventListener("change", function () {
                    var selectedPercent = selectElement.value;
                    if (selectedPercent !== "") {
                        codeInput.value = "MAGIAM" + selectedPercent;
                    } else {
                        codeInput.value = "";
                    }
                });
            });

            document.addEventListener("DOMContentLoaded", function () {
                var selectElement = document.getElementById("updateSelectPercent");
                var codeInput = document.getElementById("txtDiscountCodeUpdate");

                // Tạo tùy chọn từ 1 đến 100
                for (var i = 1; i <= 70; i++) {
                    var option = document.createElement("option");
                    option.value = i;
                    option.text = i + "% giảm giá";
                    selectElement.appendChild(option);
                }

                // Xử lý sự kiện onchange của dropdown
                selectElement.addEventListener("change", function () {
                    var selectedPercent = selectElement.value;
                    if (selectedPercent !== "") {
                        codeInput.value = "MAGIAM" + selectedPercent;
                    } else {
                        codeInput.value = "";
                    }
                });
            });
            
                             function confirmDelete(id) {
                               var confirmDelete = confirm("Bạn có chắc chắn muốn xóa mã giảm giá này?"+id);
                               if (confirmDelete) {
                                   deleteMGG(id);
                                   }
                               }

                            function deleteMGG(id) {
                                            var xhr = new XMLHttpRequest();
                                            xhr.open("POST", "quanlymggController", true);
                                            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                            var data = "action=delete&id=" + id;

                                            xhr.onreadystatechange = function () {
                                                if (xhr.readyState === 4) {
                                                    if (xhr.status === 200) {
                                                        alert("Mã giảm giá đã được xóa thành công.");
                                                        // Cập nhật giao diện hoặc thực hiện bất kỳ thay đổi nào khác tùy theo yêu cầu.
                                                        location.reload();
                                                    } else if (xhr.status === 500) {
                                                        alert("Lỗi server: Không thể xóa mã giảm giá.");
                                                    }
                                                }
                                            };

                                            xhr.send(data);
                                        }
            
            </script>       
                        
    </body>
</html>
