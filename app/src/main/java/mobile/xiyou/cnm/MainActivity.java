package mobile.xiyou.cnm;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private TextView text;
    private Usr user;
    private DrawerLayout main;
    private LoginFragment login;
    private ScoreFragment score;
    private KebiaoFragment kebiao;
    private Context c;
    private OPAdapter op_list;
    private H h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        c=this;
        h=new H();
        score=new ScoreFragment();
        kebiao=new KebiaoFragment();
        login=new LoginFragment();
        login.setHandler(h);
        getSupportFragmentManager().beginTransaction().replace(R.id.empty,login).commit();
        main=(DrawerLayout)findViewById(R.id.main);
        main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ExpandableListView l=((ExpandableListView)findViewById(R.id.op_list));
        op_list=new OPAdapter(this,h);
        l.setAdapter(op_list);
        l.setOnItemClickListener(this);
        user=new Usr(this,h);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public class H extends Handler
    {
        public static final int ERROR=-1;
        public static final int LOGIN_OK=0;
        public static final int CODE_ERRORR=1;
        public static final int PWD_ERROR=2;
        public static final int INIT_COMP=3;
        public static final int LOGIN=4;
        public static final int SET_CODE=5;
        public static final int INIT_TERM=6;
        public static final int FRESH_CHENGJI=7;
        public static final int FRESH_KEBIAO=8;
        public static final int SEARCH_CHENGJI=9;
        public static final int SEARCH_KEBIAO=10;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case SET_CODE:
                    login.setCode(msg.getData().getByteArray("src"));
                    break;
                case LOGIN:
                    user.login(msg.getData().getString("usr"),msg.getData().getString("pwd"),msg.getData().getString("code"));
                case ERROR:
                    Log.e("aaa","error");
                    break;
                case CODE_ERRORR:
                    user.freshCode();
                    login.codeError();
                    break;
                case PWD_ERROR:
                    user.freshCode();
                    login.pwdError();
                    break;
                case LOGIN_OK:
                    main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    main.openDrawer(GravityCompat.START);

                    ((TextView)findViewById(R.id.text_usr)).setText(user.getUsr());
                    break;
                case INIT_COMP:
                    ((TextView)findViewById(R.id.text_name)).setText(user.getName());
                    break;
                case INIT_TERM:
                    String c=msg.getData().getString("xuenian");
                    op_list.addChild(0,c+"第一学期");
                    op_list.addChild(0,c+"第二学期");
                    op_list.addChild(0,c+"第三学期");

                    op_list.addChild(1,c+"第一学期");
                    op_list.addChild(1,c+"第二学期");
                    op_list.addChild(1,c+"第三学期");
                    break;
                case FRESH_CHENGJI:
                    score.fresh();
                    break;
                case FRESH_KEBIAO:
                    kebiao.fresh();
                    break;
                case SEARCH_CHENGJI:
                    getSupportFragmentManager().beginTransaction().replace(R.id.empty,score).commit();
                    break;
                case SEARCH_KEBIAO:
                    getSupportFragmentManager().beginTransaction().replace(R.id.empty,kebiao).commit();
                    break;
            }
        }

        public ArrayList<ScoreItem> getScore()
        {
            return score.getScore();
        }

        public ArrayList<SubItem> getKebiao()
        {
            return kebiao.getKebiao();
        }


        public void searchChengji(int xueqi)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.empty,score).commit();
            user.searchChengji(xueqi);
        }

        public void searchKebiao (int xueqi)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.empty,kebiao).commit();
            user.searchKebiao(xueqi);
        }
    }
}
