package com.app;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode({ Mode.Throughput })
@State(Scope.Benchmark)
public class JMHMapPutter {

	private Map<Integer, Integer> synchronizedMap;
	private Map<Integer, Integer> concurrentMap;
	private static final int N = 2_000_000;
	private static final int THREAD_COUNT = 16;
	private static final int ITERATION_COUNT = 3;
	private static final int WARMUP_COUNT = 3;
	private static final int FORK_COUNT = 2;

	@Setup(Level.Trial)
	public void init() {
		synchronizedMap = Collections.synchronizedMap(new HashMap<>(N));
		concurrentMap = new ConcurrentHashMap<>(N);
	}

	@Benchmark
	@Fork(value = FORK_COUNT)
	@Warmup(iterations = WARMUP_COUNT)
	@Measurement(iterations = ITERATION_COUNT)
	@Threads(value = THREAD_COUNT)
	public Integer syncPut() {
		int key = ThreadLocalRandom.current().nextInt(1, N);
		int value = ThreadLocalRandom.current().nextInt(1, N);
		Integer result = synchronizedMap.put(key, value);
		return result;
	}

	@Benchmark
	@Fork(value = FORK_COUNT)
	@Warmup(iterations = WARMUP_COUNT)
	@Measurement(iterations = ITERATION_COUNT)
	@Threads(value = THREAD_COUNT)
	public Integer conncurrentPut() {
		int key = ThreadLocalRandom.current().nextInt(1, N);
		int value = ThreadLocalRandom.current().nextInt(1, N);
		Integer result = concurrentMap.put(key, value);

		return result;
	}
}
