package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static final class Transaction {
        private final int id;
        private final BigDecimal amount;
        private final LocalDate date;
        private final String country;
        private final String channel;

        public Transaction(int id, BigDecimal amount, LocalDate date, String country, String channel) {
            this.id = id;
            this.amount = amount;
            this.date = date;
            this.country = country;
            this.channel = channel;
        }

        public int getId() { return id; }
        public BigDecimal getAmount() { return amount; }
        public LocalDate getDate() { return date; }
        public String getCountry() { return country; }
        public String getChannel() { return channel; }

        @Override
        public String toString() {
            return String.format("[Tx #%d] %s | %s | %s RON via %s", id, date, country, amount, channel);
        }
    }

    public static final class Snapshot {
        private final Map<String, Long> countByCountry;
        private final Map<String, Long> countByChannel;
        private final BigDecimal totalAmount;
        private final List<Transaction> topTransactions;

        public Snapshot(Map<String, Long> byCountry, Map<String, Long> byChannel, BigDecimal total, List<Transaction> top) {
            this.countByCountry = Collections.unmodifiableMap(new HashMap<>(byCountry));
            this.countByChannel = Collections.unmodifiableMap(new HashMap<>(byChannel));
            this.totalAmount = total;
            this.topTransactions = List.copyOf(top);
        }

        public Map<String, Long> getCountByCountry() { return countByCountry; }
        public Map<String, Long> getCountByChannel() { return countByChannel; }
        public BigDecimal getTotalAmount() { return totalAmount; }
        public List<Transaction> getTopTransactions() { return topTransactions; }
    }

    private static class Accumulator {
        final Map<String, Long> byCountry = new HashMap<>();
        final Map<String, Long> byChannel = new HashMap<>();
        BigDecimal total = BigDecimal.ZERO;
        final List<Transaction> list = new ArrayList<>();

        void accumulate(Transaction tx) {
            byCountry.put(tx.getCountry(), byCountry.getOrDefault(tx.getCountry(), 0L) + 1);
            byChannel.put(tx.getChannel(), byChannel.getOrDefault(tx.getChannel(), 0L) + 1);
            total = total.add(tx.getAmount());
            list.add(tx);
        }

        Accumulator combine(Accumulator other) {
            other.byCountry.forEach((k, v) -> this.byCountry.put(k, this.byCountry.getOrDefault(k, 0L) + v));
            other.byChannel.forEach((k, v) -> this.byChannel.put(k, this.byChannel.getOrDefault(k, 0L) + v));
            this.total = this.total.add(other.total);
            this.list.addAll(other.list);
            return this;
        }

        Snapshot finish(int topN) {
            List<Transaction> top = list.stream().sorted(Comparator.comparing(Transaction::getAmount).reversed().thenComparingInt(Transaction::getId)).limit(topN).collect(Collectors.toList());
            return new Snapshot(byCountry, byChannel, total, top);
        }
    }

    public static Collector<Transaction, ?, Snapshot> toSnapshot(int topN) {
        return Collector.of(Accumulator::new, Accumulator::accumulate, Accumulator::combine, agg -> agg.finish(topN));
    }

    public static void main(String[] args) {
        List<Transaction> data = Arrays.asList(
            new Transaction(1, new BigDecimal("1500.00"), LocalDate.of(2026, 5, 1), "RO", "WEB"),
            new Transaction(2, new BigDecimal("750.50"), LocalDate.of(2026, 5, 1), "RO", "ATM"),
            new Transaction(3, new BigDecimal("2300.00"), LocalDate.of(2026, 5, 2), "NL", "APP"),
            new Transaction(4, new BigDecimal("50.00"), LocalDate.of(2026, 5, 2), "DE", "WEB"),
            new Transaction(5, new BigDecimal("4100.00"), LocalDate.of(2026, 5, 3), "FR", "WEB"),
            new Transaction(6, new BigDecimal("750.50"), LocalDate.of(2026, 5, 3), "NL", "POS"),
            new Transaction(7, new BigDecimal("9900.00"), LocalDate.of(2026, 5, 4), "RO", "CRYPTO"),
            new Transaction(8, new BigDecimal("120.00"), LocalDate.of(2026, 5, 4), "DE", "POS"),
            new Transaction(9, new BigDecimal("500.00"), LocalDate.of(2026, 5, 5), "FR", "APP"),
            new Transaction(10, new BigDecimal("2300.00"), LocalDate.of(2026, 5, 5), "RO", "APP")
        );

        Snapshot snapshot = data.stream().collect(toSnapshot(3));

        snapshot.getTopTransactions().forEach(System.out::println);

        snapshot.getCountByCountry().entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).forEach(e -> System.out.println("Tara: " + e.getKey() + " -> " + e.getValue() + " tranzactii"));

        snapshot.getCountByChannel().entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).forEach(e -> System.out.println("Canal: " + e.getKey() + " -> " + e.getValue() + " operatiuni"));

        System.out.println("\nSuma totala: " + snapshot.getTotalAmount() + " RON");
    }
}