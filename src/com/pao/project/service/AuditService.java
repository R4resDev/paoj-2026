package com.pao.project.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class AuditService {
    private static AuditService instance;
    private static final String FILE_PATH = "audit.csv";

    private AuditService() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
                pw.println("nume_actiune,timestamp");
            } catch (IOException e) { 
                e.printStackTrace(); 
            }
        }
    }

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public synchronized void logAction(String actionName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            writer.println(actionName + "," + LocalDateTime.now());
        } catch (IOException e) {
            System.err.println("Eroare la scriere audit: " + e.getMessage());
        }
    }
}