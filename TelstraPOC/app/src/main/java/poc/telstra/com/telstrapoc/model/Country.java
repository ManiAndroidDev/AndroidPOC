package poc.telstra.com.telstrapoc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 08/12/15.
 */
public class Country {
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("rows")
    @Expose
    private List<Facility> mFacility = new ArrayList<Facility>();

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
     * The mFacility
     */
    public List<Facility> getRows() {
        return mFacility;
    }

    /**
     *
     * @param rows
     * The mFacility
     */
    public void setRows(List<Facility> rows) {
        this.mFacility = rows;
    }

}
