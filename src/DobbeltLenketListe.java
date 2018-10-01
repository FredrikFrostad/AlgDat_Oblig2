import java.util.*;

public class DobbeltLenketListe<T> implements Liste<T>
{
    private static final class Node<T>   // en indre nodeklasse
    {
        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks)
    {
        Node<T> p;
        if(indeks < antall/2){
            p = hode;
            for (int i = 0; i < indeks; i++) {
                p = p.neste;
            }
        }else{
            p=hale;
            for (int i = 0; i < antall-indeks-1 ; i++) {
                p = p.forrige;
            }
        }
        return p;
    }

    // konstruktør
    public DobbeltLenketListe()
    {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a)
    {
        Objects.requireNonNull(a, "Tabellen a er null!");

        int i = 0;
        // i blir like stor som lengden-1, eller lik som antall elementer som ikke er null
        for (; i < a.length && a[i] == null; i++);

        if(i < a.length){ //grensesjekk
            hode = new Node<>(a[i],null, null); //Lager hode uten forrige og neste
            Node<T> p = hode;
            antall = 1; //En node er laget, og hode refererer til denne ved opprettelsen av listen.

            for (i++; i< a.length; i++) { //
                if(a[i] != null){
                    p.neste = new Node<>(a[i], null , null); //lager ny node for neste.
                    p.neste.forrige = p; //neste sin forrige er nåværende node
                    p = p.neste; //Flytter peker til ny node.
                    antall++; //Oppdaterer antall noder
                }
            }
            hale = p; //Halen flyttes etter den noden som sist er opprettet/laget.
        }
    }

    // subliste
    public Liste<T> subliste(int fra, int til)
    {
        fratilKontroll(this.antall,fra,til); //Fra til kontroll
        DobbeltLenketListe<T> subListe = new DobbeltLenketListe<>(); //Tom liste

        //et tomt intervall er lovlig, men skal returnere en tom liste
        if(til-fra == 0) return subListe;

        for (int i = 0; i < antall ; i++) { //løper igjennom alle noder for "denne" listen
            if(i>=fra && i<til) { //Gitt intervall
                //Plukker noder i "denne" listen fra gitt intervall, og legger disse over i ny subListe.
                subListe.leggInn(finnNode(i).verdi);
            }
        }
        return subListe;
    }

    @Override
    public int antall()
    {
        return this.antall;
    }

    @Override
    public boolean tom()
    {
        return antall == 0 ? true : false;
    }

    @Override
    public boolean leggInn(T verdi) //Legger til ny verdi bakerst i lista, og hale flyttes etter.
    {
        Objects.requireNonNull(verdi, "Null-verdier er ikke tillatt!");
        if (antall == 0)
            hode = hale = new Node<>(verdi, null,null);  // Listen er tom
        else {
            // Oppdaterer hale sin neste med en ny node. nåværende hale bli den nye noden sin forrige. Hale flyttes bak.
            hale = hale.neste = new Node<>(verdi, hale, null);
        }
        antall++;        //En node til er laget
        return true;     //
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi, "Null-verdier er ikke tillatt!");
        indeksKontroll(indeks,true);

        if(indeks == 0){ //Ny node skal legges inn der hode er nå. Hode må endres/oppdateres.
            Node<T> nyNode = new Node<T>(verdi,null,hode);

            if (hode != null){//fikser indeks sjekk for: 0 <= indeks <= antall som er det tillatte!
                hode.forrige = nyNode;
            }

            hode = nyNode;
            if(antall == 0) {
                hale = hode;
            }

        }else if (indeks == antall){ //Ny node skal legges inn der hale er nå. Hale må endres/oppdateres.
                hale = hale.neste = new Node<>(verdi, hale, null);

        }else{ //Ny node skal legges inn mellom nåværende hode og hale. Må oppdater node før og etter.
            Node<T> nyNode = new Node<T>(verdi,null,null);
            Node<T> p = hode;                  // p flyttes indeks - 1 ganger
            for (int i = 1; i < indeks; i++) p = p.neste;
            nyNode.forrige = p;
            nyNode.neste = p.neste;
            p.neste.forrige = nyNode;
            p.neste = nyNode;
        }

