import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class GrapheListeTest {

    private GrapheListe buildGraphe() {
        GrapheListe g = new GrapheListe();
        g.ajouterArc("A", "B", 12.0);
        g.ajouterArc("A", "D", 87.0);
        g.ajouterArc("B", "E", 11.0);
		g.ajouterArc("C", "A", 19.0);
		g.ajouterArc("D", "B", 23.0);
		g.ajouterArc("D", "B", 10.0);
        g.ajouterArc("E", "D", 43.0);
        return g;
    }

   
    public void testNoeuds() {
        GrapheListe g = buildGraphe();
        List<String> noeuds = g.getNoeuds();
        assertTrue(noeuds.contains("A"));
        assertTrue(noeuds.contains("B"));
        assertTrue(noeuds.contains("C"));
        assertTrue(noeuds.contains("D"));
        assertTrue(noeuds.contains("E"));
    }

   
    public void testAdjacentsA() {
        GrapheListe g = buildGraphe();
        List<Arc> arcs = g.getAdjacents("A").getArcs();
        assertEquals(2, arcs.size());
        assertEquals("B", arcs.get(0).getCible());
        assertEquals(12.0, arcs.get(0).getPoids());
        assertEquals("D", arcs.get(1).getCible());
        assertEquals(87.0, arcs.get(1).getPoids());
    }

    public void testNoeudSansArc() {
        GrapheListe g = buildGraphe();
        assertEquals(0, g.getAdjacents("C").getArcs().size());
    }


    public void testNoeudInexistant() {
        GrapheListe g = buildGraphe();
        assertEquals(0, g.getAdjacents("Z").getArcs().size());
    }
}