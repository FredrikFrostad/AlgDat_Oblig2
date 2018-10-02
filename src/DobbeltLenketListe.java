  import java.util.*;

@SuppressWarnings("unchecked")
public class DobbeltLenketListe<T> implements Liste<T> {

    private static final class Node<T> {   // en indre nodeklasse
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

    /**
     * Metode som finner en node på angitt indeks. Metoden søker fra hode dersom
     * index < antall/2, og fra hale dersom indeks > antall/2
     * @param indeks plassering til noden vi ønsker å hente
     * @return noden som korresponderer med parameterverdien
     */
    private Node<T> finnNode(int indeks)
    {
        indeksKontroll(indeks, false);

        Node<T> node = null;

        if (indeks < antall/2) {
            node = hode;
            for (int i = 0; i < indeks; i++) {
                node = node.neste;
            }
        }else {
            node = hale;
            for (int i = antall-1; i > indeks; i--) {
                node = node.forrige;
            }
        }
        return node;
    }

    // konstruktør
    public DobbeltLenketListe()
    {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }


    public DobbeltLenketListe(T[] a) {


        this();

        if (a == null) {
            throw new NullPointerException
                    ("parameter kan ikke være null!");
        }
        int i= 0;
        while (i < a.length && a[i] == null) i++;       //hopper over alle null-verdier som ligger først i listen

        if (i < a.length) {
            Node<T> node = hode = hale = new Node<>(a[i], null, null);  //setter første verdi != null
            antall++;

            for (i++; i < a.length; i++) {

                //setter alle resterende verdier i arrayet og hopper over nullverdier
                if (a[i] != null) {
                    node = node.neste = new Node<>(a[i], node, null);
                    antall++;
                }
            }
            hale = node;
        }
    }

    /**
     * Lager en subliste av nodene i intervallet [fra, til>
     * @param fra starten av intervallet
     * @param til slutten av intervallet
     * @return et nytt listeobjekt bestående av nodene i intervallet
     */
    public Liste<T> subliste(int fra, int til)
    {
        fratilKontroll(antall, fra, til);   //sjekker at intervallet e rgyldig

        int i = fra;

        DobbeltLenketListe sub = new DobbeltLenketListe();
        if (til - fra == 0) return sub;
        Node<T> node = sub.hode = sub.hale = new Node<>(finnNode(i++).verdi, null, null);
        sub.antall++;

        for (; i < til; i++) {
            node = node.neste = new Node<>(finnNode(i).verdi, node, null);
            sub.antall++;
        }

        sub.hale = node;

        return sub;
    }


    /**
     * Metode som sjekker et intervall gyldighet
     * @param antall antall elementer i listen intervallet er en del av
     * @param fra starten på intervallet
     * @param til slutten på intervallet
     */
    public void fratilKontroll(int antall, int fra, int til) {
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


    /**
     * Metode som returnere antaller noder i en liste
     * @return
     */
    @Override
    public int antall()
    {
        return antall;
    }

    /**
     * Metode som sjekker om en liste er tom
     * @return true dersom tom, ellers false
     */
    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    /**
     * Metode som legger inn en verdi bakerst i en liste
     * @param verdi verdien som skal legges inn
     * @return tue dersom verdien blir lagt inn
     */
    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "nullverdi er ikke tillatt!");

        if (antall == 0) {  //Sjekker om listen er tom og legger i så fall inn verdien som første node
            Node<T> node = hode = hale = new Node<>(verdi);
            antall++;
            endringer++;
        }else {     //legger inn verdien som siste node dersom listen ikke er tom
            hale = hale.neste = new Node<>(verdi,hale,null);
            antall++;
            endringer++;
        }
        return true;
    }

