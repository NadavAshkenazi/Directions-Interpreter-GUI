package homework1;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * A Route is a path that traverses arbitrary GeoSegments, regardless
 * of their names.
 * <p>
 * Routes are immutable. New Routes can be constructed by adding a segment
 * to the end of a Route. An added segment must be properly oriented; that
 * is, its p1 field must correspond to the end of the original Route, and
 * its p2 field corresponds to the end of the new Route.
 * <p>
 * Because a Route is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily
 * the same as the distance along a straight line between its endpoints.
 * <p>
 * Lastly, a Route may be viewed as a sequence of geographical features,
 * using the <tt>getGeoFeatures()</tt> method which returns an Iterator of
 * GeoFeature objects.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint            // location of the start of the route
 *   end : GeoPoint              // location of the end of the route
 *   startHeading : angle        // direction of travel at the start of the route, in degrees
 *   endHeading : angle          // direction of travel at the end of the route, in degrees
 *   geoFeatures : sequence      // a sequence of geographic features that make up this Route
 *   geoSegments : sequence      // a sequence of segments that make up this Route
 *   length : real               // total length of the route, in kilometers
 *   endingGeoSegment : GeoSegment  // last GeoSegment of the route
 * </pre>
 **/
public class Route {


 	// TODO Write abstraction function and representation invariant

	// Abs. Function
	//	A Route is a path that traverses arbitrary GeoSegments
	//	* the collection of segments is held under the ArreyList segments and also under the ArrayList feature
	//	  where it is divided to groups of segments under the same name.
	//	* start and end points are represented by the geoPoints start and end.
	//	* start and end directions are represented bt startHeading and endHeading.
	//	* the length og the entire route is the sum of all of its segments and represented by the length attribute.

	// Rep. Invariant:
	//	* start and end are not null
	//	* segments and features are not null
	//  * length equals the sum of all segments length and also equals the sum of all features length
	//	* start equals p1 of the first segment and end equals p2 of last segment.
	//	* start equals start of the first feature and end equals end of last feature.
	//	* startHeading equals first segments heading and first features startHeading
	//	* endHeading equals last segments heading and last features endHeading
	//	* endingGeoSegment is not null and equals last segment in segment and lest segment of the last feature
	//	* all headings are between 0 and 360
	//	* segments are sorted in a way such as for every to consecutive segments:
	//		leftSegment != null && rightSegment != null
	//		leftSegment.p2 equals to rightSegment.p1
	//	* features are sorted in a way such as for every to consecutive features:
	//		leftFeature != null && rightFeature != null
	//		leftFeature.name != rightFeature.name
	//		leftFeatures end point equals rightFeatures start point
	// 	* every GeoSegment GS in segments upholds:
	//		** GS is in a feature in features
	//		** if GSs neighbors in segments are in this feature they are the neighbors of GS and in the same order
	//		** if the left neighbor of GS is not in this feature:
	//			*** GS is the start if its feature in features
	//			*** the left neighbor of GS in segments is the end of the feature which is the left neighbor of GSs feature
	//		** if the right neighbor of GS is not in this feature:
	//			*** GS is the end if its feature in features
	//			*** the right neighbor of GS in segments is the start of the feature which is the right neighbor of GSs feature

    private final GeoPoint start;
    private final GeoPoint end;
    private final double startHeading;
    private final double endHeading;
    private final double length;
    private final GeoSegment endingGeoSegment;
    private final ArrayList<GeoSegment> segments;
    private final ArrayList<GeoFeature> features;

	/**
	 * max angle of a compass
	 */
	private static final double MAX_ANGLE = 360;

