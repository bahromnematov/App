package uz.gita.exam1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    AppCompatButton loginBtn;
    EditText logintext;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        loginBtn.setOnClickListener(v->checkLogin());
    }

    private void findViews(){
        loginBtn=findViewById(R.id.btn_login);
        logintext=findViewById(R.id.text_login);
        passwordText=findViewById(R.id.text_password);
    }

    private void checkLogin(){
        String login=logintext.getText().toString();
        String password=passwordText.getText().toString();

        if (login.equals("admin")&&password.equals("12345")){
            Intent intent=new Intent(this,AdminActivity.class);
            startActivity(intent);
        }else if(login.equals("user1")&&password.equals("12345")){
            Intent intent=new Intent(this,UserActivity.class);
            startActivity(intent);

        }
    }
}