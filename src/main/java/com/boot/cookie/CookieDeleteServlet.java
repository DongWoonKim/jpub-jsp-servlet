package com.boot.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/delcookie")
public class CookieDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>삭제</title></head><body>");
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jpub")) {
                    System.out.println("del del del");
                    Cookie deleteCookie = new Cookie("jpub", "");
                    deleteCookie.setMaxAge(0);
                    resp.addCookie(deleteCookie);
                }
            }
        }
        out.println("<a href='/readcookie'>readcookie</a></body></html>");
        out.close();
    }
}
