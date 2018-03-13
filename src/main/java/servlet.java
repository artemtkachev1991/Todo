import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;

@WebServlet(urlPatterns = "/todo")
public class servlet extends HttpServlet {
    private String response;
    ArrayList<todoshka> todoList;
    bd db;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        resp.setContentType("text/html");

        todoList = db.getTODOList();
        StringBuilder sb = new StringBuilder();

        for (todoshka todo: todoList) {
            sb.append(todo.getName()+"  <input name=\""+todo.getId()+"\" type=\"checkbox\" id=\""+todo.getId()+"\"><br>");

        }
        resp.getWriter().print(response.replace("{todolist}", sb.toString()));

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!req.getParameter("newTODO").equals("")) {
            db.addTODO(req.getParameter("newTODO"));
        }

        Enumeration<String> en = req.getParameterNames();
        ArrayList<String> whatToDel = new ArrayList<>();

        while (en.hasMoreElements()) {
            String par = en.nextElement();
            if (!par.equals("newTODO")) {
                System.out.println("goint to delete :"+par);
                whatToDel.add(par);
            }
        }
        db.deleteTODO(whatToDel);

        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        resp.setContentType("text/html");

        todoList = db.getTODOList();
        StringBuilder sb = new StringBuilder();
        for (todoshka todo: todoList) {
            sb.append(todo.getName()+"  <input name=\""+todo.getId()+"\" type=\"checkbox\" id=\""+todo.getId()+"\"><br>");
        }
        resp.getWriter().print(response.replace("{todolist}", sb.toString()));
    }

    @Override
    public void init() throws ServletException {
        db = new bd();

        try {
            response = new String(Files.readAllBytes(Paths.get(getClass().
                    getResource("/templates/index.html").toURI())));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
