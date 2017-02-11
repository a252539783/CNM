package mobile.xiyou.cnm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/25 0025.
 */

public class ScoreFragment extends Fragment {

    private ScoreAdapter score;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.score,container,false);
        ListView lst=(ListView)root.findViewById(R.id.score_list);
        //lst.setClickable(false);
        score=new ScoreAdapter(getContext());
        lst.setAdapter(score);

        return root;
    }

    public ArrayList<ScoreItem> getScore()
    {
        return score.getScore();
    }

    public void fresh()
    {
        score.notifyDataSetChanged();
    }
}
