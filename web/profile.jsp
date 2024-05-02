<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.net.URLDecoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./css/stylesp.css" />
        <link href="./css/indexsp.css" rel="stylesheet" />
        <link href="./css/style.css" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js" type="text/javascript"></script>
        <!--<script src="js/jQuery.UI.js" type="text/javascript"></script>-->
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
   
    <link rel="stylesheet" href="./css/profile.css">
    <link rel="stylesheet" href="./assets/themify-icons/themify-icons.css">
    <title>Thông tin cá nhân</title>
</head>        
<%!
    // Định nghĩa hàm lấy giá trị của cookie
    public String getCookieValue(javax.servlet.http.Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
%>
<%
    String userid = getCookieValue(request.getCookies(), "userid");
    String fullname = getCookieValue(request.getCookies(), "username");

// Giải mã giá trị cookie với UTF-8
        String decodedFullname = null;
        try {
            decodedFullname = URLDecoder.decode(fullname, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace(); // Xử lý exception nếu có lỗi
        }
            String phone = getCookieValue(request.getCookies(), "PhoneNumber");
        %>
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
                    <li class="has__login-item" id="admin"><a href="xemdonhang.jsp" id="info-user">Xem đơn hàng</a></li>
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
                    <li class="has__login-item" id="admin"><a href="xemdonhang.jsp" id="info-user">Xem đơn hàng</a></li>
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
                      <li class="has__login-item" id="admin"><a href="xemdonhang.jsp" id="info-user">Xem đơn hàng</a></li>
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
    <div class="container">
        <div class="container-left">
            <div class="menu_profile">
                <img src="./img/shizuka.jpg" alt="">
                <h1></h1>
                <div class="button">
                    <button class="btn1"><a href="index">Trang chủ</a></button>
                    <button class="btn2" onclick="showModalAdd()">Change pass</button>
                </div>
                <br>
            </div>
        </div>
        <div class="container-right">
            <div class="title">
                <span class="title1">Hồ sơ của tôi</span>
                <br>
                <span>Quản lý thông tin hồ sơ để bảo mật</span>
            </div>
            <div class="content">
                <div class="left">
                    <div class="menu-left">
                        <form action="updateInfoUser" method="post">
                            <input type="hidden" name="action" value="changeinfo">
                             <div class="title2">ID</div>
                            <input type="text" name="id_infoUser" id="id_infoUser" value="<%= userid %>" readonly="">
                            <div class="title2">Phone number</div>
                            <input type="text" name="phoneNumber" id="phoneNumber" value="<%= phone %>" readonly="">
                            <div class="title2">Username</div>
                            <input type="text" name="username" id="username" value="<%= decodedFullname %>">
                            <br>
                            <br>
                            <button class="btn2" type="submit" name="btbsubmit">Chỉnh sửa</button>
                        </form>                                               
                    </div>
                </div>
                <div class="right">
                    <img src="./img/anhnendep.jpg" alt="">
                </div>
            </div>
        </div>
    </div>
                            
                               <form action="updateInfoUser" method="post">
                <input type="hidden" name="action" value="changepass">
                <div class="modal__add-product" style="display: none;">
                    <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>
                    <div class="modal__add-product-content">
                        <div class="modal__add-product-header">
                            <p class="modal__add-product-header-heading">Thay đổi mật khẩu</p>
                        </div>
                        <div class="modal__add-User-id"> <input type="text" value="<%= userid %>" name="txtadminid" readonly="" id="txtadminid"></div>
                        <div class="modal__add-User-account"><input type="password" name="txtadminpass" id="txtadminuser" placeholder="Mật khẩu ( >5 kí tự )"></div>
                        <div class="modal__add-User-pass"> <input type="password" name="txtadminpassconfirm" placeholder="Xác nhận mật khẩu" id="txtadminpass"></div>
                        <div class="modal__add-product-btn" id="btnuserAdd" style="display: block;"><button type="submit">Confirm</button></div>
                        <div class="modal__add-product-btn-close" onclick="hideModalAdd();">
                            <i class="fas fa-window-close"></i>
                        </div>
                    </div>
                </div>
            </form>
                        
                        
                        
                           <%
            String successMessage = (String) request.getAttribute("successMessage");
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (successMessage != null) {
            %>
            <script>
                alert("<%= successMessage %>");
                window.location.href = "profile.jsp"; // Tải lại trang hiện tại
            </script>
            <%
            } else if (errorMessage != null) {
            %>
            <script>
                alert("<%= errorMessage %>");
                 window.location.href = "profile.jsp"; 
            </script>
            <%
            }
            %>
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
        
             function showModalAdd()
                {
                    document.querySelector('.modal__add-product').style.display = "block";
                }
                function hideModalAdd()
                {
                    document.querySelector('.modal__add-product').style.display = "none";
                }
    </script>
</body>
</html>