package com.cnn.android.basemodel.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.cnn.android.basemodel.R;
import com.cnn.android.basemodel.fragment.BaseFragment;

/**
 * Created by nmj on 16/8/8.
 */
public class BaseActivity extends AppCompatActivity {
    protected BaseFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    /**
     * overWrite method need provide id of fragment_container_activity as a container
     */
    protected int getLayoutId() {
        return R.layout.base_activity;
    }

    protected void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, null, false);
    }

    protected void replaceFragment(Fragment f, Bundle arguments, boolean isAddToStack) {
        if (isFinishing() || f == null) {
            return;
        }
        if (arguments != null) {
            f.setArguments(arguments);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_activity, f);
        if (isAddToStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }
}
