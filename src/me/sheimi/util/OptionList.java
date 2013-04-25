package me.sheimi.util;

public class OptionList {

	private String[] mArgs;
	private int mIndex;

	public OptionList(String[] args) {
		mArgs = args;
		mIndex = 0;
	}

	public boolean hasNext() {
		return mIndex == mArgs.length;
	}

	public String next() {
		if (!hasNext()) {
			return null;
		}
		String value = mArgs[mIndex];
		mIndex++;
		return value;
	}

	public int nextInt() {
		String value = next();
		if (value == null) {
			return 0;
		}
		return Integer.parseInt(value);
	}

	public void moveBack() {
		if (mIndex > 0) {
			mIndex--;
		}
	}

}
