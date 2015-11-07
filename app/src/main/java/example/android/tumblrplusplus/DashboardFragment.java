package example.android.tumblrplusplus;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DashboardFragment extends Fragment {

    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dashView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Log.e("USER", "we are in OnCreateViewDashboardFragment");
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("user");
            Log.e("USER", "got name" + name );
            ((TextView) dashView.findViewById(R.id.name)).setText(name);
        }

        return dashView;
    }
}
