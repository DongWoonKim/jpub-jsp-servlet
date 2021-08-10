package com.boot.basic;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "initServlet",
        urlPatterns="/init",
        initParams = {@WebInitParam(name = "siteName", value = "DongwoonKim")}
)
public class InitServlet extends HttpServlet {
    private String myParam = "";

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init call");
        this.myParam = config.getInitParameter("siteName");
        System.out.println("입력 받은 사이트 명은" + myParam + "입니다.");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("Hello World!");
    }
}