	void checkRep(Route route) { // given route to easily assert other routes given to methods and copy Ctor
		assert (route.start != null && route.end != null);
		assert (route.segments != null && route.features != null);
		assert (0 < route.startHeading && route.startHeading < MAX_ANGLE);
		assert (0 < route.endHeading && route.endHeading < MAX_ANGLE);
		assert (route.start.equals(route.segments.get(0).getP1()));
		assert (route.end.equals(route.segments.get(route.segments.size() - 1).getP2()));
		assert (route.start.equals(route.features.get(0).getStart()));
		assert (route.end.equals(route.features.get(route.features.size() - 1).getEnd()));
		assert (route.startHeading == route.segments.get(0).getHeading());
		assert (route.endHeading == route.segments.get(route.segments.size() - 1).getHeading());
		assert (route.startHeading == route.features.get(0).getStartHeading());
		assert (route.endHeading == route.features.get(route.features.size() - 1).getEndHeading());

		GeoSegment lastSegment = route.segments.get(0);
		double sumOfSegmentsLength = 0;
		for (GeoSegment gs : route.segments) {
			assert (gs != null);
			assert (gs instanceof GeoSegment);
			sumOfSegmentsLength += gs.getLength();
			if (gs.equals(route.segments.get(0))) {
				continue;
			}
			assert (lastSegment.getP2().equals(lastSegment.getP1()));
			lastSegment = gs;
		}
		assert (route.length == sumOfSegmentsLength);

		GeoFeature lastFeature = route.features.get(0);
		double sumOfFeaturesLength = 0;
		for (GeoFeature gf : route.features) {
			assert (gf != null);
			assert (gf instanceof GeoFeature);
			sumOfFeaturesLength += gf.getLength();
			if (gf.equals(route.features.get(0))) {
				continue;
			}
			assert (lastFeature.getEnd().equals(gf.getStart()));
			assert (!lastFeature.getName().equals(gf.getName()));
			lastFeature = gf;
			assert (route.length == sumOfFeaturesLength);
		}
		Iterator<GeoSegment> segmentsGsIt = route.segments.iterator();
		Iterator<GeoSegment> featuresGsIt = null;
		for (GeoFeature gf : route.features) {
			featuresGsIt = gf.getGeoSegments();
			while (featuresGsIt.hasNext() && segmentsGsIt.hasNext()) {
				Object segmentsGs = segmentsGsIt.next();
				Object featuresGs = featuresGsIt.next();
				assert (segmentsGs.equals(featuresGs));
				if (segmentsGs.equals(route.segments.get(route.segments.size() - 1))) {
					assert (gf.equals(route.features.get(route.features.size() - 1)));
					assert (!featuresGsIt.hasNext());
					assert (segmentsGs.equals(endingGeoSegment) && featuresGs.equals(endingGeoSegment));
				}
			}
		}
		assert (featuresGsIt.hasNext() == false && segmentsGsIt.hasNext() == false);
	}

    /**
  	 * Constructs a new Route.
     * @requires gs != null
     * @effects Constructs a new Route, r, such that
     *	        r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public Route(GeoSegment gs) {
  		// TODO Implement this constructor
        assert(gs != null);
        this.start = gs.getP1();
		this.end = gs.getP2();
		this.startHeading = gs.getHeading();
		this.endHeading = gs.getHeading();
		this.endingGeoSegment = gs;
		this.length =  gs.getLength();
		this.segments = new ArrayList<GeoSegment>();
		this.segments.add(gs);
		this.features = new ArrayList<GeoFeature>();
		this.features.add(new GeoFeature(gs));
		int i = 0;
//        checkRep(this);
  	}

	private Route(Route route, GeoSegment gs) {
		// TODO Implement this constructor
		assert (route != null);
		checkRep(route);
		assert (gs != null);
		assert (gs.getP1().equals(route.endingGeoSegment.getP2()));
		this.start = route.getStart();
		this.end = gs.getP2();
		this.startHeading = route.getStartHeading();
		this.endHeading = gs.getHeading();
		this.endingGeoSegment = gs;
		this.length = route.getLength() + gs.getLength();
		this.segments = new ArrayList<GeoSegment>(route.segments);
		this.segments.add(gs);
		this.features = new ArrayList<GeoFeature>(route.features);
		GeoFeature lastFeature = this.features.get(this.features.size() -1);
		if (lastFeature.getName().equals(gs.getName()) ){
			this.features.set(this.features.size() -1, lastFeature.addSegment(gs));
		}
		else{
			this.features.add(new GeoFeature(gs));
		}
//		checkRep(this);
	}


    /**
     * Returns location of the start of the route.
     * @return location of the start of the route.
     **/
  	public GeoPoint getStart() {
  		// TODO Implement this method
		checkRep(this);
        return this.start;
  	}


  	/**
  	 * Returns location of the end of the route.
     * @return location of the end of the route.
     **/
  	public GeoPoint getEnd() {
  		// TODO Implement this method
		checkRep(this);
		return this.end;
  	}


  	/**
  	 * Returns direction of travel at the start of the route, in degrees.
   	 * @return direction (in compass heading) of travel at the start of the
   	 *         route, in degrees.
	 *         if the first segment is of zero length return -1.
   	 **/
  	public double getStartHeading() {
  		// TODO Implement this method
		checkRep(this);
		return this.startHeading;
  	}


