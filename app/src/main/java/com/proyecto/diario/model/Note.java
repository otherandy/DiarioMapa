package com.proyecto.diario.model;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import org.bson.types.ObjectId;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject {
    public Note() {
        _id = new ObjectId();
        title = "Titulo";
        content = "";
    }

    @PrimaryKey
    private ObjectId _id;
    private String title;
    private String content;
    private Date created;
    private Date updated;
    private double lat;
    private double lng;

    public String getId() {
        return _id.toHexString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public LatLng getLocation() {
        return new LatLng(lat, lng);
    }

    public void setLocation(double latitude, double longitude) {
        this.lat = latitude;
        this.lng = longitude;
    }

    @NonNull
    @Override
    public String toString() {
        return "Note{" +
                "_id=" + _id +
                ", title= '" + title + '\'' +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", location=" + getLocation() +
                '}';
    }
}