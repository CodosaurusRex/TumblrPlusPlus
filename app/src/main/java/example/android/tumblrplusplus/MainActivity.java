package example.android.tumblrplusplus;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String consumerKey = "Omq1FerYKMWeZnlvrIH9Qy3r6YIbyVDPdkQSfU5obu8eJBnt5n";

    private final String consumerSecret = "GHqE8rxq6r0IXCbBkj9NPR4ed0EIBqb8xP9k6PdulMuwsxJfyo";


    private final String oAuthToken = "WMFunAyCRwm9Nuelcgb0P8dWR449tNvzGQconaM5GfbrT5De9r";

    private final String oAuthSecret = "DCtLxt1ajr5yHfDcLkt7nMWzl9E8CquxEc85UbTbJJAq8Dkt6r";

    private List<Post> ls;

    private PostAdapter ps;

    private RecyclerView recList;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("USER", "creating");
// Create a new client
        FetchTumblrsStuff fetch = new FetchTumblrsStuff();
        fetch.execute();
        // Set the text view as the activity layout
        setContentView(R.layout.activity_main);
        initData();
        initRec();
    }

    public void initRec(){

        ps = new PostAdapter(ls, this);
        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setAdapter(ps);
    }

    public void initData(){
        ls = new ArrayList<Post>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void openDash(List<Post> posts){
        //List<Post> ls = new ArrayList<Post>();
        try {
            Intent intent = new Intent(this, Dashboard.class);
            for (int i = 0; i < posts.size(); i++) {
                Post post = posts.get(i);
                Log.e("Post Type", post.getType());
                //Log.e("Author", post.getAuthorId());
                if (post.getType().equals("text")) {
                    TextPost textpost = (TextPost) post;
                    //Log.e("body", textpost.getBody());
                    this.ls.add(textpost);
                    ps.notifyDataSetChanged();
                    //ps = new PostAdapter(ls, this); //do we need to create a new postadapter each time idek does that defeat the purpose lol whatevs
                    recList.setAdapter(ps);
                }
                if (post.getType().equals("photo")){
                    PhotoPost photopost = (PhotoPost) post;
                    Log.e("photopost", photopost.getPhotos().get(0).getOriginalSize().getUrl());
                    this.ls.add(photopost);
                    ps.notifyDataSetChanged();
                    //ps = new PostAdapter(ls, this); //do we need to create a new postadapter each time idek does that defeat the purpose lol whatevs
                    recList.setAdapter(ps);
                }
            }
        }catch(Exception ex){
            Log.e("Error", ex.toString());
        }
//        startActivity(intent);
    }

    //start classes and such


    public class FetchTumblrsStuff extends AsyncTask<Void, Void, List<Post>> {

        @Override
        protected List<Post> doInBackground(Void...params) {
            Log.e("USER", "fetching");
            JumblrClient client = new JumblrClient(consumerKey, consumerSecret);
            client.setToken(oAuthToken, oAuthSecret);
            Log.e("USER", "got token");
            List<Post> posts = client.userDashboard();
            Log.e("USER Dash", "do in back");
            return (posts);
        }




        @Override
        protected void onPostExecute(List<Post> posts) {
            Log.e("USER", "post");
            openDash(posts);

        }

    }

/*    public class Parser{
        String target;
        public Parser(String parseMe){

            target = parseMe;

        }

        public ArrayList<myPost> parseText(String s){


        }


    }*/

/*    public class myPost{
        public myPost(String data){

        }

    }*/
/*
    public class myTextPost extends myPost{

        String body;
        String title;
        public myTextPost(String givenbody, String gtitle){
            super(body);
            this.body = givenbody;
            this.title = gtitle;
        }

        private ArrayList<String> split{
            //was going to inherit string, but can't? oh well. clearly a java noob. rip.
            ArrayList<String> ls= new ArrayList<String>();
            ls.add("<t>" + title);
            ls.add("");

        }


    }*/


}
/*

Log.e("TEXTPOST", postStr);
        if (tit != null)
        Log.e("Text Post title ", tit);
        Log.e("Text Post Body ", ((TextPost) post).getBody());
        View thisLayout = findViewById(R.id.thisLayout);
        TextView text = new TextView(this);
        text.setText(((TextPost) post).getBody());
        text.setMovementMethod(LinkMovementMethod.getInstance());*/

