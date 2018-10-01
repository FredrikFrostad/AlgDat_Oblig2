import org.w3c.dom.Node;

import java.util.Iterator;

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
        //DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);

        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        for (int i = 0; i <=13;i++) liste.leggInn((Integer)i);
        System.out.println(liste);
        System.out.println(liste.omvendtString());

        for (Iterator<Integer> iter = liste.iterator(); iter.hasNext();) {
            int verdi = iter.next();
            if (verdi % 2 == 1) {
                iter.remove();
            }
        }

        System.out.println(liste);
        System.out.println(liste.omvendtString());
    }
}

//Kode for testing av effektivitet oppgave 7
/*
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


 */
