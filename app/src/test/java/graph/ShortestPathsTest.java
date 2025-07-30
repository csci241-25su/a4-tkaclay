package graph;

import static org.junit.Assert.*;
import org.junit.FixMethodOrder;

import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.net.URL;
import java.io.FileNotFoundException;

import java.util.LinkedList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShortestPathsTest {

    /* Performs the necessary gradle-related incantation to get the
       filename of a graph text file in the src/test/resources directory at
       test time.*/
    private String getGraphResource(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return resource.getPath();
    }

    /* Returns the Graph loaded from the file with filename fn located in
     * src/test/resources at test time. */
    private Graph loadBasicGraph(String fn) {
        Graph result = null;
        String filePath = getGraphResource(fn);
        try {
          result = ShortestPaths.parseGraph("basic", filePath);
        } catch (FileNotFoundException e) {
          fail("Could not find graph " + fn);
        }
        return result;
    }

    /** Dummy test case demonstrating syntax to create a graph from scratch.
     * Write your own tests below. */
    @Test
    public void test00Nothing() {
        Graph g = new Graph();
        Node a = g.getNode("A");
        Node b = g.getNode("B");
        g.addEdge(a, b, 1);

        // sample assertion statements:
        assertTrue(true);
        assertEquals(2+2, 4);
        g.report();
    }

    /** Minimal test case to check the path from A to B in Simple0.txt */
    @Test
    public void test01Simple0() {
        Graph g = loadBasicGraph("Simple0.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node a = g.getNode("A");
        sp.compute(a);
        Node b = g.getNode("B");
        LinkedList<Node> abPath = sp.shortestPath(b);
        assertEquals(abPath.size(), 2);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 1.0, 1e-6);
    }

    @Test
    public void test02ReachableNodes0() {
        Graph g = loadBasicGraph("Simple0.txt");
        g.report();
        Object[] reachableNodes = g.getNodes().values().toArray();
        ShortestPaths sp = new ShortestPaths();
        sp.compute(g.getNode("A"));

        assertEquals(reachableNodes.length, 3);
        assertEquals(((Node) (reachableNodes[0])).getId(), "A");
        assertEquals(((Node) (reachableNodes[1])).getId(), "B");
        assertEquals(((Node) (reachableNodes[2])).getId(), "C");
        assertEquals(sp.shortestPathLength(((Node) (reachableNodes[0]))), 0.0, 0.0000000001);
        assertEquals(sp.shortestPathLength(((Node) (reachableNodes[1]))), 1.0, 0.0000000001);
        assertEquals(sp.shortestPathLength(((Node) (reachableNodes[2]))), 2.0, 0.0000000001);
    }

    @Test
    public void test03SameNode0() {
        Graph g = loadBasicGraph("Simple0.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        sp.compute(g.getNode("A"));
        assertEquals(sp.shortestPathLength(g.getNode("A")), 0.0, 0.0000000001);
        assertEquals(sp.shortestPath(g.getNode("A")).size(), 1);
    }

    @Test
    public void test04Unreachable2() {
        Graph g = loadBasicGraph("Simple2.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        sp.compute(g.getNode("A"));
        assertEquals(sp.shortestPathLength(g.getNode("D")), Double.POSITIVE_INFINITY, 0.0000000001);
        assertEquals(sp.shortestPath(g.getNode("D")), null);
    }
    

    /* Pro tip: unless you include @Test on the line above your method header,
     * gradle test will not run it! This gets me every time. */
}
