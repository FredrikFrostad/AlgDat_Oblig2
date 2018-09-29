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

        DobbeltLenketListe<Integer> liste3 = new DobbeltLenketListe<>();
        liste3.leggInn(1);
        liste3.leggInn(2);
        liste3.leggInn(3);

        System.out.println(liste3);
        System.out.println(liste3.omvendtString());

        //System.out.println(liste3.fjern(0));
        liste3.fjern((Integer)3);
        liste3.fjern((Integer)1);
        //liste3.fjern((Integer)2);


        System.out.println(liste3);
        System.out.println(liste3.omvendtString());
    }
}
