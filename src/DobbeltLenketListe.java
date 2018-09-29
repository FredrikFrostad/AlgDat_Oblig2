import java.util.*;


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

    @SuppressWarnings("uncheked")
    public DobbeltLenketListe(T[] a) {

        this();

        if (a == null) {
            throw new NullPointerException
                    ("parameter kan ikke være null!");
        }
        int i= 0;
        while (i < a.length && a[i] == null) i++;

        if (i < a.length) {
            Node<T> node = hode = hale = new Node<>(a[i], null, null);
            antall++;

            for (i++; i < a.length; i++) {

                if (a[i] != null) {
                    node = node.neste = new Node<>(a[i], node, null);
                    antall++;
                }
            }
            hale = node;
        }
    }

    // |liste
    public Liste<T> subliste(int fra, int til)
    {
        fratilKontroll(antall, fra, til);

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



    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "nullverdi er ikke tillatt!");

        if (antall == 0) {
            Node<T> node = hode = hale = new Node<>(verdi);
            antall++;
            endringer++;
        }else {
            hale = hale.neste = new Node<>(verdi,hale,null);
            antall++;
            endringer++;
        }
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi, "nullverdier er ikke tillatt!");
        indeksKontroll(indeks, true);
        if (antall == 0) {      //legger inn i tom liste
            Node<T> node = hode = hale = new Node<>(verdi);
            antall++;
            endringer++;
        }else if (indeks == 0 && antall > 0) {      //legger inn først i listen
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
        }else {         //legger inn i mellom to andre verdier
            Node<T> node = hode;
            for (int i = 0; i < indeks - 1; i++ ) node = node.neste;
            node.neste = new Node<>(verdi, node, node.neste);
            node.neste.neste.forrige = node.neste;
            antall++;
            endringer++;
        }
    }

    @Override
    public boolean inneholder(T verdi)
    {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

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
        return gmlVerdi;
    }

    @Override
    public boolean fjern(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T fjern(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void nullstill()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public boolean hasNext() {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }
    }
} // DobbeltLenketListe