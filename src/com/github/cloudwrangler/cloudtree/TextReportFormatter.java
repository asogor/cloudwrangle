package com.github.cloudwrangler.cloudtree;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TextReportFormatter implements ReportFormater {

	static Map<Integer, String> confidenceMeter = new HashMap<Integer, String>();

	static {
		confidenceMeter.put(-1, "NotRated");
		confidenceMeter.put(1, "High");
		confidenceMeter.put(2, "Medium");
		confidenceMeter.put(3, "Low");
	}

	String indent = "";
	StringWriter writer = new StringWriter();

	@Override
	public void descend() {
		indent += "\t";
	}

	@Override
	public void elevate() {
		indent = indent.substring(1);
	}

	@Override
	public void printNode(Stack stack, CloudNode node) {
		writer
				.write(indent
						+ node.getTreeId()
						+ " Estimate["
						+ node.getCount().getLucky()
						+ ","
						+ node.getCount().getHigh()
						+ "]"
						+ (node.isEndNode() ? (" Estimate Confidence:" + confidenceMeter
								.get(node.getConfidence()))
								: "")
						+ (node.isIncomplete() ? " !!INCOMPLETE!! \n" : "\n"));
	}

	public String getText() {
		return writer.toString();
	}
}
