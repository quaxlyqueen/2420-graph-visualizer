import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 *
 *  Modification of Sedgewick and Wayne's EdgeWeightedGraph, to allow a dynamic number of vertices based upon user input.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Josh Ashton
 */
public class EdgeWeightedGraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private int V;
    private int E;
    private ArrayList<Bag<Edge>> adj;

    /**
     * Create a new EdgeWeightedGraph with no vertices or edges. These may be added dynamically by the user.
     */
    public EdgeWeightedGraph() {
        this.V = 0;
        this.E = 0;
        adj = new ArrayList<>();
    }

    public EdgeWeightedGraph(In in) {
        if (in == null) throw new IllegalArgumentException("argument is null");

        try {
            V = in.readInt();
            adj = new ArrayList<Bag<Edge>>();
            for (int v = 0; v < V; v++) {
                adj.add(new Bag<Edge>());
            }

            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                double weight = in.readDouble();
                Edge e = new Edge(v, w, weight);
                addEdge(e);
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in EdgeWeightedGraph constructor", e);
        }

    }

    
    /**
     * Initializes a random edge-weighted graph with {@code V} vertices and <em>E</em> edges.
     *
     * @param  V the number of vertices
     * @param  E the number of edges
     * @throws IllegalArgumentException if {@code V < 0}
     * @throws IllegalArgumentException if {@code E < 0}
     */
    public EdgeWeightedGraph(int V, int E) {
        this.V = V;
        adj = new ArrayList<Bag<Edge>>();
        for (int v = 0; v < V; v++) {
            adj.add(new Bag<Edge>());
        }

        if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniformInt(V);
            int w = StdRandom.uniformInt(V);
            int weight = StdRandom.uniformInt(0, 100);
            Edge e = new Edge(v, w, (double) weight);
            addEdge(e);
        }
    }

    /**
     * Adds a new vertex to the edge-weighted graph.
     */
    public void addVertex() {
        Bag<Edge> b = new Bag<>();
        V++;
        adj.add(b);
    }

    /**
     * Adds the undirected edge {@code e} to this edge-weighted graph.
     *
     * @param  e the edge
     * @throws IllegalArgumentException unless both endpoints are between {@code 0} and {@code V-1}
     */
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        validateVertex(v);
        validateVertex(w);
        adj.get(v).add(e);
        adj.get(w).add(e);
        E++;
    }

    /**
     * Returns the number of vertices in this edge-weighted graph.
     *
     * @return the number of vertices in this edge-weighted graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this edge-weighted graph.
     *
     * @return the number of edges in this edge-weighted graph
     */
    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Returns the edges incident on vertex {@code v}.
     *
     * @param  v the vertex
     * @return the edges incident on vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adj.get(v);
    }

    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the degree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int degree(int v) {
        validateVertex(v);
        return adj.get(v).size();
    }

    /**
     * Returns all edges in this edge-weighted graph.
     * To iterate over the edges in this edge-weighted graph, use foreach notation:
     * {@code for (Edge e : G.edges())}.
     *
     * @return all edges in this edge-weighted graph, as an iterable
     */
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // add only one copy of each self loop (self loops will be consecutive)
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    /**
     * Returns a string representation of the edge-weighted graph.
     * This method takes time proportional to <em>E</em> + <em>V</em>.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("vertices: " + V + " || edges: " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append("vertex " + v + " edges: ");
            for (Edge e : adj.get(v)) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}
