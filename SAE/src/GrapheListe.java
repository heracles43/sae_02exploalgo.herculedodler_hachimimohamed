import java.util.*;

public class GrapheListe implements Graphe {
    private Map<String, Arcs> graphe;

    public GrapheListe() {
        this.graphe = new LinkedHashMap<>();
    }

    public void ajouterNoeud(String id) {
        graphe.putIfAbsent(id, new Arcs());
    }

    @Override
    public List<String> getNoeuds() {
        return new ArrayList<>(graphe.keySet());
    }

    @Override
    public Arcs getAdjacents(String noeud) {
        return graphe.getOrDefault(noeud, new Arcs());
    }

    public void ajouterArc(String source, String destination, double poids) {
        ajouterNoeud(source);
        ajouterNoeud(destination);
        graphe.get(source).ajouterArc(new Arc(destination, poids));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Arcs> entry : graphe.entrySet()) {
            sb.append(entry.getKey()).append(" -> ")
              .append(entry.getValue().toString()).append("\n");
        }
        return sb.toString();
    }
}
