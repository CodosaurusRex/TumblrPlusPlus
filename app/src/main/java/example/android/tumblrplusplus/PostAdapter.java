package example.android.tumblrplusplus;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;

import java.util.List;

/**
 * Created by chennosaurus on 11/6/15.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.TextPostViewHolder> {

    private List<MyPost> postList;

    public PostAdapter(List<MyPost> pList) {
        this.postList = pList;
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public void onBindViewHolder(TextPostViewHolder postViewHolder, int i) {
        if(postList.get(i) instanceof MyTextPost) {
            MyTextPost post =  (MyTextPost) postList.get(i);
            if (post.getTitle() != null){
                Log.e("Title", post.getTitle());
                postViewHolder.title.setText(Html.fromHtml(post.getTitle()));
            }
            postViewHolder.body.setText(Html.fromHtml(post.getBody()));
        }

    }

    @Override
    public TextPostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new TextPostViewHolder(itemView);
    }

    class TextPostViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected  TextView body;
        public TextPostViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            body = (TextView) view.findViewById(R.id.body);
        }

    }
}
