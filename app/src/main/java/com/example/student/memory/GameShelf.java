package com.example.student.memory;

import android.content.Context;

import java.util.ArrayList;

public class GameShelf {
    private ArrayList<Game> mGames;
    private static GameShelf sGameShelf;
    private Context mAppContext;

    private GameShelf(Context appContext) {
        mAppContext = appContext;
        mGames = new ArrayList<>();

        for (int i = 4; i <= 20; i = i + 2) {
            Game g = new Game();
            g.setSize(i);
            mGames.add(g);
        }
    }// End of GameShelf(Context)

    public static GameShelf get(Context c) {
        if (sGameShelf == null) {
            sGameShelf = new GameShelf(c.getApplicationContext());
        }

        return sGameShelf;
    }//End of get(Context)

    public ArrayList<Game> getGames(){
        return mGames;
    }//End of getGames()
}//End of GameShelf class