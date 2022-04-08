package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepo;

	@Override
	public boolean saveContact(Contact contact) {
		contact.setActiveSw("Y");//Don't need to set while editing
		Contact save = contactRepo.save(contact);
		if (save.getContactId() != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Contact> getAllContacts() {
		Contact contact=new Contact();
		contact.setActiveSw("Y");
		return contactRepo.findAll(Example.of(contact));//Here is a chance for null pointer Exception
	}

	@Override
	public boolean deleteContactById(Integer contactId) {
		Optional<Contact> findContactById = contactRepo.findById(contactId);
		if (findContactById.isPresent()) {
			//contactRepo.deleteById(contactId);//this is hard delete,go for soft delete
			Contact contact=findContactById.get();//NoSuchElementException,if value is not present.
			contact.setActiveSw("N");
			contactRepo.save(contact);
			return true;
		
		}
		return false;
	}
}
