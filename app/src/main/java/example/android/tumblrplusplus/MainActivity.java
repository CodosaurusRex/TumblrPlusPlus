package example.android.tumblrplusplus;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.User;

public class MainActivity extends AppCompatActivity {
    private final String consumerKey = "Omq1FerYKMWeZnlvrIH9Qy3r6YIbyVDPdkQSfU5obu8eJBnt5n";

    private final String consumerSecret = "GHqE8rxq6r0IXCbBkj9NPR4ed0EIBqb8xP9k6PdulMuwsxJfyo";

    private final String oAuthToken = "5AX3lj6EjPUVTbMOKvuMHBPb8M4NWZN5kerNXo4v7RYmzPKXCC";

    private final String oAuthSecret = "KsvGQwzzuKM1fv7jMNdEhDqH1NwpJL7JB6AUoxxBEfweLKh6np";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("USER", "creating");
// Create a new client
        FetchTumblrsStuff fetch = new FetchTumblrsStuff();
        fetch.execute();
        setContentView(R.layout.activity_main);
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


    public class FetchTumblrsStuff extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void...params) {

            Log.e("USER", "fetching");
            JumblrClient client = new JumblrClient(consumerKey, consumerSecret);
            //client.setToken(oAuthToken, oAuthSecret);
            client.setToken(
                    "joOletjix2MLiK9vMGybboTH8bQh6WjAfE19YI1yzydhcBl9q7",
                    "g5K2VnsRD08hxPGfK4FjdfBQdOMgmPGh7YLNt6KC91cCIqaXPE"
            );
            User user = client.user();
            Log.e("USER", user.getName());
            return (null);
        }


    }
}
