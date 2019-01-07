package clickerbot.com.troop.clickerbot.settings;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import clickerbot.com.troop.clickerbot.R;

public class SettingsFragment extends Fragment {

    private final String TAG = SettingsFragment.class.getSimpleName();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(4);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new SettingPrestigeFragment(), "Prestige");
        adapter.addFragment(new SettingSkillFragment(), "Skills");
        adapter.addFragment(new SettingOtherFragment(), "Other");
        adapter.addFragment(new SettingDelayFragment(), "Delays");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
