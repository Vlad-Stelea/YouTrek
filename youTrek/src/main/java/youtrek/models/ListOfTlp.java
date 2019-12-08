package youtrek.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListOfTlp {
    List<RegisterTlpResponseBody> listOfTLP;

    public ListOfTlp() {
        listOfTLP = new ArrayList<>();
    }

    public ListOfTlp(List<RegisterTlpResponseBody> lotlp) {
        this.listOfTLP = lotlp;
    }

    public void appendTLP(RegisterTlpResponseBody atlp) {
        this.listOfTLP.add(atlp);
    }

    public Iterator<RegisterTlpResponseBody> iterator() {
        return listOfTLP.iterator();
    }

    public int getNumTlps() {
        return listOfTLP.size();
    }
}
