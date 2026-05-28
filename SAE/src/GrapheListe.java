import java.util.*;

public class GrapheListe implements Graphe {
    private Map<String, Arcs> graphe;

    public GrapheListe() {
        this.graphe = new LinkedHashMap<>();
    }

    public void ajouterNoeud(String id) {
        graphe.putIfAbsent(id, new Arcs());
    }

    public List<String> getNoeuds() {
        return new ArrayList<>(graphe.keySet());
    }

    public Arcs getAdjacents(String noeud) {
        return graphe.getOrDefault(noeud, new Arcs());
    }

    public void ajouterArc(String source, String destination, double poids) {
        ajouterNoeud(source);
        ajouterNoeud(destination);
        graphe.get(source).ajouterArc(new Arc(destination, poids));
    }
	public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, Arcs> entry : graphe.entrySet()) {
        sb.append(entry.getKey()).append(" -> ");
        for (Arc arc : entry.getValue().getArcs()) {
            sb.append(arc.getCible())
              .append("(").append((int)arc.getPoids()).append(") ");
        }
        sb.append("\n");
    }
    return sb.toString();
}
    
}
