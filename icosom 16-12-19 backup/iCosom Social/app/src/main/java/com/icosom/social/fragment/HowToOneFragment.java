package com.icosom.social.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.icosom.social.R;

public class HowToOneFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_how_to_one, container, false);

        Glide.
                with(getContext()).
                load(R.drawable.social).
                into((ImageView) v.findViewById(R.id.iv_one));

        return v;
    }
}