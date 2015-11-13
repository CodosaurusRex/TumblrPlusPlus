package example.android.tumblrplusplus;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Post;
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

    private String myBlogName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("test", "t"); //TODO DELETE
        super.onCreate(savedInstanceState);
        FetchTumblrsStuff fetch = new FetchTumblrsStuff();
        fetch.execute(); //get various pieces of info about blog
        setContentView(R.layout.activity_main);
        initData(); //initialize the Arraylist we will attach
        initRec(myBlogName); //initialize the RecyclerView
    }

    public void initRec(String blogName) {

        ps = new PostAdapter(ls, this, blogName);
        recList = (RecyclerView) findViewById(R.id.cardList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm); //set our layout manager to a linearLayoutManager that is vertical
        recList.setAdapter(ps); //attach our postadapter to our recycler view
    }

    public void initData() {
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


    private void openDash(List<Post> posts) {
        try {
            ps.updateBlogName(myBlogName); //point the blog name string to our post adapter to myBlogName so we can reblog to the correct blog
            for (int i = 0; i < posts.size(); i++) {
                Post post = posts.get(i);
                this.ls.add(post);
                ps.notifyDataSetChanged(); //let the PostAdapter know that the list it takes from has changed
                recList.setAdapter(ps); //set the Adapter again
            }
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    //start classes and such


    public class FetchTumblrsStuff extends AsyncTask<Void, Void, List<Post>> {

        @Override
        protected List<Post> doInBackground(Void... params) {
            JumblrClient client = new JumblrClient(consumerKey, consumerSecret);
            client.setToken(oAuthToken, oAuthSecret);
            List<Post> posts = client.userDashboard(); //get out the dashboard posts to display
            List<Blog> blogs = client.user().getBlogs();
            myBlogName = blogs.get(0).getName(); //take out the first blog and set it to our blogName
            return (posts);
        }


        @Override
        protected void onPostExecute(List<Post> posts) {
            openDash(posts); //open our dashboard with posts

        }

    }


}