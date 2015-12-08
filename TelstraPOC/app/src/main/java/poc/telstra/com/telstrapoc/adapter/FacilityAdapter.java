package poc.telstra.com.telstrapoc.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.List;

import poc.telstra.com.telstrapoc.R;
import poc.telstra.com.telstrapoc.model.Facility;

/**
 * Created by admin on 08/12/15.
 */
public class FacilityAdapter extends BaseAdapter {


    private Context mContext;
    private List<Facility> mfaFacilities;
    private ImageLoader mImageLoader;


    public FacilityAdapter(Context context,List<Facility> facilities) {
        mContext = context;
        mfaFacilities = facilities;

        mImageLoader = ImageLoader.getInstance();
        DisplayImageOptions imgOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageOnLoading(android.R.color.transparent)
                .build();
        ImageLoaderConfiguration imgConfig = new ImageLoaderConfiguration.Builder(mContext)
                .defaultDisplayImageOptions(imgOptions)
                .build();
        mImageLoader.init(imgConfig);
    }


        public int getCount() {
            return mfaFacilities.size();
        }

        public Facility getItem(int position) {
            return mfaFacilities.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {


            ViewHolderItem viewHolder;

            if(convertView==null){

                // inflate the layout
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(R.layout.list_item, parent, false);

                // well set up the ViewHolder
                viewHolder = new ViewHolderItem();
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.description = (TextView) convertView.findViewById(R.id.description);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);

                // store the holder with the view.
                convertView.setTag(viewHolder);

            }else{
                // we've just avoided calling findViewById() on resource everytime
                // just use the viewHolder
                viewHolder = (ViewHolderItem) convertView.getTag();
            }

            //Faciltiy item based on the position
            Facility facility = getItem(position);

            // assign values if the object is not null
            if(facility != null) {
                // get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
                viewHolder.title.setText(facility.getTitle());
                viewHolder.description.setText(facility.getDescription());

                viewHolder.imageView.setTag(facility.getImageHref());

                mImageLoader.displayImage(facility.getImageHref(), viewHolder.imageView);
            }


            return convertView;

        }


class ViewHolderItem {
    TextView title,description;
    ImageView imageView;
}

}
