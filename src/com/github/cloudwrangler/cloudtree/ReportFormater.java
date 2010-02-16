package com.github.cloudwrangler.cloudtree;

import java.util.Stack;

public interface ReportFormater {
	public void descend();
	public void elevate();
	public void printNode(Stack parents,CloudNode node);
	public String getText();
}