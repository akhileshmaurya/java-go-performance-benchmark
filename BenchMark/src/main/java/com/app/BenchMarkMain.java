package com.app;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchMarkMain {
	public static void main(String[] args) throws RunnerException {
		//runBenchMark(JMHMapPutter.class.getName());
		runBenchMark(JMHOddNumber.class.getName());
	}

	private static void runBenchMark(String className) {
		try {
			Options opt = new OptionsBuilder().include(".*" + className + ".*").forks(1).build();
			new Runner(opt).run();
		} catch (Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
	}
}
