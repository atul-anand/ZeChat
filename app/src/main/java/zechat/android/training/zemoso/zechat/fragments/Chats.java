package zechat.android.training.zemoso.zechat.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import zechat.android.training.zemoso.zechat.R;
import zechat.android.training.zemoso.zechat.adapters.RecyclerViewAdapter;
import zechat.android.training.zemoso.zechat.java_beans.Startup;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chats extends Fragment {

    //region Variable Declaration

    private static final String TAG = Chats.class.getSimpleName();

    private Context mContext;

    //region Recycler View
    private List<Startup> mItems;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //endregion

    //endregion

    //region Singleton
    public Chats() {
        // Required empty public constructor
    }

    public static Chats newInstance() {
        Bundle args = new Bundle();
        //TODO: Set your variable values here
        Chats fragment = new Chats();
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region Inherited Methods

    //region Fragment Methods
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            //TODO initialize your variables here
            Realm realm = Realm.getDefaultInstance();
            RealmResults<Startup> data = realm.where(Startup.class).findAll();
            mItems = new ArrayList<>(data);
            Log.d(TAG, String.valueOf(mItems.size()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_active_chat_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.chat_recycler);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewAdapter = new RecyclerViewAdapter(mContext,mItems);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

            }
        });
    }
    //endregion

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    //endregion

}
