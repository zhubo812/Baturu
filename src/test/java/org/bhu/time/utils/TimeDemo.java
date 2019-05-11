package org.bhu.time.utils;

import org.junit.Test;

public class TimeDemo {

	private TimeHelper th = new TimeHelper(TimeFormat.YYYY_MM_DD.getValue());
	
	
	@Test
	public void DateTester() {
		System.out.println(th.GetDate());
	}
}
