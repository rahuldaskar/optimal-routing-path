package com.rda.optimal.route;

import com.rda.optimal.route.exception.BaseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.Map;

/*
 * Basic test cases for candidates
 * */

public class CustomTests {

    @InjectMocks
    CodingContestMain codingContestMain;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void notReachablePath() {
        long start = System.currentTimeMillis();
        Map<String, String> result = codingContestMain.processAndStorePackageRoutes(new File("src/test/resources/custom/not-reachable-path.txt"));
        long end = System.currentTimeMillis();
        System.out.println("Program took: " + (end - start) + " ms");
        Assert.assertEquals("WH11 WH5 WH1 : 11", result.get("pkg1"));
        Assert.assertEquals("WH5 WH1 WH2 WH3 WH4 : 9", result.get("pkg2"));
        Assert.assertEquals("WH2 WH1 : 1", result.get("pkg3"));
        Assert.assertEquals("WH8 WH9 WH10 WH5 : 6", result.get("pkg4"));
        Assert.assertEquals("WH7 WH8 WH9 WH10 WH5 RETURN_WH : 12", result.get("pkg5"));
        Assert.assertEquals("Can't deliver package with ID: pkg6.Because no connected warehouses found to reach from WH1 to Bangalore", result.get("pkg6"));
    }

    @Test(expected = BaseException.class)
    public void negativeTransitTime() {
        codingContestMain.processAndStorePackageRoutes(new File("src/test/resources/custom/negative-time.txt"));
    }

    @Test(expected = BaseException.class)
    public void emptyFile() {
        codingContestMain.processAndStorePackageRoutes(new File("src/test/resources/custom/empty.txt"));
    }

    @Test(expected = BaseException.class)
    public void invalidFile() {
        codingContestMain.processAndStorePackageRoutes(new File("src/test/resources/custom/invalid.txt"));
    }


    @Test(expected = BaseException.class)
    public void invalidPackageDest() {
        codingContestMain.processAndStorePackageRoutes(new File("src/test/resources/custom/invalid-package-dest.txt"));
    }

    @Test(expected = BaseException.class)
    public void invalidPackageSource() {
        codingContestMain.processAndStorePackageRoutes(new File("src/test/resources/custom/invalid-package-source.txt"));
    }

    @Test(expected = BaseException.class)
    public void invalidSourceWareHouseInDeliveryNetwork() {
        codingContestMain.processAndStorePackageRoutes(new File("src/test/resources/custom/invalid-source-wh-in-dn.txt"));
    }

    @Test(expected = BaseException.class)
    public void invalidDestinationInDeliveryNetwork() {
        codingContestMain.processAndStorePackageRoutes(new File("src/test/resources/custom/invalid-dest-wh-in-dn.txt"));
    }

    @Test
    public void samePackageSourceTimeCheck() {
        long start = System.currentTimeMillis();
        Map<String, String> result = codingContestMain.processAndStorePackageRoutes(new File("src/test/resources/custom/same-package-source-time-check.txt"));
        long end = System.currentTimeMillis();
        System.out.println("Program took: " + (end - start) + " ms");
        Assert.assertEquals(10, result.size());
    }

}
