package mobile.xiyou.cnm;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class SubItem {

    public String name,teacher;
    public ArrayList<String> room,time;

    public SubItem(String _name,String _teacher,String _time,String _room)
    {
        name=_name;
        teacher=_teacher;
        room=new ArrayList<>();
        time=new ArrayList<>();
        room.add(_room);
        time.add(_time);
    }

    public void add(String _room,String _time)
    {
        room.add(_room);
        time.add(_time);
    }

}
