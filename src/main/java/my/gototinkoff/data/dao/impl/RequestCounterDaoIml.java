package my.gototinkoff.data.dao.impl;

import my.gototinkoff.data.common.CustomSessionFactory;
import my.gototinkoff.data.dao.RequestCounterDao;
import my.gototinkoff.data.entity.RequestResult;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class RequestCounterDaoIml implements RequestCounterDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestCounterDaoIml.class);

    private final CustomSessionFactory customSessionFactory;

    public RequestCounterDaoIml(@Value("${data.maxConnectionPull:10}") int maxConnectionPull) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.customSessionFactory = CustomSessionFactory.getInstance(CustomSessionFactory.HIBERNATE_H2_KEY, maxConnectionPull);
    }

    public void save(RequestResult requestResult) {
        LOGGER.trace("save - start requestResult : {}", requestResult);
        if (Objects.isNull(requestResult)) {
            LOGGER.warn("Error on save requestResult! Object to save must not be null");
            throw new IllegalArgumentException("requestResult to save is null!");
        }

    }

    public long getSuccessCountByCurrencyCode(String currencyCode) {
        LOGGER.trace("getSuccessCountByCurrencyCode - start currencyCode : {}", currencyCode);
        Session session = customSessionFactory.openSession();
        List<RequestResult> requestResults = session.createQuery("select count(id) from ResultOperation where is_success = 1").list();
        long count = requestResults.stream()
                .filter(oneResult -> oneResult.getCurrency().getCode().equals(currencyCode))
                .count();
        LOGGER.debug("getSuccessCountByCurrencyCode - have {} success results with currency : {})", count, currencyCode);
        session.close();
        return count;
    }

    public long getAllCount() {
        LOGGER.trace("getAllCount - start");
        Session session = customSessionFactory.openSession();
        long count = (long) session.createQuery("select count(id) from ResultOperation").uniqueResult();
        LOGGER.debug("getAllCount - have {} results");
        session.close();
        return count;
    }

    public long getAllSuccessCount() {
        LOGGER.trace("getAllSuccessCount - start");
        Session session = customSessionFactory.openSession();
        long count = (long) session.createQuery("select count(id) from ResultOperation where is_success = 1").uniqueResult();
        LOGGER.debug("getAllSuccessCount - have {} success results");
        session.close();
        return count;
    }
}
