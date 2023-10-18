package com.freelkee.carmanager.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.freelkee.carmanager.response.ObjectCenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectCenterDeserializer extends StdDeserializer<ObjectCenter> {

    public ObjectCenterDeserializer() {
        this(null);
    }

    public ObjectCenterDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ObjectCenter deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        final ArrayNode jsonArray = p.getCodec().readTree(p);

        if (jsonArray.size() > 0) {
            final JsonNode geojsonObject = jsonArray.get(0).get("geojson");
            final JsonNode coordinatesArray = geojsonObject.get("coordinates");

            final var coordinatesList = new ArrayList<double[]>();

            if (geojsonObject.get("type").asText().equals("Polygon")) {
                addCoordinates(coordinatesList, coordinatesArray);
            } else if (geojsonObject.get("type").asText().equals("MultiPolygon")) {
                for (JsonNode polygon : coordinatesArray) {
                    addCoordinates(coordinatesList, polygon);
                }
            }

            return new ObjectCenter
                (
                    coordinatesList.stream()
                        .map(coordinate -> coordinate[1])
                        .mapToDouble(Double::doubleValue)
                        .average().orElse(0.0),
                    coordinatesList.stream()
                        .map(coordinate -> coordinate[0])
                        .mapToDouble(Double::doubleValue)
                        .average().orElse(0.0)
                );
        } else {
            throw new RuntimeException("Address not found");
        }
    }

    private void addCoordinates(List<double[]> coordinatesList, JsonNode coordinatesNode) {
        for (int i = 0; i < coordinatesNode.size(); i++) {
            final var coordinates = coordinatesNode.get(i);
            for (int j = 0; j < coordinates.size(); j++) {
                final var longitude = coordinates.get(j).get(0).asDouble();
                final var latitude = coordinates.get(j).get(1).asDouble();
                coordinatesList.add(new double[]{longitude, latitude});
            }
        }
    }
}
