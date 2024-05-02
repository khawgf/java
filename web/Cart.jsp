<%@page import="java.net.URLDecoder"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%!
    // Định nghĩa hàm lấy giá trị của cookie và giải mã giá trị nếu cần
    public String getCookieValue(javax.servlet.http.Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    // Kiểm tra xem cookie cần giải mã hay không
                    String cookieValue = cookie.getValue();
                    if (cookieName.equals("username")) {
                        // Giải mã giá trị cookie nếu là "username"
                        try {
                            cookieValue = URLDecoder.decode(cookieValue, "UTF-8");
                        } catch (java.io.UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    return cookieValue;
                }
            }
        }
        return null;
    }
%>

<%
    // Sử dụng hàm getCookieValue để lấy giá trị của cookie và giải mã nếu cần
    String fullname = getCookieValue(request.getCookies(), "username");
    String phone = getCookieValue(request.getCookies(), "PhoneNumber");

%>

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

        <div class="info">
            <b>Khách hàng <%= fullname%>  (<%= phone%>)</b>
            <input type="text" id="address" name="address" placeholder="Vui lòng điền địa chỉ">
        </div>

        <div class="list-cart">

            <c:choose>
                <c:when test="${empty productsInCart}">

                    <div class="info">
                        <b>Your cart is empty.</b>
                    </div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th class="img-titleCart">Image</th>
                                <th>Product name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Select</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${productsInCart}" var="entry">
                                <c:set var="cartItem" value="${entry.key}" />
                            <form action="${pageContext.request.contextPath}/Cart" method="post">
                                <tr data-id="${cartItem.idSP}" data-price="${cartItem.priceSP}">
                                    <td style="display:none;">${cartItem.idSP}</td>
                                    <td>
                                        <img class="image" src="${cartItem.imageSP}" alt="Product Image">
                                    </td>
                                    <td>${cartItem.nameSP}</td>
                                    <td>
                                        <input class="input" type="number" name="quantity" value="${entry.value}">
                                    </td>
                                    <td>
                                        <div class="price">
                                            <fmt:formatNumber value="${cartItem.priceSP}" type="number" pattern="#,##0 VND" />
                                        </div>
                                    </td>
                                    <td>
                                        <input type="hidden" name="removeProduct" value="${cartItem.idSP}">
                                        <button class="button" type="submit">Xóa</button>
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                        </tbody>
                    </table>

                </c:otherwise>
            </c:choose>
        </div>

        <div class="total">
            <div class="left">
                <input type="text" id="maKhuyenMai" placeholder="Nhập mã khuyến mãi vào đây"">
                <button onclick="checkMaKhuyenMai()">Áp dụng</button>
            </div>
            <div class="right">
                <div class="title-total">
                    <h1 class="title1">Tổng cộng</h1>
                    <h1></h1>
                </div>
                <div class="btnPayment">
                    <button onclick="redirectToPayment()" name="btnPayment">Thanh toán</button>
                </div>
            </div>
        </div>




    </body>

 <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<script>
    // Get the "Remove" button by its ID
    const removeButton = document.getElementById("removeButton");

    // Add a click event listener to the button
    removeButton.addEventListener("click", function () {
        // Reload the page when the button is clicked
        location.reload();
    });
</script>
<script>
    // Lắng nghe sự kiện khi trang tải xong
    window.addEventListener('load', () => {
        // Lấy dữ liệu số lượng từ Local Storage và áp dụng cho từng dòng
        const rows = document.querySelectorAll('tr');
        rows.forEach(row => {
            const id = row.getAttribute('data-id');
            const input = row.querySelector('input[name="quantity"]');
            if (input) {
                const storedValue = localStorage.getItem(id);
                if (storedValue !== null) {
                    input.value = storedValue;
                }
            }
        });

        // Lắng nghe sự kiện khi người dùng thay đổi số lượng
        const quantityInputs = document.querySelectorAll('input[name="quantity"]');
        quantityInputs.forEach(input => {
            input.addEventListener('input', function () {
                const id = input.closest('tr').getAttribute('data-id');
                const quantity = input.value;
                localStorage.setItem(id, quantity);
                updateTotalPrice();
            });
        });

        // Hàm cập nhật tổng giá trị
        function updateTotalPrice() {
            let totalPrice = 0;
            const rows = document.querySelectorAll('tr');
            rows.forEach(row => {
                const price = parseFloat(row.getAttribute('data-price'));
                const input = row.querySelector('input[name="quantity"]');

                if (input) {
                    const quantity = parseInt(input.value, 10);
                    if (!isNaN(quantity)) {
                        totalPrice += price * quantity;
                    }
                }
            });

            // Hiển thị tổng giá trị đã cập nhật
            const totalElement = document.querySelector('.title-total h1:last-child');
            totalElement.textContent = totalPrice.toLocaleString('vi-VN') + ' VND';
        }

        // Khởi tạo tổng giá trị ban đầu
        updateTotalPrice();
    });
    
    function checkMaKhuyenMai() {
        
        var inputValue = document.getElementById("maKhuyenMai").value;

        // Sử dụng biểu thức chính quy để tìm số trong chuỗi
        var match = inputValue.match(/\d+/);

        // Kiểm tra nếu có số được tìm thấy
        if (match) {
            var soKhuyenMai = parseInt(match[0], 10); // Chuyển chuỗi số thành số nguyên
                       let totalPrice = 0;
            const rows = document.querySelectorAll('tr');
            rows.forEach(row => {
                const price = parseFloat(row.getAttribute('data-price'));
                const input = row.querySelector('input[name="quantity"]');

                if (input) {
                    const quantity = parseInt(input.value, 10);
                    if (!isNaN(quantity)) {
                        totalPrice += price * quantity;
                    }
                }
            });
            totalPrice = totalPrice - ((totalPrice * soKhuyenMai)/100);
            // Hiển thị tổng giá trị đã cập nhật
            const totalElement = document.querySelector('.title-total h1:last-child');
            totalElement.textContent = totalPrice.toLocaleString('vi-VN') + ' VND';
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Mã khuyến mãi không tồn tại'
            });
        }
    }
</script>
 <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script>
    function redirectToPayment() {
        var productIDs = [];
        var quantities = [];
        // Lặp qua tất cả các phần tử tr
        var trElements = document.querySelectorAll("tr[data-id]");
        trElements.forEach(function (tr) {
            var id = tr.getAttribute("data-id");
            var quantityInput = tr.querySelector("input[name='quantity']");
            var quantity = quantityInput ? quantityInput.value : 1;

            productIDs.push(id);
            quantities.push(quantity);
        });

        var addressvalue = document.getElementById('address').value;
        if (addressvalue != '') { // Tạo URL chứa thông tin sản phẩm và số lượng
            var url = "${pageContext.request.contextPath}/Payment?productIDs=" + productIDs.join(",") + "&quantities=" + quantities.join(",") + "&address=" + addressvalue;
            window.location.href = url;
        }
        else alert('Vui lòng nhập địa chỉ')
       
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
</html>
