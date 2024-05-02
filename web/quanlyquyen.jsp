<%-- 
    Document   : quanlyquyen
    Created on : Oct 14, 2023, 2:09:22 PM
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
                                               
                    <form action="quanlyQuyenController" method="get" id="searchForm">
                 <input type="text" placeholder="Search something........." class="search-input" id="txtsearch" name="searchPermission">
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

                            <div class="apps__content-title-name">
                                <p>Tên</p>
                            </div>


                            <div class="apps__content-title-prices">
                                <p>Trạng thái</p>
                            </div>

                            <div class="apps__content-title-tools">
                               <p>Công cụ</p>
                            </div>
                        </div></div>
                        
                    </div>

                    <div class="apps__content-container-show hide-on-mobile-admin">
                    <c:forEach items="${quyenList}" var="quyen">
                        <div class="apps__content-view">
                            <div class="apps__content-view-id">
                                <p>${quyen.id}</p>
                            </div>

                            <div class="apps__content-view-name">
                                <p>${quyen.name}</p>
                            </div>

                            <div class="apps__content-view-prices">
                               <p>${quyen.status}</p>
                            </div>

                            <div class="apps__content-view-tools">
                                <div class="apps__content-view-tools-update" onclick="showModalUpdate(${quyen.id},'${quyen.name}')">
                                    <i class="fas fa-pen"></i>
                                </div>
                                <div class="apps__content-view-tools-delete" onclick="confirmDelete(${quyen.id});">
                                    <i class="fas fa-trash-alt"></i>
                                </div>
                            </div>
                        </div>
                           </c:forEach>
                    </div>
                                       
                                     <div class="pagination">
                <div class="pagination__list">
                    <!-- Liên kết đến trang chính -->
                    <a href="quanlyQuyenController" class="pagination__list-link">
                        <div class="pagination__list-item">Trang chủ</div>
                    </a>

                    <!-- Liên kết trang trước -->
                    <c:if test="${currentPage > 1}">
                        <a href="quanlyQuyenController?page=${currentPage - 1}&searchPermission=${param.searchPermission}" id="prePage" class="pagination__list-link">
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
                                <a href="quanlyQuyenController?page=${pageVar.index}&searchPermission=${param.searchPermission}" class="pagination__list-link">
                                    <div class="pagination__list-item">${pageVar.index}</div>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <!-- Liên kết trang tiếp theo -->
                    <c:if test="${currentPage < totalPages}">
                        <a href="quanlyQuyenController?page=${currentPage + 1}&searchPermission=${param.searchPermission}" id="nextPage" class="pagination__list-link">
                            <div class="pagination__list-item">Tiếp theo</div>
                        </a>
                    </c:if>
                </div>
            </div>
                    
        <form action="quanlyQuyenController" method="post">
        <input type="hidden" name="action" value="add">
       <div class="modal__add-product" style="display: none;">
            <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>

            <div class="modal__add-product-content">
                
                <div class="modal__add-product-header">
                    <p class="modal__add-product-header-heading">Thêm mới quyền</p>
                </div>

                <div class="modal__add-Permission-id"> <input type="text" name="txtquyenID" value="${maxId}" readonly="" id="txtadminid"></div>
                <div class="modal__add-Permission-name"> <input type="text" name="txtquyenName" placeholder="Tên quyền" value="" id="txtPermissionName"></div>                
                <div class="modal__add-product-btn" id="btnadminaddPermission" style="display: block;"><button >Thêm mới</button></div>
                <div class="modal__add-product-btn-close" onclick="hideModalAdd();">
                    <i class="fas fa-window-close"></i>
                </div>
            </div>
        </div>
                 </form>
                    
                <form action="quanlyQuyenController" method="post">
        <input type="hidden" name="action" value="update">
       <div class="modal__add-product update_Permission" style="display: none;">
            <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>

            <div class="modal__add-product-content">
                
                <div class="modal__add-product-header">
                    <p class="modal__add-product-header-heading">Cập nhật quyền</p>
                </div>

                <div class="modal__add-Permission-id"> <input type="text" name="txtquyenIDUpdate" value="${maxId}" readonly="" id="txtadminidupdate"></div>
                <div class="modal__add-Permission-name"> <input type="text" name="txtquyenNameUpdate" placeholder="Tên quyền" value="" id="txtPermissionNameupdate"></div>

                 <div class="modal__add-Permission-activity "><select name="selecttypePActivity" id="selecttypePActivity">
                                <c:forEach items="${quyenStatusList}" var="quyenStatus">
                                    <option value="${quyenStatus.id}">${quyenStatus.name}</option>
                                </c:forEach>>
                    </select>
                </div>
                
                <div class="modal__add-product-btn" id="btnadminaddPermission" style="display: block;"><button >Cập nhật</button></div>
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
            
        function showModalAdd()
       {
           document.querySelector('.modal__add-product').style.display = "block";
    }  
           function hideModalAdd()
       {
           document.querySelector('.modal__add-product').style.display = "none";
                 document.querySelector('.update_Permission').style.display = "none";

       }  
                    function showModalUpdate(id,name)
    {
        document.querySelector('.update_Permission').style.display = "block";
           document.getElementById("txtadminidupdate").value = id;
            document.getElementById("txtPermissionNameupdate").value = name;
    }  

                        function confirmDelete(id) {
                            var confirmDelete = confirm("Bạn có chắc chắn muốn xóa quyền này?"+id);
                            if (confirmDelete) {
                                deletePermission(id);
                                }
                            }

                            function deletePermission(id) {
                                            var xhr = new XMLHttpRequest();
                                            xhr.open("POST", "quanlyQuyenController", true);
                                            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                            var data = "action=delete&id=" + id;

                                            xhr.onreadystatechange = function () {
                                                if (xhr.readyState === 4) {
                                                    if (xhr.status === 200) {
                                                        alert("Quyền đã được khóa thành công.");
                                                        // Cập nhật giao diện hoặc thực hiện bất kỳ thay đổi nào khác tùy theo yêu cầu.
                                                        location.reload();
                                                    } else if (xhr.status === 500) {
                                                        alert("Lỗi server: Không thể xóa quyền.");
                                                    }
                                                }
                                            };

                                            xhr.send(data);
                                        }
            </script>
    </body>
</html>
