import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Principale {

    // Reconstruit le chemin depuis les parents
    public static List<String> reconstruireChemin(Valeurs valeurs, String depart, String arrivee) {
        List<String> chemin = new ArrayList<>();
        String courant = arrivee;
        while (courant != null && !courant.equals(depart)) {
            chemin.add(0, courant);
            courant = valeurs.getParent(courant);
        }
        if (courant != null) chemin.add(0, depart);
        return chemin;
    }

    // Charge le graphe STAN depuis les fichiers nodes/edges
    public static GrapheListe chargerGrapheSTAN(String nodesFile, String edgesFile) throws IOException {
        GrapheListe g = new GrapheListe();
        // Charger les noeuds
        for (String line : Files.readAllLines(Paths.get(nodesFile))) {
            String[] parts = line.split(";");
            if (parts.length >= 2) {
                g.ajouterNoeud(parts[0].trim());
            }
        }
        // Charger les arcs
        for (String line : Files.readAllLines(Paths.get(edgesFile))) {
            String[] parts = line.split(";");
            if (parts.length >= 3) {
                String src  = parts[0].trim();
                String dest = parts[1].trim();
                double poids = Double.parseDouble(parts[2].trim());
                g.ajouterArc(src, dest, poids);
            }
        }
        return g;
    }

    public static void main(String[] args) throws IOException {

        // ---- 1) Graphe exemple Figure 1 ----
        System.out.println("=== Graphe exemple ===");
        GrapheListe gEx = new GrapheListe();
        gEx.ajouterArc("A", "B", 12.0);
        gEx.ajouterArc("A", "D", 87.0);
        gEx.ajouterArc("B", "E", 11.0);
        gEx.ajouterArc("C", "A", 19.0);
        gEx.ajouterArc("D", "B", 23.0);
        gEx.ajouterArc("D", "C", 10.0);
        gEx.ajouterArc("E", "D", 43.0);
        System.out.println(gEx);

        BellmanFord bf = new BellmanFord();
        Valeurs vBF = bf.resoudre(gEx, "A");
        System.out.println("Bellman-Ford depuis A:");
        System.out.println(vBF);
        List<String> cheminEx = reconstruireChemin(vBF, "A", "C");
        System.out.println("Chemin A->C : " + cheminEx);

        // ---- 2) Graphe STAN ----
        // Déterminer le chemin des fichiers
        String nodesFile, edgesFile;
        if (args.length >= 2) {
            nodesFile = args[0];
            edgesFile = args[1];
        } else {
            // Chercher dans le répertoire courant ou parent
            nodesFile = findFile("stan_nodes.txt");
            edgesFile = findFile("stan_edges.txt");
        }

        if (nodesFile == null || edgesFile == null) {
            System.out.println("Fichiers STAN introuvables. Passez les chemins en arguments:");
            System.out.println("  java Principale stan_nodes.txt stan_edges.txt [depart] [arrivee]");
            return;
        }

        System.out.println("\n=== Graphe STAN ===");
        GrapheListe gSTAN = chargerGrapheSTAN(nodesFile, edgesFile);
        System.out.println("Noeuds: " + gSTAN.getNoeuds().size());

        // Départ et arrivée depuis args ou valeurs par défaut
        String depart  = args.length >= 3 ? args[2] : "HLRTT0";
        String arrivee = args.length >= 4 ? args[3] : "NYCOM0";

        // ---- Bellman-Ford sur STAN ----
        long t1 = System.currentTimeMillis();
        Valeurs vBF_STAN = new BellmanFord().resoudre(gSTAN, depart);
        long t2 = System.currentTimeMillis();
        long tempsBF = t2 - t1;

        // ---- Dijkstra sur STAN ----
        long t3 = System.currentTimeMillis();
        Valeurs vDJ_STAN = new Dijkstra().resoudre(gSTAN, depart);
        long t4 = System.currentTimeMillis();
        long tempsDJ = t4 - t3;

        // ---- Affichage résultats ----
        System.out.println("\nDe : " + depart + "  ->  " + arrivee);
        System.out.println("--- Bellman-Ford ---");
        System.out.println("Temps : " + tempsBF + " ms");
        List<String> cheminBF = reconstruireChemin(vBF_STAN, depart, arrivee);
        System.out.println("Distance : " + vBF_STAN.getValeur(arrivee) + " m");
        System.out.println("Chemin   : " + String.join(";", cheminBF));

        System.out.println("--- Dijkstra ---");
        System.out.println("Temps : " + tempsDJ + " ms");
        List<String> cheminDJ = reconstruireChemin(vDJ_STAN, depart, arrivee);
        System.out.println("Distance : " + vDJ_STAN.getValeur(arrivee) + " m");
        System.out.println("Chemin   : " + String.join(";", cheminDJ));
    }

    // Cherche un fichier dans le répertoire courant et parent
    private static String findFile(String name) {
        String[] candidates = { name, "../" + name, "../../" + name };
        for (String c : candidates) {
            if (new File(c).exists()) return c;
        }
        return null;
    }
}
