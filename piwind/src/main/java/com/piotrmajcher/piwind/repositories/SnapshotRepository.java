package com.piotrmajcher.piwind.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.piotrmajcher.piwind.domain.Snapshot;

public interface SnapshotRepository extends CrudRepository<Snapshot, Integer>{
	List<Snapshot> findByDateOrderByIdDesc(Date date);
}
