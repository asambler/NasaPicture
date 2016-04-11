package gov.nasa.client.model.picture;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import static gov.nasa.client.model.picture.PictureItemContract.PICTURE.COLUMN;

public final class PictureItem implements Parcelable {

    private long id;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("explanation")
    @Expose
    private String explanation;
    @SerializedName("hdurl")
    @Expose
    private String hdurl;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("service_version")
    @Expose
    private String serviceVersion;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    private String localPath;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(copyright).append(date).append(explanation).append(hdurl).append(mediaType).append(serviceVersion).append(title).append(url).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PictureItem)) {
            return false;
        }
        PictureItem rhs = ((PictureItem) other);
        return new EqualsBuilder().append(copyright, rhs.copyright).append(date, rhs.date).append(explanation, rhs.explanation).append(hdurl, rhs.hdurl).append(mediaType, rhs.mediaType).append(serviceVersion, rhs.serviceVersion).append(title, rhs.title).append(url, rhs.url).isEquals();
    }

    public static PictureItem fromCursor(Cursor cursor) {
        PictureItem item = new PictureItem();
        item.id = cursor.getLong(cursor.getColumnIndex(COLUMN._ID));
        item.date = cursor.getString(cursor.getColumnIndex(COLUMN.DATE));
        item.title = cursor.getString(cursor.getColumnIndex(COLUMN.TITLE));
        item.copyright = cursor.getString(cursor.getColumnIndex(COLUMN.COPYRIGHT));
        item.explanation = cursor.getString(cursor.getColumnIndex(COLUMN.EXPLANATION));
        item.hdurl = cursor.getString(cursor.getColumnIndex(COLUMN.HD_URL));
        item.mediaType = cursor.getString(cursor.getColumnIndex(COLUMN.MEDIA_TYPE));
        item.localPath = cursor.getString(cursor.getColumnIndex(COLUMN.LOCAL_PATH));
        item.serviceVersion = cursor.getString(cursor.getColumnIndex(COLUMN.SERVICE_VERSION));
        item.url = cursor.getString(cursor.getColumnIndex(COLUMN.URL));
        return item;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        //values.put(COLUMN._ID, id);
        values.put(COLUMN.DATE, date);
        values.put(COLUMN.TITLE, title);
        values.put(COLUMN.COPYRIGHT, copyright);
        values.put(COLUMN.EXPLANATION, explanation);
        values.put(COLUMN.HD_URL, hdurl);
        values.put(COLUMN.MEDIA_TYPE, mediaType);
        values.put(COLUMN.LOCAL_PATH, "none");
        values.put(COLUMN.SERVICE_VERSION, serviceVersion);
        values.put(COLUMN.URL, url);
        return values;
    }

    @Override
    public String toString() {
        return "PictureItem{" +
                "id=" + id +
                ", copyright='" + copyright + '\'' +
                ", date='" + date + '\'' +
                ", explanation='" + explanation + '\'' +
                ", hdurl='" + hdurl + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", serviceVersion='" + serviceVersion + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", localPath='" + localPath + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.copyright);
        dest.writeString(this.date);
        dest.writeString(this.explanation);
        dest.writeString(this.hdurl);
        dest.writeString(this.mediaType);
        dest.writeString(this.serviceVersion);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.localPath);
    }

    public PictureItem() {
    }

    protected PictureItem(Parcel in) {
        this.id = in.readLong();
        this.copyright = in.readString();
        this.date = in.readString();
        this.explanation = in.readString();
        this.hdurl = in.readString();
        this.mediaType = in.readString();
        this.serviceVersion = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.localPath = in.readString();
    }

    public static final Parcelable.Creator<PictureItem> CREATOR = new Parcelable.Creator<PictureItem>() {
        public PictureItem createFromParcel(Parcel source) {
            return new PictureItem(source);
        }

        public PictureItem[] newArray(int size) {
            return new PictureItem[size];
        }
    };

    public boolean isImage() {
        return "image".equals(this.mediaType);
    }

    public boolean isEmpty() {
        return this.date == null;
    }
}
