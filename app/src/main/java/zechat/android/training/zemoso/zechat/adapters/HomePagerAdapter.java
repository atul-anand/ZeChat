package zechat.android.training.zemoso.zechat.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by vin on 24/7/17.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    //region Variable Declaration
    private static final String TAG = HomePagerAdapter.class.getCanonicalName();

    private List<Fragment> fragmentList;
    //endregion

    public HomePagerAdapter(@NonNull FragmentManager fm, @NonNull List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    //region Inherited Methods
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position
        );
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getClass().getSimpleName();
    }
    //endregion
}
