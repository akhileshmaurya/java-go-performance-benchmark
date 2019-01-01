package com.app;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode({ Mode.Throughput })
@State(Scope.Benchmark)
public class JMHBatchSize {

	private List<String> list = new LinkedList<String>();

	@Benchmark
	@Warmup(iterations = 5, time = 1)
	@Measurement(iterations = 5, time = 1)
	@BenchmarkMode(Mode.AverageTime)
	public List<String> measureWrong_1() {
		list.add(list.size() / 2, "something");
		return list;
	}

	@Benchmark
	@Warmup(iterations = 5, time = 5)
	@Measurement(iterations = 5, time = 5)
	@BenchmarkMode(Mode.AverageTime)
	public List<String> measureWrong_5() {
		list.add(list.size() / 2, "something");
		return list;
	}

	@Benchmark
	@Warmup(iterations = 5, batchSize = 5000)
	@Measurement(iterations = 5, batchSize = 5000)
	@BenchmarkMode(Mode.SingleShotTime)
	public List<String> measureRight() {
		list.add(list.size() / 2, "something");
		return list;
	}

	@Setup(Level.Iteration)
	public void setup() {
		list.clear();
	}

}
