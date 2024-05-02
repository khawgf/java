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

                <div class="apps__content">

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
                            <form action="quanlyspcController" method="get" style="display: flex;align-items: center;" id="searchForm">
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

                                    <div class="app__content-title-img">
                                        <p>Ảnh sản phẩm</p>
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
                            <c:forEach items="${spcList}" var="spc">
                                <div class="app__content-view">
                                    <div class="app__content-view-id">
                                        <p>${spc.id}</p>
                                    </div>

                                    <div class="app__content-view-name">
                                        <p title='${spc.name}'>${spc.name}</p>
                                    </div>

                                    <div class="app__content-view-quantity">
                                        <p>${spc.quantity}</p>
                                    </div>

                                    <div class="app__content-view-type">
                                        <p>${spc.category}</p>
                                    </div>

                                    <div class="app__content-view-user">
                                        <p>${spc.brand}</p>
                                    </div>

                                    <div class="app__content-view-img">
                                        <img class="overflow-hidden object-cover aspect-video" src="${spc.img}" alt="" style="max-width: 60%; max-height: 60%;">
                                    </div>


                                    <div class="app__content-view-prices">
                                        <p>${spc.price}</p>
                                    </div>

                                    <div class="app__content-view-tools">
                                        <button class="app__content-view-tools-update edit-product" name="edit-product" onclick="showModalUpdate(${spc.id},${spc.quantity},${spc.price},${spc.rating},${spc.discount});" value="">
                                            <i class="fas fa-pen"></i>
                                        </button>

                                        <button class="app__content-view-tools-delete del-product" name="delete-product" onclick="confirmDelete(${spc.id});" value="">
                                            <i class="fas fa-trash-alt"></i>
                                        </button>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                         <div class="pagination">
                            <div class="pagination__list">
                                <!-- Liên kết đến trang chính -->
                                <a href="quanlyspcController" class="pagination__list-link">
                                    <div class="pagination__list-item">Trang chủ</div>
                                </a>

                                <!-- Liên kết trang trước -->
                                <c:if test="${currentPage > 1}">
                                    <a href="quanlyspcController?page=${currentPage - 1}" id="prePage" class="pagination__list-link">
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
                                            <a href="quanlyspcController?page=${pageVar.index}&searchUser=${param.searchUser}" class="pagination__list-link">
                                                <div class="pagination__list-item">${pageVar.index}</div>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <!-- Liên kết trang tiếp theo -->
                                <c:if test="${currentPage < totalPages}">
                                    <a href="quanlyspcController?page=${currentPage + 1}" id="nextPage" class="pagination__list-link">
                                        <div class="pagination__list-item">Tiếp theo</div>
                                    </a>
                                </c:if>
                            </div>
                        </div>

                        <!--                    <div class="button-container">
                                                   <a href="#" class="half-button">Quản lý sản phẩm chính</a>
                                                   <a href="#" class="half-button">Quản lý sản phẩm nhà cung cấp</a>
                                               </div>    -->

