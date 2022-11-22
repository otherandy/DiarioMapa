package com.proyecto.diario.model;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import org.bson.types.ObjectId;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject {
    @PrimaryKey
    private ObjectId _id = new ObjectId();
    private String content;
    private Date created;
    private Date updated;
    private double lat;
    private double lng;

    public String getId() {
        return _id.toString();
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
                ", content='" + content + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", location=" + getLocation() +
                '}';
    }
}