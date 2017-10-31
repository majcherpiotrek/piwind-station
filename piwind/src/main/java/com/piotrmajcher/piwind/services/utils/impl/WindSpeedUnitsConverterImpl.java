package com.piotrmajcher.piwind.services.utils.impl;

import com.piotrmajcher.piwind.services.utils.WindSpeedUnitsConverter;

public class WindSpeedUnitsConverterImpl implements WindSpeedUnitsConverter{

    private static final double MPS_TO_KNOTS = 1.943844;
    private static final double KNOTS_TO_MPS = 0.514444;
    private static final double KMH_TO_KNOTS = 0.539957;
    private static final double KNOTS_TO_KMH = 1.852;
    private static final double MPS_TO_KMH = 3.6;
    private static final double KMH_TO_MPS = 0.277778;
    @Override
    public double knotsToMps(double knots) {
        return knots * KNOTS_TO_MPS;
    }

    @Override
    public double mpsToKnots(double mps) {
        return mps * MPS_TO_KNOTS;
    }

    @Override
    public double knotsToKmh(double knots) {
        return knots * KNOTS_TO_KMH;
    }

    @Override
    public double kmhToKnots(double kmh) {
        return kmh * KMH_TO_KNOTS;
    }

    @Override
    public double mpsToKmh(double mps) {
        return mps * MPS_TO_KMH;
    }

    @Override
    public double kmhToMps(double kmh) {
        return kmh * KMH_TO_MPS;
    }
}
