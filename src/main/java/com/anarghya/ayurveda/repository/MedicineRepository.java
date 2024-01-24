package com.anarghya.ayurveda.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anarghya.ayurveda.model.Medicine;

/*
* @Repository: It is used to indicate that the class provides the mechanism
* for storage, retrieval, search, update and delete operation on objects.
*/
@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
	Optional<Medicine> findByName(String name);
	
    public Optional<Medicine> findByUserIdAndId(Long userId, Long id);
	
	public Optional<List<Medicine>> findByUserId(Long userId);

	boolean existsByuserIdAndId(Long userId, Long id);

	void deleteByUserIdAndId(Long userId, Long id);
	
	public Optional<Medicine> findByUserIdAndBatchCode(Long userId, String batchCode);
}
