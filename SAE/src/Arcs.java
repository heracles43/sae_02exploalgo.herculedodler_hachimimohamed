import java.util.ArrayList;
import java.util.List;

public class Arcs {
    private List<Arc> arcs;

    public Arcs() {
        this.arcs = new ArrayList<>();
    }

    public void ajouterArc(Arc arc) {
        arcs.add(arc);
    }

    public List<Arc> getArcs() {
        return arcs;
    }
	public String toString() {
    StringBuilder sb = new StringBuilder("[");
    for (Arc a : arcs) {
        sb.append(a.toString()).append(" ");
    }
    sb.append("]");
    return sb.toString();
}
}