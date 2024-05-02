    <%-- 
    Document   : header
    Created on : Oct 1, 2023, 3:07:18 PM
    Author     : DELL
--%>

<%@page import="java.net.URLDecoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         <link rel="stylesheet" type="text/css" href="../assets/css/style.css">
        <link rel="stylesheet" href="../fontawesome-free-5.15.4-web/fontawesome-free-5.15.4-web/css/all.min.css">
    </head>
    <body>
  <div class="header">
            <div class="header__logo">
                <a href="index">
                    <img src="./img/logo1.png" alt="" class="header__logo-img" id="logo">
                </a>
            </div>

               <%
            request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");

    // Lấy giá trị cookie từ request
    Cookie[] cookies = request.getCookies();
    String username = null;
    String userType = null;

    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("username".equals(cookie.getName())) {
                username = URLDecoder.decode(cookie.getValue(), "UTF-8");
            } else if ("type".equals(cookie.getName())) {
                userType = cookie.getValue();
            }
        }
    }

    if (username != null) {
        // User is logged in
        if ("admin".equals(userType)) {
%>
            <div class="header__has__login" style="display: flex;" id="hasLogin">
                <img src="./image/index_img/avatar9091-7o7m.svg" alt="">
                <span id="userLogin"><%= username %></span>
                <ul class="has__login-list">
                    <li class="has__login-item" id="admin"><a href="adminhome.jsp" id="info-user">Admin</a></li>
                    <li class="has__login-item" id="admin"><a href="showInvoice" id="info-user">Xem đơn hàng</a></li>
                    <li class="has__login-item"><a id="logout" onclick="logout()">Đăng xuất</a></li>
                </ul>
            </div>
<%
        } else if ("employee".equals(userType)) {
%>
            <div class="header__has__login" style="display: flex;" id="hasLogin">
                <img src="./image/index_img/avatar9091-7o7m.svg" alt="">
                <span id="userLogin"><%= username %></span>
                <ul class="has__login-list">
                    <li class="has__login-item" id="admin"><a href="adminemployeehome.jsp" id="info-user">Employee</a></li>
                    <li class="has__login-item" id="admin"><a href="profile.jsp" id="info-user">Thay đổi thông tin</a></li>
                    <li class="has__login-item" id="admin"><a href="showInvoice" id="info-user">Xem đơn hàng</a></li>
                    <li class="has__login-item"><a id="logout" onclick="logout()">Đăng xuất</a></li>
                </ul>
            </div>
<%
        } else if ("customer".equals(userType)){
%>
            <div class="header__has__login" style="display: flex;" id="hasLogin">
                <img src="./image/index_img/avatar9091-7o7m.svg" alt="">
                <span id="userLogin"><%= username %></span>
                <ul class="has__login-list">
                    <li class="has__login-item" id="admin"><a href="profile.jsp" id="info-user">Thay đổi thông tin</a></li>
                      <li class="has__login-item" id="admin"><a href="showInvoice" id="info-user">Xem đơn hàng</a></li>
                    <li class="has__login-item"><a id="logout" onclick="logout()">Đăng xuất</a></li>
                </ul>
            </div>
<%
        }
    } else {
%>
    <a href="Login.jsp">
        <div class="webgridview-profile">
            <span class="webgridview-text014"><span>Login</span></span>
            <img src="image/index_img/vectori240-9w4r.svg" alt="VectorI240" class="webgridview-vector1" />
        </div>
    </a>
<%
    }
%>
        </div>
                             <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>   
           <script>
       
      
        function logout() {
            // Sử dụng AJAX để gửi yêu cầu đăng xuất đến máy chủ
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        // Nếu đăng xuất thành công, hiển thị thông báo và tải lại trang
                        swal("Thông báo", "Đăng xuất thành công", "success")
                        .then(function () {
                            window.location.href = "index"; 
                        }); } else {
                        // Nếu có lỗi, hiển thị thông báo lỗi
                        swal("Thông báo", "Đăng xuất thất bại", "error");
                    }
                }
            };

            xhr.open("GET", "logout.jsp", true);
            xhr.send();
        }
        
        </script>
            
    </body>
</html>
