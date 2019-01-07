
package com.app;

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

@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode({Mode.Throughput})
@State(Scope.Benchmark)
public class JMHOddNumber {
  private static final int START = Integer.MIN_VALUE;
  private static final int END = Integer.MAX_VALUE;
  private static final int THREAD_COUNT = 16;
  private static final int ITERATION_COUNT = 5;
  private static final int WARMUP_COUNT = 3;
  private static final int FORK_COUNT = 5;

  @Setup(Level.Trial)
  public void init() {

  }

  @Benchmark
  @Fork(value = FORK_COUNT)
  @Warmup(iterations = WARMUP_COUNT)
  @Measurement(iterations = ITERATION_COUNT)
  @Threads(value = THREAD_COUNT)
  public boolean isOddOne() {
    int num = ThreadLocalRandom.current().nextInt(START, END);
    return (num % 2) != 0;
  }

  @Benchmark
  @Fork(value = FORK_COUNT)
  @Warmup(iterations = WARMUP_COUNT)
  @Measurement(iterations = ITERATION_COUNT)
  @Threads(value = THREAD_COUNT)
  public boolean isOddTwo() {
    int num = ThreadLocalRandom.current().nextInt(START, END);
    return (num & 1) != 0;
  }
}
