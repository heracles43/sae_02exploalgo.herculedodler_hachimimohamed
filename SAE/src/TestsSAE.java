import java.util.List;

/**
 * Tests unitaires simples sans JUnit
 * Lancer avec : java TestsSAE
 */
public class TestsSAE {

    static int ok = 0;
    static int ko = 0;

    static void assertTrue(String nom, boolean condition) {
        if (condition) { System.out.println("[OK] " + nom); ok++; }
        else           { System.out.println("[KO] " + nom); ko++; }
    }

    static void assertEquals(String nom, Object attendu, Object obtenu) {
        boolean eq = attendu == null ? obtenu == null : attendu.equals(obtenu);
        if (eq) { System.out.println("[OK] " + nom); ok++; }
        else    { System.out.println("[KO] " + nom + " | attendu=" + attendu + " obtenu=" + obtenu); ko++; }
    }

    static void assertEquals(String nom, double attendu, double obtenu, double delta) {
        if (Math.abs(attendu - obtenu) <= delta) { System.out.println("[OK] " + nom); ok++; }
        else { System.out.println("[KO] " + nom + " | attendu=" + attendu + " obtenu=" + obtenu); ko++; }
    }

    static GrapheListe buildGraphe() {
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

    // --- Tests GrapheListe ---
    static void testGraphe() {
        System.out.println("\n== Tests GrapheListe ==");
        GrapheListe g = buildGraphe();
        List<String> noeuds = g.getNoeuds();
        assertTrue("contient A", noeuds.contains("A"));
        assertTrue("contient B", noeuds.contains("B"));
        assertTrue("contient C", noeuds.contains("C"));
        assertTrue("contient D", noeuds.contains("D"));
        assertTrue("contient E", noeuds.contains("E"));

        List<Arc> arcsA = g.getAdjacents("A").getArcs();
        assertEquals("A a 2 arcs", 2, arcsA.size());
        assertEquals("A->B cible", "B", arcsA.get(0).getCible());
        assertEquals("A->B poids", 12.0, arcsA.get(0).getPoids(), 0.001);
        assertEquals("A->D cible", "D", arcsA.get(1).getCible());
        assertEquals("A->D poids", 87.0, arcsA.get(1).getPoids(), 0.001);

        assertEquals("C sans arcs sortants", 0, g.getAdjacents("C").getArcs().size());
        assertEquals("Z inexistant", 0, g.getAdjacents("Z").getArcs().size());
    }

    // --- Tests Bellman-Ford ---
    static void testBellmanFord() {
        System.out.println("\n== Tests BellmanFord ==");
        BellmanFord bf = new BellmanFord();
        Valeurs v = bf.resoudre(buildGraphe(), "A");

        assertEquals("BF dist A",  0.0,  v.getValeur("A"), 0.001);
        assertEquals("BF dist B", 12.0,  v.getValeur("B"), 0.001);
        assertEquals("BF dist E", 23.0,  v.getValeur("E"), 0.001);
        assertEquals("BF dist D", 66.0,  v.getValeur("D"), 0.001);
        assertEquals("BF dist C", 76.0,  v.getValeur("C"), 0.001);

        assertEquals("BF parent B", "A", v.getParent("B"));
        assertEquals("BF parent E", "B", v.getParent("E"));
        assertEquals("BF parent D", "E", v.getParent("D"));
        assertEquals("BF parent C", "D", v.getParent("C"));
    }

    // --- Tests Dijkstra ---
    static void testDijkstra() {
        System.out.println("\n== Tests Dijkstra ==");
        Dijkstra dj = new Dijkstra();
        Valeurs v = dj.resoudre(buildGraphe(), "A");

        assertEquals("DJ dist A",  0.0,  v.getValeur("A"), 0.001);
        assertEquals("DJ dist B", 12.0,  v.getValeur("B"), 0.001);
        assertEquals("DJ dist E", 23.0,  v.getValeur("E"), 0.001);
        assertEquals("DJ dist D", 66.0,  v.getValeur("D"), 0.001);
        assertEquals("DJ dist C", 76.0,  v.getValeur("C"), 0.001);

        assertEquals("DJ parent B", "A", v.getParent("B"));
        assertEquals("DJ parent E", "B", v.getParent("E"));
        assertEquals("DJ parent D", "E", v.getParent("D"));
        assertEquals("DJ parent C", "D", v.getParent("C"));
    }

    // --- Tests reconstruction chemin ---
    static void testChemin() {
        System.out.println("\n== Tests Chemin ==");
        BellmanFord bf = new BellmanFord();
        Valeurs v = bf.resoudre(buildGraphe(), "A");
        List<String> chemin = Principale.reconstruireChemin(v, "A", "C");
        assertEquals("chemin A->C taille", 5, chemin.size());
        assertEquals("chemin[0]", "A", chemin.get(0));
        assertEquals("chemin[1]", "B", chemin.get(1));
        assertEquals("chemin[2]", "E", chemin.get(2));
        assertEquals("chemin[3]", "D", chemin.get(3));
        assertEquals("chemin[4]", "C", chemin.get(4));
    }

    public static void main(String[] args) {
        testGraphe();
        testBellmanFord();
        testDijkstra();
        testChemin();
        System.out.println("\n=== Résultat : " + ok + " OK, " + ko + " KO ===");
    }
}
