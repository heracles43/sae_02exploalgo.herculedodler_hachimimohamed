import java.util.*;

public class Dijkstra {

    public Valeurs resoudre(Graphe g, String depart) {
        Valeurs valeurs = new Valeurs();

        // Initialisation
        for (String noeud : g.getNoeuds()) {
            valeurs.setValeur(noeud, Double.MAX_VALUE);
            valeurs.setParent(noeud, null);
        }
        valeurs.setValeur(depart, 0.0);

        // File de priorité : (distance, noeud)
        PriorityQueue<String> file = new PriorityQueue<>(
            Comparator.comparingDouble(n -> valeurs.getValeur(n))
        );
        file.add(depart);

        Set<String> visite = new HashSet<>();

        while (!file.isEmpty()) {
            String noeud = file.poll();
            if (visite.contains(noeud)) continue;
            visite.add(noeud);

            double valNoeud = valeurs.getValeur(noeud);
            for (Arc arc : g.getAdjacents(noeud).getArcs()) {
                String cible = arc.getCible();
                if (visite.contains(cible)) continue;
                double nouvelleValeur = valNoeud + arc.getPoids();
                if (nouvelleValeur < valeurs.getValeur(cible)) {
                    valeurs.setValeur(cible, nouvelleValeur);
                    valeurs.setParent(cible, noeud);
                    file.add(cible);
                }
            }
        }
        return valeurs;
    }
}
