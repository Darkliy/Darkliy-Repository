package com.example.administrator.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private EditText username, from, to, message;
    private Spinner receiverList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiverList=findViewById(R.id.receiverList);
        final Button login = findViewById(R.id.login);
        final Button reset=findViewById(R.id.reset);
        final TextView status=findViewById(R.id.status);
        assert reset!=null;
        assert login != null;
        final Button send = findViewById(R.id.send);
        assert send != null;
        username = findViewById(R.id.username);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        message = findViewById(R.id.message);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username1=username.getText().toString();
                if(username1==""||username1==null){
                    username.setHint("用户名不能为空");
                }else{
                    boolean hasUser = false;
                    String[] users = MainActivity.this.getResources().getStringArray(R.array.users);
                    for (String user:users) {
                        if (user.equals(username1)) {
                            from.setText(user
                                    + "@" + MainActivity.this.getResources().getString(R.string.email));
                            message.setText(getResources().getString(R.string.nchu)+"\n\t\t\t\t\t\t\t\t\t\t\t\t签名：" + user);
                            send.setEnabled(true);
                            reset.setEnabled(true);
                            login.setEnabled(false);
                            hasUser = true;
                            break;
                        }
                    }
                    if (!hasUser) {
                        username.setText(null);
                        username.setHint("用户"+username1+"不存在");
                    }else{
                        username.setText(null);
                        username.setHint("用户"+username1+"已登录！");
                    }
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(to.getText().toString().equals("")||to.getText().toString()==null){
                   status.setText("收件人不能为空!");
                }else{
                    Toast.makeText(MainActivity.this, "正在向" + to.getText()
                            + "发送邮件...", Toast.LENGTH_SHORT).show();
                    status.setText("成功发送到："+to.getText());
                }
            }
        });
        receiverList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    to.setText(receiverList.getItemAtPosition(position).toString());
                }else{
                    to.setText(null);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void resetClick(View v){
        Intent intent=new Intent();
        intent.setClassName(this,"com.example.administrator.myapplication.MainActivity");
        startActivity(intent);
        this.finish();
    }
}
