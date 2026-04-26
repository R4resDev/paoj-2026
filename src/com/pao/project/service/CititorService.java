package com.pao.project.service;

import com.pao.project.exception.CititorNegasitException;
import com.pao.project.model.Cititor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CititorService {
    private static CititorService instance;
    private final Map<Integer, Cititor> cititori = new HashMap<>();

    private CititorService() {
    }

    public static CititorService getInstance() {
        if (instance == null) {
            instance = new CititorService();
        }
        return instance;
    }

    public void adaugaCititor(Cititor cititor) {
        cititori.put(cititor.getId(), cititor);
    }

    public void stergeCititor(int id) {
        cititori.remove(id);
    }

    public Cititor cautaDupaId(int id) {
        Cititor cititor = cititori.get(id);
        if (cititor == null) {
            throw new CititorNegasitException("Cititorul cu id=" + id + " nu a fost gasit");
        }
        return cititor;
    }

    public Cititor cautaDupaNume(String nume) {
        for (Cititor cititor : cititori.values()) {
            if (cititor.getNume().equalsIgnoreCase(nume)) {
                return cititor;
            }
        }
        throw new CititorNegasitException("Cititorul cu numele " + nume + " nu a fost gasit");
    }

    public List<Cititor> listeazaCititori() {
        return new ArrayList<>(cititori.values());
    }
}