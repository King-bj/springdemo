package org.springdemo.dao.v2;

public class AccountDao {
    private ItemDao itemDao;
    public ItemDao getItemDao() {
        return itemDao;
    }
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}
