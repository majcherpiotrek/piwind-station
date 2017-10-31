package com.piotrmajcher.piwind.repositories;

import com.piotrmajcher.piwind.domain.Snapshot;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface SnapshotRepository extends CrudRepository<Snapshot, Integer>{
	List<Snapshot> findByDateOrderByIdDesc(LocalDate date);
}