  	/**
  	 * Returns direction of travel at the end of the route, in degrees.
     * @return direction (in compass heading) of travel at the end of the
     *         route, in degrees.
	 *         if the end segment is of zero length return -1.
     **/
  	public double getEndHeading() {
  		// TODO Implement this method
		checkRep(this);
		return this.endHeading;
  	}


  	/**
  	 * Returns total length of the route.
     * @return total length of the route, in kilometers.  NOTE: this is NOT
     *         as-the-crow-flies, but rather the total distance required to
     *         traverse the route. These values are not necessarily equal.
   	 **/
  	public double getLength() {
  		// TODO Implement this method
		checkRep(this);
		return this.length;
  	}


  	/**
     * Creates a new route that is equal to this route with gs appended to
     * its end.
   	 * @requires gs != null && gs.p1 == this.end
     * @return a new Route r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *         r.length = this.length + gs.length
     **/
  	public Route addSegment(GeoSegment gs) {
  		// TODO Implement this method
		checkRep(this);
		assert (gs != null);
		assert (gs.getP1().equals(this.endingGeoSegment.getP2()));
		Route newRoute = new Route(this, gs);
		checkRep(this);
		return newRoute;
  	}


    /**
     * Returns an Iterator of GeoFeature objects. The concatenation
     * of the GeoFeatures, in order, is equivalent to this route. No two
     * consecutive GeoFeature objects have the same name.
     * @return an Iterator of GeoFeatures such that
     * <pre>
     *      this.start        = a[0].start &&
     *      this.startHeading = a[0].startHeading &&
     *      this.end          = a[a.length - 1].end &&
     *      this.endHeading   = a[a.length - 1].endHeading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length - 1 => (a[i].name != a[i+1].name &&
     *                                     a[i].end  == a[i+1].start))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoFeature
     **/
  	public Iterator<GeoFeature> getGeoFeatures() {
  		// TODO Implement this method
		checkRep(this);
		ArrayList<GeoFeature> tempList = new ArrayList<GeoFeature>(this.features);
		checkRep(this);
		return tempList.iterator();
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this route.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum (0 <= i < a.length) . a[i].length
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     **/
  	public Iterator<GeoSegment> getGeoSegments() {
  		// TODO Implement this method
		checkRep(this);
		ArrayList<GeoSegment> tempList = new ArrayList<GeoSegment>();
		checkRep(this);
		return tempList.iterator();
  	}


  	/**
     * Compares the specified Object with this Route for equality.
     * @return true iff (o instanceof Route) &&
     *         (o.geoFeatures and this.geoFeatures contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o) {
  		// TODO Implement this method
		checkRep(this);
		if (!(o instanceof Route))
			return false;
		Route givenRoute = (Route) o;

		Iterator<GeoFeature> ownFeatureIt = this.getGeoFeatures();
		Iterator<GeoFeature> givenFeatureIt = givenRoute.getGeoFeatures();
		while (ownFeatureIt.hasNext() && givenFeatureIt.hasNext()){
			Object ownFeature = ownFeatureIt.next();
			Object givenFeature = givenFeatureIt.next();

			if (!ownFeature.equals(givenFeature))
				return false;
		}
		if (givenFeatureIt.hasNext() || ownFeatureIt.hasNext())
			return false;


		Iterator<GeoSegment> ownSegmentIt = this.getGeoSegments();
		Iterator<GeoSegment> givenSegmentIt = givenRoute.getGeoSegments();
		while (ownSegmentIt.hasNext() && givenSegmentIt.hasNext()){
			Object ownSegment = ownSegmentIt.next();
			Object givenSegment = givenSegmentIt.next();
			if (!ownSegment.equals(givenSegment))
				return false;
		}
		if (ownSegmentIt.hasNext() || givenSegmentIt.hasNext())
			return false;
		checkRep(this);
		return true;
	}


    /**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
  	public int hashCode() {
    	// This implementation will work, but you may want to modify it
    	// for improved performance.

		checkRep(this);
		return Objects.hash(start, end, startHeading, endHeading, length, endingGeoSegment);
  	}


    /**
     * Returns a string representation of this.
     * @return a string representation of this.
     **/
  	public String toString() {
		checkRep(this);
		String res = "route has " + segments.size() + " segments in " + features.size() + " features\n";
		res += "features are: \n";
		Iterator<GeoFeature> feature = this.getGeoFeatures();
		while (feature.hasNext()){
			res += "* " + feature.next().getName();
		}
		checkRep(this);
		return res;
  	}
}
