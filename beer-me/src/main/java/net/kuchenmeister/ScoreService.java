package net.kuchenmeister;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoreService {

	public static String getScore(final String id) throws Exception {
//		Thread.sleep(1000);
		return BrewMapper.getScore(id);

	}
}
