package pxgd.hyena.com.tipdemo;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    /**
     * 返会当前片段实例
     * @return
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }



    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }



    private class FetchTask extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            return null;
        }
    }






}
