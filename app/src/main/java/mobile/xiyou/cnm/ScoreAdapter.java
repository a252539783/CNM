package mobile.xiyou.cnm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/25 0025.
 */

public class ScoreAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int h;
    private ArrayList<ScoreItem> score;

    public ScoreAdapter(Context c)
    {
        h=((MainActivity)c).getWindowManager().getDefaultDisplay().getHeight()/15;
        inflater=LayoutInflater.from(c);
        score=new ArrayList<>();
        score.add(new ScoreItem());
    }

    public ArrayList<ScoreItem> getScore()
    {
        score=new ArrayList<>();
        score.add(new ScoreItem());
        return score;
    }

    @Override
    public int getCount() {
        return score.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null||convertView.getId()!=position)
        {
            convertView=inflater.inflate(R.layout.score_item,null,false);
            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h));
            convertView.setId(position);
            ((TextView)convertView.findViewById(R.id.sub)).setText(score.get(position).sub);
            ((TextView)convertView.findViewById(R.id.chengji)).setText(score.get(position).chengji);
            ((TextView)convertView.findViewById(R.id.xuefen)).setText(score.get(position).xuefen);
            ((TextView)convertView.findViewById(R.id.jidian)).setText(score.get(position).jidian);
        }

        return convertView;
    }


}
