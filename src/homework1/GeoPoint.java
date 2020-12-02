package homework1;

import java.lang.Math;
import java.util.Objects;

/**
 * A homework1.GeoPoint is a point on the earth. GeoPoints are immutable.
 * <p>
 * North latitudes and east longitudes are represented by positive numbers.
 * South latitudes and west longitudes are represented by negative numbers.
 * <p>
 * The code may assume that the represented points are nearby the Technion.
 * <p>
 * <b>Implementation direction</b>:<br>
 * The Ziv square is at approximately 32 deg. 46 min. 59 sec. N
 * latitude and 35 deg. 0 min. 52 sec. E longitude. There are 60 minutes
 * per degree, and 60 seconds per minute. So, in decimal, these correspond
 * to 32.783098 North latitude and 35.014528 East longitude. The 
 * constructor takes integers in millionths of degrees. To create a new
 * homework1.GeoPoint located in the the Ziv square, use:
 * <tt>homework1.GeoPoint zivCrossroad = new homework1.GeoPoint(32783098,35014528);</tt>
 * <p>
 * Near the Technion, there are approximately 110.901 kilometers per degree
 * of latitude and 93.681 kilometers per degree of longitude. An
 * implementation may use these values when determining distances and
 * headings.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   latitude :  real        // latitude measured in degrees
 *   longitude : real        // longitude measured in degrees
 * </pre>
 **/
public class GeoPoint {
	// Abs. Function
	// 	represents a point on earth by latitude and longitude.
	//	latitude and longitude are held in millionths of degrees.
	//
	// Rep. Invariant:
	//	MIN_LATITUDE < latitude < MAX_LATITUDE
	//	MIN_LONGITUDE < longitude < MAX_LONGITUDE

	private void checkRep(){
		assert (this.latitude >= MIN_LATITUDE);
		assert (this.latitude <= MAX_LATITUDE);
		assert (this.longitude >= MIN_LONGITUDE);
		assert (this.longitude <= MAX_LONGITUDE);
	}

	/** Minimum value the latitude field can have in this class. **/
	public static final int MIN_LATITUDE  =  -90 * 1000000;
	    
	/** Maximum value the latitude field can have in this class. **/
	public static final int MAX_LATITUDE  =   90 * 1000000;
	    
	/** Minimum value the longitude field can have in this class. **/
	public static final int MIN_LONGITUDE = -180 * 1000000;
	    
	/** Maximum value the longitude field can have in this class. **/
	public static final int MAX_LONGITUDE =  180 * 1000000;

  	/**
   	 * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
  	public static final double KM_PER_DEGREE_LATITUDE = 110.901;

  	/**
     * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
  	public static final double KM_PER_DEGREE_LONGITUDE = 93.681;

	/**
	 * Convertion rate between actual degrees and how they are represented under geoPoint
	 */
  	private static final double DEGREES_CONVERSION_RATE = 1000000;


	// Implementation hint:
	// Doubles and floating point math can cause some problems. The exact
	// value of a double can not be guaranteed except within some epsilon.
	// Because of this, using doubles for the equals() and hashCode()
	// methods can have erroneous results. Do not use floats or doubles for
	// any computations in hashCode(), equals(), or where any other time 
	// exact values are required. (Exact values are not required for length 
	// and distance computations). Because of this, you should consider 
	// using ints for your internal representation of homework1.GeoPoint.

  	
  	// TODO Write abstraction function and representation invariant
	private final int  latitude;
	private final int longitude;


  	/**
  	 * Constructs homework1.GeoPoint from a latitude and longitude.
     * @requires the point given by (latitude, longitude) in millionths
   	 *           of a degree is valid such that:
   	 *           (MIN_LATITUDE <= latitude <= MAX_LATITUDE) and
     * 	 		 (MIN_LONGITUDE <= longitude <= MAX_LONGITUDE)
   	 * @effects constructs a homework1.GeoPoint from a latitude and longitude
     *          given in millionths of degrees.
   	 **/
  	public GeoPoint(int latitude, int longitude) {
  		// TODO Implement this constructor
		assert (latitude >= MIN_LATITUDE);
		assert (latitude <= MAX_LATITUDE);
		assert (longitude >= MIN_LONGITUDE);
		assert (longitude <= MAX_LONGITUDE);
		this.latitude = latitude;
		this.longitude = longitude;
		checkRep();
  	}

  	 
  	/**
     * Returns the latitude of this.
     * @return the latitude of this in millionths of degrees.
     */
  	public int getLatitude() {
  		// TODO Implement this method
		checkRep();
		return this.latitude;
  	}


