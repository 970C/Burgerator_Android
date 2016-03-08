package burgerator.yelp;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Kevin on 3/7/2016.
 */
public class YelpRestaurant {
    public boolean is_claimed;
    public Double rating;
    public String mobile_url;
    public String rating_img_url;
    public int review_count;
    public String name;
    public String rating_img_url_small;
    public String url;
    public List<List<String>> categories;
    public long menu_date_updated;
    public String phone;
    public String snippet_text;
    public String image_url;
    public String snippet_image_url;
    public String display_phone;
    public String rating_img_url_large;
    public String menu_provider;
    public String id;
    public boolean is_closed;
    public YelpLocation location;

    public class YelpLocation{
        public String cross_streets;
        public String city;
        public List<String> display_address;
        public Double geo_accuracy;
        public List<String> neighborhoods;
        public String postal_code;
        public String country_code;
        public List<String> address;
        public YelpCoordinate coordinate;
        public String state_code;

        public class YelpCoordinate{
            public BigDecimal latitude;
            public BigDecimal longitude;
        }
    }

}
