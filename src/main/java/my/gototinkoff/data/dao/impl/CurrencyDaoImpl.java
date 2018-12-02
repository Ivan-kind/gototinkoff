package my.gototinkoff.data.dao.impl;

import my.gototinkoff.data.common.CustomSessionFactory;
import my.gototinkoff.data.dao.CurrencyDao;
import my.gototinkoff.data.entity.Currency;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class CurrencyDaoImpl implements CurrencyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyDaoImpl.class);

    private final CustomSessionFactory customSessionFactory;

    public CurrencyDaoImpl(@Value("${data.maxConnectionPull:10}") int maxConnectionPull) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.customSessionFactory = CustomSessionFactory.getInstance(CustomSessionFactory.HIBERNATE_H2_KEY, maxConnectionPull);
    }


    public void save(Currency currency) {
        LOGGER.trace("save - start currency : {}", currency);
        if (Objects.isNull(currency)) {
            LOGGER.warn("Error on save currency! Object to save must not be null");
            throw new IllegalArgumentException("currency to save is null!");
        }
    }

    public List<Currency> getSavedCurrecies() {
        LOGGER.trace("getSavedCurrencies - start");
        Session session = customSessionFactory.openSession();
        List<Currency> currencies = session.createSQLQuery("SELECT * FROM Currency").list();
        LOGGER.debug("getSavedCurrecies - currencies : {}", currencies);
        session.close();
        return currencies;
    }

    @Override
    public Currency findCurrencyByCode(String code) {
        LOGGER.trace("findCurrencyByCode - start code : {}", code);
        Session session = customSessionFactory.openSession();
        Query query = session.createQuery("select * from Currency where code>=:code " +
                "and created_at<=:timeTo and type=:someType");
        query.setString("code", code);
        List<Currency> currencies = query.list();
        LOGGER.debug("findCurrencyByCode - currencies : {}", currencies);
        session.close();
        return Objects.nonNull(currencies) && !currencies.isEmpty() ? currencies.get(0) : null;
    }
}
