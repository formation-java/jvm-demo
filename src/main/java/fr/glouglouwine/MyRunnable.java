package fr.glouglouwine;

public class MyRunnable implements Runnable {

	private String name;
	private int workDuration;

	public MyRunnable(String name, int duration) {
		this.name = name;
		this.workDuration = duration;
	}

	public void run() {
		System.out.println("Hi, I'm " + name + ", and I'm running!");
		try {
			Thread.sleep(workDuration);
			System.out.println(name + " here - my work is done, so... ciao!");
		} catch (InterruptedException e) {
			System.out.println(name + " here - you stopped me, I heard you, so I'm going away!");
		}
	}

}
