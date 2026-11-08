package jsp.springboot.springboot_demo_curd;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import jsp.sprinigsecurity.exception.IdNotFoundException;
//import jsp.sprinigsecurity.exception.NoRecordAvailableException;

@RestController
public class ShipmentController {
	
	@Autowired
	private ShipmentRepository shipmentRepository;
	private CrudRepository<Shipment, Integer> shipRepository;
	
	@PostMapping("/ship")
	public ResponseEntity<ResponseStructure<Shipment>> saveShipment(@RequestBody Shipment ship) {
		
		Shipment s = shipmentRepository.save(ship);
		
		ResponseStructure<Shipment> res = new ResponseStructure<Shipment>();
		res.setStatusCode(201);
		res.setMessage("Shipment saved successfully");
		res.setData(s);
		
		return new ResponseEntity<ResponseStructure<Shipment>>(res, HttpStatus.CREATED);
	}
	
	@PostMapping("/ship/all")
	public ResponseEntity<ResponseStructure<List<Shipment>>> saveAllShipment(@RequestBody List<Shipment> books) {
		List<Shipment> b1 = shipmentRepository.saveAll(books);
		
		ResponseStructure<List<Shipment>> res = new ResponseStructure<>();
		res.setStatusCode(201);
		res.setMessage("Book saved successfully");
		res.setData(shipmentRepository.saveAll(books));
		return new ResponseEntity<ResponseStructure<List<Shipment>>>(res, HttpStatus.CREATED);
	}
	
	@GetMapping("/ship/{id}")
	public ResponseEntity<ResponseStructure<Shipment>> getById(@PathVariable Integer id) {
		
		Optional<Shipment> opt=shipmentRepository.findById(id);

		ResponseStructure<Shipment> res = new ResponseStructure<>();
		if(opt.isPresent()) {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("All records fetched");
			res.setData(opt.get());
			return new ResponseEntity<ResponseStructure<Shipment>>(res, HttpStatus.OK);
		
		}
			else {
				throw new IdNotFoundException("Id doesn't exists..!");
			}
}
	//Fetching the All Records Done.
	@GetMapping("/ship")
	public ResponseEntity<ResponseStructure<List<Shipment>>> getAllShipment() {
		List<Shipment> li = shipmentRepository.findAll();
		
		ResponseStructure<List<Shipment>> res = new ResponseStructure<>();
		if(li.isEmpty()) {
			throw new NoRecordAvailableException("Record deosn't exists..!");
	}
		else {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("All records fetched");
			res.setData(li);
			return new ResponseEntity<ResponseStructure<List<Shipment>>>(res, HttpStatus.CREATED);
			
			}
			
		}
	
	//Delete a Shipment Record
		@DeleteMapping("/ship/{id}")
		public ResponseEntity<ResponseStructure<Shipment>> DeleteRec(@PathVariable Integer id) {
			Optional<Shipment> opt=shipmentRepository.findById(id);
			
			ResponseStructure<Shipment> res = new ResponseStructure<>();
			if(opt.isPresent()) {
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Book record Deleted based on ID");
				shipmentRepository.delete(opt.get());
				return new ResponseEntity<ResponseStructure<Shipment>>(res, HttpStatus.CREATED);
			}
				else {
					res.setStatusCode(HttpStatus.NOT_FOUND.value());
					res.setMessage("Record Not Found");
					return new ResponseEntity<ResponseStructure<Shipment>>(res, HttpStatus.NOT_FOUND);
				}
		}
		
		@DeleteMapping("/ship")
		public ResponseEntity<ResponseStructure<Shipment>> deleteShipmentAll() {
			
			ResponseStructure<Shipment> res=new ResponseStructure<Shipment>();
			
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("all book deleted successfully");
				shipmentRepository.deleteAll();
				return new ResponseEntity<ResponseStructure<Shipment>>(res, HttpStatus.OK);
			
		}
		
		//update a record(complete updation)
		//method--> T save(T ref)
		@PutMapping("/ship")
		public ResponseEntity<ResponseStructure<Shipment>> updateShipment(@RequestBody Shipment ship) {
			ResponseStructure<Shipment> res=new ResponseStructure<Shipment>();
			
			// Case 1
			if (ship.getId() == null) {
				res.setStatusCode(HttpStatus.NOT_FOUND.value());
				res.setMessage("Id not Provided");
				res.setData(ship);
				return new ResponseEntity<ResponseStructure<Shipment>>(res, HttpStatus.NOT_FOUND);
			}

			Optional<Shipment> opt = shipmentRepository.findById(ship.getId());

			// case 2
			if (opt.isPresent()) {
				shipmentRepository.save(ship);
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Id found and Record has been updated.");
				res.setData(ship);
				return new ResponseEntity<ResponseStructure<Shipment>>(res, HttpStatus.OK);
			}
			// case 3
			else {
				res.setStatusCode(HttpStatus.NOT_FOUND.value());
				res.setMessage("Id not found in DB");
				return new ResponseEntity<ResponseStructure<Shipment>>(res, HttpStatus.NOT_FOUND);
			}
		
		}
		
		@PatchMapping("/ship/{id}")
		public ResponseEntity<ResponseStructure<Shipment>> updateBook(@PathVariable Integer id, @RequestBody Map<String, Object> data) {
			
			
			Optional<Shipment> opt = shipmentRepository.findById(id);
			ResponseStructure<Shipment> res = new ResponseStructure<Shipment>();
			
			if(opt.isPresent()) {
				Shipment ship = opt.get();
				
				
				for(Map.Entry<String, Object> entry: data.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					
					switch(key) {
					case "trackingNumber" : 
						ship.setTrackingNumber((Integer)value);
						res.setStatusCode(HttpStatus.OK.value());
						res.setMessage("Record found and updated..");
						res.setData(ship);
						break;
					case "origin" :
						ship.setOrigin((String)value);
						res.setStatusCode(HttpStatus.OK.value());
						res.setMessage("Record found and updated..");
						res.setData(ship);
						break;
					
					case "destination" :
						ship.setDestination((String)value);
						res.setStatusCode(HttpStatus.OK.value());
						res.setMessage("Record found and updated..");
						res.setData(ship);
						break;
					}
				}
				shipmentRepository.save(ship);
				return new ResponseEntity<ResponseStructure<Shipment>>(res, HttpStatus.FOUND);
			}
			else {
				    res.setStatusCode(HttpStatus.NOT_FOUND.value());
				    res.setMessage("No such ID found");
				    return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
				}
		}
	
	
}
