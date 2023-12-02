package com.example.pguti.spring.demo.services;

import com.example.pguti.spring.demo.entity.Flat;
import com.example.pguti.spring.demo.repository.FlatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlatService {
    @Value("${spring.datasource.url}")
    private String test;

    private final FlatsRepository flatsRepository;

    public void save(Flat flat) {
        flatsRepository.save(flat);
    }

    public List<Flat> getAll() {
        return flatsRepository.findAll();
    }

    public String testRetry2(boolean retry) {
        return testRetry(retry);
    }

    @Retryable(retryFor = IllegalArgumentException.class, maxAttempts = 3)
    public String testRetry(boolean retry) {
        if (retry) {
            throw new IllegalArgumentException("Земля плоская");
        }

        return "Земля круглая";
    }

    public Flat getFlat(Long id){
        return flatsRepository.findById(id).orElseThrow();
    }

    public Flat updateFlat(Flat flat){
        return flatsRepository.save(flat);
    }

    public void deleteFlat(Long id){
        flatsRepository.deleteById(id);
    }
}
