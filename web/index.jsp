<%-- 
    Document   : index
    Created on : Sep 29, 2023, 12:59:17 PM
    Author     : ASUS
--%>

<%@page import="java.net.URLDecoder"%>
<%@page import="model.Category"%>
<%@page import="model.Product"%>
<%@page import="model.Brand"%>
<%@page import="java.util.List"%>
<%@page import="dao.CategoryDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./css/stylesp.css" />
        <link href="./css/indexsp.css" rel="stylesheet" />
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
   
    </head>
    <body>
   
      <div class="webgridview-container">
        <div class="webgridview-webgridview">
          
          <div class="layout-hearder">
            <div class="webgridview-layoutnavbaralibaba">
              <img
                src="image/index_img/bgi240-2od.svg"
                alt="BGI240"
                class="webgridview-bg"
              />
              <img
                src="image/index_img/line61i240-kqcj.svg"
                alt="Line61I240"
                class="webgridview-line61"
              />
              <img
                src="image/index_img/borderi240-9bx8.svg"
                alt="BorderI240"
                class="webgridview-border"
              />
              
              <ul class="webgridview-navlist">
                <li><a href="index">Home</a><i class="fa-solid fa-bars fa-lg" style="color: #141414; top: 20px; left: 3px"></i></li>
                <li><a href="#">Product</a>
                  <i class="fa-solid fa-chevron-down fa-l" style="color: #727274; left: 95px; top: 20px; cursor: pointer;" ></i>
                  <ul class="sub-menu">
                        <c:forEach items="${listCategory}" var="cate">
                            <li ><a href="javascript:void(0);" class="categoryMenu" onclick="toggleCategory(this)">${cate.nameCate}</a></li>
                        </c:forEach>
                      </ul>
                </li>

                <li><a href="#">Introduction</a></li>
                <li><a href="#">Contact</a></li>
              </ul>
            </div>
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
          <div class="webgridview-sectionmain">
            <img
              src="image/index_img/bgcard9091-i08.svg"
              alt="bgcard9091"
              class="webgridview-bgcard"
            />
            <div class="webgridview-banner">
              <div class="webgridview-maskgroup">
                <img
                  src="image/index_img/bannerboard800x42029091-qr28-400h.png"
                  alt="Bannerboard800x42029091"
                  class="webgridview-bannerboard800x4202"
                />
              </div>
              <div class="webgridview-text132">
                <span class="webgridview-text133">
                  <span>Latest trending</span>
                </span>
                <span class="webgridview-text135">
                  <span>Electronic items</span>
                </span>
              </div>
              <button class="webgridview-buttonbtnbasic13">Learn more</button>
              
            </div>
            <img
              src="image/index_img/rectangle3009091-n5wv-300w.png"
              alt="Rectangle3009091"
              class="webgridview-rectangle300"
            />
            <span class="webgridview-text139 TextBase">
              <span>Hi, user  let’s get stated</span>
            </span>
            <button class="webgridview-buttonbtnbasic14">Join now</button>

            <<a href="Login.jsp">
            <button class="webgridview-buttonbtnbasic15">Log in</button>
