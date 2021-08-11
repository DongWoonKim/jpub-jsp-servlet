package com.boot.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet(name = "uploadServlet", urlPatterns = "/upload")
@MultipartConfig (
        // 파일 업로드 시에 메모리에 저장되는 임시 파일 크기를 지정
        fileSizeThreshold = 1024 * 1024 * 2, // 2mb
        // 업로드할 파일의 최대 크기를 지정
        maxFileSize = 1024 * 1024 * 10, // 10mb
        // request 시에 최대 크기를 지정
        maxRequestSize = 1024 * 1024 * 50, // 50mb
        // 파일 업로드 시에 임시 저장 디렉토리를 지정
        location = "/Documents/upload"
)
public class uploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        // 경로
        final String path = req.getParameter("destination");
        // 파일
        final Part filePart = req.getPart("file");
        // 파일 이름
        final String fileName = getFileName(filePart);

        final PrintWriter writer = resp.getWriter();

        try (OutputStream out = new FileOutputStream(new File(path + File.separator + fileName)); InputStream filecontent = filePart.getInputStream()) {
            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            writer.print("new File: " + fileName + path + "에 생성되었습니다.");

        } catch (FileNotFoundException fne) {
            System.out.println(fne.getMessage());
        } finally {
            writer.close();
        }
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("Part Header = {0}" + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}
