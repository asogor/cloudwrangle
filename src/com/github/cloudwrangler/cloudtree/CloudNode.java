package com.github.cloudwrangler.cloudtree;

import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class CloudNode {
	private final String treeId;
	private  String usecase;
	private int low = 0;
	private int high = 0;
	private int confidence = 3;

	private boolean endNode = false;
	private Map<String, CloudNode> children = new TreeMap<String, CloudNode>();

	public CloudNode(String treeId, String usecase) {
		super();
		this.treeId = treeId;
		this.usecase = usecase;
	}

	public boolean isEndNode() {
		return endNode;
	}

	public void addChild(CloudNode child) {
		this.children.put(child.getTreeId(), child);
	}

	public String getTreeId() {
		return treeId;
	}

	public String getUsecase() {
		return usecase;
	}

	public CloudNode getChild(String subId) {
		return this.children.get(subId);
	}

	public void mark(int low, int high, int confidence) {
		this.high = high;
		this.low = low;
		this.confidence = confidence;
		this.endNode = true;
	}

	public boolean isIncomplete()
	{
		return (this.children.size()>0 && endNode);
	}
	
	public Counter getCount() {
		Counter result = new Counter();
		result.addHigh(Math.round(high * (0.5 + (confidence * 0.5))));
		result.addLucky(low);
		if (!this.endNode) {
			for (CloudNode child : this.children.values()) {
				result.addHigh(child.getCount().getHigh());
				result.addLucky(child.getCount().getLucky());
			}
		}
		return result;
	}

	public int getConfidence() {
		return confidence;
	}

	public void report(Stack<String> stack, ReportFormater report) {
		report.printNode(stack, this);
		report.descend();
		stack.push(this.treeId);
		if (!this.endNode) {
			for (CloudNode child : this.children.values()) {
				child.report(stack, report);
			}
		}
		stack.pop();
		report.elevate();

	}
}
