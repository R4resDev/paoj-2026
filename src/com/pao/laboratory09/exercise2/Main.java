package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "src/com/pao/laboratory09/exercise2/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip)
        // 2. Scrie toate înregistrările în OUTPUT_FILE cu DataOutputStream (format binar, RECORD_SIZE=32 bytes/înreg.)
        //    - bytes 0-3:   id (int, little-endian via ByteBuffer)
        //    - bytes 4-11:  suma (double, little-endian via ByteBuffer)
        //    - bytes 12-21: data (String, 10 chars ASCII, paddat cu spații la dreapta)
        //    - byte 22:     tip (0=CREDIT, 1=DEBIT)
        //    - byte 23:     status (0=PENDING, 1=PROCESSED, 2=REJECTED)
        //    - bytes 24-31: padding (zerouri)
        // 3. Procesează comenzile din stdin până la EOF cu RandomAccessFile:
        //    - READ idx       → seek(idx * RECORD_SIZE), citește și afișează înregistrarea
        //    - UPDATE idx ST  → seek(idx * RECORD_SIZE + 23), scrie noul status (0/1/2)
        //                       afișează "Updated [idx]: STATUS"
        //    - PRINT_ALL      → citește și afișează toate înregistrările
        //
        // Format linie output:
        //   [idx] id=<id> data=<data> tip=<CREDIT|DEBIT> suma=<suma:.2f> RON status=<STATUS>

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            for (int i = 0; i < n; i++) {
                int id = scanner.nextInt();
                double suma = scanner.nextDouble();
                String data = scanner.next();
                TipTranzactie tip = TipTranzactie.valueOf(scanner.next().toUpperCase());

                dos.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(id).array());
                dos.write(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(suma).array());
                dos.write(String.format("%-10s", data).getBytes());
                
                if (tip == TipTranzactie.CREDIT) {
                    dos.writeByte(0);
                } else {
                    dos.writeByte(1);
                }
                
                dos.writeByte(0);
                dos.write(new byte[8]);
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (scanner.hasNext()) {
                String comanda = scanner.next();

                if (comanda.equals("READ") || comanda.equals("PRINT_ALL")) {
                    int start;
                    int count;
                    
                    if (comanda.equals("READ")) {
                        start = scanner.nextInt();
                        count = 1;
                    } else {
                        start = 0;
                        count = n;
                    }

                    for (int i = start; i < start + count; i++) {
                        byte[] buffer = new byte[RECORD_SIZE];
                        raf.seek((long) i * RECORD_SIZE);
                        raf.readFully(buffer);

                        ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
                        int id = bb.getInt(0);
                        double suma = bb.getDouble(4);
                        String data = new String(buffer, 12, 10).trim();
                        
                        String tipStr;
                        if (buffer[22] == 0) {
                            tipStr = "CREDIT";
                        } else {
                            tipStr = "DEBIT";
                        }
                        
                        String statusStr = new String[]{"PENDING", "PROCESSED", "REJECTED"}[buffer[23]];

                        System.out.printf("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s\n", i, id, data, tipStr, suma, statusStr);
                    }
                } else if (comanda.equals("UPDATE")) {
                    int idx = scanner.nextInt();
                    String statusStr = scanner.next();
                    
                    int statusCode;
                    if (statusStr.equals("PENDING")) {
                        statusCode = 0;
                    } else if (statusStr.equals("PROCESSED")) {
                        statusCode = 1;
                    } else {
                        statusCode = 2;
                    }

                    raf.seek((long) idx * RECORD_SIZE + 23);
                    raf.write(statusCode);
                    System.out.println("Updated [" + idx + "]: " + statusStr);
                }
            }
        }
    }
}