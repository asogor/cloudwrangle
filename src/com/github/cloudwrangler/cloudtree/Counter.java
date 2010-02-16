package com.github.cloudwrangler.cloudtree;

public class Counter {
	private long high=0, lucky=0;

	public long getHigh() {
		return high;
	}

	public long getLucky() {
		return lucky;
	}

	public void addLucky(long l) {
		lucky += l;
	}

	public void addHigh(long l) {
		high += l;
	}
}
