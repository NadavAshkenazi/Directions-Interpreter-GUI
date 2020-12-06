package homework1;
import javax.swing.text.Segment;
import java.util.Objects;

/**
 * A GeoSegment models a straight line segment on the earth. GeoSegments
 * are immutable.
 * <p>
 * A compass heading is a nonnegative real number less than 360. In compass
 * headings, north = 0, east = 90, south = 180, and west = 270.
 * <p>
 * When used in a map, a GeoSegment might represent part of a street,
 * boundary, or other feature.
 * As an example usage, this map
 * <pre>
 *  Trumpeldor   a
 *  Avenue       |
 *               i--j--k  Hanita
 *               |
 *               z
 * </pre>
 * could be represented by the following GeoSegments:
 * ("Trumpeldor Avenue", a, i), ("Trumpeldor Avenue", z, i),
 * ("Hanita", i, j), and ("Hanita", j, k).
 * </p>
 *
 * </p>
 * A name is given to all GeoSegment objects so that it is possible to
 * differentiate between two GeoSegment objects with identical
 * GeoPoint endpoints. Equality between GeoSegment objects requires
 * that the names be equal String objects and the end points be equal
 * GeoPoint objects.
 * </p>
 *
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   name : String       // name of the geographic feature identified
 *   p1 : GeoPoint       // first endpoint of the segment
 *   p2 : GeoPoint       // second endpoint of the segment
 *   length : real       // straight-line distance between p1 and p2, in kilometers
 *   heading : angle     // compass heading from p1 to p2, in degrees
 * </pre>
 **/
public class GeoSegment  {

	// Abs. Function
	// 	represents a segment which is a way between two points on earth.
	//	every segment has a name,length between two points, compass degree from the 1st point to the 2nd.
	//  the point represents as Goepoints (p1,p2)
	//  th name is a string
	//	the length is a real number >=0 (double)
	//	the compass degree is real number n,  0<=N<=360 (double)
	//
	// Rep. Invariant:
	//	p1 != null
	//	p2 != null
	//	name!=null && name!=""

	private void checkRep(){
		assert (this.p1 != null);
		assert (this.p2 !=null);
		assert (this.name != null);
	}

	private final String name;
	private final GeoPoint p1;
	private final GeoPoint p2;
	private final double length;
	private final double heading;



	/**
	 * Constructs a new GeoSegment with the specified name and endpoints.
	 * @requires name != null && p1 != null && p2 != null
	 * @effects constructs a new GeoSegment with the specified name and endpoints.
	 **/
	public GeoSegment(String name, GeoPoint p1, GeoPoint p2) {
		assert (name!=null);
		assert (p1!=null);
		assert (p2!=null);
		this.name = new String(name);
		this.p1 = p1;
		this.p2 = p2;
		this.length = p1.distanceTo(p2);
		this.heading = p1.headingTo(p2);
		checkRep();
	}


	/**
	 * Returns a new GeoSegment like this one, but with its endpoints reversed.
	 * @return a new GeoSegment gs such that gs.name = this.name
	 *         && gs.p1 = this.p2 && gs.p2 = this.p1
	 **/
	public GeoSegment reverse() {
		checkRep();
		GeoSegment gs = new GeoSegment (this.name,this.p2,this.p1);
		checkRep();
		return gs;
	}


	/**
	 * Returns the name of this GeoSegment.
	 * @return the name of this GeoSegment.
	 */
	public String getName() {
		checkRep();
		return this.name;
	}


	/**
	 * Returns first endpoint of the segment.
	 * @return first endpoint of the segment.
	 */
	public GeoPoint getP1() {
		checkRep();
		return this.p1;
	}


	/**
	 * Returns second endpoint of the segment.
	 * @return second endpoint of the segment.
	 */
	public GeoPoint getP2() {
		checkRep();
		return this.p2;
	}


	/**
	 * Returns the length of the segment.
	 * @return the length of the segment, using the flat-surface, near the
	 *         Technion approximation.
	 */
	public double getLength() {
		checkRep();
		return this.length;
	}


	/**
	 * Returns the compass heading from p1 to p2 or -1.
	 * @return the compass heading from p1 to p2, in degrees, using the
	 *         flat-surface, near the Technion approximation.
	 *         if p1==p2 it means this.length == 0 then return value will be -1
	 **/
	public double getHeading() {
		checkRep();
		if (this.getLength() > 0)
			return this.heading;
		return -1;
	}


	/**
	 * Compares the specified Object with this GeoSegment for equality.
	 * @return gs != null && (gs instanceof GeoSegment)
	 *         && gs.name = this.name && gs.p1 = this.p1 && gs.p2 = this.p2
	 **/
	public boolean equals(Object gs) {
		checkRep();
		if (!(gs instanceof GeoSegment)) return false;
		GeoSegment segment = (GeoSegment) gs;
		checkRep();
		return (segment.getName().equals(this.name)
				&& segment.getP1().equals(this.p1)
				&& segment.getP2().equals(this.p2)
				);
	}


	/**
	 * Returns a hash code value for this.
	 * @return a hash code value for this.
	 **/
	public int hashCode() {
			checkRep();
			return Objects.hash(this.heading,this.length,this.name,this.p1,this.p2);
	}


	/**
	 * Returns a string representation of this.
	 * @return a string representation of this.
	 **/
	public String toString() {
		checkRep();
		String res = "(\"" + this.name +"\""
					+ ", " + this.p1.toString()
					+ ", " + this.p2.toString()+")";
		checkRep();
		return res;
	}

}

