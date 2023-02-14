package com.example.crashdetector.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.crashdetector.ContactListAdapter;
import com.example.crashdetector.ContactsPage;
import com.example.crashdetector.CrashesPage;
import com.example.crashdetector.MainActivity;
import com.example.crashdetector.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    private CrashesPage crashesPage;
    private ContactsPage contactsPage;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 1) {
            crashesPage = CrashesPage.newInstance();
            return crashesPage;
        }
        contactsPage = ContactsPage.newInstance();
        return contactsPage;
//        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    public void updateListAdapters() {
        System.out.println("GOT HERE");
        contactsPage.updateList();
        crashesPage.updateList();
    }
}