package example.android.tumblrplusplus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotoSize;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chennosaurus on 11/6/15.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> postList;
    private Context context;
    String blogName;

    public PostAdapter(List<Post> pList, Context context, String blogName) {
        this.postList = pList;
        this.context = context;
        this.blogName = blogName;
    }

    public void updateBlogName(String name){
        this.blogName = name;
    } //updates blog name to reflect our current blog name to reblog to the right place

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder postViewHolder, final int i) {
        LinearLayout v = ((PostViewHolder) postViewHolder).getView();
        v.removeAllViews(); //clear all the views
        TextView bloggerName = new TextView(context); //add Text View for the blogger that posted this particular post
        String blogName = postList.get(i).getBlogName(); //get String of the blogger's name that posted
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        bloggerName.setText(blogName); //set the textview to show the blogger's name
        v.addView(bloggerName);
        if(postList.get(i) instanceof TextPost) { //if the post is a textpost...
            TextPost post =  (TextPost) postList.get(i); //get the post and cast it as a TextPost
            if (post.getTitle() != null){
                TextView myTitle = new TextView(context);
                myTitle.setText(Html.fromHtml(post.getTitle())); //set the title
                myTitle.setTextSize(20); //make the title a bit bigger for emphasis
                v.addView(myTitle);
                myTitle.setLayoutParams(params);
            }
            List<View> ls = parseHTML(post.getBody());
            for (int k = 0; k<ls.size();k++){
                v.addView(ls.get(k));
                ls.get(k).setLayoutParams(params);
            }
        }
        if(postList.get(i) instanceof PhotoPost) { //if it's a photo...
            PhotoPost photopost = (PhotoPost) postList.get(i); //cast it as a photopost
            List<Photo> photols = photopost.getPhotos(); //get the photos out of the photopost and put them in a list
            List<ImageView> imgs = new ArrayList<ImageView>(); //initialize a list of imageViews
            for(int j = 0; j < photols.size(); j++){
                imgs.add(new ImageView(context)); //for every single photo, add a single image View
                v.addView(imgs.get(j)); //add the image view to the screen
                PhotoSize targetSize = photols.get(j).getOriginalSize(); //set the target size to original size (we'll scale them later)
                String url = targetSize.getUrl(); //get the url of the photo of this size
                dispImage(url, imgs.get(j));
            }
            List<View> ls = parseHTML(photopost.getCaption());
            for (int k = 0; k<ls.size();k++){
                v.addView(ls.get(k));
                ls.get(k).setLayoutParams(params);
            }

        }

        Button b = new Button(context); //initialize our reblog button
        b.setText("reblog it yo");
        v.addView(b);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { //if you click the button, reblog it to the specified blog using async task
                ReblogStuff rs = new ReblogStuff();
                rs.execute(postList.get(i));
            }
        });


        Button likeButton = new Button(context);
        likeButton.setText("like it yo");
        v.addView(likeButton);
        likeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { //if you click the button, reblog it to the specified blog using async task
                LikeStuff likeStuff = new LikeStuff();
                likeStuff.execute(postList.get(i));
            }
        });



    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new PostViewHolder(itemView);


    }

    class PostViewHolder extends RecyclerView.ViewHolder { //very basic implementation of a viewHolder that doesn't innately hold any views- we add them programmatically to account for variable types/number of views
        private View myview;
        public PostViewHolder(View view){
            super(view);
            myview = view;
        }

        public LinearLayout getView() { //return the view (access)
            LinearLayout relView = (LinearLayout) myview.findViewById(R.id.myRelative);
            return relView;
        }

    }



    public class ReblogStuff extends AsyncTask<Post, Void, Void> {


        @Override
        protected Void doInBackground(Post... params) {
            Post post = params[0]; //get the post
            post.reblog(blogName); //reblog it TODO MAKE THIS STOP CRASHING LOL D:
            return null;
        }
    }


    public class LikeStuff extends AsyncTask<Post, Void, Void>{


        @Override
        protected Void doInBackground(Post... params) {
            Post post = params[0];
            post.like();
            return null;
        }
    }


    public List<View> parseHTML(String html) {
        ArrayList<View> ls = new ArrayList();
        String[] parts = html.split("<img");
        ls.add(new TextView(context)); //this is what you do with the first one, because the first tag won't be img
        ((TextView) ls.get(ls.size()-1)).setText(Html.fromHtml(parts[0]));
        for (int i = 1; i < parts.length; i++) {
            String unprocessed[] = parts[i].split("/>", 2); //get unprocessed (contains the entire contents of img tag)
            String unprocessedImage = unprocessed[0].split("src=")[1]; //splice out image with quotes
            String processedImage = unprocessedImage.split("\"",3)[1]; //take the stuff inbetween quotes
            ls.add(new ImageView(context));
            dispImage(processedImage, (ImageView) ls.get(ls.size() - 1)); //bind the image
            ls.add(new TextView(context));
            ((TextView) ls.get(ls.size()-1)).setText(Html.fromHtml(unprocessed[1])); //set the second part to text, leave first part so we can parse it to image (should be above)
            ((TextView) ls.get(ls.size()-1)).setMovementMethod(LinkMovementMethod.getInstance()); //make links clickable

            }

        return ls;
    }


    public void dispImage(String url, ImageView img){
        String fileName = Uri.parse(url).getLastPathSegment(); //get last part of url to use as a filename to store photo
        File file = new File(context.getFilesDir(), fileName); //make a file object in the context folder with the our filename
        if (file.exists()) //if our file exists, get it, and turn it into a bitmap, display it
        {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else { //if our file doesn't exist, download it, stick it in our folder, then set the imageview to it
            new DownloadImageTask(img, context).execute(url);
        }

        img.setScaleType(ImageView.ScaleType.FIT_XY); //set images to fit scale
        img.setAdjustViewBounds(true);//^^

    }

}
