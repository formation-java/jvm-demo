package fr.glouglouwine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Thing {

	private List<Point> someDataList = new ArrayList<>();

	public Thing(int size) {
		fillUpMemory(size);
	}
	
	private void fillUpMemory(int size) {
		for (int i = 0; i < size; ++i) {
			someDataList.add(new Point(1, 1));
		}
	}

}
