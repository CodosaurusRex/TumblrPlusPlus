package example.android.tumblrplusplus;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;

import java.util.List;

/**
 * Created by chennosaurus on 11/6/15.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> postList;
    private Context context;

    public PostAdapter(List<Post> pList, Context context) {
        this.postList = pList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder postViewHolder, int i) {
        if(postList.get(i) instanceof TextPost) {
            TextPost post =  (TextPost) postList.get(i);
            if (post.getTitle() != null){
                Log.e("Title", post.getTitle());
                ((TextPostViewHolder) postViewHolder).title.setText(Html.fromHtml(post.getTitle()));
            }
            ((TextPostViewHolder) postViewHolder).body.setText(Html.fromHtml(post.getBody()));
            ((TextPostViewHolder) postViewHolder).img.setImageDrawable(null);
        }
        if(postList.get(i) instanceof PhotoPost) {
            PhotoPost photopost = (PhotoPost) postList.get(i);
            String imURL = photopost.getPhotos().get(0).getOriginalSize().getUrl();
            ImageView im1 = ((TextPostViewHolder) postViewHolder).img;
            ((TextPostViewHolder) postViewHolder).title.setText(" ");
            ((TextPostViewHolder) postViewHolder).body.setText(" ");
            Log.e("imageView ", "trying to get an image using Download task from URL " + imURL);
            //ImageView im2 = new ImageView(context);
            RelativeLayout v = ((TextPostViewHolder) postViewHolder).getView();
            //v.addView(im2);
            new DownloadImageTask(im1).execute(imURL);
            //new DownloadImageTask(im2).execute(imURL);

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
        protected TextView title;
        protected  TextView body;
        protected ImageView img;
        private View myview;
        public TextPostViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            body = (TextView) view.findViewById(R.id.body);
            img = (ImageView) view.findViewById(R.id.im);
            myview = view;
        }

        public RelativeLayout getView() {
            RelativeLayout relView = (RelativeLayout) myview.findViewById(R.id.myRelative);
            return relView;
        }
    }
    class PhotoPostViewHolder extends RecyclerView.ViewHolder{
        protected TextView title;
        public PhotoPostViewHolder(View view){
            super(view);
            //String title = "photo post here";
            title = (TextView) view.findViewById(R.id.title);

        }

    }
}