  	/**
     * Returns the longitude of this.
     * @return the latitude of this in millionths of degrees.
     */
  	public int getLongitude() {
  		// TODO Implement this method
		checkRep();
		return this.longitude;
	}


  	/**
     * Computes the distance between GeoPoints.
     * @requires gp != null
     * @return the distance from this to gp, using the flat-surface, near
     *         the Technion approximation.
     **/
  	public double distanceTo(GeoPoint gp) { // todo: check how
  		// TODO Implement this method
		checkRep();
		double longitudeDelta = ((double)this.longitude - (double)gp.getLongitude()) * KM_PER_DEGREE_LONGITUDE;
		double latitudeDelta = ((double)this.latitude - (double)gp.getLatitude()) * KM_PER_DEGREE_LATITUDE;
		checkRep();
		return ((Math.sqrt(Math.pow(longitudeDelta, 2) + Math.pow(latitudeDelta, 2))) / DEGREES_CONVERSION_RATE);
	}


  	/**
     * Computes the compass heading between GeoPoints.
     * @requires gp != null && !this.equals(gp)
     * @return the compass heading h from this to gp, in degrees, using the
     *         flat-surface, near the Technion approximation, such that
     *         0 <= h < 360. In compass headings, north = 0, east = 90,
     *         south = 180, and west = 270.
     **/
  	public double headingTo(GeoPoint gp) {
		 //	Implementation hints:
		 // 1. You may find the mehtod Math.atan2() useful when
		 // implementing this method. More info can be found at:
		 // http://docs.oracle.com/javase/8/docs/api/java/lang/Math.html
		 //
		 // 2. Keep in mind that in our coordinate system, north is 0
		 // degrees and degrees increase in the clockwise direction. By
		 // mathematical convention, "east" is 0 degrees, and degrees
		 // increase in the counterclockwise direction. 

  		// TODO Implement this method
		checkRep();
		double longitudeDelta = ((gp.longitude -this.longitude) / DEGREES_CONVERSION_RATE) * KM_PER_DEGREE_LONGITUDE;
		double latitudeDelta = ((gp.latitude -this.latitude ) / DEGREES_CONVERSION_RATE) * KM_PER_DEGREE_LATITUDE;
		checkRep();
		double result = (450 - (Math.toDegrees(Math.atan2(latitudeDelta, longitudeDelta)))) % 360;
		if (result < 0)
			result += 360;
		return result;
  	}


  	/**
     * Compares the specified Object with this homework1.GeoPoint for equality.
     * @return gp != null && (gp instanceof homework1.GeoPoint) &&
     * 		   gp.latitude = this.latitude && gp.longitude = this.longitude
     **/
  	public boolean equals(Object gp) {
  		// TODO Implement this method
		checkRep();
		if (!(gp instanceof GeoPoint))
			return false;
		GeoPoint point = (GeoPoint) gp;
		checkRep();
		return (this.longitude == point.longitude && this.latitude == point.latitude);
  	}


  	/**
     * Returns a hash code value for this homework1.GeoPoint.
     * @return a hash code value for this homework1.GeoPoint.
   	 **/
  	public int hashCode() {
    	// This implementation will work, but you may want to modify it
    	// for improved performance.
		checkRep();
		return Objects.hash(this.longitude, this.latitude);
  	}


  	/**
     * Returns a string representation of this homework1.GeoPoint.
     * @return a string representation of this homework1.GeoPoint.
     **/
  	public String toString() {
  		// TODO Implement this method
		checkRep();
		String res = "latitude: " + this.latitude + ", longitude: " + this.longitude;
		checkRep();
		return res;
  	}
}
