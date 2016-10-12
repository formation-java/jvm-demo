package fr.blablacar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class App {

	private final static Map<String, Thread> threads = new HashMap<>();

	private static Thing ref1;
	private static Thing ref2;
	
	public static void main(String[] args) {
		System.out.println("** Java memory & threads demo **");
		printUsage();
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		boolean done = false;

		System.out.println("What shall I do?");
		while (!done) {
			try {
				String cmd = consoleReader.readLine();
				String[] tokens = cmd.split(" ");
				if (tokens.length < 1) {
					printUsage();
					continue;
				}
				String action = tokens[0];
				if ("start".equals(action)) {
					startThread(tokens);
				} else if ("store".equals(action)) {
					store(tokens);
				} else if ("forget".equals(action)) {
					forget(tokens);	} 
				else if ("print".equals(action)) {
					System.out.println(ref2 != null ? ref2.toString() : "");
				} else if ("stop".equals(action)) {
					stopThread(tokens);
				} else if ("exit".equals(action)) {
					done = true;
				} else {
					printUsage();
				}
			} catch (Throwable e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		System.out.println("Okay, we're out! Thanks for playing!");
	}

	private static void store(String[] tokens) {
		int nb = tokens.length == 2 ? Integer.parseInt(tokens[1]) : 1;
		int size = nb * 1000;
		ref1 = new Thing(size);
		ref2 = ref1;
		System.out.println("Stored");
	}
	
	private static void forget(String[] tokens) {
		boolean forgetRef1 = tokens.length == 1 || "ref1".equals(tokens[1]) || "all".equals(tokens[1]);
		boolean forgetRef2 = tokens.length == 1 || "ref2".equals(tokens[1]) || "all".equals(tokens[1]);
		if (forgetRef1) {
			ref1 = null;
			System.out.println("Okay, ref1 totally forgot what it was!");
		}
		if (forgetRef2) {
			ref2 = null;
			System.out.println("Okay, ref2 totally forgot what it was!");
		}
	}

	private static void printUsage() {
		System.out.print("Here are orders you can give me:\n"
				+"\tstart threadName [durationInSeconds]\n"
				+"\tstop threadName\n"
				+"\tstop threadName\n"
				+"\tstore [howMuch]\n"
				+"\tforget [ref1 | ref2 | both]\n");
	}

	private static void startThread(String[] args) {
		String name = args[1];
		ensureThreadNotRunning(name);
		int sleepTimeSeconds = args.length == 3 ? Integer.parseInt(args[2]) : 10;
		int sleepTimeMs = sleepTimeSeconds * 1000;
		MyRunnable r = new MyRunnable(name, sleepTimeMs);
		Thread thread = new Thread(r, name);
		threads.put(name, thread);
		System.out.println("Starting thread " + name);
		thread.start();
	}

	private static void stopThread(String[] args) {
		String name = args[1];
		ensureThreadExists(name);
		Thread thread = threads.get(name);
		thread.interrupt();
		System.out.println("Stopping thread " + name);
		threads.remove(name);
	}

	private static void ensureThreadExists(String name) {
		if (!threads.containsKey(name)) {
			throw new RuntimeException("We don't have any thread called " + name + "!");
		}
	}

	private static void ensureThreadNotRunning(String name) {
		if (!threads.containsKey(name)) {
			return;
		}
		Thread thread = threads.get(name);
		if (thread.isAlive()) {
			throw new RuntimeException("We already have a thread called " + name + " currently running!");
		}

	}

	
}
