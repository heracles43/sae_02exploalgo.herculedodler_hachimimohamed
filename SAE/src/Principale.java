public class Principale {
    public static void main(String[] args){
        GrapheListe g = new GrapheListe();
        g.ajouteArc("A", "B", 12.0);
        g.ajouteArc("A", "D", 87.0);
        g.ajouteArc("B", "E", 11.0);
        g.ajouteArc("C", "A", 19.0);
        g.ajouteArc("D", "B", 23.0);
        g.ajouteArc("D", "B", 10.0);
        g.ajouteArc("E", "D", 43.0);
        System.out.println(g);
    }
}
