package hua.lee.skills.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hua.lee.skills.databinding.FragmentMediaBinding;

public class MediaFragment extends Fragment {

    private FragmentMediaBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMediaBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
