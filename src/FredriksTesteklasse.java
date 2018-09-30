import org.w3c.dom.Node;

public class FredriksTesteklasse {

    public static void main(String[] args) {
        //String[] s = {"Ole", null, "Per", "Kari", null};
        //Liste<String> liste = new DobbeltLenketListe<>(s);
        //System.out.println(liste.antall() + " " + liste.tom());
        //// Utskrift: 3 false
//
        //liste.leggInn("Kjell");
        //System.out.println(liste.toString());
        //System.out.println(((DobbeltLenketListe<String>) liste).omvendtString());
//
        //Character[] c = {'A','B','C','D','E','F','G','H','I','J',};
        //DobbeltLenketListe<Character> liste2 = new DobbeltLenketListe<>(c);

         DobbeltLenketListe<String> liste = new DobbeltLenketListe<>(new String[]{"A", "B", "C", "D", "E", "F", "G"});
        //liste.leggInn(2);
        //liste.leggInn(3);

        System.out.println(liste);
        System.out.println(liste.omvendtString());

        liste.fjern(0);
        //liste.fjern(5);

        System.out.println(liste);
        System.out.println(liste.omvendtString());
    }
}
