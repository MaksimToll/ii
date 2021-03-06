package org.ayfaar.app.dao.impl;

import org.ayfaar.app.dao.ItemDao;
import org.ayfaar.app.model.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.ayfaar.app.utils.RegExpUtils.W;
import static org.ayfaar.app.utils.RegExpUtils.w;

@SuppressWarnings("unchecked")
@Repository
public class ItemDaoImpl extends AbstractHibernateDAO<Item> implements ItemDao {
    public ItemDaoImpl() {
        super(Item.class);
    }

    @Override
    public Item getByNumber(String number) {
        return get("number", number);
    }

    @Override
    public List<Item> find(String query) {
	    query = query.toLowerCase().replaceAll("\\*", "["+w+"]*");
        return sqlQuery("SELECT * FROM item WHERE LOWER(content) REGEXP '("+ W +"|^)"
                    + query
                    + W + "'")
            .addEntity(Item.class)
            .setMaxResults(20)
            .list();
    }
}
