package project.projima.com.productos.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private String name;
    private String description;
    private String iamgeUrl;
    private String uniqueid;
    private int price;

    public Product() {
    }

    public Product(String name, String description, String iamgeUrl, int price) {
        this.name = name;
        this.description = description;
        this.iamgeUrl = iamgeUrl;
        this.price = price;
    }

    protected Product(Parcel in) {
        name = in.readString();
        description = in.readString();
        iamgeUrl = in.readString();
        price = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIamgeUrl() {
        return iamgeUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        this.iamgeUrl = iamgeUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(iamgeUrl);
        dest.writeInt(price);
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }
}
