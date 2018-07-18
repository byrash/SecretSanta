package com.shivaji;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class MainTest {

  @Test
  void assignSantaSurprises() {

    Main main = new Main();

    final HashSet<String> names =
        new HashSet<>(
            Arrays.asList(
                "Aswath",
                "Shivaji",
                "Deepthi",
                "Mahesh",
                "Bhargavi",
                "Pavan",
                "Srinu",
                "Sirisha",
                "Xanda",
                "Maneeth",
                "Yash",
                "Daniel",
                "Sirat",
                "Heamnya",
                "Kiyan",
                "Victor",
                "Danzil"));

    IntStream.range(0, 100) // As using random try 100 times to assert its working in all cases
        .forEach(
            i -> {
              long start = System.nanoTime();
              Map<String, String> santaSurprises = main.assignSantaSurprises(names);
              long end = System.nanoTime();
              long seconds = TimeUnit.MICROSECONDS.convert((end - start), TimeUnit.NANOSECONDS);
              System.out.println("Took [ " + seconds + " ms ] to generate Santa Surprises");

              // Is all names assigned receivers
              assertThat(santaSurprises.size() == names.size());

              // A giver cant gift them self
              assertFalse(
                  santaSurprises
                      .entrySet()
                      .stream()
                      .anyMatch(entry -> entry.getKey().equals(entry.getValue())));

              Predicate<Entry<String, String>> isGiverAndReceiverCyclic =
                  entry ->
                      santaSurprises
                          .get(entry.getKey())
                          .equalsIgnoreCase(santaSurprises.get(entry.getValue()));

              // Giver and receiver cant be cyclic
              assertFalse(santaSurprises.entrySet().stream().anyMatch(isGiverAndReceiverCyclic));
            });
  }
}
