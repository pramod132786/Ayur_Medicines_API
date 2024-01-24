package com.anarghya.ayurveda.services;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anarghya.ayurveda.exception.MedicineNotFoundException;
//import com.anarghya.ayurveda.model.Category;
import com.anarghya.ayurveda.model.Medicine;
//import com.anarghya.ayurveda.repository.CategoryRepository;
import com.anarghya.ayurveda.repository.MedicineRepository;
import com.anarghya.ayurveda.utils.EmailUtils;

/****************************
 * @Service: It is used with classes that provide some business functionalities.
 ****************************/
@Service
public class MedicineServicesImplementation implements MedicineServices {

	/****************************
	 * @Autowired: It allows Spring to resolve and inject collaborating beans into
	 *             our bean.
	 ****************************/
	@Autowired
	private MedicineRepository medicineRepository;
	
	@Autowired
	private EmailUtils mailUtils;

//	@Autowired
//	private CategoryRepository categoryRepository;

	@Override
	public List<Medicine> getAllMedicines() {
		return medicineRepository.findAll();
	}

	@Override
	public Medicine getMedicineById(Long id) {
		return medicineRepository.findById(id).orElse(null);
	}

	@Override
	public ResponseEntity<String> addMedicine(Medicine medicine) {

		if (medicineRepository.findByName(medicine.getName()).isPresent()) {
			return ResponseEntity.badRequest().body("Medicine is already present.");
		}
		medicineRepository.save(medicine);
		return ResponseEntity.ok("Medicine added successfully");
	}

	@Override
	public Medicine updateMedicine(Long id, Medicine updatedMedicine) {
		updatedMedicine.setId(id);
		return medicineRepository.save(updatedMedicine);
	}

	@Override
	public void deleteMedicine(Long id) {
		medicineRepository.deleteById(id);
	}

	@Override
	public Medicine getUserByIdAndMedicineId(Long userId, Long id) {

		Optional<Medicine> byUserIdAndMedicineId = medicineRepository.findByUserIdAndId(userId, id);
		if (byUserIdAndMedicineId.isEmpty()) {
			return byUserIdAndMedicineId.orElseThrow(() -> new MedicineNotFoundException("Medicine not Found"));
		}

		return byUserIdAndMedicineId.get();
	}

	@Override
	public ResponseEntity<Medicine> upsertMedicine(Long userId, Medicine medicine) {

		Optional<Medicine> medicineByUser = medicineRepository.findByUserIdAndId(userId, medicine.getId());

		if (medicineByUser.isPresent()) {

			medicine.setUserId(userId);
			medicine.setId(medicine.getId());
			return new ResponseEntity<>(medicineRepository.save(medicine), HttpStatus.OK);

		} else {
			Optional<Medicine> byUserIdAndBatchCode = medicineRepository.findByUserIdAndBatchCode(userId,
					medicine.getBatchCode());

			if (byUserIdAndBatchCode.isEmpty()) {
				medicine.setUserId(userId);
				Medicine medicineSaved = medicineRepository.save(medicine);
				return new ResponseEntity<>(medicineSaved, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(byUserIdAndBatchCode.get(), HttpStatus.FOUND);
			}
		}

	}

	@Override
	public List<Medicine> viewMedicineByUser(Long userId) {
		Optional<List<Medicine>> byUserId = medicineRepository.findByUserId(userId);
		if (byUserId.isPresent()) {
			List<Medicine> medicines = byUserId.get();
			return medicines;

		}
		return null;
	}

	@Override
	public String deleteMedicineByUser(Long userId, Long id) {
//		Optional<Medicine> findByUserIdAndId = medicineRepository.findByUserIdAndId(userId, id);
		boolean exists = medicineRepository.existsByuserIdAndId(userId, id);
		if (exists) {

			medicineRepository.deleteByUserIdAndId(userId, id);
			return "Medicine  was deleted";
		} else {
			return "Facing some issues on deleting Medicine May userId and Medicine was not Present";
		}

	}

	@Override
	public Medicine decreaseQuantity(Long id, int quantity) {
		Optional<Medicine> findById = medicineRepository.findById(id);
		Medicine medicine = findById.get();

		
		if (findById.isEmpty()) {
			return findById.orElseThrow(() -> new MedicineNotFoundException("Medicine not Found"));
		}
		
		if (medicine.getQuantity() > 1) {
			int quantitys = medicine.getQuantity();
			medicine.setQuantity(quantitys - quantity);
			Medicine save = medicineRepository.save(medicine);
		
			if(medicine.getQuantity()<=10) {
				
				String subject="This is regarding Stock decrease Notification Alert.";
				StringBuffer body = new StringBuffer("");

				body.append("<h4>\"Alert: The stock of "+save.getName() +" is reaching a critical level. It is recommended to place an order for more stock to ensure availability.\"</h4>");
				body.append("Medicine Id :: " + save.getId() + "<br>");
				body.append("Medicine Name :: " + save.getName() + "<br>");
				body.append("Medicine quantity :: " + save.getQuantity() + "<br>");
				body.append("Medicine batch :: " + save.getBatchCode() + "<br>");
				boolean sendMail = mailUtils.sendMail("pramodgoskula@gmail.com", subject, body.toString());
				if(sendMail) {
					return medicine;
				}else {
					return null;
				}
			}
			
		}
		return findById.orElseThrow(() -> new MedicineNotFoundException("Medicine Quantity not more than 1"));
	}

//	public List<Medicine> getMedicineByCategory(Long  id ) {
//		List<Medicine> matchingMedicines = new ArrayList<>();
//		Optional<Category> categoryOptional=categoryRepository.findById(id);
//		List<Medicine> medicines=medicineRepository.findAll();
//		Category category=categoryOptional.get();
//		for(Medicine medicine: medicines) {
//			if(category.getName().equals(medicine.getCategory())) {
//				matchingMedicines.add(medicine);
//			}
//		}
//
//        return matchingMedicines;
//	}

}