    /**
     * Metode som legger inn en verdi på en indeks gitt som parameter
     * @param indeks plasseringen i listen der verdien skal legges inn
     * @param verdi data som skal legges inn i listen
     */
    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi, "nullverdier er ikke tillatt!");
        indeksKontroll(indeks, true);
        if (antall == 0) {                                      //legger inn i tom liste
            Node<T> node = hode = hale = new Node<>(verdi);
            antall++;
            endringer++;
        }else if (indeks == 0 && antall > 0) {                  //legger inn først i listen
            Node node = hode;
            node.forrige = new Node<>(verdi, null, node);
            hode = node.forrige;
            antall++;
            endringer++;
        }
        else if (indeks == antall && antall > 0) {     //legger inn bakerst
            hale = hale.neste = new Node<>(verdi,hale,null);
            antall++;
             endringer++;
        }else {                                                    //legger inn i mellom to andre verdier
            Node<T> node = hode;

            for (int i = 0; i < indeks - 1; i++ ) node = node.neste;

            node.neste = new Node<>(verdi, node, node.neste);
            node.neste.neste.forrige = node.neste;
            antall++;
            endringer++;
        }
    }

    /**
     * Metode som sjekker om en verdi finnes i listen
     * @param verdi verdien vi skal søke etter
     * @return true dersom verdien finnes i listen, ellers false
     */
    @Override
    public boolean inneholder(T verdi)
    {
        return indeksTil(verdi) != -1;
    }

    /**
     * Metode som henter verdien på en gitt indeks
     * @param indeks indeksen vi ønsker verdien fra
     * @return verdien som ligger på indeksen gitt som parameter
     */
    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    /**
     *      * @param verdi
     * @return
     */
    @Override
    public int indeksTil(T verdi)
    {

        if (verdi==null) return -1;

        Node<T> node = hode;

        for (int i = 0; i < antall; i++) {
            if (verdi.equals(node.verdi)) return i;
            node = node.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        Objects.requireNonNull(nyverdi, "ikke tillatt med nullverdier!");
        indeksKontroll(indeks, false);
        T gmlVerdi = finnNode(indeks).verdi;
        finnNode(indeks).verdi = nyverdi;
        endringer++;
        return gmlVerdi;
    }

    /**
     *  Fjerner første node fra venstre som er lik argumentvariablen. Koden i denne metoden ser ikke ut
     *  i måneskinn, og er resultatet av en lang dags ferd mot natt. Men undertegnede velger å gi seg selv
     *  et klapp på skulderen, jekke nok en duggfrisk en og fortsette ufortrødent videre inn i det ukjente.
     * @param verdi er verdien vi ønsker å slette fra listen
     * @return true dersom verdien finnes i listen, false ellers
     */
    @Override
    public boolean fjern(T verdi)
    {
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
    }

    /**
     * Fjerner nodne på en gitt indeks. Denne metoden er om mulig enda mere vaklevoren enn metoden over.
     * Her har den unge uredde IT-student valgt å løse problemet ved å ukritisk kaste if-setinger på
     * det til det ble lei og gikk hjem.
     * @param indeks indeksen til noden som skal fjernes
     * @return verdien til den fjernede noden
     */
    @Override
    public T fjern(int indeks)
    {
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
    }

    /**
     * Metode som nulstiller listen.
     * Har gjort tidsmålinger som sammenligner denne metoden med
     * en løkke som repetitivt kaller fjern(0) til listen er tom.
     * Resultatet er at denne metoden er ca dobbelt så rask når
     * listen blir veldig stor. (110ms vs 200ms ved antall = 10000000)
     */
    @Override
    public void nullstill()
    {
        Node<T> node = hode, temp = null;
        while (node != null) {
            temp = node.neste;
            node.neste = null;
            node.forrige = null;
            node.verdi = null;
            node = temp;
            antall--;
            endringer++;
        }
        hode = hale = null;
    }


    @Override
    public String toString()
    {
        if (antall == 0) return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(hode.verdi);

        Node<T> node = hode.neste;

        while (node != null) {
            sb.append(", ").append(node.verdi);
            node = node.neste;
        }

        sb.append("]");
        return sb.toString();
    }

    public String omvendtString()
    {
        if (antall == 0) return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(hale.verdi);

        Node<T> node = hale.forrige;

        while (node != null) {
            sb.append(", ").append(node.verdi);
            node = node.forrige;
        }

        sb.append("]");
        return sb.toString();
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        if (liste.antall() < 2) return;

        T value1 = liste.hent(0);
        T value2 = liste.hent(1);


        if (c.compare(value1, value2) > 0) {
            liste.fjern(0);
            liste.leggInn(1, value1);
        }

        for (int i = 0; i < liste.antall() - 1; i++) {
            for (int j = 0; j < liste.antall() - 1; j++) {
                value1 = liste.hent(j);
                value2 = liste.hent(j+1);

                if (c.compare(value1,value2) > 0) {
                    liste.fjern(j);
                    liste.leggInn(j + 1 ,value1);
                }
            }
        }
    }


    @Override
    public Iterator<T> iterator()
    {
        DobbeltLenketListeIterator iter = new DobbeltLenketListeIterator();
        iter.denne = hode;
        iter.iteratorendringer = endringer;

        return iter;
    }

    public Iterator<T> iterator(int indeks)
    {
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            this();
            denne = finnNode(indeks);
        }

        @Override
        public boolean hasNext() {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next() {

            if(iteratorendringer != endringer) {
                throw new ConcurrentModificationException
                        ("listen er endret!");
            }
            if (!hasNext()) {
                throw new NoSuchElementException
                        ("det er ikke flere elementer i listen!");
            }
            fjernOK = true;
            T out = denne.verdi;
            denne = denne.neste;
            return out;
        }

        @Override
        public void remove() {
            if (!fjernOK) {
                throw new IllegalStateException
                        ("ulovlig tilstand!");
            }
            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException
                        ("listen er endret!");
            }
            fjernOK = false;

            if (antall == 1) {      //fjerner eneste verdi
                hode = hale = null;
                antall--;
                endringer++;
                iteratorendringer++;
            }
            else if (denne == null) {      //fjerner siste node
                hale = hale.forrige;
                hale.neste = null;
                antall--;
                endringer++;
                iteratorendringer++;
            } else if (denne.forrige == hode) {     //fjerner første node
                hode = hode.neste;
                hode.forrige = null;
                antall--;
                endringer++;
                iteratorendringer++;
            }else {                                 //fjerner node mellom to noder
                Node<T> node = denne.forrige;
                node.forrige.neste = denne;
                node.neste.forrige = node.forrige;
                node = null;
                antall--;
                endringer++;
                iteratorendringer++;
            }
        }
    }
} // DobbeltLenketListe

