package com.example.student.memory;

import android.support.v4.app.Fragment;

public class TileListActivity extends SingleFragmentActivity {

    public Fragment createFragment(){
        return new TileListFragment();
    }// End of createFragment()

}// End of TileListActivity class