<!--////////////////////////////////////////////add_product-->
                        <div class="modal__add-product" style="display: none;">
                            <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>

                            <div class="modal__add-product-content">

                                <div class="modal__add-product-header">
                                    <p class="modal__add-product-header-heading">Thêm mới sản phẩm</p>
                                </div>

                                <div class="modal__add-product-id">
                                    <select name="spncc" id="selectIDspncc" style="width: 80%; padding: 10px; border: 1px solid #ccc; border-radius: 5px; font-size: 16px; background-color: #fff; color: #333;" onchange="updateProductPrice(this.value)">
                                        <c:forEach items="${suppliers}" var="supplier">
                                            <option value="${supplier.id}">${supplier.name}</option>
                                        </c:forEach>
                                    </select>

                                    <div class="modal__add-product-quantity">
                                        <input type="text" placeholder="Số lượng sản phẩm" value="" id="txtadminquantity" style="width: 80%; padding: 10px; border: 1px solid #ccc; border-radius: 5px; font-size: 16px; background-color: #fff; color: #333;">
                                    </div>

                                    <div class="modal__add-product-prices">
                                        <input type="text" placeholder="Giá sản phẩm" value="" id="txtadminprices" style="width: 80%; padding: 10px; border: 1px solid #ccc; border-radius: 5px; font-size: 16px; background-color: #fff; color: #333;">
                                    </div>



                                    <div class="modal__add-product-btn" id="btnadminaddProduct" style="display: block;"><button onclick="actionAddProduct();">Thêm mới</button></div>
                                    <script>
                                        //////////////////////////// autoload
                                        window.onload = function () {
                                            // Gọi hàm updateProductPrice với giá trị ban đầu của combobox (selectedSupplierId)
                                            var selectedSupplierId = document.getElementById("selectIDspncc").value; // Thay "yourComboBoxId" bằng ID thực tế của combobox
                                            updateProductPrice(selectedSupplierId);
                                        }////////////////////////////////////update price_combobox
                                        function updateProductPrice(selectedSupplierId) {
                                            // Gọi DAO để lấy giá sản phẩm
                                            var xhr = new XMLHttpRequest();
                                            xhr.open("GET", "quanlyspcController?action=getProductPrice&id_spncc=" + selectedSupplierId, true);

                                            xhr.onreadystatechange = function () {
                                                if (xhr.readyState === 4 && xhr.status === 200) {
                                                    var price = xhr.responseText;
                                                    document.getElementById("txtadminprices").value = price;
                                                }
                                            };

                                            xhr.send();
                                        }
                                   //////////////////////////////////////// addproduct_spncc
                                         function actionAddProduct() {
                                            var spncc = document.getElementById("selectIDspncc").value;
                                            var quantity = document.getElementById("txtadminquantity").value;
                                            var price = document.getElementById("txtadminprices").value;
                                            if (isNaN(quantity) || isNaN(price)) {
                                                    alert("Giá và số lượng phải là số!");
                                                return ;}
                                            if (quantity === "" || spncc === "" || price === "") {
                                                alert("Số lượng không được để trống");
                                                return;
                                            }

                                            var xhr = new XMLHttpRequest();
                                            xhr.open("POST", "quanlyspcController", true);
                                            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                            var data = "action=addProductToCTPX&spncc=" + spncc + "&quantity=" + quantity + "&price=" + price;

                                            xhr.onreadystatechange = function() {
                                                if (xhr.readyState === 4) {
                                                    if (xhr.status === 200) {
                                                        var confirmation = confirm("Bạn có muốn xuất dữ liệu không?");

                                                        if (confirmation) {
                                                            var exportXhr = new XMLHttpRequest();
                                                            exportXhr.open("POST", "quanlyspcController", true);
                                                            exportXhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                                            var exportData = "action=exportProductToCTPX&spncc=" + spncc + "&quantity=" + quantity + "&price=" + price;

                                                            exportXhr.onreadystatechange = function() {
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
                                                            alert("Đã thêm sản phẩm thành công.");
                                                            location.reload();
                                                        }
                                                    } else {
                                                        alert("Số lượng trong kho không đủ");
                                                    }
                                                }
                                            };

                                        xhr.send(data);
                                    }

                                        
                                        
                                         function actionUpdateProduct() {
                                                var discount = document.getElementById("txtdiscount_update").value;
                                                var rating = document.getElementById("txtadminrating_update").value;
                                                var id = document.getElementById("txtProductId_update").value;
                                                var quantity = document.getElementById("txtadminquantity_update").value;
                                                var price = document.getElementById("txtadminprices_update").value;

                                                if (quantity === "" || rating === "" || price === "" || discount === "") {
                                                    alert("Số lượng, rating, price, và discount không được để trống.");
                                                    return;
                                                }
                                                if (isNaN(quantity) || isNaN(price)) {
                                                    alert("Giá và số lượng phải là số!");
                                                return ;}
                                                var xhr = new XMLHttpRequest();
                                                xhr.open("POST", "quanlyspcController", true);
                                                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                                var data = "action=updateProduct&id=" + id + "&quantity=" + quantity + "&price=" + price + "&rating=" + rating + "&discount=" + discount;

                                                xhr.onreadystatechange = function() {
                                                    if (xhr.readyState === 4) {
                                                        if (xhr.status === 200) {
                                                            var confirmation = confirm("Bạn có muốn in thông tin sản phẩm đã cập nhật không?");
                                                            if (confirmation) {
                                                                var exportXhr = new XMLHttpRequest();
                                                                exportXhr.open("POST", "quanlyspcController", true);
                                                                exportXhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                                                var exportData = "action=exportUpdatedProductInfo&id=" + id + "&quantity=" + quantity + "&price=" + price + "&rating=" + rating + "&discount=" + discount;

                                                                exportXhr.onreadystatechange = function() {
                                                                    if (exportXhr.readyState === 4) {
                                                                        if (exportXhr.status === 200) {
                                                                            alert("Thông tin sản phẩm đã được in ra thành công.");
                                                                            location.reload();
                                                                        } else {
                                                                            alert("Lỗi khi in thông tin sản phẩm.");
                                                                        }
                                                                    }
                                                                };

                                                                exportXhr.send(exportData);
                                                            } else {
                                                                alert("Sản phẩm đã được cập nhật thành công.");
                                                                location.reload();
                                                            }
                                                        } else if (xhr.status === 500) {
                                                            alert("Lỗi server: Số lượng trong kho không đủ.");
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
                                            xhr.open("POST", "quanlyspcController", true);
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


                                    </script>
                                   

                                    <div class="modal__add-product-btn-close" onclick="hideModalAdd();">
                                        <i class="fas fa-window-close"></i>
                                    </div>
                                </div>
                            </div>

                        </div>
<!--////////////////////////////////////////////update_product-->
                          <div class="modal__add-product Update_modal" style="display: none;">
                            <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>
                            <div class="modal__add-product-content">
                                <div class="modal__add-product-header">
                                        <p class="modal__add-product-header-heading">Cập nhật sản phẩm</p>
                                    </div>

                                    <div class="modal__add-product-id">
                                        <!-- Thêm một input ẩn để lưu id của sản phẩm -->
                                        <input type="hidden" id="txtProductId_update" value="">

                                       <!-- Thêm trường dữ liệu cho Rating sản phẩm -->
                                <div class="modal__add-product-quantity">
                                    <label for="txtadminrating_update" style="margin-left: 24px;">Rating:</label>
                                    <input type="text" placeholder="Rating sản phẩm" value="" id="txtadminrating_update" style="width: 80%; padding: 10px; border: 1px solid #ccc; border-radius: 5px; font-size: 16px; background-color: #fff; color: #333;">
                                </div>

                                <!-- Thêm trường dữ liệu cho Discount sản phẩm -->
                                <div class="modal__add-product-quantity">
                                    <label for="txtdiscount_update" style="margin-left: 10px;">Discount:</label>
                                    <input type="text" placeholder="Discount sản phẩm" value="" id="txtdiscount_update" style="width: 80%; padding: 10px; border: 1px solid #ccc; border-radius: 5px; font-size: 16px; background-color: #fff; color: #333;">
                                </div>

                                <!-- Thêm trường dữ liệu cho Số lượng sản phẩm -->
                                <div class="modal__add-product-quantity">
                                    <label for="txtadminquantity_update" style="margin-left: 10px;">Số lượng:</label>
                                    <input type="text" placeholder="Số lượng sản phẩm" value="" id="txtadminquantity_update" style="width: 80%; padding: 10px; border: 1px solid #ccc; border-radius: 5px; font-size: 16px; background-color: #fff; color: #333;">
                                </div>

                                <!-- Thêm trường dữ liệu cho Giá sản phẩm -->
                                <div class="modal__add-product-prices">
                                    <label for="txtadminprices_update" style="margin-left: 43px;">Giá:</label>
                                    <input type="text" placeholder="Giá sản phẩm" value="" id="txtadminprices_update" style="width: 80%; padding: 10px; border: 1px solid #ccc; border-radius: 5px; font-size: 16px; background-color: #fff; color: #333;">
                                </div>

                                    </div>

                                    <div class="modal__add-product-btn" style="display: block;" id="btnadminupdateProduct">
                                        <button onclick="actionUpdateProduct();">Cập nhật</button> <!-- Gọi hàm updateProduct() khi nút được nhấn -->
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
                            
                            function showModalAdd()
                            {
                                document.querySelector('.modal__add-product').style.display = "block";
                                document.querySelector('.modal__add-product-header-heading').innerText = "Thêm sản phẩm mới";
                            }
                            function hideModalAdd()
                            {
                                document.querySelector('.modal__add-product').style.display = "none";
                                 document.querySelector('.Update_modal').style.display = "none";
                            }
                            function showModalUpdate(id,quantity,price,rating,discount)
                            {
                                
                                
                                document.querySelector('.Update_modal').style.display = "block";
                                document.querySelector('.modal__add-product-header-heading').innerText = "Cập nhật sản phẩm";
                                
                                            
                                document.getElementById("txtProductId_update").value = id;
                                document.getElementById("txtadminrating_update").value=rating;
                                document.getElementById("txtdiscount_update").value = discount;
                                document.getElementById("txtadminquantity_update").value = quantity;
                                document.getElementById("txtadminprices_update").value = price;
                                        
                                
                            }

            </script>
    </body>
</html>
