<%
    // Ki?m tra cookies
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("username".equals(cookie.getName())) {
                String username = cookie.getValue();
                response.sendRedirect("index");
                return; // D?ng x? lý ?? tránh ti?p t?c ki?m tra
            }
        }
    }

    // Ti?p t?c x? lý n?u không có cookie "username"
    // Ví d?: Hi?n th? form ??ng nh?p
%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>
<% response.setHeader("Pragma", "no-cache"); %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" href="./fontawesome-free-5.15.4-web/fontawesome-free-5.15.4-web/css/all.min.css">
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
</head>
<body class="">
    <div class="container">
        <div class="primary-bg">
            <div class="box signin">
                <h2>Already Have an Account?</h2>
                <button class="signinBtn">Sign in</button>
            </div>

            <div class="box signup">
                <h2>Don't Have an Account?</h2>
                <button class="signupBtn">Sign up</button>
            </div>
        </div>
        <div class="formBx">
            <div class="form signinForm">
                <form id="login-form" action="loginController" method="post">
                         <input type="hidden" name="action" value="login">
                    <h3>Sign In</h3>
                    <div class="form-group">
                        <input name="user-name" id="user-name" type="text"  placeholder="Username">
                        <span class="form-message"></span>
                    </div>
                    <div class="form-group">
                        <input name="user-password" id="user-password" type="password"  placeholder="Password">
                        <span class="form-message"></span>
                    </div>
                    <p class="forgot" onclick="showModalAdd()">Forgot Password</p>
                    <div class="form-group">
                        <button type="submit" class="form-submit login-btn">Submit</button>
                    </div>
                    <div class="auth-form__social">
                        <span class="auth-form__social--title">Login with social: </span>
                        <a href="" class="auth-form__social--facebook btn btn--size-s btn--with-icon">
                         <i class="auth-form__social--icon fab fa-facebook-square"></i>
                        </a>
     
                        <a href="" class=" auth-form__social--google btn btn--size-s btn--with-icon">
                         <i class=" auth-form__social--icon fab fa-google"></i>
                        </a>

                        <a href="" class=" auth-form__social--github btn btn--size-s btn--with-icon">
                            <i class=" auth-form__social--icon fab fa-github"></i>
                           </a>
     
                     </div>

                </form>
            </div>

            <div class="form signupForm">
                <form action="loginController" id='formRegister' method="POST" >
                           <input type="hidden" name="action" value="register">
                    <h3>Sign Up</h3>
                    <div class="form-group invalid">
                        <input name="newuserphone" id="new-user-name" type="text" placeholder="Account">
                        <span id="errolUserName" class="form-message"></span>
                    </div>
                    <div class="form-group invalid">
                        <input name="newusername" id="new-user-email" type="text" placeholder="Name">
                        <span id="errolEmail" class="form-message"></span>
                    </div>
                    <div class="form-group invalid">
                        <input name="newuserpassword" id="new-user-password" type="password" placeholder="Password (>5 characters)" aria-autocomplete="list">
                        <span id="errolPassword" class="form-message"></span>
                    </div>
                    <div class="form-group invalid">
                        <input name="confirmuserpassword" id="confirm-user-password" type="password" placeholder="Confirm Password">
                        <span id="errolConfirmPassword" class="form-message"></span>
                    </div>
                    <div class="form-group">
                        <button type="submit" name="signup" class="form-submit" onclick="onRegister()">Register</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
       <form action="forgetPassController" method="post">
                <input type="hidden" name="action" value="changepass">
                <div class="modal__add-forgetPass " style="display: none;">
                    <div class="modal__add-forgetPass-overlay" onclick="hideModalAdd();"></div>
                    <div class="modal__add-forgetPass-content">
                        <div class="modal__add-product-header">
                            <p class="modal__add-product-header-heading">Forget Pass</p>
                        </div>
                        <div class="modal__add-User-account"><input type="text" name="txtadminpass" id="txtadminuser" placeholder="Your Phone"></div>
                        <div class="modal__add-User-pass"> <input type="text" name="txtadminpassconfirm" placeholder="Your Email" id="txtadminpass"></div>
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
                window.location.href = "Login.jsp"; // T?i l?i trang hi?n t?i
            </script>
            <%
            } else if (errorMessage != null) {
            %>
            <script>
                alert("<%= errorMessage %>");
                 window.location.href = "Login.jsp"; // T?i l?i trang hi?n t?i
            </script>
            <%
            }
            %>
            
             <%
            String successMessageLogin = (String) request.getAttribute("successMessageLogin");
            String errorMessageLogin = (String) request.getAttribute("errorMessageLogin");
            if (successMessageLogin != null) {
            %>
            <script>
                alert("<%= successMessageLogin %>");
                window.location.href = "index"; // T?i l?i trang hi?n t?i
            </script>
            <%
            } else if (errorMessageLogin != null) {
            %>
            <script>
                alert("<%= errorMessageLogin %>");
                 window.location.href = "Login.jsp"; // T?i l?i trang hi?n t?i
            </script>
            <%
            }
            %>
            
   

    <script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="main.js"></script>
    <script type="text/javascript">
    const signinBtn = document.querySelector('.signinBtn')
    const signupBtn = document.querySelector('.signupBtn')
    const formBx = document.querySelector('.formBx')
    const body = document.querySelector('body')

    signupBtn.onclick = function() {
        formBx.classList.add('active')
        body.classList.add('active')
    }
    signinBtn.onclick = function() {
        formBx.classList.remove('active')
        body.classList.remove('active')
    }

        function showModalAdd()
                {
                    document.querySelector('.modal__add-forgetPass').style.display = "block";
                }
                function hideModalAdd()
                {
                    document.querySelector('.modal__add-forgetPass').style.display = "none";
                }

    </script>

</body>
</html>