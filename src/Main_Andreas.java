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

        System.out.println();
        DobbeltLenketListe<Character> charListe = new DobbeltLenketListe<>();
        charListe.leggInn(0, 'C');  // ny verdi i tom liste
        charListe.leggInn(0, 'A');  // ny verdi legges forrest
        charListe.leggInn(2, 'D');  // ny verdi legges bakerst
        charListe.leggInn(1, 'B');  // ny verdi nest forrest [
        //[A, B, C, D]


        System.out.println(charListe.toString());
/*
        System.out.println();
        DobbeltLenketListe<Integer> intListe = new DobbeltLenketListe<>();
        intListe.leggInn(0, 4);  // ny verdi i tom liste
        intListe.leggInn(0, 2);  // ny verdi legges forrest
        intListe.leggInn(2, 6);  // ny verdi legges bakerst
        intListe.leggInn(1, 3);  // ny verdi nest forrest
*/
    }
}
