<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web project</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" href="fontawesome-free-5.15.4-web/fontawesome-free-5.15.4-web/css/all.min.css">
    </head>
    <body>
        <div class="apps" onload="hideAdminBar();">
            <div id="preLoader"></div>

                    <jsp:include page="headeradmin.jsp" />  

            <div class="apps__container">

                <div class="apps__content" >

                     <div class="btn_back">
            <a href="adminhome.jsp">Quay về trang chủ</a>
        </div>
                    
                    <div class="apps__content-header">
                        <div class="apps__content-heading">
                            <div class="apps__content-heading">
                                <label for="product-dropdown">Chọn danh sách sản phẩm:</label>
                                <select id="product-dropdown" style="font-size: 16px; width: 250px;">
                                    <option value="1" style="font-size: 14px;">Danh sách sản phẩm chính</option>
                                    <option value="2" style="font-size: 14px;">Danh sách sản phẩm kho</option>
                                </select>
                            </div>
                            
                            <form action="quanlyspnccController" method="get" style="display: flex;align-items: center;" id="searchForm">
                                <input type="text" placeholder="Search something........." class="search-input" id="txtsearch" name="searchUser">
                                <button class="header__search-btn" type="submit">
                                    <i class="search-btn fas fa-search icon-search"></i>
                                </button>
                            </form>
                            <script>
                                const productDropdown = document.getElementById("product-dropdown");

                                // Kiểm tra URL hiện tại để thiết lập giá trị mặc định cho combobox
                                const currentURL = window.location.pathname;
                                if (currentURL.includes("quanlyspcController")) {
                                    productDropdown.value = "1"; // Chọn Danh sách sản phẩm chính
                                } else if (currentURL.includes("quanlyspnccController")) {
                                    productDropdown.value = "2"; // Chọn Danh sách sản phẩm kho
                                }

                                productDropdown.addEventListener("change", function () {
                                    const selectedValue = productDropdown.value;
                                    if (selectedValue === "1") {
                                        window.location.href = "quanlyspcController";
                                    } else if (selectedValue === "2") {
                                        window.location.href = "quanlyspnccController";
                                    }
                                });
                            </script>

                            
                        </div>
                        <div class="apps__content-add-product" style="margin-left: 450px" onclick="showModalimport();">
                                        <button>Import</button>
                        </div>
                        <div class="apps__content-add-product" onclick="showModalAdd();">
                            <button><i class="fas fa-cart-plus"></i> Nhập hàng</button>
                        </div>
                         
                    </div>

                    <div class="apps__content-container">
                        <div class="apps__content-container-title hide-on-mobile">
                            <div class="app__content-container-title hide-on-mobile">
                                <div class="app__content-title">
                                    <div class="app__content-title-id">
                                        <p>ID</p>
                                    </div>

                                    <div class="app__content-title-name">
                                        <p>Tên sản phẩm</p>
                                    </div>

                                    <div class="app__content-title-quantity">
                                        <p>Số lượng</p>
                                    </div>

                                    <div class="app__content-title-type">
                                        <p>Category</p>
                                    </div>

                                    <div class="app__content-title-user">
                                        <p>Brand</p>
                                    </div>
                                    <div class="app__content-title-user">
                                        <p>Nhà cung cấp </p>
                                    </div>

                                    <div class="app__content-title-img">
                                        <p>Ảnh sản phẩm</p>
                                    </div>
                                    <div class="app__content-title-img">
                                        <p>Origin</p>
                                    </div>

                                    <div class="app__content-title-prices">
                                        <p>Giá bán</p>
                                    </div>

                                    <div class="app__content-title-tools">
                                        <p>Công cụ</p>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="apps__content-container-show hide-on-mobile-admin">
                            <c:forEach items="${spnccList}" var="spncc">
                                <div class="app__content-view">
                                    <div class="app__content-view-id">
                                        <p>${spncc.id}</p>
                                    </div>

                                    <div class="app__content-view-name">
                                        <p title='${spncc.name}'>${spncc.name}</p>
                                    </div>

                                    <div class="app__content-view-quantity">
                                        <p>${spncc.quantity}</p>
                                    </div>

                                    <div class="app__content-view-type">
                                        <p>${spncc.category}</p>
                                    </div>

                                    <div class="app__content-view-user">
                                        <p>${spncc.brand}</p>
                                    </div>
                                    <div class="app__content-view-user">
                                        <p>${spncc.namencc}</p>
                                    </div>

                                    <div class="app__content-view-img">
                                        <img class="overflow-hidden object-cover aspect-video" src="${spncc.img}" alt="" style="max-width: 60%; max-height: 60%;">
                                    </div>

                                    <div class="app__content-view-prices">
                                        <p>${spncc.origin}</p>
                                    </div>
                                    <div class="app__content-view-prices">
                                        <p>${spncc.price}</p>
                                    </div>

                                    <div class="app__content-view-tools">
                                        <button class="app__content-view-tools-update edit-product" name="edit-product" onclick="showModalUpdate(${spncc.id},${spncc.id_producer},${spncc.id_category},${spncc.id_brand}, '${spncc.name}', '${spncc.origin}', '${spncc.price}', '${spncc.quantity}', '${spncc.description}', '${spncc.img}', '${spncc.img1}', '${spncc.img2}', '${spncc.img3}');" value="">
                                            <i class="fas fa-pen"></i>
                                        </button>

                                        <button class="app__content-view-tools-delete del-product" name="delete-product" onclick="confirmDelete(${spncc.id});" value="">
                                            <i class="fas fa-trash-alt"></i>
                                        </button>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="pagination">
                            <div class="pagination__list">
                                <!-- Liên kết đến trang chính -->
                                <a href="quanlyspnccController" class="pagination__list-link">
                                    <div class="pagination__list-item">Trang chủ</div>
                                </a>

                                <!-- Liên kết trang trước -->
                                <c:if test="${currentPage > 1}">
                                    <a href="quanlyspnccController?page=${currentPage - 1}" id="prePage" class="pagination__list-link">
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
                                            <a href="quanlyspnccController?page=${pageVar.index}&searchUser=${param.searchUser}" class="pagination__list-link">
                                                <div class="pagination__list-item">${pageVar.index}</div>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <!-- Liên kết trang tiếp theo -->
                                <c:if test="${currentPage < totalPages}">
                                    <a href="quanlyspnccController?page=${currentPage + 1}" id="nextPage" class="pagination__list-link">
                                        <div class="pagination__list-item">Tiếp theo</div>
                                    </a>
                                </c:if>
                            </div>
                        </div>

                        <!--                    <div class="button-container">
                                                   <a href="#" class="half-button">Quản lý sản phẩm chính</a>
                                                   <a href="#" class="half-button">Quản lý sản phẩm nhà cung cấp</a>
                                               </div>    -->

                        <div class="modal__import-product" style="display: none;">
                            <div class="modal__add-product-overlay" onclick="hideModalimport();"></div>

                            <div class="modal__add-product-content" >
                                <div class="modal__add-product-header">
                                    <p class="modal__add-product-header-heading" style="margin-left: 155px">Import</p>
                                </div>

                                <div class="modal__add-product-form">
                                   
                                        <div class="modal__add-product-file-input" style="margin-top: 20px">
                                            <label for="excelFile" style="align-items: center;margin-left: 4px ">Chọn file Excel:</label>
                                            <input type="file" name="excelFile" id="excelFile" accept=".xlsx, .xls" />
                                        </div>

                                        <div class="modal__add-product-btn" id="btnadminimportProduct" style="display: block;">
                                            <button onclick="actionimportProduct();" style="margin-left: 65px">Import</button>
                                        </div>
                                    
                                </div>

                                <div class="modal__add-product-btn-close" onclick="hideModalimport();">
                                    <i class="fas fa-window-close"></i>
                                </div>
                            </div>
                        </div>

                       
                        <div class="modal__add-product" style="display: none;">
                            <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>

                            <div class="modal__add-product-content" style="top:1%">

                                <div class="modal__add-product-header">
                                    <p class="modal__add-product-header-heading addP">Thêm mới sản phẩm</p>
                                    <p class="modal__add-product-header-heading updateP" style="display:none">Cập nhật sản phẩm</p>
                                    
                                </div>
                                <!--FORM_THEMMOI-->

                                <input type="hidden" id="txtProductId_update" value="">
                                <div class="modal__add-product-user">
                                    <label for="selectnccproduct" >Nhà cung cấp:</label>
                                    <select name="" id="selectnccproduct">
                                        <c:forEach items="${ncc}" var="ncc">
                                            <option value="${ncc.id}">${ncc.name}</option>
                                        </c:forEach>
                                    </select>

                                </div>

                                <div class="modal__add-product-row"style="display: flex; align-items: center;">
                                    <div class="modal__add-product-type">
                                        <div style="margin-left: 15px;">
                                            <label for="selectcategoryproduct" >Loại:</label>
                                            <select name="" id="selectcategoryproduct">
                                                <c:forEach items="${category}" var="category">
                                                    <option value="${category.id}">${category.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="modal__add-product-type">
                                        <div style="margin-left: 15px;">
                                            <label for="selectbrandproduct" >Thương hiệu:</label>
                                            <select name="" id="selectbrandproduct">
                                                <c:forEach items="${brand}" var="brand">
                                                    <option value="${brand.id}">${brand.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>


                                <div class="modal__add-product-origin">
                                    <label for="txtadminname"style="margin-left: 10px;">Tên:</label>
                                    <input type="text" placeholder="Tên sp" value="" id="txtadminname">
                                </div>

                                <div class="modal__add-product-origin">
                                    <label for="txtadminorigin"style="margin-left: -16px;">Xuất xứ:</label>
                                    <input type="text" placeholder="Xuất xứ" value="" id="txtadminorigin">
                                </div>

                                <div class="modal__add-product-prices">
                                    <label for="txtadminprices"style="margin-left: 11px;">Giá:</label>
                                    <input type="text" placeholder="Giá sản phẩm" value="" id="txtadminprices">
                                </div>

                                <div class="modal__add-product-quantity">
                                    <label for="txtadminquantity"style="margin-left: -24px;">Số lượng:</label>
                                    <input type="text" placeholder="Số lượng sản phẩm" value="" id="txtadminquantity">
                                </div>

                                <div style="display: flex; margin-top: 10px" >
                                    <label for="txtadmindescription"style="margin-left: 30px; margin-right: 10px">Mô tả:</label>
                                    <div class="modal__add-product-description">
                                        <textarea placeholder="Mô tả" id="txtadmindescription" name="description" rows="2" cols="32"></textarea>
                                    </div>
                                </div>

                                <div class="modal__add-product-description">
                                    <label for="fadminimg" class="custom-file-label"style="margin-left: 3px;"> Ảnh  :</label>
                                    <input type="url" name="" placeholder="URL"id="fadminimg">
                                </div>
                                <div class="modal__add-product-description">
                                    <label for="fadminimg1" class="custom-file-label"style="margin-left: -6px;">Ảnh 1:</label>
                                    <input type="url" placeholder="URL" name="" id="fadminimg1">
                                </div>

                                <div class="modal__add-product-description">
                                    <label for="fadminimg2" class="custom-file-label"style="margin-left: -6px;">Ảnh 2:</label>
                                    <input type="url" name=""placeholder="URL" id="fadminimg2">
                                </div>

                                <div class="modal__add-product-description">
                                    <label for="fadminimg3" class="custom-file-label"style="margin-left: -6px;">Ảnh 3:</label>
                                    <input type="url" name=""placeholder="URL" id="fadminimg3">
                                </div>

                                <div class="modal__add-product-btn" id="btnadminaddProduct" style="display: block;">
                                    <button onclick="actionAddProduct();">Thêm mới</button>
                                </div>


                                <div class="modal__add-product-btn" style="display: none;" id="btnadminupdateProduct">
                                    <button onclick="actionUpdateProduct();" >Cập nhật</button>
                                </div>

                                <div class="modal__add-product-btn-close" onclick="hideModalAdd();">
                                    <i class="fas fa-window-close"></i>
                                </div>
                            </div>
                        </div>

                    </div>



                </div>


            </div>

        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
                                    function actionAddProduct() {
                                    var ncc = document.getElementById("selectnccproduct").value;
                                    var category = document.getElementById("selectcategoryproduct").value;
                                    var brand = document.getElementById("selectbrandproduct").value;
                                    var name = document.getElementById("txtadminname").value;
                                    var origin = document.getElementById("txtadminorigin").value;
                                    var price = document.getElementById("txtadminprices").value;
                                    var quantity = document.getElementById("txtadminquantity").value;
                                    var description = document.getElementById("txtadmindescription").value;
                                    var fad = document.getElementById("fadminimg").value;
                                    var fad1 = document.getElementById("fadminimg1").value;
                                    var fad2 = document.getElementById("fadminimg2").value;
                                    var fad3 = document.getElementById("fadminimg3").value;

                                    // Kiểm tra xem tất cả các trường cần thiết đã được điền đầy đủ
                                    if (ncc === "" || category === "" || brand === "" || name === "" || origin === "" || price === "" || quantity === "" || description === "" || fad === "" || fad1 === "" || fad2 === "" || fad3 === "") {
                                        alert("Không được để trống bất kỳ trường nào.");
                                        return;
                                    }
                                      if (isNaN(quantity) || isNaN(price)) {
                                                    alert("Giá và số lượng phải là số!");
                                                return ;}

                                    var xhr = new XMLHttpRequest();
                                    xhr.open("POST", "quanlyspnccController", true);
                                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                                    // Tạo chuỗi dữ liệu bao gồm tất cả các trường
                                    var data = "action=addProductToCTPN&selectnccproduct=" + ncc + "&selectcategoryproduct=" + category + "&selectbrandproduct=" + brand + "&txtadminname=" + name + "&txtadminorigin=" + origin + "&txtadminprices=" + price + "&txtadminquantity=" + quantity + "&txtadmindescription=" + description + "&fadminimg=" + fad + "&fadminimg1=" + fad1 + "&fadminimg2=" + fad2 + "&fadminimg3=" + fad3;

                                    xhr.onreadystatechange = function () {
                                        if (xhr.readyState === 4) {
                                            if (xhr.status === 200) {
                                                var confirmation = confirm("Bạn có muốn xuất dữ liệu không?");
                                                if (confirmation) {
                                                    var exportXhr = new XMLHttpRequest();
                                                    exportXhr.open("POST", "quanlyspnccController", true);
                                                    exportXhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                                    var exportData = "action=exportProductToCTPN&selectnccproduct=" + ncc + "&selectcategoryproduct=" + category + "&selectbrandproduct=" + brand + "&txtadminname=" + name + "&txtadminorigin=" + origin + "&txtadminprices=" + price + "&txtadminquantity=" + quantity + "&txtadmindescription=" + description + "&fadminimg=" + fad + "&fadminimg1=" + fad1 + "&fadminimg2=" + fad2 + "&fadminimg3=" + fad3;

                                                    exportXhr.onreadystatechange = function () {
                                                        if (exportXhr.readyState === 4) {
                                                            if (exportXhr.status === 200) {
                                                                alert("Dữ liệu đã được xuất thành công.");
                                                                location.reload();
                                                            } else {
                                                                alert("Lỗi khi xuất dữ liệu.");
                                                            }
                                                        }
                                                    };

                                                    exportXhr.send(exportData);
                                                } else {
                                                    alert("Sản phẩm đã được thêm thành công.");
                                                    location.reload();
                                                }
                                            } else {
                                                alert("Tên sản phẩm đã tồn tại!!");
                                            }
                                        }
                                    };

                                    xhr.send(data);
                                }

                                    function actionUpdateProduct() {
                                                var id = document.getElementById("txtProductId_update").value;
                                                var ncc = document.getElementById("selectnccproduct").value;
                                                var category = document.getElementById("selectcategoryproduct").value;
                                                var brand = document.getElementById("selectbrandproduct").value;
                                                var name = document.getElementById("txtadminname").value;
                                                var origin = document.getElementById("txtadminorigin").value;
                                                var price = document.getElementById("txtadminprices").value;
                                                var quantity = document.getElementById("txtadminquantity").value;
                                                var description = document.getElementById("txtadmindescription").value;
                                                var fad = document.getElementById("fadminimg").value;
                                                var fad1 = document.getElementById("fadminimg1").value;
                                                var fad2 = document.getElementById("fadminimg2").value;
                                                var fad3 = document.getElementById("fadminimg3").value;

                                                if (
                                                    ncc === "" ||
                                                    category === "" ||
                                                    brand === "" ||
                                                    name === "" ||
                                                    origin === "" ||
                                                    price === "" ||
                                                    quantity === "" ||
                                                    description === "" ||
                                                    fad === "" ||
                                                    fad1 === "" ||
                                                    fad2 === "" ||
                                                    fad3 === ""
                                                ) {
                                                    alert("Không được để trống bất kỳ trường nào.");
                                                    return;
                                                }
                                                if (isNaN(quantity) || isNaN(price)) {
                                                    alert("Giá và số lượng phải là số!");
                                                return ;}

                                                var xhr = new XMLHttpRequest();
                                                xhr.open("POST", "quanlyspnccController", true);
                                                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                                                var data =
                                                    "action=updateProductToCTPN&id=" +
                                                    id +
                                                    "&selectnccproduct=" +
                                                    ncc +
                                                    "&selectcategoryproduct=" +
                                                    category +
                                                    "&selectbrandproduct=" +
                                                    brand +
                                                    "&txtadminname=" +
                                                    name +
                                                    "&txtadminorigin=" +
                                                    origin +
                                                    "&txtadminprices=" +
                                                    price +
                                                    "&txtadminquantity=" +
                                                    quantity +
                                                    "&txtadmindescription=" +
                                                    description +
                                                    "&fadminimg=" +
                                                    fad +
                                                    "&fadminimg1=" +
                                                    fad1 +
                                                    "&fadminimg2=" +
                                                    fad2 +
                                                    "&fadminimg3=" +
                                                    fad3;

                                                xhr.onreadystatechange = function () {
                                                    if (xhr.readyState === 4) {
                                                        if (xhr.status === 200) {
                                                            var confirmation = confirm("Sản phẩm đã được cập nhật thành công. Bạn có muốn in phiếu không?");

                                                            if (confirmation) {
                                                                var exportData = "action=actionupdateexport&id=" + id + "&selectnccproduct=" + ncc + "&selectcategoryproduct=" + category + "&selectbrandproduct=" + brand + "&txtadminname=" + name + "&txtadminorigin=" + origin + "&txtadminprices=" + price + "&txtadminquantity=" + quantity + "&txtadmindescription=" + description + "&fadminimg=" + fad + "&fadminimg1=" + fad1 + "&fadminimg2=" + fad2 + "&fadminimg3=" + fad3;

                                                                var exportXhr = new XMLHttpRequest();
                                                                exportXhr.open("POST", "quanlyspnccController", true);
                                                                exportXhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                                                                exportXhr.onreadystatechange = function () {
                                                                    if (exportXhr.readyState === 4) {
                                                                        if (exportXhr.status === 200) {
                                                                            var pdfConfirmation = confirm("Dữ liệu đã được export thành công. Bạn có muốn in phiếu không?");
                                                                            if (pdfConfirmation) {
                                                                                var pdfXhr = new XMLHttpRequest();
                                                                                pdfXhr.open("POST", "quanlyspnccController", true);
                                                                                pdfXhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                                                                                pdfXhr.onreadystatechange = function () {
                                                                                    if (pdfXhr.readyState === 4) {
                                                                                        if (pdfXhr.status === 200) {
                                                                                            alert("Phiếu đã được in.");
                                                                                        } else {
                                                                                            alert("Lỗi khi in phiếu.");
                                                                                        }
                                                                                    }
                                                                                };

                                                                                pdfXhr.send(exportData);
                                                                            }
                                                                        } else {
                                                                            alert("Lỗi khi export dữ liệu.");
                                                                        }
                                                                    }
                                                                };

                                                                exportXhr.send(exportData);
                                                            }

                                                            location.reload();
                                                        } else {
                                                            alert("Lỗi khi cập nhật sản phẩm.");
                                                        }
                                                    }
                                                };

                                                xhr.send(data);
                                            }



                                        function confirmDelete(id) {
                                            var confirmDelete = confirm("Bạn có chắc chắn muốn xóa sản phẩm này?");
                                            if (confirmDelete) {
                                                deleteProduct(id);
                                            }
                                        }

                                        function deleteProduct(id) {
                                            var xhr = new XMLHttpRequest();
                                            xhr.open("POST", "quanlyspnccController", true);
                                            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                            var data = "action=deleteProduct&id=" + id;

                                            xhr.onreadystatechange = function () {
                                                if (xhr.readyState === 4) {
                                                    if (xhr.status === 200) {
                                                        alert("Sản phẩm đã được xóa thành công.");
                                                        // Cập nhật giao diện hoặc thực hiện bất kỳ thay đổi nào khác tùy theo yêu cầu.
                                                        location.reload();
                                                    } else if (xhr.status === 500) {
                                                        alert("Lỗi server: Không thể xóa sản phẩm.");
                                                    }
                                                }
                                            };

                                            xhr.send(data);
                                        }
                                    
                                    function showModalAdd()
                                    {
                                        document.querySelector('.updateP').style.display = "none";
                                        document.querySelector('.addP').style.display = "block";
                                        document.querySelector('.modal__add-product').style.display = "block";
                                        document.getElementById('btnadminupdateProduct').style.display = "none";
                                        document.getElementById('btnadminaddProduct').style.display = "block";
                                        document.getElementById("txtProductId_update").value = "";
                                        document.getElementById("selectnccproduct").value = "";
                                        document.getElementById("selectcategoryproduct").value = "";
                                        document.getElementById("selectbrandproduct").value = "";
                                        document.getElementById("txtadminname").value = "";
                                        document.getElementById("txtadminorigin").value = "";
                                        document.getElementById("txtadminprices").value = "";
                                        document.getElementById("txtadminquantity").value = "";
                                        document.getElementById("txtadmindescription").value = "";
                                        document.getElementById("fadminimg").value = "";
                                        document.getElementById("fadminimg1").value = "";
                                        document.getElementById("fadminimg2").value = "";
                                        document.getElementById("fadminimg3").value = "";

                                    }
                                    function hideModalAdd()
                                    {
                                        document.querySelector('.modal__add-product').style.display = "none";

                                    }
                                    function hideModalimport()
                                    {
                                        document.querySelector('.modal__import-product').style.display = "none";

                                    }
                                    function showModalimport(){
                                        document.querySelector('.modal__import-product').style.display = "block";
                                       document.querySelector('.modal__add-product-header-heading').innerText = "import";
                                        
                                    }

                                    function showModalUpdate(id, namencc, category, brand, name, origin, price, quantity, description, img, img1, img2, img3)
                                    {
                                            document.querySelector('.updateP').style.display = "block";
                                        document.querySelector('.addP').style.display = "none";
                                        document.querySelector('.modal__add-product').style.display = "block";
                                        document.getElementById('btnadminupdateProduct').style.display = "block";
                                        document.getElementById('btnadminaddProduct').style.display = "none";

                                        document.getElementById("txtProductId_update").value = id;
                                        document.getElementById("selectnccproduct").value = namencc;
                                        document.getElementById("selectcategoryproduct").value = category;
                                        document.getElementById("selectbrandproduct").value = brand;
                                        document.getElementById("txtadminname").value = name;
                                        document.getElementById("txtadminorigin").value = origin;
                                        document.getElementById("txtadminprices").value = price;
                                        document.getElementById("txtadminquantity").value = quantity;
                                        document.getElementById("txtadmindescription").value = description;
                                        document.getElementById("fadminimg").value = img;
                                        document.getElementById("fadminimg1").value = img1;
                                        document.getElementById("fadminimg2").value = img2;
                                        document.getElementById("fadminimg3").value = img3;


                                    }
                                    
                                    function actionimportProduct() {
                                    var fileInput = document.getElementById('excelFile');
                                    var file = fileInput.files[0]; // Lấy file từ input

                                    var formData = new FormData();
                                    formData.append('excelFile', file); // Thêm file vào FormData

                                    fetch('UploadExcelServlet', {
                                        method: 'POST',
                                        body: formData
                                    })
                                    .then(response => {
                                        if (response.ok) {
                                            return response.text();
                                        } else {
                                            throw new Error('Network response was not ok.');
                                        }
                                    })
                                    .then(data => {
                                        alert("Dữ liệu từ file Excel đã được xử lý: " + data);
                                        location.reload(); // Refresh trang hoặc cập nhật giao diện
                                    })
                                    .catch(error => {
                                        alert("Có lỗi xảy ra khi xử lý file Excel: " + error.message);
                                    });
                                }


        </script>
    </body>
</html>
