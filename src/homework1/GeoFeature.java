package homework1;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Objects;

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
	// 	start and end points of travel along this route are represented by the GeoPoints start and end.
	//	start and end directions are represented by the doubles startHeading and endHeading.
	//	name of the GeoFeature is represented by the String name.
	//
	// Rep. Invariant:
	//	* start and end are not null
	//	* segments is not null
	// 	* length is the sum of the length of all segments included in th feature.
	//	* start equals p1 of the first segment and end equals p2 of last segment.
	//	* all names of segments under GeoFeature are the same and equal the name of the GeoFeature
	//	* segments are sorted in a way such as for every to consecutive segments:
	//		leftSegment != null && rightSegment != null
	//		leftSegment.p2 equals to rightSegment.p1
	//	* all headings are between 0 and 360

	

	private final GeoPoint start;
	private final GeoPoint end;
	private final double startHeading;
	private final double endHeading;
	private final  String name;
	private final double length;
	private final ArrayList<GeoSegment> segments;

	/**
	 * max angle of a compass
	 */
	private static final double MAX_ANGLE = 360;

	private static void checkRep(GeoFeature gf){ // given gf to easily assert other geoFeatures given to methods and copy Ctor
		assert (gf.start != null && gf.end != null && gf.segments !=null);
		assert (0 < gf.startHeading && gf.startHeading < MAX_ANGLE);
		assert (0 < gf.endHeading && gf.endHeading < MAX_ANGLE);
		assert (gf.start.equals(gf.segments.get(0).getP1()));
		assert (gf.end.equals(gf.segments.get(gf.segments.size()-1).getP2()));
		GeoSegment lastSegment = gf.segments.get(0);
		double sumOfLength = 0;
		for (GeoSegment gs : gf.segments){
			assert (gs != null);
			assert (gs instanceof GeoSegment);
			assert(gf.name.equals(gs.getName()));
			sumOfLength += gs.getLength();
			if (gs.equals(gf.segments.get(0))){
				continue;
			}
			assert(lastSegment.getP2().equals(gs.getP1()));
			lastSegment = gs;
		}
		assert (gf.length == sumOfLength);
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
		assert (gs != null);
		this.start = gs.getP1();
		this.end = gs.getP2();
		this.startHeading = gs.getHeading();
		this.endHeading = gs.getHeading();
		this.length = gs.getLength();
		this.name = gs.getName();
		this.segments = new ArrayList<GeoSegment>();
		this.segments.add(gs);
		checkRep(this);
  	}

	private GeoFeature(GeoFeature gf, GeoSegment gs) {
		assert (gf != null);
		checkRep(gf);
		assert (gs != null);
		assert (gf.getEnd().equals(gs.getP1()));
		assert (gf.getName().equals(gs.getName()));
		this.start = gf.getStart();
		this.end = gs.getP2();
		this.startHeading = gf.getStartHeading();
		this.endHeading = gs.getHeading();
		this.length = gf.getLength() + gs.getLength();
		this.name = gs.getName();
		this.segments = new ArrayList<GeoSegment>(gf.segments);
		this.segments.add(gs);
		checkRep(this);
	}

 	/**
 	  * Returns name of geographic feature.
      * @return name of geographic feature
      */
  	public String getName() {
		checkRep(this);
		return this.name;
  	}


  	/**
  	 * Returns location of the start of the geographic feature.
     * @return location of the start of the geographic feature.
     */
  	public GeoPoint getStart() {
		checkRep(this);
		return this.start;
	}


  	/**
  	 * Returns location of the end of the geographic feature.
     * @return location of the end of the geographic feature.
     */
  	public GeoPoint getEnd() {
		checkRep(this);
		return this.end;
  	}


  	/**
  	 * Returns direction of travel at the start of the geographic feature.
     * @return direction (in standard heading) of travel at the start of the
     *         geographic feature, in degrees.
	 *         if the first segment is of zero length return -1.
     */
  	public double getStartHeading() {
		checkRep(this);
		return this.startHeading;
  	}


  	/**
  	 * Returns direction of travel at the end of the geographic feature.
     * @return direction (in standard heading) of travel at the end of the
     *         geographic feature, in degrees.
	 *         if the end segment is of zero length return -1.
     */
  	public double getEndHeading() {
		checkRep(this);
		return this.endHeading;
  	}


  	/**
  	 * Returns total length of the geographic feature, in kilometers.
     * @return total length of the geographic feature, in kilometers.
     *         NOTE: this is NOT as-the-crow-flies, but rather the total
     *         distance required to traverse the geographic feature. These
     *         values are not necessarily equal.
     */
  	public double getLength() {
		checkRep(this);
		return this.length;
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
		checkRep(this);
		assert (gs != null);
		assert (gs.getName().equals(this.name));
		assert (gs.getP1().equals(this.getEnd()));
		return new GeoFeature(this, gs);
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
		checkRep(this);
		ArrayList<GeoSegment>newSegments = new ArrayList<GeoSegment>(this.segments);
		checkRep(this);
		return newSegments.iterator();
  	}


  	/**
     * Compares the argument with this homework1.GeoFeature for equality.
     * @return o != null && (o instanceof homework1.GeoFeature) &&
     *         (o.geoSegments and this.geoSegments contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o) {
		checkRep(this);
		if (!(o instanceof GeoFeature))
			return false;
		GeoFeature gf = (GeoFeature) o;
		if (this.segments.size() != gf.segments.size())
			return false;
		for (int i = 0; i < this.segments.size(); i++){
			if (!(this.segments.get(i).equals(gf.segments.get(i))))
				return false;
		}
		checkRep(this);
		return true;
  	}


  	/**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
  	public int hashCode() {
    	// This implementation will work, but you may want to modify it
    	// improved performance.
    	checkRep(this);
    	return Objects.hash(start, end, name, startHeading, endHeading, length);
  	}


  	/**
  	 * Returns a string representation of this.
   	 * @return a string representation of this.
     **/
  	public String toString() {
		checkRep(this);
		String res = segments.size() + " segments in " + name;
		return res;
  	}
}
