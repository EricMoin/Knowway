

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.util.ArrayList;

@MultipartConfig
@WebServlet("/ArticleServlet")
public class ArticleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("接收到文件");
        req.setCharacterEncoding("UTF-8");
        Part part = req.getPart("Article");
        String fileName  = part.getSubmittedFileName();
        String filePath = req.getServletContext().getRealPath("/")+"Article/";
        File file = new File(filePath+fileName);
        if(!file.exists()) file.createNewFile();
        try{
            BufferedInputStream bis = new BufferedInputStream( part.getInputStream() );
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            int rd = 0;
            while( (rd=bis.read())!=-1 ){
                bos.write((char)rd);
            }
            bos.flush();
            bos.close();
            bis.close();
        }catch(Exception e){

        }
        doGet(req,resp);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filePath = request.getServletContext().getRealPath("/")+"Article/";
        File file = new File(filePath+"ArticleCategory.json");
        if(!file.exists()) file.createNewFile();
        File directory = new File(filePath);
        if(!directory.exists()) directory.mkdir();
        File[] fileList = directory.listFiles();
        FileWriter fw = new FileWriter(file);
        ArrayList<ArticleCard> articleList = new ArrayList<ArticleCard>();
        for(File f:fileList){
            if(!f.getName().contains(".html")) continue;
            int pos = f.getName().indexOf('&');
            String articleTitle = f.getName().substring(0,pos);
            int posDot = f.getName().indexOf('.');
            String articleAuthor = f.getName().substring(pos+1,posDot);
            articleList.add( new ArticleCard(articleAuthor,0,articleTitle,"") );
        }
        new GsonBuilder().setPrettyPrinting().create().toJson(articleList,fw);
        fw.flush();
        fw.close();
    }
}
