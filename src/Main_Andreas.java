import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class Main_Andreas {
    public static void main(String[] args) {

        //Oppgave1
        /*
        Liste<String> liste = new DobbeltLenketListe<>();
        System.out.println(liste.antall() + " " + liste.tom());
        // Utskrift: 0 true
        */
/*
        String[] s = {"Ole", null, "Per", "Kari", null};
        Liste<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste.antall() + " " + liste.tom());


        String[] t = {"Ole",null,"Jonas"};
        Liste<String> liste2 = new DobbeltLenketListe<>(t);
        System.out.println(liste2.antall() + " " + liste2.tom());
*/
/*
        //Oppg2a
        String[] s1 = {}, s2 = {"A"}, s3 = {null,"A",null,"B",null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);
        System.out.println(l1.toString() + " " + l2.toString()
                + " " + l3.toString() + " " + l1.omvendtString() + " "
                + l2.omvendtString() + " " + l3.omvendtString());
        // Utskrift: [] [A] [A, B] [] [A] [B, A]

*/
        //Oppg2b
/*
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        System.out.println(liste.toString() + " " + liste.omvendtString());
        for (int i = 1; i <= 3; i++)
        {
            liste.leggInn(i);
            System.out.println(liste.toString() + " " + liste.omvendtString());
        }
*/
        // Utskrift:
        // [] []
        // [1] [1]
        // [1, 2] [2, 1]
        // [1, 2, 3] [3, 2, 1]
/*
        Character[] c = {'A','B','C','D','E','F','G','H','I','J',};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
        //System.out.println(liste.hent(0));
        System.out.println(liste.subliste(3,8)); // [D, E, F, G, H]
        System.out.println(liste.subliste(5,5)); // []
        System.out.println(liste.subliste(8,liste.antall())); // [I, J]
        System.out.println(liste.subliste(0,11)); // skal kaste unntak
*/
        //Oppgave 4
      /*
        Character[] c = {'A','B','C','D','E','F','G','H','I','J',};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
        System.out.println(liste.indeksTil('J'));
*/

        //Oppgave 5
/*
        Character[] c = {'A','B','C','D','E','F','G','H','I','J',};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
        System.out.println(liste.antall());
        System.out.println(liste.toString());
        liste.leggInn(1,'X');
        //liste.leggInn(1, 3);  // ny verdi nest forrest
        System.out.println(liste.toString());
        System.out.println(liste.antall());
*/

//        System.out.println();
  //      DobbeltLenketListe<Character> charListe = new DobbeltLenketListe<>();

        //bakerst test
        /*
        charListe.leggInn(0, 'A');  // ny verdi i tom liste [C]
        charListe.leggInn(1, 'B');  // ny verdi i tom liste [B, C]
        charListe.leggInn(2, 'C');  // ny verdi i tom liste [B, C]
        charListe.leggInn(3, 'D');  // ny verdi i tom liste [B, C]
*/
        //Legg inn først teest
 //       charListe.leggInn(0, 'D');  // ny verdi i tom liste [C]
   //     charListe.leggInn(0, 'C');  // ny verdi i tom liste [B, C]
   //     charListe.leggInn(0, 'B');  // ny verdi i tom liste [B, C]
   //     charListe.leggInn(0, 'A');  // ny verdi i tom liste [B, C]



        //charListe.leggInn(0, 'A');  // ny verdi i tom liste [A, B, C]

//        charListe.leggInn(0, 'C');  // ny verdi i tom liste [C]
//        charListe.leggInn(0, 'A');  // ny verdi legges forrest [A, C]
//        charListe.leggInn(2, 'D');  // ny verdi legges bakerst [A, C, D]
//        charListe.leggInn(1, 'B');  // ny verdi nest forrest [A, B, C, D]
        //[A, B, C, D]


      //  System.out.println(charListe.toString());
/*
        System.out.println();
        DobbeltLenketListe<Integer> intListe = new DobbeltLenketListe<>();
        intListe.leggInn(0, 4);  // ny verdi i tom liste
        intListe.leggInn(0, 2);  // ny verdi legges forrest
        intListe.leggInn(2, 6);  // ny verdi legges bakerst
        intListe.leggInn(1, 3);  // ny verdi nest forrest
*/

/*
        Character[] c = {'A','B','C','D'};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
        System.out.println(liste.toString());
        System.out.println("Fjernet verdi = " + liste.fjern(2));
        System.out.println(liste.toString());
*/

/*
        Character[] c = {'A','B','C','D'};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
        System.out.println(liste.toString());
        System.out.println("Fjernet verdi = " + liste.fjern(c[2]));
        System.out.println(liste.toString());
        System.out.println(liste.omvendtString());


        Liste<Integer> nyliste = new DobbeltLenketListe<>();
        for (int i = 1; i <= 100_000; i++) nyliste.leggInn(i);
        long tid1 = System.currentTimeMillis();
        for (int i = 40000; i <= 50000; i++) nyliste.fjern(new Integer(i));
        tid1 = System.currentTimeMillis() - tid1;
        System.out.println("Tiden et tar å fjerne ved fjern(T verdi) = " + tid1);

        nyliste = new DobbeltLenketListe<>();
        for (int i = 1; i <= 100_000; i++) nyliste.leggInn(i);
        long tid2 = System.currentTimeMillis();
        for (int i = 40000; i <= 50000; i++) nyliste.fjern(i);
        tid2 = System.currentTimeMillis() - tid2;
        System.out.println("Tiden et tar å fjerne ved fjern(indeks) = " + tid2);

        long maks = Math.max(tid1, tid2);
        System.out.println("Maks av tid1, og tid2 = " + maks);
        long min = Math.min(tid1, tid2);

        System.out.println("min*1.5="+ min*1.5);
        if (maks > 1.5 * min) System.out.println("Maks > 1.5*min... ikke lov");

        System.out.println(min);
        System.out.println(maks);
*/

        //Oppgave8
        String[] navn = {"Lars","Anders","Bodil","Kari","Per","Berit"};
        Liste<String> liste = new DobbeltLenketListe<>(navn);

        //iterator test
        Iterator<String> i =liste.iterator();
        while (i.hasNext()){
            String verdi = i.next();
            System.out.print(verdi + " ");
        }

        System.out.println();

        Iterator<String> j = new DobbeltLenketListe<String>(navn).iterator(1);
        while (j.hasNext()){
            String verdi = j.next();
            System.out.print(verdi + " ");
        }


        System.out.println();

        //forEach testen
        liste.forEach(s -> System.out.print(s + " "));
        System.out.println();
        for (String s : liste) System.out.print(s + " ");

        /*
        i = liste.iterator();
        liste.fjern(0); // bruker fjern(indeks) etter at iteratoren er opprettet

        i = liste.iterator();
        liste.leggInn(8);  // bruker leggInn(T) etter at iteratoren er opprettet

        try {
            i.next();

            System.out.println
                    ("Oppgave 8i: ForventetAntallEndringer ikke endret i leggInn(T)!");
        } catch (Exception e) {
            if (!(e instanceof ConcurrentModificationException)) {
                System.out.println("Oppgave 8j: Det kastes feil type unntak!");

            }
        }*/


// Utskrift:
// Lars Anders Bodil Kari Per Berit
// Lars Anders Bodil Kari Per Berit
    }
}
