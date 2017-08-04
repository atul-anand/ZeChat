package zechat.android.training.zemoso.zechat.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import zechat.android.training.zemoso.zechat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfile extends Fragment {

    private static final String TAG = EditProfile.class.getSimpleName();
    EditText mDescription;
    EditText mStatus;
    Button mButton;
    Button mBrowseButton;
    static Bundle args;
    //region Singleton
    public EditProfile() {
        // Required empty public constructor
    }

    public static EditProfile newInstance(){
        args = new Bundle();
        EditProfile fragment = new EditProfile();
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDescription = view.findViewById(R.id.profile_edit_text_desc);
        mStatus = view.findViewById(R.id.profile_edit_text_status);
        mButton = view.findViewById(R.id.profile_button_submit);
        mBrowseButton = view.findViewById(R.id.profile_button_browse);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, (String) args.getCharSequence("description"));
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Submit",Toast.LENGTH_SHORT).show();
            }
        });
        mBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 1);
            }
        });
    }
}
