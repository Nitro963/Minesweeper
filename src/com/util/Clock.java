package com.util;

public class Clock implements Runnable{
	int clk;
	public String digitalFormat() {
		int sec = clk % 60;
		int min = clk / 60;
		StringBuilder strb = new StringBuilder();
		if(min < 10)
			strb.append(String.valueOf(0));
		strb.append(String.valueOf(min));
		strb.append(":");
		if(sec < 10)
			strb.append(String.valueOf(0));
		strb.append(String.valueOf(sec));
		return strb.toString();
	}
	
	public void run() {
		clk++;
	}
}