</a>
            <img
              src="image/index_img/avatar9091-7o7m.svg"
              alt="Avatar9091"
              class="webgridview-avatar"
            />
            <div class="webgridview-block">
              <span class="webgridview-text145 TextBase">
                <span>Get US $10 off with a new supplier</span>
              </span>
            </div>
            <div class="webgridview-block1">
              <span class="webgridview-text147 TextBase">
                <span>Send quotes with supplier preferences</span>
              </span>
            </div>
          </div>
        
    <div class="content" >
        <!--<div id="total">-->
        <div id="content-product">
            <div class="webgridview-contenttop">
                <span class="webgridview-text TextBase">
                  <span>Results: <span id="resultCount">${allProduct}</span> items</span>
                </span>
                <div class="webgridview-btngroup">
                    <button id="webgridview-buttonbtngroup"><i class="fas fa-bars fa-lg"></i></button>
                  <button id="webgridview-buttonbtngroup1" ><i class="fas fa-grip-horizontal fa-lg"></i></button>
                </div>
                <span class="webgridview-text002 TextBase">
                    <span><a href="index">Clear all filter</a></span>
                </span>
            </div>
        <!--</div>-->   
    <div class="webgridview-layoutproductcard horizontal" id="content">
    <c:forEach items="${listProduct}" var="product" >
        <div class="col-md-4 mb-4" >
                    <div class="card">
                        <div class="card-body" >
                            <div class="card-img">
                                <img
                                  src="${product.imageSP}"
                                  alt="image33I239"
                                  class="webgridview-image33"
                                />
                            </div>
                            <div class="webgridview-content">
                                <div class="webgridview-group1000">
                                    <span class="webgridview-text022 TitleH5">
                                            <fmt:formatNumber type = "currency" value = "${product.priceSP}" />
                                      
                                    </span>
                                    <c:choose>
                                        <c:when test="${product.discountSP != 0}">
                                            <span class="webgridview-text024">
                                                <span>
                                                    <c:set var="OriginalPrice" value="${product.priceSP / ((100-product.discountSP) / 100)}" />
                                                    <fmt:formatNumber type="currency" value="${OriginalPrice}" />
                                                </span>
                                            </span>
                                        </c:when>
                                    </c:choose>
                                </div>
                                <div class="webgridview-rating">
                                    <c:set var="rating" value="${product.ratingSP}" />
                                    <c:choose>
                                        <c:when test="${rating >= 9}">
                                            <img
                                                    src="image/index_img/miscrating9092-b6.svg"
                                                    alt="Miscrating9092"
                                                    class="webgridview-miscrating"
                                                  />
                                            <span class="webgridview-text018 TextBase"><span>${product.ratingSP}</span></span>
                                        </c:when>
                                        <c:when test="${rating >= 7.5}">
                                            <img
                                                src="image/index_img/miscrating9092-f8m.svg"
                                                alt="Miscrating9092"
                                                class="webgridview-miscrating"
                                              />
                                            <span class="webgridview-text018 TextBase"><span>${product.ratingSP}</span></span>
                                        </c:when>
                                        <c:when test="${rating >= 5.9}">
                                            <img
                                                src="image/index_img/miscrating9092-fcp.svg"
                                                alt="Miscrating9092"
                                                class="webgridview-miscrating"
                                              />
                                            <span class="webgridview-text018 TextBase"><span>${product.ratingSP}</span></span>
                                        </c:when>
                                        <c:when test="${rating >= 3.5}">
                                            <img
                                                src="image/index_img/miscrating9092-ielc.svg"
                                                alt="Miscrating9092"
                                                class="webgridview-miscrating"
                                              />
                                            <span class="webgridview-text018 TextBase"><span>${product.ratingSP}</span></span>
                                        </c:when>
                                        <c:otherwise>
                                            
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <button class="webgridview-buttonbtnbasic">
                                    <i class="far fa-heart fa-lg" ></i>
                                </button>
                                    <span class="webgridview-titleproduct">
                                    ${product.nameSP}
                                </span>
                                <a href="${pageContext.request.contextPath}/details?productId=${product.idSP}"><button class="webgridview-buttonbtnbasic01">
                                    <i class="fa-solid fa-chevron-right"></i>
                                    <span class="text-view-details">View details</span>
                                    </button></a>
                            </div>
                        </div>
                    </div>
                </div>
        
    </c:forEach>
                    
            <input type="hidden" id="pageNumber" value="${tag}">
            <input type="hidden" id="endPage" value="${endPage}">
                <div class="col-12 col-md-4 text-center">
                <div aria-label="Page navigation example">
                    <div class="webgridview-Page">
                    <div class="webgridview-frame273">
                        <c:if test="${tag > 1}">
                            <div class="page-item" id="previous-page">
                                <div class="page-link">
                                    <i class="fas fa-chevron-left"></i>
                                </div>
                            </div>
                        </c:if>
                        
                        <c:if test="${endPage > 1}">
                            <c:set var="minValue" value="${tag - 1 > 0 ? tag - 1 : 1}" />
                            <c:set var="maxValue" value="${minValue + 2 > endPage ? endPage : minValue + 2}" />

                            <c:forEach var="i" begin="${minValue}" end="${maxValue}" varStatus="loop">
                                <c:choose>
                                    <c:when test="${i == tag}">
                                        <div class="page-item active current-page" data-page="${i}">
                                            <div class="page-link">${i}</div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="page-item current-page" data-page="${i}">
                                            <div class="page-link">${i}</div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:if>   
                        <c:if test="${tag < endPage}">
                            <div class="page-item" id="next-page">
                                <div class="page-link">
                                    <i class="fas fa-chevron-right"></i>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
                </div>
            </div>
        </div>
      </div>

        <div class="webgridview-sidebar">
            <div id="wrapper">
                <div class="accordion" >
                    <div class="accordion-item active">
                        <div class="accordion-header">
                            <span >Category</span>
                            <i class="fas fa-chevron-down" ></i>		
                        </div>
                        <div class="accordion-body">
         
                            <c:forEach items="${listCategory}" var="cate">
                              <span id="categorySelect" class="webgridview-Category" onclick="toggleCategory(this)" >${cate.nameCate}</span><br>
                            </c:forEach>
                          
                          <span class="webgridview-Category" style="color: blue;">See all</span>
                        </div>
                    </div>
                          
                    <div class="accordion-item active">
                        <div class="accordion-header">
                            <span >Brands</span>
                            <i class="fas fa-chevron-down"></i>		
                        </div>
                        <div class="accordion-body" id="brandsContainer" id="brands-${cate.idCate}">
                            
                            <c:forEach items="${brandsByCategory}" var="brands">
                                 <div class="webgridview-formselectioncheckboxoff04">

                                     <input type="checkbox" 
                                            class="webgridview-checkbox04" 
                                            name="brandCheckbox" 
                                            id="${brands.nameBrand}"
                                            value="${brands.nameBrand}"
                                            
                                            />
                                     <label>${brands.nameBrand}</label>

                                 </div>
                             </c:forEach>
                         
                             <span class="webgridview-Category" style="color: blue; ">See all</span>
                      </div>
                    </div>
                    
                    <div class="accordion-item active">
                        <div class="accordion-header">
                          <span >Price range</span>
                        <i class="fas fa-chevron-down"></i>		
                        </div>
                        <div class="accordion-body">
                          
                          <div class="webgridview">
                            <div class="webgridview-min1">
                             
                            <span class="webgridview-Category">Min</span><br>
                            <div class="webgridview-inputprice">
                                  <input
                                    type="number" 
                                    min=0
                                    max="38980000"
                                    placeholder="0"
                                    id="min_price" 
                                    class="webgridview-textinput2 input"
                                  />
                                </div>
                             </div>
                             <div class="webgridview-max1" style="margin-left: 10px">
                              <span class="webgridview-Category">Max</span><br>
                                  <div class="webgridview-inputprice">
                                    <input
                                      type="number" 
                                      min=2790000 
                                      max="38990000" 
                                      id="max_price" 
                                      placeholder="999999"
                                      class="webgridview-textinput3 input"
                                    />
                                  </div>
                                </div>
                            </div>
                            <button class="price-range-search" id="price-range-submit" onclick="searchProduct(1);">Apply</button><br>
                             <span class="webgridview-Category" style="color: blue;">See all</span>
                        </div>
                    </div>
                          
                    <div class="accordion-item active">
                        <div class="accordion-header">
                          <span >Ratings</span>
                        <i class="fas fa-chevron-down"></i>		
                        </div>
                        <div class="accordion-body">
                          <div class="webgridview-formselectioncheckboxoff04">
                              <input type="checkbox" class="webgridview-checkbox04" name="ratingFilter" value="5" />
                              <img
                                src="image/index_img/miscrating9092-b6.svg"
                                alt="Miscrating9092"
                                class="webgridview-miscrating06"
                              />
                            </div>
                            <div class="webgridview-formselectioncheckboxoff04">
                              <input type="checkbox" class="webgridview-checkbox04" name="ratingFilter" value="4"  />
                              <img
                                src="image/index_img/miscrating9092-f8m.svg"
                                alt="Miscrating9092"
                                class="webgridview-miscrating07"
                              />
                            </div>
                            <div class="webgridview-formselectioncheckboxoff04">
                              <input type="checkbox" class="webgridview-checkbox04" name="ratingFilter" value="3"/>
                              <img
                                src="image/index_img/miscrating9092-fcp.svg"
                                alt="Miscrating9092"
                                class="webgridview-miscrating08"
                              />
                            </div>
                            <div class="webgridview-formselectioncheckboxoff04">
                              <input type="checkbox" class="webgridview-checkbox04" name="ratingFilter" value="2" />
                              <img
                                src="image/index_img/miscrating9092-ielc.svg"
                                alt="Miscrating9092"
                                class="webgridview-miscrating09"
                              />
                            </div>
                                              
                      </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
            <div class="footer">
                <div class="webgridview-layoutnewsletteralibaba">
                    <img
                      src="image/index_img/bgi239-ibnn-200h.png"
                      alt="bgI239"
                      class="webgridview-bg2"
                    />
                    <div class="webgridview-textform">
                      <div class="webgridview-form">
                        <div class="webgridview-formiconinput">
                          <img
                            src="image/index_img/iconemaili239-s8wp.svg"
                            alt="IconemailI239"
                            class="webgridview-iconemail"
                          />
                          <input
                            type="text"
                            placeholder="Email"
                            class="webgridview-textinput1 input"
                          />
                        </div>
                        <button class="webgridview-buttonbtnbasic12">Subscribe</button>
                      </div>
                      <div class="webgridview-text081">
                        <span class="webgridview-text082 TextInfo">
                          <span>
                            Get daily news on upcoming offers from many suppliers all
                            over the world
                          </span>
                        </span>
                        <span class="webgridview-text084 Title-H4">
                          <span>Subscribe on our newsletter</span>
                        </span>
                      </div>
                    </div>
                  </div>
                  <div class="webgridview-layoutfooteralibaba">
                    <div class="webgridview-col">
                      <span class="webgridview-text086 TextTitle">
                        <span>For users</span>
                      </span>
                      <span class="webgridview-text088 Textnormal">
                        <span>Login</span>
                      </span>
                      <span class="webgridview-text090 Textnormal">
                        <span>Register</span>
                      </span>
                      <span class="webgridview-text092 Textnormal">
                        <span>Settings</span>
                      </span>
                      <span class="webgridview-text094 Textnormal">
                        <span>My Orders</span>
                      </span>
                    </div>
                    <div class="webgridview-group522">
                      <img
                        src="image/index_img/brandlogocoloredi239-7n2n.svg"
                        alt="BrandlogocoloredI239"
                        class="webgridview-brandlogocolored1"
                      />
                      <span class="webgridview-text096 TextInfo">
                        <span>
                          Best information about the company gies here but now lorem
                          ipsum is
                        </span>
                      </span>
                      <div class="webgridview-group987">
                        <img
                          src="image/index_img/iconcontactsocialfacebook3i239-t88.svg"
                          alt="Iconcontactsocialfacebook3I239"
                          class="webgridview-iconcontactsocialfacebook3"
                        />
                        <img
                          src="image/index_img/iconcontactsocialtwitter3i239-925m.svg"
                          alt="Iconcontactsocialtwitter3I239"
                          class="webgridview-iconcontactsocialtwitter3"
                        />
                        <img
                          src="image/index_img/iconcontactsociallinkedin3i239-lojd.svg"
                          alt="Iconcontactsociallinkedin3I239"
                          class="webgridview-iconcontactsociallinkedin3"
                        />
                        <img
                          src="image/index_img/iconcontactsocialyoutube3i239-7bg.svg"
                          alt="Iconcontactsocialyoutube3I239"
                          class="webgridview-iconcontactsocialyoutube3"
                        />
                      </div>
                    </div>
                    <div class="webgridview-group988">
                      <div class="webgridview-paymentmethodlanguage">
                        <span class="webgridview-text098 Textnormal">
                          <span>© 2023 Ecommerce.</span>
                        </span>
                      </div>
                    </div>
                    <div class="webgridview-col1">
                      <button class="webgridview-miscmarketbutton">
                        <div class="webgridview-group">
                          <img
                            src="image/index_img/subtracti239-55yn.svg"
                            alt="SubtractI239"
                            class="webgridview-subtract"
                          />
                        </div>
                      </button>
                      <button class="webgridview-miscmarketbutton1">
                        <div class="webgridview-group1">
                          <img
                            src="image/index_img/logoi239-ovi.svg"
                            alt="LogoI239"
                            class="webgridview-logo"
                          />
                        </div>
                      </button>
                      <span class="webgridview-text100 TextTitle">
                        <span>Get app</span>
                      </span>
                    </div>
                    <div class="webgridview-col2">
                      <span class="webgridview-text102 TextTitle">
                        <span>Information</span>
                      </span>
                      <span class="webgridview-text104 Textnormal">
                        <span>Help Center</span>
                      </span>
                      <span class="webgridview-text106 Textnormal">
                        <span>Money Refund</span>
                      </span>
                      <span class="webgridview-text108 Textnormal">
                        <span>Shipping</span>
                      </span>
                      <span class="webgridview-text110 Textnormal">
                        <span>Contact us</span>
                      </span>
                    </div>
                    <div class="webgridview-col3">
                      <span class="webgridview-text112 TextTitle">
                        <span>Partnership</span>
                      </span>
                      <span class="webgridview-text114 Textnormal">
                        <span>About Us</span>
                      </span>
                      <span class="webgridview-text116 Textnormal">
                        <span>Find store</span>
                      </span>
                      <span class="webgridview-text118 Textnormal">
                        <span>Categories</span>
                      </span>
                      <span class="webgridview-text120 Textnormal">
                        <span>Blogs</span>
                      </span>
                    </div>
                    <div class="webgridview-col4">
                      <span class="webgridview-text122 TextTitle">
                        <span>About</span>
                      </span>
                      <span class="webgridview-text124 Textnormal">
                        <span>About Us</span>
                      </span>
                      <span class="webgridview-text126 Textnormal">
                        <span>Find store</span>
                      </span>
                      <span class="webgridview-text128 Textnormal">
                        <span>Categories</span>
                      </span>
                      <span class="webgridview-text130 Textnormal">
                        <span>Blogs</span>
                      </span>
                    </div>
                  </div>
            </div>
   
        </div>
      </div>
                         <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script>
        
        var input_min_price = document.getElementById("min_price");
        var input_max_price = document.getElementById("max_price");
        input_min_price.addEventListener("keypress", function(event) {
          if (event.key === "Enter") {
            event.preventDefault();
            document.getElementById("price-range-submit").onclick();
          }
        });
        
        input_max_price.addEventListener("keypress", function(event) {
          if (event.key === "Enter") {
            event.preventDefault();
            document.getElementById("price-range-submit").onclick();
          }
        });
        


        var selectedCategory = null;

        function toggleCategory(element) {
            if (element.classList.contains("active")) {
                element.classList.remove("active");
            } else {
                // Loại bỏ "active" từ phần tử trước (nếu có)
                if (selectedCategory) {
                    selectedCategory.classList.remove("active");
                }

                element.classList.add("active");
                selectedCategory = element;

                searchProduct(1);
                searchbrandsByCategory();
            }
        }

        
        var brandCheckboxes = document.querySelectorAll("input[name='brandCheckbox']");

        // Thêm sự kiện nghe lựa chọn cho mỗi hộp kiểm xếp hạng
        for (var i = 0; i < brandCheckboxes.length; i++) {
            brandCheckboxes[i].addEventListener('change', function () {
                searchProduct(1);
            });
        }

        var ratingCheckboxes = document.querySelectorAll("input[name='ratingFilter']");

        // Thêm sự kiện nghe lựa chọn cho mỗi hộp kiểm xếp hạng
        for (var i = 0; i < ratingCheckboxes.length; i++) {
            ratingCheckboxes[i].addEventListener('change', function () {
                searchProduct(1);
            });
        }
        
        function handleBrandSelection(checkbox) {
            // Lấy danh sách các checkbox đã chọn
            var selectedBrands = [];
            var brandCheckboxes = document.querySelectorAll('input[name="brandCheckbox"]:checked');
            brandCheckboxes.forEach(function (checkbox) {
              selectedBrands.push(checkbox.value);
            });

            // Gọi hàm Ajax với danh sách các thương hiệu đã chọn
            searchProduct(1);
          }
          
        $(document).on('click', '#previous-page', function (e) {
            e.preventDefault();
            var pageNumber = parseInt($('#pageNumber').val());
            if (pageNumber > 1) {
                searchProduct(pageNumber - 1);
            }
        });

        $(document).on('click', '#next-page', function (e) {
            e.preventDefault();
            var pageNumber = parseInt($('#pageNumber').val());
            var endPage = parseInt($('#endPage').val());
            if (pageNumber < endPage) {
                searchProduct(pageNumber + 1);
            }
        });

        $(document).on('click', '.current-page', function (e) {
            e.preventDefault();
            var pageNumber = $(this).data('page');
            $('#pageNumber').val(pageNumber);
            searchProduct(pageNumber);
        });

       
        
        function searchProduct(pageNumber){
            var txtSearchName = document.getElementById("searchname").value.trim();
            var selectedCategory1 = document.querySelector(".categoryMenu.active");
            var selectedCategory2 = document.querySelector(".webgridview-Category.active");
            var category = selectedCategory1 ? selectedCategory1.textContent : "";
            var category = selectedCategory2 ? selectedCategory2.textContent : category;
            var minprice = document.getElementById("min_price").value.trim();
            var maxprice = document.getElementById("max_price").value.trim();
            
            var selectedBrands = [];
            var brandCheckboxes = document.querySelectorAll('input[name="brandCheckbox"]:checked');
            brandCheckboxes.forEach(function(checkbox) {
                selectedBrands.push(checkbox.value);
            });
            
            var selectedRatings = [];
            var ratingCheckboxes = document.querySelectorAll('input[name="ratingFilter"]:checked');
            ratingCheckboxes.forEach(function(checkbox) {
                selectedRatings.push(checkbox.value);
            });
            $.ajax({
                url: "/j2ee_project/SearchController",
                type: "get", //send it through get method
                data: {
                    name: txtSearchName,
                    category: category,
                    brand: selectedBrands,
                    minPrice: minprice,
                    maxPrice: maxprice,
                    ratingFilter: selectedRatings,
                    page: pageNumber 
                    
                    
                },
                traditional: true, // Đặt traditional thành true để gửi mảng dưới dạng "ratingFilter=1&ratingFilter=2&ratingFilter=3" (phù hợp với ngôn ngữ máy chủ Java)
                success: function (data) {
                    var row = document.getElementById("content-product");
                    row.innerHTML =data;
                                        
                    
                },
                    error: function (xhr) {
                        //Do Something to handle error
                    }
            });
        }
        
        function searchbrandsByCategory(){
            var selectedCategory1 = document.querySelector(".categoryMenu.active");
            var selectedCategory2 = document.querySelector(".webgridview-Category.active");
            var category = selectedCategory1 ? selectedCategory1.textContent : "";
            var category = selectedCategory2 ? selectedCategory2.textContent : category;

            $.ajax({
                url: "/j2ee_project/BrandsByCategoryController",
                type: "get", //send it through get method
                data: {
                    category: category
                                    
                },
                traditional: true, 
                success: function (data) {
                   
                    var brandContainer = document.getElementById("brandsContainer");
                    brandContainer.innerHTML = data;
                    
                },
                    error: function (xhr) {
                        //Do Something to handle error
                    }

            });
        }
        
    </script>
    <script>
       
       
       
        document.addEventListener("DOMContentLoaded", function () {
            const productContainer = document.getElementById("content");
            const horizontalButton = document.getElementById("webgridview-buttonbtngroup1");
            const verticalButton = document.getElementById("webgridview-buttonbtngroup");
            const pageItems=document.getElementsByClassName("page-item");

//            productContainer.classList.add("horizontal");
//            console.log(verticalButton);

            horizontalButton.addEventListener("click", function () {
                productContainer.classList.remove("vertical");
                productContainer.classList.add("horizontal");
            });

            verticalButton.addEventListener("click", function () {
                productContainer.classList.remove("horizontal");
                productContainer.classList.add("vertical");
            });

        });

      $(document).ready(function() {
        $('.accordion-item.active .accordion-body').slideDown();
        $('.accordion-header').click(function() {
            $(this).parent().toggleClass('active');
              $(this).parent().children('.accordion-body').slideToggle();
        });
      });
      
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
