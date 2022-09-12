package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "created_at",
        "id",
        "id_str",
        "text",
        "entities",
        "coordinates",
        "retweet_count",
        "favorite_count",
        "favorited",
        "retweeted"
})

public class Tweet {
    @JsonProperty
    public String created_at;

    @JsonProperty
    public long id;

    @JsonProperty
    public String id_str;

    @JsonProperty
    public String text;

    @JsonProperty
    public Entities entites;

    @JsonProperty
    public Coordinates coordinates;

    @JsonProperty
    public Integer retweet_count;

    @JsonProperty
    public Integer favorite_count;
    @JsonProperty
    public boolean favorited;
    @JsonProperty
    public boolean retweeted;

    @JsonGetter
    public String getCreated_at(){
        return created_at;
    }
    @JsonSetter
    public void setCreated_at(){this.created_at = created_at;}

    @JsonGetter
    public long getId(){return id;}

    @JsonSetter
    public void setId(){this.id = id;}

    @JsonGetter
    public String getId_str(){return id_str;}

    @JsonSetter
    public void setId_str(){this.id_str = id_str;}

    @JsonGetter
    public String getText(){return text;}

    @JsonSetter
    public void setText(){this.text = text;}

    @JsonGetter
    public Entities getEntites(){return entites;}

    @JsonSetter
    public void setEntites(){this.entites = entites;}

    @JsonGetter
    public Coordinates getCoordinates(){return coordinates;}

    @JsonSetter
    public void setCoordinates(){this.coordinates = coordinates;}

    @JsonGetter
    public Integer getRetweet_count(){return retweet_count;}

    @JsonSetter
    public void setRetweet_count(){this.retweet_count = retweet_count;}

    @JsonGetter
    public Integer getFavorite_count(){return favorite_count;}

    @JsonSetter
    public void setFavorite_count(){this.favorite_count = favorite_count;}

    @JsonGetter
    public boolean getFavorited(){return favorited;}

    @JsonSetter
    public void setFavorited(){this.favorited = favorited;}

    @JsonGetter
    public boolean getRetweeted(){return retweeted;}

    @JsonSetter
    public void setRetweeted(){this.retweeted = retweeted;}

}
