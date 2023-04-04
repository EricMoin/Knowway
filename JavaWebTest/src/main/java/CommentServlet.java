
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.*;
import java.util.ArrayList;
@MultipartConfig
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    private String path;//视频评论文件夹路径
    private String fileName;//上传的视频名.json
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Part part = request.getPart("Comment");
        System.out.println("Get File");
        path= request.getServletContext().getRealPath("/")+"Comment/";
        fileName  = part.getSubmittedFileName().toString();
        File comment = new File(path+fileName);
        if(!comment.exists()) comment.createNewFile();
        System.out.println(path+fileName);
        try{
            BufferedInputStream bis = new BufferedInputStream( part.getInputStream() );
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(comment));
            int rd = 0;
            while( (rd=bis.read())!=-1 ){
                bos.write((char)(rd));
            }
            bos.flush();
            bos.close();
            bis.close();
        }catch(Exception e){

        }
       updateComment(request,response,part);
    }
    private void updateComment(HttpServletRequest request, HttpServletResponse response,Part part) throws IOException {
        int posDot = fileName.lastIndexOf('.');
        String videoName = fileName.substring(0,posDot);
        File commentDir  = new File(path+videoName);
        System.out.println(commentDir.getAbsolutePath().toString());
        if( !commentDir.exists() ) commentDir.mkdir();

        File file = new File(commentDir.getAbsolutePath()+"/CommentCategory.json");
        if(!file.exists()) file.createNewFile();
        JsonReader json = new JsonReader(new FileReader(path+fileName));
        VideoComment singleComment = singleJsonParser(json);
        json = new JsonReader(new FileReader(file));
        ArrayList<VideoComment> commentList = arrayJsonParser(json);
        commentList.add(singleComment);
        json.close();

        FileWriter fw = new FileWriter(file);
        new GsonBuilder().setPrettyPrinting().create().toJson(commentList,fw);
        fw.flush();
        fw.close();
    }

    private ArrayList<VideoComment> arrayJsonParser(JsonReader json) {
        ArrayList<VideoComment> commentList = new ArrayList<VideoComment>();
        try {
            json.beginArray();
            while(json.hasNext()){
                commentList.add( singleJsonParser(json) );
            }
            json.endArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commentList;
    }
    private VideoComment singleJsonParser(JsonReader json) throws IOException,FileNotFoundException {
        VideoComment vc = new VideoComment();
        try {
            json.beginObject();
            while (json.hasNext()) {
                String tag = json.nextName();
                switch (tag) {
                    case "commentAuthor":
                        vc.commentAuthor = json.nextString();
                        break;
                    case "commentImageId":
                        vc.commentImageId = json.nextInt();
                        break;
                    case "commentInfo":
                        vc.commentInfo = json.nextString();
                        break;
                    case "commentWhere":
                        vc.commentWhere = json.nextString();
                        break;
                }
            }
            json.endObject();
        }catch (Exception e){ e.printStackTrace(); }
        return vc;
    }
}
