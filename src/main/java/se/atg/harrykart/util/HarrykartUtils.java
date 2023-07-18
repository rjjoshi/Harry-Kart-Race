package se.atg.harrykart.util;

import se.atg.harrykart.constants.HarryKartConstants;

public class HarrykartUtils {
	public static double getTimeforLoop(int speed) {
		return HarryKartConstants.TRACK_LENGTH_IN_METERS / speed;
	}
}
