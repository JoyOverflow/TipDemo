package pxgd.hyena.com.mydemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    public BlankFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder{

        public CrimeHolder(View itemView) {
            super(itemView);
        }
    }
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {

        }
        @Override
        public int getItemCount() {
            return 0;
        }
    }

}
