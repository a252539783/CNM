package mobile.xiyou.cnm;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/15 0015.
 */

public class Usr {

    private String usr,pwd,code,cookie,name,scoreurl,kebiaourl,chengjistate,kebiaostate;
    private RequestQueue rq;
    private int xueqi;
    private ArrayList<String> xuenian;
    private MainActivity.H mh;

    public Usr(Context c,MainActivity.H mh)
    {
        this.mh=mh;
        rq= Volley.newRequestQueue(c);
        rq.add(new NSRequest("http://222.24.62.120/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                cookie=response.headers.get("Set-Cookie");
                freshCode();
                response.headers.put("Cache-Control","no-store");
                return super.parseNetworkResponse(response);
            }
        });
    }

    public void searchKebiao(int _xueqi)
    {
        xueqi=_xueqi;
        rq.add(new NSRequest(Request.Method.POST,kebiaourl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int x;
                ArrayList<SubItem> kebiao=mh.getKebiao();
                String sname,steacher,sroom,stime;
                SubItem subItem;
                while (true)
                {
                    x=response.indexOf("rowspan=\"2\"");
                    if (x==-1)break;

                    response=response.substring(x);
                    response=response.substring(response.indexOf(">")+1);
                    sname=response.substring(0,response.indexOf("<"));
                    response=response.substring(response.indexOf(">")+1);
                    stime=response.substring(0,response.indexOf("<"));
                    response=response.substring(response.indexOf(">")+1);
                    steacher=response.substring(0,response.indexOf("<"));
                    response=response.substring(response.indexOf(">")+1);
                    sroom=response.substring(0,response.indexOf("<"));
                    subItem=new SubItem(sname,steacher,stime,sroom);

                    response=response.substring(response.indexOf(">")+1);
                    while (response.charAt(0)=='<'&&response.charAt(1)=='b')
                    {
                        response=response.substring(response.indexOf(">")+1);
                        response=response.substring(response.indexOf(">")+1);
                        stime=response.substring(0,response.indexOf("<"));
                        response=response.substring(response.indexOf(">")+1);
                        response=response.substring(response.indexOf(">")+1);
                        sroom=response.substring(0,response.indexOf("<"));
                        response=response.substring(response.indexOf(">")+1);

                        subItem.add(sroom,stime);
                    }

                    kebiao.add(subItem);
                }
                mh.sendEmptyMessage(mh.FRESH_KEBIAO);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> head=new HashMap();
                head.put("Host","222.24.62.120");

                head.put("Connection","keep-alive");
                head.put("Cache-Control","max-age=0");
                head.put("Origin","http://222.24.62.120");
                head.put("Upgrade-Insecure-Requests","1");
                head.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                head.put("Content-Type","application/x-www-form-urlencoded");
                head.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                head.put("Referer",scoreurl);


                head.put("Accept-Encoding","gzip, deflate");
                head.put("Accept-Language","zh-CN,zh;q=0.8");
                head.put("Cookie",cookie);
                return head;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                setCoding("gbk");
                Map<String,String> x=new LinkedHashMap<String, String>();
                x.put("__VIEWSTATE",kebiaostate);
                x.put("__EVENTTARGET","");
                x.put("__EVENTARGUMENT","");
                x.put("xnd",xuenian.get(xueqi/3));
                x.put("xqd",""+(xueqi%3+1));
                return x;
            }
        });
    }

    public void searchChengji(int _xueqi)
    {
        xueqi=_xueqi;
        rq.add(new NSRequest(Request.Method.POST,scoreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.indexOf("<td>"+xuenian.get(xueqi/3))==-1)
                {
                    return;
                }
                ArrayList<ScoreItem> score=mh.getScore();
                String sub,chengji,xuefen,jidian;
                response=response.substring(response.indexOf("<td>"+xuenian.get(xueqi/3)))    ;
                while (true)
                {
                    response=response.substring(response.indexOf("<td>",5));
                    response=response.substring(response.indexOf("<td>",5));
                    response=response.substring(response.indexOf("<td>",5));
                    sub=response.substring(4,response.indexOf("</"));
                    response=response.substring(response.indexOf("<td>",5));
                    response=response.substring(response.indexOf("<td>",5));
                    response=response.substring(response.indexOf("<td>",5));
                    xuefen=response.substring(4,response.indexOf("</"));
                    response=response.substring(response.indexOf("<td>",5));
                    jidian=response.substring(4,response.indexOf("</"));
                    response=response.substring(response.indexOf("<td>",5));
                    chengji=response.substring(4,response.indexOf("</"));
                    score.add(new ScoreItem(sub,chengji,xuefen,jidian));
                    response=response.substring(response.indexOf("<td>",5));
                    response=response.substring(response.indexOf("<td>",5));
                    response=response.substring(response.indexOf("<td>",5));
                    response=response.substring(response.indexOf("<td>",5));
                    response=response.substring(response.indexOf("<td>",5));
                    response=response.substring(response.indexOf("<td>",5));

                    int x=response.indexOf("<td>"+xuenian.get(xueqi/3));
                    if (x==-1)
                        break;

                    response=response.substring(response.indexOf("<td>",5));
                }

                mh.sendEmptyMessage(mh.FRESH_CHENGJI);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> head=new HashMap();
                head.put("Host","222.24.62.120");

                head.put("Connection","keep-alive");
                head.put("Cache-Control","max-age=0");
                head.put("Origin","http://222.24.62.120");
                head.put("Upgrade-Insecure-Requests","1");
                head.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                head.put("Content-Type","application/x-www-form-urlencoded");
                head.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                head.put("Referer",scoreurl);


                head.put("Accept-Encoding","gzip, deflate");
                head.put("Accept-Language","zh-CN,zh;q=0.8");
                head.put("Cookie",cookie);
                return head;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                setCoding("gbk");
                Map<String,String> x=new LinkedHashMap<String, String>();
                x.put("__VIEWSTATE",chengjistate);
                x.put("__EVENTTARGET","");
                x.put("__EVENTARGUMENT","");
                x.put("hidLanguage","");
                x.put("ddlXN",xuenian.get(xueqi/3));
                x.put("ddlXQ",""+(xueqi%3+1));
                x.put("ddl_kcxz","");
                x.put("btn_xq","学期成绩");
                return x;
            }
        });
    }

    public void init()
    {
        xuenian=new ArrayList<>();
        try {
            scoreurl="http://222.24.62.120/xscjcx.aspx?xh="+usr+"&xm="+ URLEncoder.encode(name,"gbk")+scoreurl;
            kebiaourl="http://222.24.62.120/xskbcx.aspx?xh="+usr+"&xm="+URLEncoder.encode(name,"gbk")+kebiaourl;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        rq.add(new NSRequest(scoreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int x=response.indexOf("VIEWSTATE\" value=\"")+18;
                chengjistate=response.substring(x,response.indexOf("\"",x+5));

                response=response.substring(response.indexOf("ddlXN"));
                response=response.substring(response.indexOf("-")+7);

                while (true) {
                    x=response.indexOf("<");
                    if (!response.substring(x+2,x+5).equals("opt"))
                        break;
                    xuenian.add(response.substring(0, 9));

                    Message msg=new Message();
                    msg.what=mh.INIT_TERM;
                    msg.arg1=xuenian.size()-1;
                    Bundle bundle=new Bundle();
                    bundle.putString("xuenian",xuenian.get(xuenian.size()-1));
                    msg.setData(bundle);
                    mh.sendMessage(msg);

                    x=response.indexOf("-",6);
                    response=response.substring(x+7);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                setCoding("gbk");
                Map<String, String> head=new HashMap();
                head.put("Cookie",cookie);
                head.put("Referer","http://222.24.62.120/xs_main.aspx?xh="+usr);
                return head;
            }
        });

        rq.add(new NSRequest(kebiaourl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int x=response.indexOf("VIEWSTATE\" value=\"")+18;
                kebiaostate=response.substring(x,response.indexOf("\"",x+5));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                setCoding("gbk");
                Map<String, String> head=new HashMap();
                head.put("Cookie",cookie);
                head.put("Referer","http://222.24.62.120/xs_main.aspx?xh="+usr);
                return head;
            }
        });
    }

    public void freshCode()
    {
        rq.add(new NSRequest("http://222.24.62.120/CheckCode.aspx", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> head=new HashMap();
                head.put("Cookie",cookie);
                return head;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Message m=new Message();
                m.what=mh.SET_CODE;
                Bundle x=new Bundle();
                x.putByteArray("src",response.data);
                m.setData(x);
                mh.sendMessage(m);
                response.headers.put("Cache-Control","no-store");
                return super.parseNetworkResponse(response);
            }
        });
    }

    public String getName()
    {
        return name;
    }

    public String getUsr()
    {
        return usr;
    }

    public void login(final String user, String passwd, String check)
    {
        this.usr=user;
        this.pwd=passwd;
        this.code=check;
        rq.add(new NSRequest(Request.Method.POST,"http://222.24.62.120/default2.aspx", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.contains("请登录"))
                {
                    mh.sendEmptyMessage(mh.LOGIN_OK);


                    String x=response.split("xm\">")[1];
                    name=x.substring(0,x.indexOf("同学"));

                    String url=response.substring(response.indexOf("xskbcx"));
                    url=url.substring(url.indexOf(name));
                    kebiaourl=url.substring(url.indexOf("&"),url.indexOf("\""));
                    url=url.substring(10);
                    url=url.substring(url.indexOf(name));
                    scoreurl=url.substring(url.indexOf("&"),url.indexOf("\""));
                    init();

                    mh.sendEmptyMessage(mh.INIT_COMP);
                }
                else if (response.contains("alert('验证码"))
                {
                    mh.sendEmptyMessage(mh.CODE_ERRORR);
                }else if (response.contains("密码错误"))
                {
                    mh.sendEmptyMessage(mh.PWD_ERROR);
                }else
                {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> head=new HashMap();
                head.put("Host","222.24.62.120");

                head.put("Connection","keep-alive");
                head.put("Cache-Control","max-age=0");
                head.put("Origin","http://222.24.62.120");
                head.put("Upgrade-Insecure-Requests","1");
                head.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                head.put("Content-Type","application/x-www-form-urlencoded");
                head.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                head.put("Referer","http://222.24.62.120/xs_main.aspx?xh=04153036");


                head.put("Accept-Encoding","gzip, deflate");
                head.put("Accept-Language","zh-CN,zh;q=0.8");
                head.put("Cookie",cookie);
                return head;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                setCoding("gbk");
                Map<String,String> x=new LinkedHashMap<String, String>();
                x.put("__VIEWSTATE","dDwtNTE2MjI4MTQ7Oz5O9kSeYykjfN0r53Yqhqckbvd83A==");
                x.put("txtUserName",usr);
                x.put("Textbox1","");
                x.put("TextBox2",pwd);
                x.put("txtSecretCode",code);
                x.put("RadioButtonList1","学生");
                x.put("Button1","");
                x.put("lbLanguage","");
                x.put("hidPdrs","");
                x.put("hidsc","");
                return x;
            }
        });
    }

}
