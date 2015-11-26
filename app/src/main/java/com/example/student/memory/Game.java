package com.example.student.memory;

public class Game {
    private int mSize;
    public static final String EXTRA_SIZE = "com.example.student.memory.game.size";

    public Game(){
    }//End ofGame()

    public int getSize(){
        return mSize;
    }//End of getSize()

    public void setSize(int size){
        mSize = size;
    }// End of setSize(int)
}// End of Game class