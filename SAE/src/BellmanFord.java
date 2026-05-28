public class BellmanFord {

    public Valeurs resoudre(Graphe g, String depart) {
        Valeurs valeurs = new Valeurs();

        // Initialisation : tous les noeuds à +infini
        for (String noeud : g.getNoeuds()) {
            valeurs.setValeur(noeud, Double.MAX_VALUE);
            valeurs.setParent(noeud, null);
        }
        valeurs.setValeur(depart, 0.0);

        boolean modifie = true;
        while (modifie) {
            modifie = false;
            for (String noeud : g.getNoeuds()) {
                double valNoeud = valeurs.getValeur(noeud);
                if (valNoeud == Double.MAX_VALUE) continue;
                for (Arc arc : g.getAdjacents(noeud).getArcs()) {
                    String cible = arc.getCible();
                    double nouvelleValeur = valNoeud + arc.getPoids();
                    if (nouvelleValeur < valeurs.getValeur(cible)) {
                        valeurs.setValeur(cible, nouvelleValeur);
                        valeurs.setParent(cible, noeud);
                        modifie = true;
                    }
                }
            }
        }
        return valeurs;
    }
}