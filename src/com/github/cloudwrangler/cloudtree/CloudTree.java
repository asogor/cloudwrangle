package com.github.cloudwrangler.cloudtree;

import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CloudTree {
	CloudNode rootNode = null;

	public static final Pattern treeValidator = Pattern
			.compile("[\\{[\\w|\\s|\\-|/]+\\}]+");

	public static final Pattern estimateValidator = Pattern
			.compile("(\\d+),(\\d+)");

	public static final Pattern confidenceValidator = Pattern
			.compile("[1|2|3]");

	public void addNode(String tree, // {PJC}{UI}{GamePicker}{DisplayControl}
			String usecase, // GamePicker Flash UI
			String target, // Launch
			String confidence, // : 2
			String estimate,// : 5,10
			List<NodeParseError> errorLog) {
		try {
			String[] idx = this.parseTree(tree, errorLog);
			CloudNode current = rootNode;
			CloudNode previous = null;
			for (String i : idx) {
				if (current == null) {
					current = new CloudNode(i, usecase);
					rootNode = current;
				} else if (current.getTreeId().equals(i)) {
					// just skip it is the root
				} else {
					previous = current;
					current = current.getChild(i);
					if (current == null) {
						current = new CloudNode(i, usecase);
						previous.addChild(current);
					}
				}
			}
			int[] highLucky = new int[2];
			parseEstimate(tree, estimate, highLucky, errorLog);
			current.mark(highLucky[0], highLucky[1], parseConfidence(tree,
					confidence, errorLog));
		} catch (NodeIsCrap crap) {
			errorLog.add(new NodeParseError("Entry is not Kosher:" + tree));
		}

	}

	private String[] parseTree(String input, List<NodeParseError> errorLog)
			throws NodeIsCrap {
		Matcher matcher = treeValidator.matcher(input);
		if (matcher.matches()) {
			return input.substring(1, input.length() - 1).split("\\}\\{");
		} else {
			errorLog.add(new NodeParseError("Could not parse Tree element:"
					+ input));
		}
		throw new NodeIsCrap();
	}

	private void parseUseCase(String useCase) {

	}

	private void parseTarget(String target) {

	}

	private int parseConfidence(String tree, String confidence,
			List<NodeParseError> errorLog) {
		if (confidence == null) {
			return -1;
		}
		Matcher matcher = confidenceValidator.matcher(confidence);
		if (matcher.matches()) {
			return Integer.parseInt(confidence);
		} else {
			errorLog.add(new NodeParseError("Assuming LOW confidence for "
					+ tree + " invalid value:" + confidence));
			return 3;
		}
	}

	private void parseEstimate(String tree, String estimate, int[] highLucky,
			List<NodeParseError> errorLog) throws NodeIsCrap {
		if (estimate == null) {
			highLucky[0] = 0;
			highLucky[1] = 0;
			return;
		}
		Matcher matcher = estimateValidator.matcher(estimate);
		if (matcher.matches()) {
			String[] parts = estimate.split(",");
			highLucky[0] = Integer.parseInt(parts[0]);
			highLucky[1] = Integer.parseInt(parts[1]);
			return;
		} else {
			errorLog.add(new NodeParseError("Invalid estimate:" + estimate
					+ " discarded:" + tree));
		}
		throw new NodeIsCrap();
	}

	public void report(ReportFormater report) {
		Stack stack = new Stack<String>();
		rootNode.report(stack, report);
	}

	private class NodeIsCrap extends Exception {
	}
}
