package com.icosom.social.activity;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import com.icosom.social.R;
import com.icosom.social.fragment.EnterOTPFragment;
import com.icosom.social.fragment.NameSetUpFragment;

public class SetUpProfileActivity extends AppCompatActivity
{
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile);

        fragmentManager = getSupportFragmentManager();

        if (getIntent().getBooleanExtra("otp_verification", false))
        {
            fragmentManager.beginTransaction().
                    replace(R.id.container, new EnterOTPFragment()).
                    commit();
        }
        else
        {
            fragmentManager.beginTransaction().
                    replace(R.id.container, new NameSetUpFragment()).
                    commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount()>0)
        {
            fragmentManager.popBackStack();
            return;
        }
        super.onBackPressed();
    }
}