package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "coordinates",
        "type"
})

public class Coordinates {
    @JsonProperty("type")
    private String type;
    @JsonProperty("coordinates")
    private List<Integer> coordinates;

    @JsonProperty("text")
    public String text;
    @JsonGetter
    public String getType() {
        return type;
    }
    @JsonSetter
    public void setType(String type) {
        this.type = type;
    }
    @JsonGetter
    public List<Integer> getCoordinates() {
        return coordinates;
    }
    @JsonSetter
    public void setCoordinates(List<Integer> coordinates) {
        this.coordinates = coordinates;
    }
}
