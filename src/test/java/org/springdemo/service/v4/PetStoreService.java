package org.springdemo.service.v4;

import org.springdemo.beans.factory.annotation.Autowired;
import org.springdemo.dao.v3.AccountDao;
import org.springdemo.dao.v3.ItemDao;
import org.springdemo.stereotype.Component;

@Component(value="petStore")

public class PetStoreService {
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private ItemDao  itemDao;
	
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}
	
	
}