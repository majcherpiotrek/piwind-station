package com.piotrmajcher.piwind.repositories;

import com.piotrmajcher.piwind.domain.Snapshot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SnapshotRepository extends CrudRepository<Snapshot, Integer>{

	@Query("SELECT snap FROM Snapshot snap WHERE snap.dateTime = (SELECT MAX(snap2.dateTime) FROM Snapshot snap2 WHERE snap2.date = :localDate)")
	Snapshot findLastSnapshotFromDate(@Param("localDate") LocalDate localDate);
}
