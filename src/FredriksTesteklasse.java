import org.w3c.dom.Node;

public class FredriksTesteklasse {

    public static void main(String[] args) {
        String[] s = {"Ole", null, "Per", "Kari", null};
        Liste<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste.antall() + " " + liste.tom());
        // Utskrift: 3 false

        liste.leggInn("Kjell");
        System.out.println(liste.toString());
        System.out.println(((DobbeltLenketListe<String>) liste).omvendtString());

        Character[] c = {'A','B','C','D','E','F','G','H','I','J',};
        DobbeltLenketListe<Character> liste2 = new DobbeltLenketListe<>(c);

        //System.out.println(liste2.hent(0));
        //System.out.println(liste2.hent(1));
        //System.out.println(liste2.hent(2));
        //System.out.println(liste2.hent(3));
        //System.out.println(liste2.hent(4));
        //System.out.println(liste2.hent(5));
        //System.out.println(liste2.hent(6));
        //System.out.println(liste2.hent(7));
        //System.out.println(liste2.hent(8));
        //System.out.println(liste2.hent(9));

        //System.out.println(liste2.subliste(2,7) );
        //System.out.println(liste2.subliste(3,8)); // [D, E, F, G, H]
        //System.out.println(liste2.subliste(5,5)); // []
        //System.out.println(liste2.subliste(8,liste2.antall())); // [I, J]
        //System.out.println(liste2.subliste(0,11)); // skal kaste unntak

    }
}
