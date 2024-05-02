<%@page import="java.net.URLDecoder"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="./css/indexsp.css" rel="stylesheet" />
        <link rel="stylesheet" href="./assets/css/cart.css" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js" type="text/javascript"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        

        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />


    <style data-tag="reset-style-sheet">
      html {  line-height: 1.15;}body {  margin: 0;}* {  box-sizing: border-box;  border-width: 0;  border-style: solid;}p,li,ul,pre,div,h1,h2,h3,h4,h5,h6,figure,blockquote,figcaption {  margin: 0;  padding: 0;}button {  background-color: transparent;}button,input,optgroup,select,textarea {  font-family: inherit;  font-size: 100%;  line-height: 1.15;  margin: 0;}button,select {  text-transform: none;}button,[type="button"],[type="reset"],[type="submit"] {  -webkit-appearance: button;}button::-moz-focus-inner,[type="button"]::-moz-focus-inner,[type="reset"]::-moz-focus-inner,[type="submit"]::-moz-focus-inner {  border-style: none;  padding: 0;}button:-moz-focus,[type="button"]:-moz-focus,[type="reset"]:-moz-focus,[type="submit"]:-moz-focus {  outline: 1px dotted ButtonText;}a {  color: inherit;  text-decoration: inherit;}input {  padding: 2px 4px;}img {  display: block;}html { scroll-behavior: smooth  }
    </style>
    <style data-tag="default-style-sheet">
      html {
        font-family: Inter;
        font-size: 16px;
      }

      body {
        font-weight: 400;
        font-style:normal;
        text-decoration: none;
        text-transform: none;
        letter-spacing: normal;
        line-height: 1.15;
        color: var(--dl-color-gray-black);
        background-color: var(--dl-color-gray-white);

      }
    </style>
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&amp;display=swap"
      data-tag="font"
    />
   
    <link rel="stylesheet" href="./assets/css/cart.css">
    <link rel="stylesheet" href="./assets/themify-icons/themify-icons.css">
    <title>Invoice</title>
</head>

<body>
<div class="layout-hearder">

            <div class="webgridview-layoutheaderalibaba">
              <img
                src="image/index_img/bgi240-pvzg.svg"
                alt="BGI240"
                class="webgridview-bg1"
              />
              <div class="webgridview-actions">

                  
            <a href="${pageContext.request.contextPath}/Cart">
                                <div class="webgridview-cart">
                                  <img src="image/index_img/vectori240-orbm.svg" alt="VectorI240"
                                    class="webgridview-vector" />
                                  <span class="webgridview-text012"><span>My cart</span></span>
                                </div>
                              </a>
                  
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
        if ("admin".equals(username)) {
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
              
              <div class="webgridview-searchform">
                <div class="webgridview-forminputgroupinputleft">
                  <img
                    src="image/index_img/iconcontrolsearchi240-ma5m.svg"
                    alt="IconcontrolsearchI240"
                    class="webgridview-iconcontrolsearch"
                  />
                        <input
                            type="text"
                            placeholder="Search"
                            class="webgridview-textinput input"
                            name="searchname"
                            id="searchname"
                            oninput="searchProduct(1);"
                            
                        />
                </div>
                  <button type="submit" class="webgridview-buttonbtngroup2" id="btn-search" >Search</button>
                </form>
              </div>
              
              <a href="index">
              <img
                src="image/index_img/brandlogocoloredi240-btcy.svg"
                alt="BrandlogocoloredI240"
                href="index"
                class="webgridview-brandlogocolored"
              />
            </a>

            </div>
          </div>

    <div class="list-Invoice">
        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="invoice" items="${invoices}">
                    <tr>
                        <td>${invoice.id}</td>
                        <td>${invoice.phone}</td>
                        <td>${invoice.address}</td>
                        <td>
                            <c:choose>
                                <c:when test="${invoice.status eq 1}">Đang xác nhận</c:when>
                                <c:when test="${invoice.status eq 2}">Đang chuẩn bị</c:when>
                                <c:when test="${invoice.status eq 3}">Đang giao</c:when>
                                <c:when test="${invoice.status eq 4}">Đã giao</c:when>
                            </c:choose>
                        </td>
                        <td>
                            <button class="button" onclick="redirectPDF(${invoice.id})">
                                Xem
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

 <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script>
    function redirectPDF(invoiceId) {
        if (invoiceId !== '') {
            var url = "${pageContext.request.contextPath}/PrintInvoice?invoiceID=" + invoiceId;
            window.location.href = url;
        }
    }
    function logout() {
            // Sử dụng AJAX để gửi yêu cầu đăng xuất đến máy chủ
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        // Nếu đăng xuất thành công, hiển thị thông báo và tải lại trang
                        swal("Thông báo", "Đăng xuất thành công", "success")
                            .then(function () {
                                window.location.reload();
                            });
                    } else {
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
 
