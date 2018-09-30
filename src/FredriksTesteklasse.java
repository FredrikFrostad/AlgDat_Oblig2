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

         DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
         for (int i = 0; i < 10000000;i++) {
             liste.leggInn(i);
         }

         long start = System.currentTimeMillis();
         liste.nullstill();
         long slutt =System.currentTimeMillis();
        System.out.println(slutt - start);

        for (int i = 0; i < 10000000;i++) {
            liste.leggInn(i);
        }

        start = System.currentTimeMillis();
        while(liste.antall() > 0) {
            liste.fjern(0);
        }
        slutt = System.currentTimeMillis();
        System.out.println(slutt-start);

        //System.out.println(liste);
        //System.out.println(liste.omvendtString());
//
//
        //System.out.println(liste);
        //System.out.println(liste.omvendtString());
    }
}
