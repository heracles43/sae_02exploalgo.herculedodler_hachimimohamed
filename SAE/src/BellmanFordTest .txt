import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BellmanFordTest {

    private GrapheListe buildGraphe() {
        GrapheListe g = new GrapheListe();
        g.ajouterArc("A", "B", 12.0);
        g.ajouterArc("A", "D", 87.0);
        g.ajouterArc("B", "E", 11.0);
        g.ajouterArc("C", "A", 19.0);
        g.ajouterArc("D", "B", 23.0);
        g.ajouterArc("D", "C", 10.0);
        g.ajouterArc("E", "D", 43.0);
        return g;
    }

   
    public void testDistances() {
        BellmanFord bf = new BellmanFord();
        Valeurs v = bf.resoudre(buildGraphe(), "A");

        assertEquals(0.0,  v.getValeur("A"));
        assertEquals(12.0, v.getValeur("B"));
        assertEquals(76.0, v.getValeur("C")); // A->B->E->D->C
        assertEquals(66.0, v.getValeur("D")); // A->B->E->D
        assertEquals(23.0, v.getValeur("E")); // A->B->E
    }

   
    public void testParents() {
        BellmanFord bf = new BellmanFord();
        Valeurs v = bf.resoudre(buildGraphe(), "A");

        assertNull(v.getParent("A"));
        assertEquals("A", v.getParent("B"));
        assertEquals("B", v.getParent("E"));
        assertEquals("E", v.getParent("D"));
        assertEquals("D", v.getParent("C"));
    }

    
    public void testDepartIsolé() {
        GrapheListe g = new GrapheListe();
        g.ajouterNoeud("X");
        g.ajouterNoeud("Y");
        BellmanFord bf = new BellmanFord();
        Valeurs v = bf.resoudre(g, "X");
        assertEquals(0.0, v.getValeur("X"));
        assertEquals(Double.MAX_VALUE, v.getValeur("Y"));
    }
}