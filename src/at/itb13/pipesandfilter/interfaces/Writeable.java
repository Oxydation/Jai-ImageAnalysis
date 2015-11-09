package at.itb13.pipesandfilter.interfaces;

import java.io.StreamCorruptedException;

public interface Writeable<T> {
	public void write(T value) throws StreamCorruptedException;
}
