import java.util.*;

@SuppressWarnings("Unchecked")
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
        endringer++;
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

            endringer++; //Hver element/node som nullstilles er en endring
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
        for (int i = 1; i < liste.antall(); i++){  // starter med i = 1

            int  j = i - 1; //
            T verdiRefNode = liste.hent(i); //Henter inn referanse node som det skal sammenlignes mot for indre løkke
            //Loop går så lenge noden til venstre for refNode har større verdi enn verdien til refNode. refNode < InternNode
            for (; j >= 0 && c.compare(verdiRefNode, liste.hent(j)) < 0; j--) {
                T verdiIndreNode = liste.hent(j); //Mellomlagrer noden som skal midlertidig fjernes fra listen
                liste.fjern(j); //Røsker ut noden forran refNode når den har en større verdi enn refNode
                //Noden som ble fjernet legges nå inn etter refNode. Resulterer i at RefNode indirekte beveger seg til venstre
                liste.leggInn(j+1,verdiIndreNode);
            }
        }
    }

    //Fra til kontroll med generics
    public static <T> void fratilKontroll(T[] a , int fra, int til) {
        if (a == null)
            throw new NullPointerException( "Tabellen i parameterlisten er tom!" );

        if (fra < 0)                                  // fra er negativ
            throw new ArrayIndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > a.length)                          // til er utenfor tabellen
            throw new ArrayIndexOutOfBoundsException
                    ("til(" + til + ") > tablengde(" + a.length + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");

        if (fra == til) throw new NoSuchElementException
                ("fra(" + fra + ") = til(" + til + ") - tomt tabellintervall!");
    }

    //Bytt med generics
    public static <T> void bytt(T[] a, int i, int j){
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    //Quicksort with generics
    private static <T> int parter(T[] a, int v, int h, T skilleverdi, Comparator <? super T> c)
    {
        while (true)                                  // stopper når v > h
        {

            while (v <= h && (c.compare(a[v], skilleverdi)<0)) v++;   // h er stoppverdi for v
            while (v <= h && (c.compare(a[h],skilleverdi)>=0)) h--;  // v er stoppverdi for h

            if (v < h) bytt(a,v++,h--);                 // bytter om a[v] og a[h]
            else  return v;  // a[v] er nåden første som ikke er mindre enn skilleverdi
            while (c.compare(a[v],skilleverdi) < 0) v++;
            while (c.compare(skilleverdi,a[h]) <= 0) h--;
        }
    }

    private static <T> int parter(T[] a, T skilleverdi, Comparator<? super T> c){
        return parter(a,0,a.length-1,skilleverdi,c);
    }



    private static <T> int sParter(T[] a, int v, int h, int indeks, Comparator<? super T> c)
    {
        bytt(a, indeks, h);           // skilleverdi a[indeks] flyttes bakerst
        int pos = parter(a, v, h - 1, a[h],c);  // partisjonerer a[v:h − 1]
        bytt(a, pos, h);              // bytter for å få skilleverdien på rett plass
        return pos;                   // returnerer posisjonen til skilleverdien
    }

    private static <T> int sParter(T[] a, int indeks, Comparator<? super T> c)   // bruker hele tabellen
    {
        return sParter(a,0,a.length-1,indeks,c); // v = 0 og h = a.lenght-1
    }

    private static <T> void kvikksortering0(T[] a , int v, int h, Comparator<? super T> c){
        if (v >= h) return; //a[v:h] er tomt eller har maks ett element
        int k = sParter(a, v, h, (v+h)/2,c); //Bruker midtverdien
        kvikksortering0(a,v, k-1,c); //Sorterer intervallet a[v:k-1]
        kvikksortering0(a,k+1,h,c); //Sorterer intervallet a[k+1:h]
    }

    private static <T> void kvikksortering(T[] a, int fra, int til, Comparator<? super T> c){ //a[fra:til>
        fratilKontroll(a,fra,til);
        kvikksortering0(a,fra,til-1,c); //v = fra, h=til-1
    }

    private static <T> void kvikksortering(T[] a, Comparator<? super T> c){
        fratilKontroll(a,0,a.length);
        kvikksortering0(a,0,a.length-1,c);
    }


    @Override
    public Iterator<T> iterator()
    {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks)
    {
        indeksKontroll(indeks,false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> //Indre Iteratorklasse
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
            //denne = hode;     // denne starter på den første i listen
            denne = finnNode(indeks);
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next()
        {
            T denneVerdi;
            if(!hasNext()) throw new NoSuchElementException("Finnes ingen neste");

            if(iteratorendringer != endringer){
                throw new ConcurrentModificationException("Ikke lov å iterere samtidig som endringer gjøres");
            }else{
                fjernOK = true;
                denneVerdi = denne.verdi;
                denne=denne.neste;
            }

        return denneVerdi;

        }

        @Override
        public void remove()
        {
            //TODO: Må progges ferdig
            //(denne == hode)//Betyr at 'denne' ikke er oppdatert med next metoden, og kan det er ikke lov å fjerne
            //(antall ==0)//Betyr listen er tom, og kan det er ikke lov å fjerne noe fra en tom liste.
            if(antall ==0||denne==hode) throw new IllegalStateException("Ikke lov å kalle metoden");

            if(endringer != iteratorendringer){
                throw new ConcurrentModificationException("Listen er endret");
            }

            fjernOK = true;
            if(denne == null){
                if(antall == 1){
                    hode = null;
                    hale = null;
                } else{
                    hale = hale.forrige; //Flytter halen en frem
                    hale.neste = null;
                }
            }
            else if(denne.forrige ==hode){
                hode = denne;
                hode.forrige = null;
                denne.forrige = null; //Trengs denne egentlig når ?

            }
            else{

                if(denne == hode){
                  //  throw new IllegalStateException("Denne er ikke oppdatert med next motoden");
                }else{

                }

                denne.forrige.forrige.neste = denne;
                denne.forrige = denne.forrige.forrige;

            }

            //denne.forrige = null;
            antall--;
            endringer++;
            iteratorendringer++;
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