package mobile.xiyou.cnm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class KebiaoFragment extends Fragment {

    private KebiaoAdapter kebiao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.kebiao,container,false);
        ListView lst=(ListView)root.findViewById(R.id.kebiao_list);
        lst.setClickable(false);
        kebiao=new KebiaoAdapter(getContext());
        lst.setAdapter(kebiao);

        return root;
    }

    public ArrayList<SubItem> getKebiao()
    {
        return kebiao.getKebiao();
    }

    public void fresh()
    {
        kebiao.notifyDataSetChanged();
    }

}
