package mobile.xiyou.cnm;

/**
 * Created by Administrator on 2017/2/1 0001.
 */

public class ScoreItem
{
    public String sub,chengji,xuefen,jidian;

    public  ScoreItem()
    {
        sub="课程";
        chengji="成绩";
        xuefen="学分";
        jidian="绩点";
    }

    public ScoreItem(String _sub,String _chengji,String _xuefen,String _jidian)
    {
        sub=_sub;
        chengji=_chengji;
        xuefen=_xuefen;
        jidian=_jidian;
    }
}