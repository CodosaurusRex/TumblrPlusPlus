package example.android.tumblrplusplus;

public class MyTextPost extends MyPost{
    private String title;
    private String body;

    public MyTextPost(String...params){
        super(params);
        String mytitle = params[0];
        String mybody = params[1];
        this.title = mytitle;
        this.body = mybody;
    }

    public String getTitle(){
        return title;
    }

    public String getBody(){
        return body;
    }


}
