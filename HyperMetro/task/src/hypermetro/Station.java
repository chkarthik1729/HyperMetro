package hypermetro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Station {

    String name;
    Station prevStation;
    Station nextStation;
    Map<String, List<Station>> transfer;

    public Station (String name, Station prev, Station next) {
        this.name = name;
        this.prevStation = prev;
        this.nextStation = next;
    }

    public Station getNextStation() {
        return nextStation;
    }

    public Station getPrevStation() {
        return prevStation;
    }

    public void setNextStation(Station next) {
        this.nextStation = next;
    }

    public void setPrevStation(Station prev) {
        this.prevStation = prev;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, List<Station>> getTransfer() {
        return transfer;
    }

    public void addTransfer(String lane, Station station) {
        if (transfer == null)
            transfer = new HashMap<>();

        if (!transfer.containsKey(lane))
            transfer.put(lane, new ArrayList<>());

        transfer.get(lane).add(station);
    }
}
