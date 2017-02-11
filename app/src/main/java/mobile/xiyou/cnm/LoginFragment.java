package mobile.xiyou.cnm;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/1/25 0025.
 */

public class LoginFragment extends Fragment implements View.OnClickListener{

    private EditText edit_usr,edit_pwd,edit_code;
    private ImageView img_code;
    private Button b_login;
    private MainActivity.H mh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.activity_main,container,false);
        img_code=(ImageView)root.findViewById(R.id.img_code);
        edit_code=(EditText)root.findViewById(R.id.edit_code);
        edit_pwd=(EditText)root.findViewById(R.id.edit_pwd);;
        edit_usr=(EditText)root.findViewById(R.id.edit_usr);
        edit_usr.setText("04153036");
        edit_pwd.setText("asd123");
        b_login=(Button)root.findViewById(R.id.b_login);
        b_login.setOnClickListener(this);

        return root;
    }

    public void setHandler(MainActivity.H h){mh=h;}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        b_login.setClickable(false);
        Message msg=new Message();
        msg.what=mh.LOGIN;
        Bundle x=new Bundle();
        x.putString("usr",edit_usr.getText().toString());
        x.putString("pwd",edit_pwd.getText().toString());
        x.putString("code",edit_code.getText().toString());
        msg.setData(x);
        mh.sendMessage(msg);
    }

    public void codeError()
    {
        edit_code.setText("");
        edit_code.setHintTextColor(Color.RED);
        edit_code.setHint("验证码不正确");
        b_login.setClickable(true);
    }

    public void pwdError()
    {
        edit_code.setText("");
        edit_code.setHintTextColor(Color.GRAY);
        edit_pwd.setText("");
        edit_pwd.setHint("密码不正确");
        edit_pwd.setHintTextColor(Color.RED);
        b_login.setClickable(true);
    }

    public void setCode(byte[] bb)
    {
        img_code.setImageBitmap(BitmapFactory.decodeByteArray(bb,0,bb.length));
    }
}
