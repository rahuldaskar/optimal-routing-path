package com.rda.optimal.route;

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

public class CodingContestMainTests {

    @InjectMocks
    CodingContestMain codingContestMain;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void process() {
        long start = System.currentTimeMillis();
        //Modified input to pass file directly from here so as to write custom tests
        Map<String, String> result = codingContestMain.processAndStorePackageRoutes(new File("src/main/resources/input.txt"));
        long end = System.currentTimeMillis();
        System.out.println("Program took: " + (end - start) + " ms");
        Assert.assertEquals("WH11 WH5 WH1 : 11", result.get("pkg1"));
        Assert.assertEquals("WH5 WH1 WH2 WH3 WH4 : 9", result.get("pkg2"));
        Assert.assertEquals("WH2 WH1 : 1", result.get("pkg3"));
        Assert.assertEquals("WH8 WH9 WH10 WH5 : 6", result.get("pkg4"));
        Assert.assertEquals("WH7 WH8 WH9 WH10 WH5 RETURN_WH : 12", result.get("pkg5"));
    }


}
