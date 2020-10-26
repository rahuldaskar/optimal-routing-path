package com.rda.optimal.route.common;

import com.rda.optimal.route.model.WareHouse;

/**
 * Holds constants.
 */
public final class Constants {
    public static final String INPUT_SECTION_1 = "# Section 1: List of company warehouses in the country";
    public static final String INPUT_SECTION_2 = "# Section 2: Delivery network";
    public static final String INPUT_SECTION_3 = "# Section 3: Packages";
    public static final String EMPTY_FILE_ERROR = "Input file is empty!";
    public static final String CANT_READ_INPUT_ERROR = "Unable to read input file!";
    public static final String INPUT_SPLIT_CHAR = " ";
    public static final WareHouse RETURN_WARE_HOUSE = new WareHouse("RETURN_WH", "RETURN");

    /**
     * Hiding default constructor.
     */
    private Constants() {

    }


}
