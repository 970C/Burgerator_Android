package burgerator.yelp;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.ArrayList;
import java.util.List;


/**
 * This is Burgerator's method for accessing the Yelp API V2.
 * Code inspired by YelpAPI java example.
 * 
 * This program utilizes the capabilities of the Yelp API version 2.0 by using the Search API to
 * query for businesses by a search term and location, and the Business API to query additional
 * information about a result from the search query.
 * 
 *
 * See <a href="http://www.yelp.com/developers/documentation">Yelp Documentation</a> for more info.
 * 
 */
public class YelpAPI {

  private static final String API_HOST = "api.yelp.com";
  private static final String DEFAULT_TERM = "burgers";
  private static final String DEFAULT_LOCATION = "Yakima, WA";
  private static final int SEARCH_LIMIT = 20;
  private static final String SEARCH_PATH = "/v2/search";
  private static final String BUSINESS_PATH = "/v2/business";

  /*
   * Update OAuth credentials below from the Yelp Developers API site:
   * http://www.yelp.com/developers/getting_started/api_access
   */
  private static final String CONSUMER_KEY = "gP8tFaK3EUIXuYnVgiF3Eg";
  private static final String CONSUMER_SECRET = "6mTmYvFwCjq_jxlR6vzgBUy1ENo";
  private static final String TOKEN = "UA4FPch6l1HEBT9lqXNJTTadm632NkHA";
  private static final String TOKEN_SECRET = "b1hrUcjSmR1vu1BdcJEQdluOWVQ";

  private OAuthService service;
  private Token accessToken;



  /**
   * Creates and returns an {@link OAuthRequest} based on the API endpoint specified.
   * Comes stock with YelpAPI Java example
   *
   * @param path API endpoint to be queried
   * @return <tt>OAuthRequest</tt>
   */
  private OAuthRequest createOAuthRequest(String path) {
    OAuthRequest request = new OAuthRequest(Verb.GET, "https://" + API_HOST + path);
    return request;
  }

  /**
   * Sends an {@link OAuthRequest} and returns the {@link Response} body.
   * Comes stock with YelpAPI Java example
   *
   * @param request {@link OAuthRequest} corresponding to the API request
   * @return <tt>String</tt> body of API response
   */
  private String sendRequestAndGetResponse(OAuthRequest request) {
    System.out.println("Querying " + request.getCompleteUrl() + " ...");
    this.service.signRequest(this.accessToken, request);
    Response response = request.send();
    return response.getBody();
  }

  /**
   * Setup the Yelp API OAuth credentials.
   * Comes stock with YelpAPI Java example
   *
   * @param consumerKey Consumer key
   * @param consumerSecret Consumer secret
   * @param token Token
   * @param tokenSecret Token secret
   *
   * @deprecated Use YelpAPI() instead: Team Burgerator Yelp API credentials are hard coded
   */
  public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
    this.service =
            new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
                    .apiSecret(consumerSecret).build();
    this.accessToken = new Token(token, tokenSecret);
  }



  /**
   * Constructor for an instance of the Yelp API.
   * Uses Burgerator's Yelp credentials.
   */
  public YelpAPI(){
    this.service =
            new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(CONSUMER_KEY)
                    .apiSecret(CONSUMER_SECRET).build();
    this.accessToken = new Token(TOKEN, TOKEN_SECRET);
  }

  /**
   * Creates and sends a request to the Search API by term and location.
   * <p>
   * See <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp Search API V2</a>
   * for more info.
   * 
   * @param term <tt>String</tt> of the search term to be queried
   * @param location <tt>String</tt> of the location
   * @return <tt>String</tt> JSON Response
   */
  private String searchForBusinessesByLocation(String term, String location) {
    OAuthRequest request = createOAuthRequest(SEARCH_PATH);
    request.addQuerystringParameter("term", term);
    request.addQuerystringParameter("location", location);
    request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
    return sendRequestAndGetResponse(request);
  }

  /**
   * Creates and sends a request to the Business API by business ID.
   *
   * See <a href="http://www.yelp.com/developers/documentation/v2/business">Yelp Business API V2</a>
   * for more info.
   * 
   * @param businessID String business ID of the requested business
   * @return A single restaurant represented as a JSONObject
   */
  public JSONObject searchByBusinessId(String businessID) {
    OAuthRequest request = createOAuthRequest(BUSINESS_PATH + "/" + businessID);

    JSONObject business = null;
    try {
      business = new JSONObject(sendRequestAndGetResponse(request));
    }catch(JSONException e){ Log.e("YelpAPI", e.toString());}

    return business;
  }

  /**
   * Used as an underlying method for the other two getRestaurants overloads.
   *
   * @param searchResponseJSON
   * @return A list of restaurants represented as JSONObjects
   */
  private List<JSONObject> getRestaurants(String searchResponseJSON){
    ArrayList<JSONObject> restaurants = new ArrayList<JSONObject>();

    //Initialize response
    JSONObject response = null;
    try {
      response = new JSONObject(searchResponseJSON);
    } catch (JSONException pe) {
      System.out.println("Error: could not parse JSON response:");
      System.out.println(searchResponseJSON);
      System.exit(1);
    }

    //Gather data from response
    try {
      JSONArray businesses = (JSONArray) response.get("businesses");

      for(int i=0; i<businesses.length(); i++){
        restaurants.add((JSONObject)businesses.get(i));
      }

    }catch(JSONException e){
      Log.e("Yelp API queryAPI(Y)", e.toString());
    }

    return restaurants;
  }

  /**
   * Queries the Search API using an existing YelpAPI service instance
   *
   * @param yelpApi YelpAPI service instance
   * @return A list of restaurants represented as JSONObjects
   */
  public List<JSONObject> getRestaurants(YelpAPI yelpApi) {

    String searchResponseJSON =
        yelpApi.searchForBusinessesByLocation(DEFAULT_TERM, DEFAULT_LOCATION);

    return getRestaurants(searchResponseJSON);
  }

  /**
   * Queries the Search API using an existing YelpAPI service instance and a location
   *
   * @param yelpApi YelpAPI service instance
   * @param location A string that represents a location. example: ""Yakima, WA""
   * @return A list of restaurants represented as JSONObjects
   */
  public List<JSONObject> getRestaurants(YelpAPI yelpApi, String location) {

    String searchResponseJSON =
            yelpApi.searchForBusinessesByLocation(DEFAULT_TERM, location);

    return getRestaurants(searchResponseJSON);
  }

  /**
   * Queries the Search API using an existing YelpAPI service instance and a gps location
   *
   * @param yelpApi YelpAPI service instance
   * @param location A Location object that represents a gps location. example: 47.0051047,120.5412578
   * @return A list of restaurants represented as JSONObjects
   */
  public List<JSONObject> getRestaurantsByGPS(YelpAPI yelpApi, Location location){

    OAuthRequest request = createOAuthRequest(SEARCH_PATH);
    request.addQuerystringParameter("term", DEFAULT_TERM);
    String currentGPSLocation =   String.format("%f",location.getLatitude())+","+
                                  String.format("%f",location.getLongitude());
    request.addQuerystringParameter("ll", currentGPSLocation);  //ex: 37.77493,-122.419415
    request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
    String searchResponseJSON = sendRequestAndGetResponse(request);

    return getRestaurants(searchResponseJSON);
  }


}
