package travelingsalesmanproblem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class TravelingSalesmanProblem extends JPanel implements KeyListener, ActionListener {

    JFrame frame; // the frame to draw on
    Color ghostCursor = new Color(0, 0, 0, 60); // colour of the ghost cursor
    Color startingCity = new Color(36, 49, 199); // colour of the starting city
    Color city = new Color(0, 0, 0); // colour of a regular city
    Color roads = new Color(255, 0, 30); // colour of roads
    Color hints = new Color(0, 0, 0, 60); // colour of hints
    int mouseX; // the mouses x position
    int mouseY; // the mouses y position
    int citySize = 20; // the size of a city

    ArrayList<City> currentCitys = new ArrayList<City>(); // the list of citys

    public static void main(String[] args) {

        new TravelingSalesmanProblem();

    }

    /**
     * paint method
     */
    @Override
    public void paint(Graphics g) {

        g.clearRect(0, 0, frame.getWidth(), frame.getHeight());

        drawHints(g);
        drawCursor(g);
        drawCitys(g);
        drawRoads(g);

    }

    /**
     * constructor
     */
    public TravelingSalesmanProblem() {

        frame = new JFrame();

        frame.add(this);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        // setting start position of the frame
        frame.setLocationRelativeTo(null);

        // closing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title
        frame.setTitle("TSP");

        frame.setBackground(Color.white);

        // set frame visibility
        frame.setVisible(true);

        // adding a key listner
        frame.addKeyListener(this);

        // adding a mouse listner
        AddMouseHandler();

    }

    /**
     * function to approximate the solution to the TSP
     * 
     * @param citys  - the citys used in the solution
     * @param beenTo - the citys that have been travelled to
     * @return ArrayList<City> - the list of citys in order of travel
     */
    private ArrayList<City> approximationAlgorithm(ArrayList<City> citys, ArrayList<City> beenTo) {

        System.out.println("solving");

        ArrayList<City> citysToGoTo = citys;
        ArrayList<City> citysBeenTo = beenTo;
        ArrayList<Double> distances = new ArrayList<>();

        if (citys.isEmpty()) {

            return beenTo;

        } else {

            for (int i = 0; i < citysToGoTo.size(); i++) {

                distances.add(Math.sqrt(
                        Math.pow(citysBeenTo.get(citysBeenTo.size() - 1).getX() - citysToGoTo.get(i).getX(), 2) + Math
                                .pow(citysBeenTo.get(citysBeenTo.size() - 1).getY() - citysToGoTo.get(i).getY(), 2)));

            }
            double lowestValue = distances.get(0);
            int index = 0;
            for (int i = 0; i < distances.size(); i++) {

                if (distances.get(i) < lowestValue) {

                    lowestValue = distances.get(i);
                    index = i;

                }

            }

            citysBeenTo.add(citysToGoTo.get(index));
            citysToGoTo.remove(index);

            citysBeenTo = approximationAlgorithm(citysToGoTo, citysBeenTo);
        }

        return citysBeenTo;
    }

    // keylistner
    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {

            case 27:
                // esc
                System.exit(0);
                break;

            case 68:
                // d
                currentCitys.clear();
                repaint();

            default:
                System.out.println("you pressed: " + ke.getKeyCode());
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    // adds a mouse listner to the program so the user can zoom in and out of the
    // fractal
    private void AddMouseHandler() {

        MouseInputAdapter mia = new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();

                int mouseX = p.x;
                int mouseY = p.y;

                System.out.println("mouse x: " + mouseX);
                System.out.println("mouse y: " + mouseY);

                currentCitys.add(City.getCity(mouseX, mouseY));
                ArrayList<City> startingCity = new ArrayList<City>();
                startingCity.add(currentCitys.get(0));
                currentCitys.remove(0);
                currentCitys = approximationAlgorithm(currentCitys, startingCity);

                repaint();
            }

            public void mouseMoved(MouseEvent me) {

                Point p = me.getPoint();

                mouseX = p.x;
                mouseY = p.y;

                repaint();

            }

        };
        addMouseListener(mia);
        addMouseMotionListener(mia);
    }

    private void drawCursor(Graphics g) {

        g.setColor(ghostCursor);
        g.fillOval(mouseX - ((citySize * 1) / 2), mouseY - ((citySize * 1) / 2), citySize, citySize);

    }

    /**
     * function to draw hints
     * 
     * @param g
     */
    private void drawHints(Graphics g) {

        g.setColor(hints);
        g.drawString("Click to place down citys", 5, 10);
        g.drawString("press 'd' to delete the current cities", 5, 30);
        g.drawString("press 'esc' to exit", 5, 50);

    }

    /**
     * function to draw all the placed citys
     * 
     * @param g
     */
    private void drawCitys(Graphics g) {

        if (!currentCitys.isEmpty()) {
            g.setColor(startingCity);
            g.fillOval(currentCitys.get(0).getX() - (citySize / 2), currentCitys.get(0).getY() - (citySize / 2),
                    citySize, citySize);

            g.setColor(city);
            for (int i = 1; i < currentCitys.size(); i++) {
                g.fillOval(currentCitys.get(i).getX() - (citySize / 2), currentCitys.get(i).getY() - (citySize / 2),
                        citySize, citySize);
            }
        }

    }

    /**
     * function to draw all the roads
     * 
     * @param g
     */
    private void drawRoads(Graphics g) {

        if (!currentCitys.isEmpty()) {
            int startingX = currentCitys.get(0).getX();
            int startingY = currentCitys.get(0).getY();

            int currentX = startingX;
            int currentY = startingY;

            for (int i = 0; i < currentCitys.size(); i++) {
                g.setColor(roads);
                g.drawLine(currentX, currentY, currentCitys.get(i).getX(), currentCitys.get(i).getY());
                currentX = currentCitys.get(i).getX();
                currentY = currentCitys.get(i).getY();

            }
            g.drawLine(currentX, currentY, startingX, startingY);

        }

    }

}
