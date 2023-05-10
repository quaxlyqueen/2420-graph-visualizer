import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.text.Position;

import edu.princeton.cs.algs4.Edge;

public class DisplayPanel extends JPanel {

    private static ST<Integer, Vertex> st;
    private int n;
    private static final int SIZE = 256;
    private int a = SIZE / 2;
    private int b = a;
    private int r = 4 * SIZE / 5;

    public DisplayPanel() {
        super();
            setPreferredSize(new Dimension(300, 300));
            setSize(getPreferredSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        st = new ST<>();
        EdgeWeightedGraph G = GraphFunctions.getGraph();
        for(int v = 0; v < G.V(); v++)
            st.put(v, new Vertex(v));
        n = st.size();
        // draw the entire graph first

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        a = getWidth() / 2;
        b = getHeight() / 2;
        int m = Math.min(a, b);
        r = 4 * m / 5;
        int r2;
        if(n > 0) r2 = Math.abs(m - r) / n;
        else r2 = Math.abs(m - r) / 2;
        for (int i = 0; i < n; i++) {
            double t = 2 * Math.PI * i / n;
            int x = (int) Math.round(a + r * Math.cos(t));
            int y = (int) Math.round(b + r * Math.sin(t));
            g2d.fillOval(x - r2, y - r2, 2 * r2, 2 * r2);
            st.put(i, new Vertex(x, y));
        }

        g.getFont().deriveFont(Font.BOLD);
        g2d.setColor(Color.ORANGE);
        if(n < 50) {
            for(int i = 0; i < n; i++) {
                int x = st.get(i).getX();
                int y = st.get(i).getY();

                g.drawString(i + "", x - (2 * r2), y + (2 * r2));
            }
        }

        drawLine(r2, g, g2d, G.edges(), Color.WHITE);
        drawLine(r2, g, g2d, GraphFunctions.getMST(), Color.GREEN);
    }

    private void drawLine(int r2, Graphics g, Graphics2D g2d, Iterable<Edge> edges, Color color) {
        g2d.setColor(color);
        for (Edge e: edges) {
            int v = e.either();
            int w = e.other(v);

            int vX = st.get(v).getX();
            int wX = st.get(w).getX();
            int vY = st.get(v).getY();
            int wY = st.get(w).getY();

            g.drawLine(vX, vY, wX, wY);
            g.drawString(String.format("%.0f", e.weight()), ((vX + wX) / 2) + r2, ((vY + wY) / 2) + r2);
        }
    }

    private class Vertex { // extends JComponent for @Override of paint()?
        private int x;
        private int y;
        private int v;

        public Vertex(int v) {
            this.v = v;
        }

        public Vertex(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getV() {
            return v;
        }
    }
}
