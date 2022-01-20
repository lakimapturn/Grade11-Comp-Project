package com.example.CompProject.Repository;

import com.example.CompProject.Entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRespository extends JpaRepository<Train, Long> {
}
