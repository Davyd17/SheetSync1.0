package com.demo.sheetsync.repository;

import com.demo.sheetsync.model.entity.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SheetRepository extends JpaRepository<Sheet, Integer> {


}
