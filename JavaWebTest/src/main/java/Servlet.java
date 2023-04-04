
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;


@MultipartConfig
@WebServlet("/updateServlet")
public class Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new ArticleServlet().doGet(req,resp);
        new updateVideoServlet().doGet(req,resp);
    }
}
