package example.android.tumblrplusplus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
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
        TextView text = (TextView) findViewById(R.id.myText);
        text.setText(Html.fromHtml("Iowa City IA (SPX) Nov 06, 2015<br/><img src=\"http://www.spxdaily.com/images-bg/march-2015-sun-cme-compress-mars-magnetosphere-bg.jpg\" hspace=\"5\" vspace=\"2\" align=\"right\" border=\"0\" width=\"160\" height=\"128\"/>\n" +
                "    Mars has been all over the news, from the blockbuster finding of seasonal water on the Red Planet to the wildly successful film, The Martian. Now, researchers&ndash;including those at the University of Iowa&ndash;have learned more about what happened to the climate on Mars since it was a warm and watery planet billions of years ago.\n" +
                "    The researchers announced on Thursday that NASA&rsquo;s MAVEN (Mars Atmos<br/><a href=\"http://www.marsdaily.com/reports/Martian_desiccation_999.html\">Full article</a>"));
        text.setMovementMethod(LinkMovementMethod.getInstance());
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
        try {
            Intent intent = new Intent(this, Dashboard.class);
            for (int i = 0; i < posts.size(); i++) {
                Post post = (Post) posts.get(i);
                String postStr = post.toString();
                //Log.e("Post Type", post.getType());
                //Log.e("Author", post.getAuthorId());
                if (post.getType().equals("text")) {
                    String tit = ((TextPost) post).getTitle();
                    Log.e("TEXTPOST", postStr);
                    if (tit != null)
                        Log.e("Text Post title ", tit);
                    Log.e("Text Post Body ", ((TextPost) post).getBody());
                    View thisLayout = findViewById(R.id.thisLayout);
                    TextView text = new TextView(this);
                    text.setText(((TextPost) post).getBody());
                    text.setMovementMethod(LinkMovementMethod.getInstance());
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

