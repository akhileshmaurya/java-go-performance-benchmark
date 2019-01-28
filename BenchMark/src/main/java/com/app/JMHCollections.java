
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
public class JMHCollections {
  private static final int THREAD_COUNT = 16;
  private static final int ITERATION_COUNT = 5;
  private static final int WARMUP_COUNT = 3;
  private static final int FORK_COUNT = 5;
  private int[] primitive = new int[2];
  private Integer[] wrapper = new Integer[2];

  @Setup(Level.Trial)
  public void init() {
    int first = ThreadLocalRandom.current().nextInt();
    int second = ThreadLocalRandom.current().nextInt();
    primitive[0] = first;
    primitive[1] = second;
    wrapper[0] = first;
    wrapper[1] = second;
  }

  @Benchmark
  @Fork(value = FORK_COUNT)
  @Warmup(iterations = WARMUP_COUNT)
  @Measurement(iterations = ITERATION_COUNT)
  @Threads(value = THREAD_COUNT)
  public int sumPrimitive() {
    return primitive[0] + primitive[1];
  }

  @Benchmark
  @Fork(value = FORK_COUNT)
  @Warmup(iterations = WARMUP_COUNT)
  @Measurement(iterations = ITERATION_COUNT)
  @Threads(value = THREAD_COUNT)
  public Integer sumWrapper() {
    return wrapper[0] + wrapper[1];

  }
}
