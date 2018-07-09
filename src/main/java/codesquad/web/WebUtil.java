package codesquad.web;

import org.springframework.data.repository.CrudRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class WebUtil {
    public static HttpServletResponse alert(String message, HttpServletResponse response) {
        try {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('" + message + "'); history.go(-1);</script>");
            out.flush();
        } catch (Exception e) {

        }
        return response;
    }

}
