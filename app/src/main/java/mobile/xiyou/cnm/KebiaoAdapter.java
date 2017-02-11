package mobile.xiyou.cnm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class KebiaoAdapter extends BaseAdapter {

    private ArrayList<SubItem> kebiao;
    private  int h,index;
    private LayoutInflater inflater;

    public KebiaoAdapter(Context c)
    {
        index=0;
        h=((MainActivity)c).getWindowManager().getDefaultDisplay().getHeight()/7;
        inflater= LayoutInflater.from(c);
        kebiao=new ArrayList<>();
    }

    public ArrayList<SubItem> getKebiao()
    {
        index=0;
        kebiao=new ArrayList<>();
        return kebiao;
    }


    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder x;
        if (convertView==null)
        {
            convertView=inflater.inflate(R.layout.kebiaoitem,null,false);
            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h));
            convertView.setId(position);
            x=new Holder((TextView)convertView.findViewById(R.id.day_1),(TextView)convertView.findViewById(R.id.day_2),(TextView)convertView.findViewById(R.id.day_3),(TextView)convertView.findViewById(R.id.day_4),(TextView)convertView.findViewById(R.id.day_5),(TextView)convertView.findViewById(R.id.day_6),(TextView)convertView.findViewById(R.id.day_7));
            convertView.setTag(x);

        }
            ((TextView)convertView.findViewById(R.id.jie1)).setText(""+(position*2+1));
            ((TextView)convertView.findViewById(R.id.jie2)).setText(""+(position*2+2));
            x=(Holder)convertView.getTag();
            for (index=0;index<kebiao.size();index++)
            while (index<kebiao.size()&&kebiao.get(index).time.get(0).charAt(3)==(""+(position*2+1)).charAt(0))
            {
                if (kebiao.get(index).time.get(0).charAt(4)==','||(position*2+1>=10&&kebiao.get(index).time.get(0).charAt(4)==(""+(position*2+1)).charAt(0)))
                {
                    int d=getDay(kebiao.get(index).time.get(0).charAt(1));
                    x.day[d].setAlpha(1);
                    x.day[d].setText(kebiao.get(index).name+" "+kebiao.get(index).room.get(0));
                    Log.e("xxaax",""+d+":"+d+x.day[d].getText());
                    index++;
                }
        }



        return convertView;
    }

    private class Holder
    {
        public TextView[] day;

        public Holder(TextView d1,TextView d2,TextView d3,TextView d4,TextView d5,TextView d6,TextView d7)
        {
            day=new TextView[7];
            day[0]=d1;
            day[1]=d2;
            day[2]=d3;
            day[3]=d4;
            day[4]=d5;
            day[5]=d6;
            day[6]=d7;
        }
    }

    public int getDay(char x)
    {
        switch (x)
        {
            case '一':
                return 0;
            case '二':
                return 1;
            case '三':
                return 2;
            case '四':
                return 3;
            case '五':
                return 4;
            case '六':
                return 5;
            case '七':
                return 7;
        }
        return 0;
    }
}
