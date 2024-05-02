<%-- 
    Document   : quanlynguoidung
    Created on : Oct 12, 2023, 9:53:34 PM
    Author     : DELL
--%>

<%@page import="java.util.List"%>
<%@page import="dao.quanlyuserDAO"%>
<%@page import="controller.quanlyuserController"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                <p class="apps__content-heading-content">Danh sách</p>
            </div>
         
            <form action="quanlyuserController" method="get" id="searchForm">
                 <input type="text" placeholder="Search something........." class="search-input" id="txtsearch" name="searchUser">
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
                            <p>Số điện thoại</p>
                        </div>

                        <div class="apps__content-title-name">
                            <p>Tên</p>
                        </div>

                        <div class="apps__content-title-img">
                            <p>Loại</p>
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
                <c:forEach items="${userList}" var="user">
                    <div class="apps__content-view">
                        <div class="apps__content-view-id">
                            <p>${user.id}</p>
                        </div>
                        <div class="apps__content-view-user">
                            <p>${user.phone}</p>
                        </div>
                        <div class="apps__content-view-name">
                            <p>${user.name}</p>
                        </div>
                        
                        <div class="apps__content-view-img">
                            <p>${user.type}</p>
                        </div>
                        <div class="apps__content-view-prices">
                            <p>${user.status}</p>
                        </div>
                        <div class="apps__content-view-tools">
                            <div class="apps__content-view-tools-update" onclick="showModalUpdate(${user.id},'${user.phone}','${user.pass}','${user.name}');">
                                <i class="fas fa-pen"></i>
                            </div>
                            <div class="apps__content-view-tools-delete" onclick="confirmDelete(${user.id});">
                                <i class="fas fa-trash-alt"></i>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

                  <div class="pagination">
                <div class="pagination__list">
                    <!-- Liên kết đến trang chính -->
                    <a href="quanlyuserController" class="pagination__list-link">
                        <div class="pagination__list-item">Trang chủ</div>
                    </a>

                    <!-- Liên kết trang trước -->
                    <c:if test="${currentPage > 1}">
                        <a href="quanlyuserController?page=${currentPage - 1}&searchUser=${param.searchUser}" id="prePage" class="pagination__list-link">
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
                                <a href="quanlyuserController?page=${pageVar.index}&searchUser=${param.searchUser}" class="pagination__list-link">
                                    <div class="pagination__list-item">${pageVar.index}</div>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <!-- Liên kết trang tiếp theo -->
                    <c:if test="${currentPage < totalPages}">
                        <a href="quanlyuserController?page=${currentPage + 1}&searchUser=${param.searchUser}" id="nextPage" class="pagination__list-link">
                            <div class="pagination__list-item">Tiếp theo</div>
                        </a>
                    </c:if>
                </div>
            </div>

            
            <form action="quanlyuserController" method="post">
                <input type="hidden" name="action" value="add">
                <div class="modal__add-product" style="display: none;">
                    <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>

                    <div class="modal__add-product-content">

                        <div class="modal__add-product-header">
                            <p class="modal__add-product-header-heading">Thêm mới người dùng</p>
                        </div>

                        <div class="modal__add-User-id"> <input type="text" value="${maxId}" name="txtadminid" readonly="" id="txtadminid"></div>

                        <div class="modal__add-User-account"><input type="text" name="txtadminuser" id="txtadminuser" placeholder="Tài khoản..."></div>

                        
                        <div class="modal__add-User-pass"> <input type="text" name="txtadminpass" placeholder="Mật khẩu..." id="txtadminpass"></div>

                        <div class="modal__add-User-name"> <input type="text" placeholder="Tên của bạn" name="txtadminemail" id="txtadminemail"></div>

                        <div class="modal__add-User-activity">
                            <select name="status" id="selectUserActivity">
                                <c:forEach items="${userStatusList}" var="userStatus">
                                    <option value="${userStatus.id}">${userStatus.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="modal__add-User-type">
                            <select name="type" id="selectUserPermission">
                          <c:forEach items="${userTypeList}" var="userType">
                                <option value="${userType.id}">${userType.name}</option>
                        </c:forEach>
                            </select>
                        </div>
                        <div class="modal__add-product-btn" id="btnuserAdd" style="display: block;"><button type="submit">Thêm mới</button></div>
                        <div class="modal__add-product-btn-close" onclick="hideModalAdd();">
                            <i class="fas fa-window-close"></i>
                        </div>
                    </div>
                </div>
            </form>
                        
            <form id="updateUserForm" action="quanlyuserController" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="userId" id="hiddenUserId">
                <div class="modal__add-product UpdateModal" style="display: none;">
                    <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>

                    <div class="modal__add-product-content">

                        <div class="modal__add-product-header">
                            <p class="modal__add-product-header-heading">Cập nhật người dùng</p>
                        </div>
                        <div class="modal__add-User-id"> <input type="text" value="" name="txtadminidUpdate" readonly="" id="txtadminidupdate"></div>
                        <div class="modal__add-User-account"><input type="text" name="txtadminuserUpdate" value="" id="txtadminuserupdate" placeholder="Tài khoản..."></div>
                        <div class="modal__add-User-pass"> <input type="text" name="txtadminpassUpdate" value="" placeholder="Mật khẩu..." id="txtadminpassupdate"></div>
                        <div class="modal__add-User-name"> <input type="text" placeholder="Tên của bạn" value="" name="txtadminemailUpdate" id="txtadminemailupdate"></div>
                        <div class="modal__add-User-activity">
                            <select name="statusUpdate" id="selectUserActivity">
                                <c:forEach items="${userStatusList}" var="userStatus">
                                    <option id="selectUserActivityOP" value="${userStatus.id}">${userStatus.name}</option>
                                </c:forEach>
                            </select>
                        </div>


                        <div class="modal__add-User-type">
                            <select name="typeUpdate" id="selectUserPermission">
                          <c:forEach items="${userTypeList}" var="userType">
                                <option id="selectUserPermissionOP" value="${userType.id}">${userType.name}</option>
                        </c:forEach>
                            </select>


                        </div>

                        <div class="modal__add-product-btn" style="display: block;" id="btnuserupdate"><button type="submit">Cập nhật</button></div>

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
        </div>
        </div>
        </div>
        </div>
         <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script>

                function showModalAdd()
                {
                    document.querySelector('.modal__add-product').style.display = "block";
                    document.querySelector('.modal__add-product-header-heading').innerText = "Thêm người dùng mới";
                }
                function hideModalAdd()
                {
                    document.querySelector('.modal__add-product').style.display = "none";
                                     document.querySelector('.UpdateModal').style.display = "none";

                }
               function showModalUpdate(id,phone,pass,name) {
               document.querySelector('.UpdateModal').style.display = 'block';
            document.getElementById("txtadminidupdate").value = id;
            document.getElementById("txtadminuserupdate").value = phone;
            document.getElementById("txtadminpassupdate").value = pass;
            document.getElementById("txtadminemailupdate").value = name;

        }

                        function confirmDelete(id) {
                            var confirmDelete = confirm("Bạn có chắc chắn muốn xóa sản phẩm này?"+id);
                            if (confirmDelete) {
                                deleteUser(id);
                                }
                            }

                            function deleteUser(id) {
                                            var xhr = new XMLHttpRequest();
                                            xhr.open("POST", "quanlyuserController", true);
                                            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                            var data = "action=delete&id=" + id;

                                            xhr.onreadystatechange = function () {
                                                if (xhr.readyState === 4) {
                                                    if (xhr.status === 200) {
                                                        alert("Người dùng đã được khóa thành công.");
                                                        // Cập nhật giao diện hoặc thực hiện bất kỳ thay đổi nào khác tùy theo yêu cầu.
                                                        location.reload();
                                                    } else if (xhr.status === 500) {
                                                        alert("Lỗi server: Không thể xóa người dùng.");
                                                    }
                                                }
                                            };

                                            xhr.send(data);
                                        }
                                        
                   
            </script>
    </body>
</html>
