package com.example.murk.kwizgeeq.model;

import com.wrapper.spotify.*;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.methods.UserPlaylistsRequest;
import com.wrapper.spotify.models.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Henrik on 23/04/2017.
 */

public class SpotifyQuestion extends Question implements Serializable {
    private final Api api;
    User user;

    public SpotifyQuestion(String clientID, String clientSecret) throws IOException, WebApiException {
        super();

        this.api = Api.builder().clientId(clientID).clientSecret(clientSecret).build();

        this.user = api.getMe().build().get();
    }

    public void newSong() throws IOException, WebApiException {
        List<SimplePlaylist> playlists = api.getPlaylistsForUser(user.getId()).build().get().getItems();

        Random random = new Random();
        SimplePlaylist randomPlayList = playlists.get(random.nextInt(playlists.size()));

        //TODO: a lot more

    }


}
