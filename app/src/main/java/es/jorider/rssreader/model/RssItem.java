package es.jorider.rssreader.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jorge
 */
public class RssItem implements Parcelable {

    private int id = 0;
    private String title = null;
    private String description = null;
    private String date = null;
    private Bitmap image = null;
    private String imageSrc = null;

    public RssItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.description);
        dest.writeParcelable(this.image, 0);
        dest.writeString(this.imageSrc);
    }

    private RssItem(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.date = in.readString();
        this.description = in.readString();
        this.image = in.readParcelable(Bitmap.class.getClassLoader());
        this.imageSrc = in.readString();
    }

    public static final Parcelable.Creator<RssItem> CREATOR = new Parcelable.Creator<RssItem>() {
        public RssItem createFromParcel(Parcel source) {
            return new RssItem(source);
        }

        public RssItem[] newArray(int size) {
            return new RssItem[size];
        }
    };
}