        antall++; //Har lagt til en ny node, og antall oppdateres
        endringer++; //Har gjort en endring på lista
    }

    @Override
    public boolean inneholder(T verdi)
    {
       return indeksTil(verdi) != -1 ? true : false; //Sjekker om det finnes en indeks som har verdien verdi.
    }

    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks,false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi)
    {
        if(verdi == null) return -1;

        Node<T> p = hode;
        for (int i = 0; i < antall; i++) {
            if(p.verdi.equals(verdi)) return i; //har må equals brukes fordi det jobbes med objekter!
            p = p.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        Objects.requireNonNull(nyverdi, "Null-verdier er ikke tillatt!");
        indeksKontroll(indeks,false);
        T forrigeVerdi = finnNode(indeks).verdi;
        if(nyverdi != null) finnNode(indeks).verdi = nyverdi;
        endringer ++;
        return forrigeVerdi;

    }

    @Override
    public boolean fjern(T verdi)
    {
       /*
       //gammel kode
        if(verdi == null) return false; //Må brukr verdi == null her. Kan ikke bruke verdi.equals(null) om verdi er null

        Node<T> q = hode, p = null;

        while(q != null){
            if(q.verdi.equals(verdi)) break;
            p = q;
            q = q.neste;
        }

        if(q==null) return false;
        else if (q==hode) hode = hode.neste;
        else p.neste = q.neste;

        if(q==hale) hale = p;

        q.verdi = null;
        q.neste = null;

        antall--;

        return true;
*/
        //Fredrik sin kode
 /*
        //Sjekker om listen er tom, returnerer i så fall false
        if (antall == 0) return false;

        Node<T> node = hode;
        int i = 0;

        while(node.neste != null) {

            if (node.verdi.equals(verdi) && i == 0) { //Sletter første node
                hode = hode.neste;
                hode.forrige = null;
                antall--;
                endringer++;
                return true;
            }else if(node.verdi.equals(verdi)) {    //Sletter noder som ligger i mellom hode og hale
                node.forrige.neste = node.neste;
                node.neste.forrige = node.forrige;
                node = null;
                antall--;
                endringer++;
                return true;
            }

            node = node.neste;
            i++;
        }
        if(hode.verdi.equals(verdi) && antall == 1) {
            hode = hale =null;
            antall--;
            endringer++;
            return true;
        }
        else if(node.verdi.equals(verdi) && i == antall - 1) {   //Sletter siste node
            hale = hale.forrige;
            hale.neste = null;
            antall--;
            endringer++;
            return true;
        }
        return false;


*/
        //min kode

        if(verdi == null) return false; //Må brukr verdi == null her. Kan ikke bruke verdi.equals(null) om verdi er null

        Node<T> p = hode;

        int i = 1;

        if(hode.verdi.equals(verdi)) {
            fjernHjelp(hode);
            return true;
        }

        else if(hale.verdi.equals(verdi)) {
            fjernHjelp(hale);
            return true;
        }

        else {
            for (; i < antall; i++) {
                p = p.neste;
                if (p.verdi.equals(verdi)) {
                    fjernHjelp(p);
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public T fjern(int indeks)
    {

        //Frrdrik sin kode
        /*
        indeksKontroll(indeks, false);

        Node<T> node = hode;
        T ret;

        if (antall == 1) {      //sletter første node når antall = 1
            ret = node.verdi;
            hode = hode.neste;
            node = null;
            antall--;
            endringer++;
            return ret;
        }
        if (indeks == 0 && antall > 1) {      //sletter første node når antall > 1
            ret = node.verdi;
            hode = hode.neste;
            hode.forrige = null;
            node = null;
            antall--;
            endringer++;
            return ret;
        } else if(indeks == antall-1) {     //sletter siste node
            ret = node.verdi;
            hale = hale.forrige;
            hale.neste = null;
            antall--;
            endringer++;
            return ret;
        }else {     //sletter node mellom hode og hale
            int i = 0;

            while(i < indeks) {
                node = node.neste;
                i++;
            }
            ret = node.verdi;
            node.forrige.neste = node.neste;
            node.neste.forrige = node.forrige;
            node = null;
            antall--;
            endringer++;
        }
        return ret;
        */

        indeksKontroll(indeks,false);
        if(indeks == 0){
            antall--;
            T temp = hode.verdi;
            hode = hode.neste; //Neste etter hode blir node 1. ikke node 0.
            //Hvis det kun er en node igjen etter at hode sin nest har blitt hode -> neste sin forrige skal ikke oppdateres
            if(antall > 1) hode.neste.forrige = hode;
            if(antall>0) hode.forrige = null;
            endringer++;
            return temp;
        }
        else if(indeks == antall-1){ //siste indeks
            antall--;
            T temp = hale.verdi;
            hale = hale.forrige; //Neste etter hode blir node 1. ikke node 0.
            if(antall>1) hale.forrige.neste = hale;
            hale.neste = null;
            endringer++;
            return temp;
        }
        else{
           // Node<T> p = finnNode(indeks);

            Node<T> p = hode;
            for (int i = 0; i < indeks ; i++) p = p.neste;


            //int j=0;
            //while (j < indeks) {
            //    p = p.neste;
            //    j++;
            //}

            T temp = p.verdi;
            p.neste.forrige = p.forrige; //Oppdaterer neste sin forrige peker
            p.forrige.neste = p.neste;//Oppdaterer forrige sin neste peker.
            antall--;
            endringer++;
            return temp;
        }

    }

    private void fjernHjelp(Node<T> denne){

        if(denne.forrige == null) { //Betraktes som at du står på hode. indeks == 0
            antall--;
            hode = hode.neste; //Neste etter hode blir node 1. ikke node 0.
            //Hvis det kun er en node igjen etter at hode sin nest har blitt hode -> neste sin forrige skal ikke oppdateres
            if(antall > 1) hode.neste.forrige = hode;
            if(antall>0) hode.forrige = null;
            endringer++;
        }
        else if(denne.neste==null){ //Står på halen. Indeks == antall-1
            antall--;
            hale = hale.forrige; //Neste etter hode blir node 1. ikke node 0.
            if(antall>1) hale.forrige.neste = hale;
            hale.neste = null;
            endringer++;
        }
        else{ //
            antall--;
            denne.neste.forrige = denne.forrige; //Oppdaterer neste sin forrige peker
            denne.forrige.neste = denne.neste;//Oppdaterer forrige sin neste peker.
            endringer++;
        }
    }

    @Override
    public void nullstill()
    {
        //throw new UnsupportedOperationException("Ikke laget ennå!");
        //Fasit men må gjøres anderledes.

        Node<T> p = hode, q = null;
        while (p != null){ //Starter på hode, og løper igjennom listen, og setter neste, og verdi til null underveis for hver enkelt node.
            q = p.neste;
            p.neste = null;
            p.verdi = null;
            p = q;
        }

        //Setter hode, og hale til null, og antall til 0
        hode = hale = null;
        antall = 0;
    }

    @Override
    public String toString()
    {
        if(antall == 0 || hode == null) return "[]"; //returnerer [] hvis listen er tom

        StringBuilder s = new StringBuilder();
        Node<T> p = hode; //Første node = hode
        s.append("[");
        s.append(p.verdi);
        p=p.neste;

        while(p != null){
            s.append(", ");
            s.append(p.verdi); //legger til verdier for nåværende node inn i s.
            p=p.neste;
        }
        s.append("]");
        return s.toString();
    }

    public String omvendtString()
    {
        if(antall == 0 || hode == null) return "[]"; //returnerer [] hvis listen er tom

        StringJoiner s = new StringJoiner(", ", "[", "]");
        Node<T> p = hale; //Første node = hode
        while(p != null){
            s.add(String.valueOf(p.verdi)); //legger til verdier for nåværende node inn i s.
            p=p.forrige;
        }
        return s.toString();
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public Iterator<T> iterator()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public Iterator<T> iterator(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator()
        {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks)
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next()
        {

            throw new UnsupportedOperationException("Ikke laget ennå!");
           // if(iteratorendringer == endringer){

            //}

        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator

    //Lagg inn fra oppg3
    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

} // DobbeltLenketListe  bbeltLenketListe