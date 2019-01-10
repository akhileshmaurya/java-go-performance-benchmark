
package com.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
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
@BenchmarkMode({Mode.Throughput})
@State(Scope.Benchmark)
public class JMHLoops {
  private static final int THREAD_COUNT = 16;
  private static final int ITERATION_COUNT = 5;
  private static final int WARMUP_COUNT = 3;
  private static final int FORK_COUNT = 5;
  private static final int SIZE_OF_ARRAY = 10000;
  private static List<String> strings = new ArrayList<String>(SIZE_OF_ARRAY);

  @Setup(Level.Trial)
  public void init() {
    for (int i = 0; i < SIZE_OF_ARRAY; i++)
      strings.add(RandomStringUtils.random(50, true, false));
  }

  @Benchmark
  @Fork(value = FORK_COUNT)
  @Warmup(iterations = WARMUP_COUNT)
  @Measurement(iterations = ITERATION_COUNT)
  @Threads(value = THREAD_COUNT)
  public void forEachString() {
    for (String str : strings) {
      str.length();
    }
  }

  @Benchmark
  @Fork(value = FORK_COUNT)
  @Warmup(iterations = WARMUP_COUNT)
  @Measurement(iterations = ITERATION_COUNT)
  @Threads(value = THREAD_COUNT)
  public void forLoop() {
    for (int i = 0; i < strings.size(); i++) {
      strings.get(i).length();
    }
  }

  @Benchmark
  @Fork(value = FORK_COUNT)
  @Warmup(iterations = WARMUP_COUNT)
  @Measurement(iterations = ITERATION_COUNT)
  @Threads(value = THREAD_COUNT)
  public void itratorLoop() {
    Iterator<String> itr = strings.iterator();
    while (itr.hasNext()) {
      itr.next().length();
    }
  }
}
