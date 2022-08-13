package hua.lee.skills.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hua.lee.skills.databinding.FragmentJniBinding;
import hua.lee.skills.jni.MathJni;

public class NativeFragment extends Fragment {
    private FragmentJniBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentJniBinding.inflate(inflater, container, false);
        binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res = MathJni.add(1, 2);
                Toast.makeText(getContext(), "native res:" + res, Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}
