package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "hashtags",
        "user_mentions"
})

public class Entities {
    private List<Hashtag> hashtags;
    private List<UserMention> userMentions;

    @JsonGetter
    public List<Hashtag> getHashtags(){
        return hashtags;
    }

    @JsonSetter
    public void setHashtags(){
        this.hashtags = hashtags;
    }

    @JsonGetter
    public List<UserMention> getUserMentions(){
        return userMentions;
    }

    @JsonSetter
    public void setUserMentions(){
        this.userMentions = userMentions;
    }
}
