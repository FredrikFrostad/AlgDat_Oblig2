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
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "Null-verdier er ikke tillatt!");
        if (antall == 0)
            hode = hale = new Node<>(verdi, null,null);  // Listen er tom
        else {
            // Oppdaterer hale sin neste med en ny node. nåværende hale bli den nye noden sin forrige. Hale flyttes bak.
            hale = hale.neste = new Node<>(verdi, hale, null);
        }
        antall++;        // En node til er laget
        return true;     //
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public boolean inneholder(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        indeksKontroll(indeks,false);
        T forrigeVerdi = finnNode(indeks).verdi;
        if(nyverdi != null) finnNode(indeks).verdi = nyverdi;
        endringer ++;
        return forrigeVerdi;

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
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator


} // DobbeltLenketListe  bbeltLenketListe