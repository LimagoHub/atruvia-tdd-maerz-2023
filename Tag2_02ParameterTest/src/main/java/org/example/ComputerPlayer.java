package org.example;


import java.util.List;

public class ComputerPlayer  {



    private static final int TURNS [] = {3,1,1,2};

    /*
        wenn anzahl der Steine durch 4 einen Rest von 0 hat nimm 3 Steine
        wenn anzahl der Steine durch 4 einen Rest von 1 hat nimm 1 Steine
        wenn anzahl der Steine durch 4 einen Rest von 2 hat nimm 1 Steine
        wenn anzahl der Steine durch 4 einen Rest von 3 hat nimm 2 Steine
     */

    public Integer doTurn(Integer stones) {

        int turn = TURNS[stones % 4];
        System.out.println(String.format("Computer nimmt %s Steine.", turn));
        return turn;
    }

}
