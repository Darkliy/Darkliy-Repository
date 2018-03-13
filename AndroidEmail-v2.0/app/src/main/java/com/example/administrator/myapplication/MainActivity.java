package com.example.administrator.myapplication;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private EditText username, to, message,subject;
    private TextView from;
    private Spinner receiverList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiverList=findViewById(R.id.receiverList);
        final Button login = findViewById(R.id.login);
        final Button reset=findViewById(R.id.reset);
        final Button more=findViewById(R.id.more);
        final TextView status=findViewById(R.id.status);
        assert reset!=null;
        assert login != null;
        final Button send = findViewById(R.id.send);
        assert send != null;
        username = findViewById(R.id.username);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        message = findViewById(R.id.message);
        subject=findViewById(R.id.subject);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username1=username.getText().toString().trim();
                if(username1.equals("")||username1==null){
                    username.setText(null);
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
                            username.setEnabled(false);
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
                String address=to.getText().toString().trim();
                if(address.equals("")||address==null){
                    to.setText(null);
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
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("选择常用联系人");
                String[] man=MainActivity.this.getResources().getStringArray(R.array.receiverList);
                final String[] man1=new String[man.length-1];
                for(int i=0;i<man1.length;i++){
                    man1[i]=man[i+1];
                }
                builder.setItems(man1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        to.setText(man1[which]);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确定注销用户吗?");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        username.setHint("用户名");
                        username.setEnabled(true);
                        login.setEnabled(true);
                        reset.setEnabled(false);
                        to.setText(null);
                        receiverList.setSelection(0);
                        from.setText(null);
                        subject.setText(null);;
                        message.setText(getResources().getString(R.string.nchu));
                        send.setEnabled(false);
                        status.setText(null);
                    }
                });
               builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               });
               builder.create().show();
            }
        });
    }
}
