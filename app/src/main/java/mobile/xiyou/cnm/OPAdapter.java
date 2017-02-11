package mobile.xiyou.cnm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/25 0025.
 */

public class OPAdapter extends BaseExpandableListAdapter implements View.OnClickListener,View.OnTouchListener{

    private LayoutInflater inflater;
    private String[] op;
    private ArrayList<String>[] opChilds;
    private int h;
    private MainActivity.H mh;

    public OPAdapter(Context c, MainActivity.H mh)
    {
        this.mh=mh;
        h=((MainActivity)c).getWindowManager().getDefaultDisplay().getHeight()/10;
        inflater=LayoutInflater.from(c);
        op=new String[]{"查看成绩","查看课表","修改密码"};
        opChilds=new ArrayList[3];
        opChilds[0]=new ArrayList<>();
        opChilds[1]=new ArrayList<>();
        opChilds[2]=new ArrayList<>();
    }

    public OPAdapter addChild(int i,String s)
    {
        opChilds[i].add(s);
        return this;
    }

    @Override
    public int getGroupCount() {
        return op.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return opChilds[groupPosition].size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView==null||convertView.getId()!=groupPosition)
        {
            convertView=inflater.inflate(R.layout.text,parent,false);
            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h));
            convertView.setId(groupPosition);
            ((TextView)convertView.findViewById(R.id.text_op)).setText(op[groupPosition]);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView==null||convertView.getId()!=groupPosition)
        {
            convertView=inflater.inflate(R.layout.childtext,parent,false);
            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h/3*2));

            ((TextView)convertView.findViewById(R.id.text_op)).setText(opChilds[groupPosition].get(childPosition));
            convertView.setOnClickListener(this);
            convertView.setOnTouchListener(this);
        }
        convertView.setId(groupPosition*6+childPosition);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()<opChilds[0].size())
        mh.searchChengji(v.getId());
        else
        {
            mh.searchKebiao(v.getId()-opChilds[0].size());
            Log.e("aaa",""+(v.getId()-opChilds[0].size()));
        }
    }
}
