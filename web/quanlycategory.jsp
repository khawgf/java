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
                                <label for="product-dropdown">Chọn danh sách :</label>
                                <select id="product-dropdown" style="font-size: 16px; width: 250px;">
                                    <option value="1" style="font-size: 14px;">Danh sách brand</option>
                                    <option value="2" style="font-size: 14px;">Danh sách category</option>
                                </select>
                            </div>
                        <form action="quanlycategoryController" method="get" style="display: flex;align-items: center;" id="searchForm">
                            <input type="text" placeholder="Search something........." class="search-input" id="txtsearch" name="searchUser">
                            <button class="header__search-btn" type="submit">
                                <i class="search-btn fas fa-search icon-search"></i>
                            </button>
                        </form>
                        <script>
                                const productDropdown = document.getElementById("product-dropdown");

                                // Kiểm tra URL hiện tại để thiết lập giá trị mặc định cho combobox
                                const currentURL = window.location.pathname;
                                if (currentURL.includes("quanlybrandController")) {
                                    productDropdown.value = "1"; // Chọn Danh sách sản phẩm chính
                                } else if (currentURL.includes("quanlycategoryController")) {
                                    productDropdown.value = "2"; // Chọn Danh sách sản phẩm kho
                                }

                                productDropdown.addEventListener("change", function () {
                                    const selectedValue = productDropdown.value;
                                    if (selectedValue === "1") {
                                        window.location.href = "quanlybrandController";
                                    } else if (selectedValue === "2") {
                                        window.location.href = "quanlycategoryController";
                                    }
                                });
                            </script>

                        <div class="apps__content-add-product" onclick="showModalAdd();">
                            <button><i class="fas fa-cart-plus"></i> Thêm category</button>
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
                                        <p>Tên category</p>
                                    </div>

                                   
                                    

                                    <div class="app__content-title-tools">
                                        <p>Công cụ</p>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="apps__content-container-show hide-on-mobile-admin">
                            <c:forEach items="${spcList}" var="spncc">
                                <div class="app__content-view">
                                    <div class="app__content-view-id">
                                        <p>${spncc.id}</p>
                                    </div>

                                    <div class="app__content-view-name">
                                        <p title='${spncc.name}'>${spncc.name}</p>
                                    </div>

                                    

                                  

                                    <div class="app__content-view-tools">
                                        <button class="app__content-view-tools-update edit-product" name="edit-product" onclick="showModalUpdate(${spncc.id},'${spncc.name}');" value="">
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
                                <a href="quanlycategoryController" class="pagination__list-link">
                                    <div class="pagination__list-item">Trang chủ</div>
                                </a>

                                <!-- Liên kết trang trước -->
                                <c:if test="${currentPage > 1}">
                                    <a href="quanlycategoryController?page=${currentPage - 1}" id="prePage" class="pagination__list-link">
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
                                        <a href="quanlycategoryController?page=${pageVar.index}&searchUser=${param.searchUser}" class="pagination__list-link">
                                            <div class="pagination__list-item">${pageVar.index}</div>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                                <!-- Liên kết trang tiếp theo -->
                                <c:if test="${currentPage < totalPages}">
                                    <a href="quanlycategoryController?page=${currentPage + 1}" id="nextPage" class="pagination__list-link">
                                        <div class="pagination__list-item">Tiếp theo</div>
                                    </a>
                                </c:if>
                            </div>
                        </div>

                       

                        <div class="modal__add-product" style="display: none;">
                            <div class="modal__add-product-overlay" onclick="hideModalAdd();"></div>

                            <div class="modal__add-product-content">

                                <div class="modal__add-product-header">
                                    <p class="modal__add-product-header-heading">Thêm mới category</p>
                                </div>
                                <!--FORM_THEMMOI-->

                                <input type="hidden" id="txtProductId_update" value="">
                                


                                <div class="modal__add-product-origin">
                                    <label for="txtadminname"style="margin-left: 10px;">Tên:</label>
                                    <input type="text" placeholder="Tên category" value="" id="txtadminname">
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
                                            var name = document.getElementById("txtadminname").value;
                                           
                                           alert(name);
                                            // Kiểm tra xem tất cả các trường cần thiết đã được điền đầy đủ
                                            if (name === "" ) {
                                                alert("Không được để trống bất kỳ trường nào.");
                                                return;
                                            }
                                             

                                            var xhr = new XMLHttpRequest();
                                            xhr.open("POST", "quanlycategoryController", true);
                                            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                                            // Tạo chuỗi dữ liệu bao gồm chỉ hai trường name và phone
                                            var data = "action=addNCC&txtadminname=" + name;

                                            xhr.onreadystatechange = function () {
                                                if (xhr.readyState === 4) {
                                                    if (xhr.status === 200) {
                                                        alert(" Đã được thêm thành công.");
                                                        // Cập nhật giao diện hoặc thực hiện bất kỳ thay đổi nào khác tùy theo yêu cầu.
                                                        location.reload();
                                                    } else {
                                                        alert("Tên đã tồn tại!!");
                                                    }
                                                }
                                            };

                                            xhr.send(data);
                                        }

                                    function actionUpdateProduct() {
                                                var id = document.getElementById("txtProductId_update").value;
                                                var name = document.getElementById("txtadminname").value;
                                                

                                                // Kiểm tra xem tất cả các trường cần thiết đã được điền đầy đủ
                                                if (name === "") {
                                                    alert("Không được để trống bất kỳ trường nào.");
                                                    return;
                                                }
                                                 
                                                var xhr = new XMLHttpRequest();
                                                xhr.open("POST", "quanlycategoryController", true);
                                                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                                                // Tạo chuỗi dữ liệu bao gồm chỉ id, name và phone
                                                var data = "action=updateNCC&id=" + id + "&txtadminname=" + name;

                                                xhr.onreadystatechange = function () {
                                                    if (xhr.readyState === 4) {
                                                        if (xhr.status === 200) {
                                                            alert("Đã được update thành công.");
                                                            // Cập nhật giao diện hoặc thực hiện bất kỳ thay đổi nào khác tùy theo yêu cầu.
                                                            location.reload();
                                                        } else {
                                                            alert("Lỗi");
                                                        }
                                                    }
                                                };

                                                xhr.send(data);
                                            }



                                        function confirmDelete(id) {
                                            var confirmDelete = confirm("Bạn có chắc chắn muốn xóa  ?"+id);
                                            if (confirmDelete) {
                                                deleteProduct(id);
                                            }
                                        }

                                        function deleteProduct(id) {
                                            var xhr = new XMLHttpRequest();
                                            xhr.open("POST", "quanlycategoryController", true);
                                            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                            var data = "action=deleteProduct&id=" + id;

                                            xhr.onreadystatechange = function () {
                                                if (xhr.readyState === 4) {
                                                    if (xhr.status === 200) {
                                                        alert("Đã được xóa thành công.");
                                                        // Cập nhật giao diện hoặc thực hiện bất kỳ thay đổi nào khác tùy theo yêu cầu.
                                                        location.reload();
                                                    } else if (xhr.status === 500) {
                                                        alert("Lỗi server: Không thể xóa .");
                                                    }
                                                }
                                            };

                                            xhr.send(data);
                                        }

                                    function showModalAdd()
                                    {
                                        document.querySelector('.modal__add-product').style.display = "block";
                                        document.querySelector('.modal__add-product-header-heading').innerText = "Thêm category mới";
                                        document.getElementById('btnadminupdateProduct').style.display = "none";
                                        document.getElementById('btnadminaddProduct').style.display = "block";


                                        
                                        document.getElementById("txtadminname").value = "";
                                        
                                        

                                    }
                                    function hideModalAdd()
                                    {
                                        document.querySelector('.modal__add-product').style.display = "none";

                                    }


                                    function showModalUpdate(id,name,phone)
                                    {
                                        document.querySelector('.modal__add-product').style.display = "block";
                                        document.getElementById('btnadminupdateProduct').style.display = "block";
                                        document.getElementById('btnadminaddProduct').style.display = "none";
                                        document.querySelector('.modal__add-product-header-heading').innerText = "Cập nhật category";

                                        document.getElementById("txtProductId_update").value = id;
                                        document.getElementById("txtadminname").value = name;
                                        
                                        


                                    }

        </script>
    </body>
</html>
