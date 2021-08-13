package com.boot.pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
    [프론트 컨트롤러 패턴]
     프론트 컨트롤러 패턴은(Front Controller Patter)은 컨트롤러가 공통 요청을 수행하고 뷰를 호출하는 패턴이다.
    요청에 대해 컨트롤러가 응답하고, 결과에 따라 서블릿이나 JSP로 만든 뷰를 보여 주게 된다.

    서버 측에서 메서드를 사용하여 화면을 전환하는 방법에는 두 가지가 있다.
    -> Response 객체의 sendRedirect 메서드
    : 응답을 터치라인 밖으로(서버에서 클라이언트로) 던지는 것과 같으므로 속성을 저장할 수 없고 다른 로직을 추가할 수 없다.
    -> RequestDispatcher 객체의 forward 메서드
    : 서버 내부에서 흐름이 이동하므로 속성을 저장할 수 있고, 브라우저(클라이언트)에게 바로 전달하지 않고 원하는 작업을 처리한 후에
      응답을 전환할 수 있으므로 컨트롤러를 만들 때 많이 사용한다.

     하지만 컨트롤러에서 직접적으로 forward 메서드를 사용하게 될 경우에는 URL이 변경되거나 뷰가 변경될 때마다 컨트롤러를 변경하게 되어서
    추후에 유지보수가 어려워진다.
    이럴 때는 커맨드 패턴(Command Pattern)을 이용해서 컨트롤러 클래스의 복잡도를 낮출 수 있다.

    [커맨드 패턴]
     커맨드 패턴은 명령(로직)을 객체 안에 캡슐화해서 저장함으로써 컨트롤러와 같은 클래스를 수정하지 않고 재사용할 수 있게 하는 패턴이다.

 */

@WebServlet(urlPatterns = "/controller", initParams = {@WebInitParam(name="mapping", value = "/WEB-INF/command.properties")})
public class FrontController extends HttpServlet {

    private Properties cmdMapping;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        InputStream is = null;

        try {
            String location = config.getInitParameter("mapping");
            is = getServletContext().getResourceAsStream(location);
            cmdMapping = new Properties();
            cmdMapping.load(is);
        } catch(IOException ioe) {
            getServletContext().log("I/O Error", ioe);
        } finally {
            try {
                is.close();
            } catch (IOException ioe) {

            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd      = req.getParameter("cmd");
        String cmdClass = (String)cmdMapping.get(cmd);
        Command command = null;
        System.out.println("cmd cmd cmd : " + cmd);
        System.out.println("cmd cmd cmd2 : " + cmdClass);

        try {

            command = (Command) Class.forName(cmdClass).getDeclaredConstructor().newInstance();

        } catch (ClassNotFoundException e) {
            getServletContext().log("class not found", e);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        command.setReq(req);
        command.setRes(resp);
        command.setServletContext(getServletContext());
        command.execute();


    }
}
