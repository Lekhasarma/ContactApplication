package com.example.demo.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.AppConstants;
import com.example.demo.entity.Contact;
import com.example.demo.properties.AppMessages;
import com.example.demo.service.ContactService;

@RestController
@CrossOrigin
public class ContactRestController {
	@Autowired
	private ContactService contactServiceObj;

	@Autowired
	private AppMessages appMessages;

	Map<String, String> messages;

	@PostMapping(value = "contact/savecontact", consumes = "application/json")
	public ResponseEntity<String> saveContact(@RequestBody Contact contact) {

		messages = appMessages.getMessages();
		boolean saveContact = contactServiceObj.saveContact(contact);
		if (saveContact) {
			return new ResponseEntity<>(messages.get(AppConstants.CONTACT_SAVE_SUCCESS), HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(messages.get(AppConstants.CONTACT_SAVE_FAILED),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value = "/contact/contacts", produces = "application/json")
	public ResponseEntity<List<Contact>> getContacts() {
		List<Contact> contacts = contactServiceObj.getAllContacts();
		return new ResponseEntity<>(contacts, HttpStatus.OK);
	}

	@PutMapping(value = "/contact/{contactId}", consumes = "application/json")
	public ResponseEntity<String> editContact(@PathVariable Integer contactId, @RequestBody Contact contact) {
		contact.setContactId(contactId);// ???
		contactServiceObj.saveContact(contact);
		return new ResponseEntity<>(messages.get(AppConstants.CONTACT_UPDATED), HttpStatus.OK);
	}

	@DeleteMapping("/contact/{contactId}")
	public ResponseEntity<String> deleteContact(@PathVariable Integer contactId) {
		boolean deleteContactById = contactServiceObj.deleteContactById(contactId);
		if (deleteContactById) {
			return new ResponseEntity<>(messages.get(AppConstants.CONTACT_DELT_SUCCESS), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(messages.get(AppConstants.CONTACT_DELT_FAILED),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
}
