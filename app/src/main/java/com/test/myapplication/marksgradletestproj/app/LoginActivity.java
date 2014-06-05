package com.test.myapplication.marksgradletestproj.app;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.test.myapplication.marksgradletestproj.app.android.RegisterFragment;
import com.test.myapplication.marksgradletestproj.app.android.RegisterFragment_;
import com.test.myapplication.marksgradletestproj.app.model.DatabaseHelper;
import com.test.myapplication.marksgradletestproj.app.model.TestObject;
import com.test.myapplication.marksgradletestproj.app.model.User;
import com.test.myapplication.marksgradletestproj.app.network.MyRestClient;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.sql.SQLException;
import java.util.ArrayList;

@EActivity(R.layout.log_in)
public class LoginActivity extends Activity {

    @ViewById
    EditText usernameEditText;

    @ViewById
    EditText passwordEditText;

    @ViewById
    Switch rememberMeSwitch;

    @ViewById
    Button loginButton;

    @OrmLiteDao(helper = DatabaseHelper.class, model = User.class)
    Dao<User, String> userDao;

    @Pref
    MyPrefs_ myPrefs;

    @RestService
    MyRestClient myRestClient;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void initView(){

        rememberMeSwitch.setChecked(myPrefs.rememberMe().get());
    }

    @Click
    public void loginButtonClicked(){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        try {
            //TODO: Should be a query to server.
            QueryBuilder<User, String> queryBuilder = userDao.queryBuilder();
            queryBuilder.where().eq("userName", username);
            PreparedQuery<User> preparedQuery = queryBuilder.prepare();
            ArrayList<User> result = (ArrayList<User>) userDao.query(preparedQuery);

            if(result.size() != 0 && result.get(0).getPassword().equals(password)) {
                Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "Username or password is incorrect.", Toast.LENGTH_LONG).show();
                passwordEditText.setText("");
            }
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        getThisObject();

    }

    @Background
    public void getThisObject(){
        TestObject testObject = myRestClient.getTestObject(6);
    }


    @Click
    public void registerButtonClicked(){
        RegisterFragment registerFragment = new RegisterFragment_();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, registerFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Click
    public void rememberMeSwitchClicked() {
        Toast.makeText(this, "TEST", Toast.LENGTH_LONG).show();
        myPrefs.rememberMe().put(rememberMeSwitch.isChecked());
    }
}
