public class Main_Andreas {
    public static void main(String[] args) {

        //Oppgave1
        /*
        Liste<String> liste = new DobbeltLenketListe<>();
        System.out.println(liste.antall() + " " + liste.tom());
        // Utskrift: 0 true
        */

        String[] s = {"Ole", null, "Per", "Kari", null};
        Liste<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste.antall() + " " + liste.tom());


        String[] t = {"Ole",null,"Jonas"};
        Liste<String> liste2 = new DobbeltLenketListe<>(t);
        System.out.println(liste2.antall() + " " + liste2.tom());


    }
}
