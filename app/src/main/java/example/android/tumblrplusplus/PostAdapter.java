package example.android.tumblrplusplus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chennosaurus on 11/6/15.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> postList;
    private Context context;
    private int count;

    public PostAdapter(List<Post> pList, Context context) {
        this.postList = pList;
        this.context = context;
        Log.e("post adapter", "adding 1");
        count = 0;;
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder postViewHolder, int i) {
        LinearLayout v = ((TextPostViewHolder) postViewHolder).getView();
        v.removeAllViews();
        if(postList.get(i) instanceof TextPost) {
            TextPost post =  (TextPost) postList.get(i);
            TextView myText = new TextView(context);
            if (post.getTitle() != null){
                Log.e("Title", post.getTitle());
                myText.setText(Html.fromHtml(post.getTitle()));
            }
            TextView myBody = new TextView(context);
            myBody.setText(Html.fromHtml(post.getBody()));
            v.addView(myText);
            v.addView(myBody);
        }
        if(postList.get(i) instanceof PhotoPost) {
            PhotoPost photopost = (PhotoPost) postList.get(i);
            List<Photo> photols = photopost.getPhotos();
            List<ImageView> imgs = new ArrayList<ImageView>();
            for(int j = 0; j < photols.size(); j++){
                imgs.add(new ImageView(context));
                //View cardView = v.findViewById(R.id.card_view);
                //int w = cardView.getWidth();
                v.addView(imgs.get(j));
                //imgs.get(j).getLayoutParams().height = w;
                String url = photols.get(j).getOriginalSize().getUrl();
                String fileName = Uri.parse(url).getLastPathSegment();
                File file = new File(context.getFilesDir(), fileName);
                if (file.exists())
                {
                    try {
                        FileInputStream inputStream = new FileInputStream(file);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imgs.get(j).setImageBitmap(bitmap);
                        Log.e("reading from file", "");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    new DownloadImageTask(imgs.get(j), context).execute(url);
                }
            }


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new TextPostViewHolder(itemView);
    }

    class TextPostViewHolder extends RecyclerView.ViewHolder {
        private View myview;
        public TextPostViewHolder(View view){
            super(view);
            myview = view;
        }

        public LinearLayout getView() {
            LinearLayout relView = (LinearLayout) myview.findViewById(R.id.myRelative);
            return relView;
        }
    }


}
