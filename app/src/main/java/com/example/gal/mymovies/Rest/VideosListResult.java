
package com.example.gal.mymovies.Rest;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideosListResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<VideosResult> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideosResult> getResults() {
        return results;
    }

    public void setResults(List<VideosResult> results) {
        this.results = results;
    }

}
