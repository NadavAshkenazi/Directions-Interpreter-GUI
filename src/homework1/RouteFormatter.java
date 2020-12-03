package homework1;


import java.util.Iterator;

/**
 * A RouteFormatter class knows how to create a textual description of
 * directions from one location to another. The class is abstract to
 * support different textual descriptions.
 */
public abstract class RouteFormatter {

  	/**
     * Give directions for following this Route, starting at its start point
     * and facing in the specified heading.
     * @requires route != null && 
     * 			0 <= heading < 360
     * @param route the route for which to print directions.
   	 * @param heading the initial heading.
     * @return A newline-terminated directions <tt>String</tt> giving
     * 	       human-readable directions from start to end along this route.
     **/
  	public String computeDirections(Route route, double heading) {
  		// Implementation hint:
		// This method should call computeLine() for each geographic
		// feature in this route and concatenate the results into a single
		// String.
  		
  		// TODO Implement this method
		Iterator<GeoFeature> it = route.getGeoFeatures();
		String directions = "";
		double oldHeding = heading;
		while(it.hasNext()){
			GeoFeature tempFeature = it.next();
			directions += computeLine(tempFeature, oldHeding);
			oldHeding = tempFeature.getEndHeading();
		}
		return directions;
  	}


  	/**
     * Computes a single line of a multi-line directions String that
     * represents the instructions for traversing a single geographic
     * feature.
     * @requires geoFeature != null
     * @param geoFeature the geographical feature to traverse.
   	 * @param origHeading the initial heading.
     * @return A newline-terminated <tt>String</tt> that gives directions
     * 		   on how to traverse this geographic feature.
     */
  	public abstract String computeLine(GeoFeature geoFeature, double origHeading);


	/**
	 * Computesthe severity to turn based on the heading change with no regards to direction.
	 * @requires 0 <= heading1 < 360 &&
	 *           0 <= heading2 < 360 &&
	 *           heading2 >= heading1
	 * @param bigHeading bigger heading.
	 * @param smallHeading smaller heading.
	 * @return the severity of the turn, for bigHeating-smallHeading = delta
	 * <p>
	 * <pre>
	 * Continue             if delta < 10
	 * Turn slight     if 10 <= delta < 60
	 * Turn            if 60 <= delta < 120
	 * Turn sharp      if 120 <= delta < 179
	 * U-turn               if 179 <= delta
	 * </pre>
	 */
	private String _decideTurnAbs(double bigHeading, double smallHeading){
		double delta = bigHeading - smallHeading;
		if (delta < 10)
			return "Continue";
		else if (delta < 60)
			return "Turn slight";
		else if (delta < 120)
			return "Turn";
		else if (delta < 179)
			return "Turn sharp";
		return "U-turn";
	}

  	/**
     * Computes directions to turn based on the heading change.
     * @requires 0 <= oldHeading < 360 &&
     *           0 <= newHeading < 360
     * @param origHeading the start heading.
   	 * @param newHeading the desired new heading.
     * @return English directions to go from the old heading to the new
     * 		   one. Let the angle from the original heading to the new
     * 		   heading be a. The turn should be annotated as:
     * <p>
     * <pre>
     * Continue             if a < 10
     * Turn slight right    if 10 <= a < 60
     * Turn right           if 60 <= a < 120
     * Turn sharp right     if 120 <= a < 179
     * U-turn               if 179 <= a
     * </pre>
     * and likewise for left turns.
     */

  	protected String getTurnString(double origHeading, double newHeading) {
  		// TODO Implement this method
		String severity;
		String direction;
		if (newHeading > origHeading){
			severity = _decideTurnAbs(newHeading, origHeading);
			direction = "right";
		}

		else{
			severity = _decideTurnAbs(origHeading, newHeading);
			direction = "left";
		}
		if (!severity.equals("continue") && !!severity.equals("U-turn"))
			return severity + direction;
		return severity;
	}
}
