
import homework1.ExampleGeoSegments;
import homework1.GeoFeature;
import homework1.GeoSegment;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;




public class GeoFeatureTest {
    private static final GeoSegment[] segments= ExampleGeoSegments.segments;
    private GeoFeature gf_Hankin=null;
    private GeoFeature gf_Hankin_reversed=null;
//private GeoFeature gf_Trumpeldor=null;
//private GeoFeature gf_Hagalil=null;
//private GeoFeature gf_Hanita=null;
//private GeoFeature gf_Simha_Golan=null;
//private GeoFeature gf_Ruppin=null;
//private GeoFeature gf_International=null;
//private GeoFeature gf_Yaari=null;
//private GeoFeature Natan_Komoi=null;


    @BeforeEach
    public void setUp() throws Exception {
        gf_Hankin_reversed= new GeoFeature (segments[12].reverse()); //todo:reverse?
        gf_Hankin  		  = new GeoFeature (segments[10]);
    }

    @Test
    public void testEquals() {
        gf_Hankin=gf_Hankin.addSegment(segments[11]);
        gf_Hankin=gf_Hankin.addSegment(segments[12]);
        gf_Hankin_reversed=gf_Hankin_reversed.addSegment(segments[11].reverse());
        gf_Hankin_reversed=gf_Hankin_reversed.addSegment(segments[10].reverse());
        Assertions.assertEquals( gf_Hankin, gf_Hankin,"Self equality");
        //assertEquals("Equal to copy", gf_Hankin,new GeoFeature(segments[10]));
        Assertions.assertTrue(!gf_Hankin_reversed.equals(new Integer(6)), "totally different objects are equal.");
        Assertions.assertTrue(!gf_Hankin_reversed.equals(gf_Hankin), "same Feature but reversed");

    }

    @Test
    public void testLength_reversed_and_regular(){
        gf_Hankin=gf_Hankin.addSegment(segments[11]);
        gf_Hankin=gf_Hankin.addSegment(segments[12]);
        gf_Hankin_reversed=gf_Hankin_reversed.addSegment(segments[11].reverse());
        gf_Hankin_reversed=gf_Hankin_reversed.addSegment(segments[10].reverse());
        double length=gf_Hankin.getLength();
        System.out.println(gf_Hankin.getLength());
        Assertions.assertEquals(length,gf_Hankin_reversed.getLength(),0.1, "expected to be the same size");
        Assertions.assertTrue(
                gf_Hankin.hashCode() != gf_Hankin_reversed.hashCode(), ".equals() objects must not have the same .hashCode()");
    }

    @Test
    public void test__if_contained_feature_equal(){
        gf_Hankin=gf_Hankin.addSegment(segments[11]);
        gf_Hankin=gf_Hankin.addSegment(segments[12]);
        System.out.println(gf_Hankin.toString());
        GeoFeature gf_Hankin1=new GeoFeature (segments[10]);
        gf_Hankin1=gf_Hankin1.addSegment(segments[11]);
        System.out.println(gf_Hankin1.toString());
        Assertions.assertTrue(!gf_Hankin1.equals(gf_Hankin), "must not be equal.");
        Assertions.assertTrue(!gf_Hankin.equals(gf_Hankin1), "must not be equal.");

    }

}
