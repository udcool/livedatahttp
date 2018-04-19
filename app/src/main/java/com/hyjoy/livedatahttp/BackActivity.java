package com.hyjoy.livedatahttp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hyjoy on 2018/4/19.
 */
public class BackActivity extends AppCompatActivity {

    private static final String FRAGMENT_CLASS_NAME = "fragment_class_name";

    public static void startFragment(Context context, Class<? extends Fragment> clazz) {
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_CLASS_NAME, clazz.getName());
        startFragment(context, bundle);
    }

    private static void startFragment(Context context, Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Bundle must not be null");
        }

        String name = bundle.getString(FRAGMENT_CLASS_NAME, null);
        if (name == null) {
            throw new IllegalArgumentException("Fragment ID/name must has one");
        }

        Intent intent = new Intent(context, BackActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);

        String clazzName = getIntent().getStringExtra(FRAGMENT_CLASS_NAME);
        if (clazzName == null) {
            finish();
            return;
        }
        Fragment fragment = Fragment.instantiate(this, clazzName);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.cl_container, fragment);
        transaction.commit();
    }
}
