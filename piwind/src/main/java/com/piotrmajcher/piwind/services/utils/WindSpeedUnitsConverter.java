package com.piotrmajcher.piwind.services.utils;

public interface WindSpeedUnitsConverter {

    double knotsToMps(double knots);

    double mpsToKnots(double mps);

    double knotsToKmh(double knots);

    double kmhToKnots(double kmh);

    double mpsToKmh(double mps);

    double kmhToMps(double kmh);
}
