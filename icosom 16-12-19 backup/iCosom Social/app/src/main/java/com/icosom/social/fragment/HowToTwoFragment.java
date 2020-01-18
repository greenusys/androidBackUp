package com.icosom.social.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.icosom.social.R;

public class HowToTwoFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_how_to_two, container, false);

        Glide.
                with(getContext()).
                load(R.drawable.messenger).
                into((ImageView) v.findViewById(R.id.iv_two));

        return v;
    }
}