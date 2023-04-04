
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.util.ArrayList;

@WebServlet("/updateVideoServlet")
public class updateVideoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] tagList = {"Mountain/","Medical/","Fate/","Face/","Divine/"};
        for(String tag :tagList){
            update(tag,request);
        }
    }
    private void update(String tag,HttpServletRequest request) throws IOException {
        String filePath = request.getServletContext().getRealPath("/")+"Video/";
        File file = new File(filePath+tag+"VideoCategory.json");
        if(!file.exists()) file.createNewFile();
        File directory = new File(filePath+tag);
        if(!directory.exists()) directory.mkdir();
        File[] fileList = directory.listFiles();
        FileWriter fw = new FileWriter(file);
        ArrayList<VideoCard> videoList = new ArrayList<VideoCard>();
        for(File f:fileList){
            if(!f.getName().contains(".mp4")) continue;
            videoList.add( new VideoCard(f.getName(),0,tag) );
        }
        new GsonBuilder().setPrettyPrinting().create().toJson(videoList,fw);
        fw.flush();
        fw.close();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
