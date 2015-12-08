package poc.telstra.com.telstrapoc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 08/12/15.
 */
public class Facility {
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("imageHref")
    @Expose
    private Object mImageHref;

    /**
     *
     * @return
     * The mTitle
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     *
     * @param title
     * The mTitle
     */
    public void setTitle(String title) {
        this.mTitle = title;
    }

    /**
     *
     * @return
     * The mDescription
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     *
     * @param description
     * The mDescription
     */
    public void setDescription(String description) {
        this.mDescription = description;
    }

    /**
     *
     * @return
     * The imageHref
     */
    public Object getImageHref() {
        return mImageHref;
    }

    /**
     *
     * @param imageHref
     * The imageHref
     */
    public void setImageHref(Object imageHref) {
        this.mImageHref = imageHref;
    }

}
