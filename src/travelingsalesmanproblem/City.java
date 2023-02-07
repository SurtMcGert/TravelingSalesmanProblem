/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelingsalesmanproblem;

/**
 *
 * @author hayre14
 */
public class City {

    private int x;// x-coordinate of city
    private int y;// y-coordinate of city

    // private constructor
    private City(int x, int y) {

        this.x = x;
        this.y = y;

    }

    /**
     * function to get a new city
     * 
     * @param x - the x position of the city
     * @param y - the y position of the city
     * @return City - the new city
     */
    public static City getCity(int x, int y) {

        return new City(x, y);

    }

    /**
     * function to get the citys x-coordinate
     * 
     * @return int - the citys x-coordinate
     */
    public int getX() {

        return x;

    }

    /**
     * function to get the citys y-coordinate
     * 
     * @return int - the citys y-coordinate
     */
    public int getY() {

        return y;

    }

}
