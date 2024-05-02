<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>

<%
    // Xóa cookie khi đăng xuất
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("username".equals(cookie.getName())) {
                cookie.setMaxAge(0); // Thiết lập thời gian sống của cookie về 0 để nó bị xóa
                response.addCookie(cookie);
            } else if ("type".equals(cookie.getName())) {
                cookie.setMaxAge(0); // Thiết lập thời gian sống của cookie về 0 để nó bị xóa
                response.addCookie(cookie);
            }else if ("PhoneNumber".equals(cookie.getName())) {
                cookie.setMaxAge(0); // Thiết lập thời gian sống của cookie về 0 để nó bị xóa
                response.addCookie(cookie);
            }else if ("userid".equals(cookie.getName())) {
                cookie.setMaxAge(0); // Thiết lập thời gian sống của cookie về 0 để nó bị xóa
                response.addCookie(cookie);
            }
        }
    }

    // Gửi phản hồi về client
    response.getWriter().write("Logout successful");
%>
