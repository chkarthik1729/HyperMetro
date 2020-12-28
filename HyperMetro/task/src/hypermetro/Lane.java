package hypermetro;

import java.util.NoSuchElementException;

public class Lane {
    private String name;

    private Station start;
    private Station end;

    private int stationsCount;

    public Lane(String name) {
        this.name = name;
        start = end = null;
        stationsCount = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Station getStart() {
        return this.start;
    }

    public Station getEnd() {
        return this.end;
    }

    public void addStart(String stationName) {
        Station newStation = new Station(stationName, null, start);
        if (start != null) start.setPrevStation(newStation);
        start = newStation;

        if (end == null) end = newStation;

        stationsCount++;
    }

    public void addEnd(String stationName) {
        Station newStation = new Station(stationName, end, null);
        if (end != null) end.setNextStation(newStation);
        end = newStation;

        if (start == null) start = newStation;

        stationsCount++;
    }

    public void addBefore(Station station, String stationName) {
        if (station == null) throw new NoSuchElementException();

        Station newStation = new Station(stationName, null, station);

        if (station.getPrevStation() == null) {
            station.setPrevStation(newStation);
            newStation.setNextStation(newStation);
            start = newStation;
        } else {
            var temp = station.getPrevStation();
            newStation.setPrevStation(newStation);
            newStation.setNextStation(station);
            temp.setNextStation(newStation);
        }

        stationsCount++;
    }

    public void removeFirst() {
        if (stationsCount == 0) throw new NoSuchElementException();

        start = start.getNextStation();
        start.setPrevStation(null);
        stationsCount--;
    }

    public void removeLast() {
        if (stationsCount == 0) throw new NoSuchElementException();

        end = end.getPrevStation();
        end.setNextStation(null);
        stationsCount--;
    }

    public void remove(Station station) {
        if (station == null) throw new NoSuchElementException();

        if (station.getPrevStation() == null) {
            removeFirst();
        } else if (station.getNextStation() == null) {
            removeLast();
        } else {
            station.getPrevStation().setNextStation(station.getNextStation());
            station.getNextStation().setPrevStation(station.getPrevStation());
            stationsCount--;
        }
    }

    public int getNoOfStations() {
        return this.stationsCount;
    }

    public Station findStation(String stationName) {
        assert stationName != null;
        Station curr = start;
        while (curr != null) {
            if (stationName.equals(curr.getName()))
                return curr;
            curr = curr.getNextStation();
        }
        return null;
    }
}
