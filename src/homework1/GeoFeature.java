package homework1;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A homework1.GeoFeature represents a route from one location to another along a
 * single geographic feature. GeoFeatures are immutable.
 * <p>
 * homework1.GeoFeature abstracts over a sequence of GeoSegments, all of which have
 * the same name, thus providing a representation for nonlinear or nonatomic
 * geographic features. As an example, a homework1.GeoFeature might represent the
 * course of a winding river, or travel along a road through intersections
 * but remaining on the same road.
 * <p>
 * GeoFeatures are immutable. New GeoFeatures can be constructed by adding
 * a segment to the end of a homework1.GeoFeature. An added segment must be properly
 * oriented; that is, its p1 field must correspond to the end of the original
 * homework1.GeoFeature, and its p2 field corresponds to the end of the new homework1.GeoFeature,
 * and the name of the homework1.GeoSegment being added must match the name of the
 * existing homework1.GeoFeature.
 * <p>
 * Because a homework1.GeoFeature is not necessarily straight, its length - the
 * distance traveled by following the path from start to end - is not
 * necessarily the same as the distance along a straight line between
 * its endpoints.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : homework1.GeoPoint       // location of the start of the geographic feature
 *   end : homework1.GeoPoint         // location of the end of the geographic feature
 *   startHeading : angle   // direction of travel at the start of the geographic feature, in degrees
 *   endHeading : angle     // direction of travel at the end of the geographic feature, in degrees
 *   geoSegments : sequence	// a sequence of segments that make up this geographic feature
 *   name : String          // name of geographic feature
 *   length : real          // total length of the geographic feature, in kilometers
 * </pre>
 **/
public class GeoFeature {
	
	// Implementation hint:
	// When asked to return an Iterator, consider using the iterator() method
	// in the List interface. Two nice classes that implement the List
	// interface are ArrayList and LinkedList. If comparing two Lists for
	// equality is needed, consider using the equals() method of List. More
	// info can be found at:
	//   http://docs.oracle.com/javase/8/docs/api/java/util/List.html


	// Abs. Function
	// 	represents a route from one location to another along a single geographic feature.
	// 	start and end points of travel along this route are represented by th GeoPoints start and end.
	//	start and end directions are represented by the doubles startHeading and endHeading.
	//	name of the homework1.GeoFeature is represented by the String name.
	//
	// Rep. Invariant:
	//	MIN_LATITUDE < latitude < MAX_LATITUDE
	//	MIN_LONGITUDE < longitude < MAX_LONGITUDE
	
  	// TODO Write abstraction function and representation invariant

	private final GeoPoint start;
	private final GeoPoint end;
	private final double startHeading;
	private final double endHeading;
	private final  String name;
	private final double length; //todo: computed when?;
	private LinkedList<GeoSegment> segments;

	private void checkRep(){
		GeoSegment lastSegment = segments[0];
		for (GeoSegment gs : segments){

		}
	}

	/**
     * Constructs a new homework1.GeoFeature.
     * @requires gs != null
     * @effects Constructs a new homework1.GeoFeature, r, such that
     *	        r.name = gs.name &&
     *          r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public GeoFeature(GeoSegment gs) {
  		// TODO Implement this constructor
		assert (gs != null);
		this.start = gs.getP1();
		this.end = gs.getP2();
		this.startHeading = gs.getHeading();
		this.endHeading = gs.getHeading();
		this.length = gs.getLength();
		this.name = gs.getName();
		this.segments = new LinkedList<>(segments);
		segments.addLast(gs);

  	}
  

 	/**
 	  * Returns name of geographic feature.
      * @return name of geographic feature
      */
  	public String getName() {
  		// TODO Implement this method
  	}


  	/**
  	 * Returns location of the start of the geographic feature.
     * @return location of the start of the geographic feature.
     */
  	public GeoPoint getStart() {
  		// TODO Implement this method
  	}


  	/**
  	 * Returns location of the end of the geographic feature.
     * @return location of the end of the geographic feature.
     */
  	public GeoPoint getEnd() {
  		// TODO Implement this method
  	}


  	/**
  	 * Returns direction of travel at the start of the geographic feature.
     * @return direction (in standard heading) of travel at the start of the
     *         geographic feature, in degrees.
     */
  	public double getStartHeading() {
  		// TODO Implement this method
  	}


  	/**
  	 * Returns direction of travel at the end of the geographic feature.
     * @return direction (in standard heading) of travel at the end of the
     *         geographic feature, in degrees.
     */
  	public double getEndHeading() {
  		// TODO Implement this method
  	}


  	/**
  	 * Returns total length of the geographic feature, in kilometers.
     * @return total length of the geographic feature, in kilometers.
     *         NOTE: this is NOT as-the-crow-flies, but rather the total
     *         distance required to traverse the geographic feature. These
     *         values are not necessarily equal.
     */
  	public double getLength() {
  		// TODO Implement this method
  	}


  	/**
   	 * Creates a new homework1.GeoFeature that is equal to this homework1.GeoFeature with gs
   	 * appended to its end.
     * @requires gs != null && gs.p1 = this.end && gs.name = this.name.
     * @return a new homework1.GeoFeature r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *    	   r.length = this.length + gs.length
     **/
  	public GeoFeature addSegment(GeoSegment gs) {
  		// TODO Implement this method
  	}


  	/**
     * Returns an Iterator of homework1.GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this homework1.GeoFeature. All the
     * GeoSegments have the same name.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length-1 => (a[i].name == a[i+1].name &&
     *                                   a[i].p2d  == a[i+1].p1))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     */
  	public Iterator<GeoSegment> getGeoSegments() {
  		// TODO Implement this method
  	}


  	/**
     * Compares the argument with this homework1.GeoFeature for equality.
     * @return o != null && (o instanceof homework1.GeoFeature) &&
     *         (o.geoSegments and this.geoSegments contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o) {
  		// TODO Implement this method
  	}


  	/**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
  	public int hashCode() {
    	// This implementation will work, but you may want to modify it
    	// improved performance.
    	
    	return 1;
  	}


  	/**
  	 * Returns a string representation of this.
   	 * @return a string representation of this.
     **/
  	public String toString() {
  		// TODO Implement this method
  	}
}
