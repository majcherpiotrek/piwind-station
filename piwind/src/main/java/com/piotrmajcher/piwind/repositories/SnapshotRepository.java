package com.piotrmajcher.piwind.repositories;

import com.piotrmajcher.piwind.domain.Snapshot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SnapshotRepository extends CrudRepository<Snapshot, Integer>{

	@Query("SELECT snap FROM Snapshot snap WHERE snap.id = (SELECT MAX(snap2.id) FROM Snapshot snap2)")
	Snapshot findLastSnapshot();
}
