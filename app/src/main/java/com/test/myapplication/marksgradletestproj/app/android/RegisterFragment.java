package com.test.myapplication.marksgradletestproj.app.android;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.test.myapplication.marksgradletestproj.app.model.DatabaseHelper;
import com.test.myapplication.marksgradletestproj.app.model.User;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.ViewById;
import com.test.myapplication.marksgradletestproj.app.R;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by mark on 5/22/14.
 */
@EFragment(R.layout.register)
public class RegisterFragment extends Fragment {

    @ViewById
    EditText usernameEditText;

    @ViewById
    EditText passwordEditText;

    @ViewById
    EditText confirmPasswordEditText;

    @ViewById
    Button signUpButton;

    @OrmLiteDao(helper = DatabaseHelper.class, model = User.class)
    Dao<User, String> userDao;

    @Click
    public void signUpButtonClicked() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        try{
            //Check to see if username exists
            //TODO: Should be a API to database
            QueryBuilder<User, String> queryBuilder = userDao.queryBuilder();
            queryBuilder.where().eq("userName", username);
            PreparedQuery<User> preparedQuery = queryBuilder.prepare();
            ArrayList<User> result = (ArrayList<User>) userDao.query(preparedQuery);

            if(result.isEmpty()) {
                if(password.equals(confirmPassword)) {
                    User newUser = new User();
                    newUser.setUserName(username);
                    newUser.setPassword(password);
                    clearForm();
                    userDao.create(newUser);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.remove(this);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    ft.commit();
                }
                else {
                    Toast.makeText(getActivity(), R.string.passwordMismatch, Toast.LENGTH_LONG).show();
                    confirmPasswordEditText.setText("");
                    passwordEditText.setText("");
                }
            }
            else  {
                usernameEditText.setText("");
                Toast.makeText(getActivity(), R.string.usernameExists, Toast.LENGTH_LONG).show();
            }
        }
        catch (SQLException e) {
            Toast.makeText(getActivity(), "Query Error", Toast.LENGTH_LONG).show();
        }
    }

    public void clearForm() {
        usernameEditText.setText("");
        confirmPasswordEditText.setText("");
        passwordEditText.setText("");
    }
}
