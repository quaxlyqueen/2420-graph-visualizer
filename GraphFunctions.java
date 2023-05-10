import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.KruskalMST;

import java.io.File;
import java.util.Random;

import edu.princeton.cs.algs4.Edge;

/**
 * @author Josh Ashton
 */
public class GraphFunctions {

    private static KruskalMST mst;
    private static EdgeWeightedGraph g = new EdgeWeightedGraph(); // TODO: Convert to a DirectedEdgeWeightedGraph at some point.

    public static void init() {
        String fileName = "./resources/GraphInternet.txt";
        g = new EdgeWeightedGraph(new In(fileName));

        edu.princeton.cs.algs4.EdgeWeightedGraph G = new edu.princeton.cs.algs4.EdgeWeightedGraph(g.V());
        for (Edge e : g.edges()) {
            G.addEdge(e);
        }
        mst = new KruskalMST(G);
    }

    public static void random(int V, int E) {
        g = new EdgeWeightedGraph(V, E);
        edu.princeton.cs.algs4.EdgeWeightedGraph G = new edu.princeton.cs.algs4.EdgeWeightedGraph(g.V());
        for (Edge e : g.edges()) {
            G.addEdge(e);
        }
        mst = new KruskalMST(G);
    }

    public static void reset() {
        g = new EdgeWeightedGraph();

        edu.princeton.cs.algs4.EdgeWeightedGraph G = new edu.princeton.cs.algs4.EdgeWeightedGraph(g.V());
        for (Edge e : g.edges()) {
            G.addEdge(e);
        }
        mst = new KruskalMST(G);
    }

    public static void loadGraph(File f) {
        g = new EdgeWeightedGraph(new In(f));

        edu.princeton.cs.algs4.EdgeWeightedGraph G = new edu.princeton.cs.algs4.EdgeWeightedGraph(g.V());
        for (Edge e : g.edges()) {
            G.addEdge(e);
        }
        mst = new KruskalMST(G);
    }

    public static void addVertex() {
        g.addVertex();
    }

    public static void addEdge(int v, int w, double cost) {
        g.addEdge(new Edge(v, w, cost));
    }

    public static Iterable<Edge> getMST() {
        edu.princeton.cs.algs4.EdgeWeightedGraph G = new edu.princeton.cs.algs4.EdgeWeightedGraph(g.V());
        for (Edge e : g.edges()) {
            G.addEdge(e);
        }
        return new KruskalMST(G).edges();
    }

    public static EdgeWeightedGraph getGraph() {
        return g;
    }
}
