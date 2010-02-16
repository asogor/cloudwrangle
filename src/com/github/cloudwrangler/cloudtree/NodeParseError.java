package com.github.cloudwrangler.cloudtree;

public class NodeParseError {
	private String error;

	public NodeParseError(String error) {
		super();
		this.error = error;
	}

	@Override
	public String toString() {
		return error;
	}
}
