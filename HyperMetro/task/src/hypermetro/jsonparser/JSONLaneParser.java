package hypermetro.jsonparser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hypermetro.Lane;
import hypermetro.Station;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JSONLaneParser {

    public static Map<String, Lane> readLanesFromFile(String fileName) throws IOException {
        var laneMap = new HashMap<String, Lane>();

        try (var reader = new BufferedReader(new FileReader(fileName))) {
            var parser = new JsonParser();
            var outerObject = parser.parse(reader).getAsJsonObject();

            // Form lanes
            for (var laneEntry : outerObject.entrySet()) {
                String laneName = laneEntry.getKey();
                Lane lane = readLane(laneName, laneEntry.getValue().getAsJsonObject());
                laneMap.put(laneName, lane);
            }

            // Add transfer between stations
            for (var laneEntry : outerObject.entrySet()) {
                for (var stationEntry : laneEntry.getValue().getAsJsonObject().entrySet()) {
                    addTransfers(laneEntry.getKey(), stationEntry.getValue().getAsJsonObject(), laneMap);
                }
            }
            return laneMap;
        }
    }

    private static Lane readLane(String laneName, JsonObject jsonObject) {
        var lane = new Lane(laneName);
        for (var entry : jsonObject.entrySet()) {
            JsonObject station = entry.getValue().getAsJsonObject();
            lane.addEnd(station.get("name").getAsString());
        }
        return lane;
    }

    private static void addTransfers(String fromLaneName, JsonObject stationObject, Map<String, Lane> laneMap) {
        String fromStationName = stationObject.get("name").getAsString();
        JsonElement transferElement = stationObject.get("transfer");
        if (transferElement == null || transferElement.isJsonNull()) return;

        JsonObject transferObject = transferElement.getAsJsonObject();
        Lane toLane = laneMap.get(transferObject.get("line").getAsString());
        Station toStation = toLane.findStation(transferObject.get("station").getAsString());
        laneMap.get(fromLaneName).findStation(fromStationName).addTransfer(toLane.getName(), toStation);
    }
}
