package com.boot.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

@WebServlet(urlPatterns = "/newcookie")
public class CookieCreateServer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<html><head><title> 쿠키예제 </title></head><body>");
        out.println("<br/>");

        Cookie jcookie = new Cookie("jpub", "books");
        // 쿠키값 한글 입력 시에는 URLEncoder를 이용해서 문자열을 감싸줄 것.
        //Cookie jcookie2 = new Cookie("kor", URLEncoder.encode("한글 데이터", "UTF-8"));
        jcookie.setMaxAge(1000*36); // 1시간
        resp.addCookie(jcookie);
        out.println("<a href='/readcookie'>readcookie</a></body></html>");
        out.close();
    }
}
