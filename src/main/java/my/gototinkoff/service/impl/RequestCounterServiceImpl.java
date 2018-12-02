package my.gototinkoff.service.impl;

import my.gototinkoff.data.dao.CurrencyDao;
import my.gototinkoff.data.dao.RequestCounterDao;
import my.gototinkoff.data.entity.Currency;
import my.gototinkoff.data.entity.RequestResult;
import my.gototinkoff.pojo.ResultOperation;
import my.gototinkoff.pojo.StatResult;
import my.gototinkoff.service.RequestCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class RequestCounterServiceImpl implements RequestCounterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestCounterServiceImpl.class);

    private static final int SOME_ERROR_CODE = 1;
    public static final String SOME_ERROR_MESSAGE = "Some error";

    private final CurrencyDao currencyDao;
    private final RequestCounterDao requestCounterDao;

    @Autowired
    public RequestCounterServiceImpl(CurrencyDao currencyDao, RequestCounterDao requestCounterDao) {
        this.currencyDao = currencyDao;
        this.requestCounterDao = requestCounterDao;
    }

    @Override
    public ResultOperation stat() {
        LOGGER.trace("stat - start");
        try {
            StatResult statResult = new StatResult();
            statResult.setAllRequestCount(requestCounterDao.getAllCount());
            statResult.setSuccessRequestCount(requestCounterDao.getAllSuccessCount());
            List<Currency> currencies = currencyDao.getSavedCurrecies();
            Map<String, Long> successReuqestCntByCurrencies = new HashMap<>();
            currencies.stream()
                    .map(Currency::getCode)
                    .forEach(oneCode -> successReuqestCntByCurrencies.put(oneCode, requestCounterDao.getSuccessCountByCurrencyCode(oneCode)));
            statResult.setSuccessReuqestCntByCurrencies(successReuqestCntByCurrencies);
            LOGGER.debug("stat - statResult : {}", statResult);
            return ResultOperation.ok(statResult);
        } catch (Exception e) {
            LOGGER.error("stat - ERROR!", e);
            return ResultOperation.error(SOME_ERROR_CODE, SOME_ERROR_MESSAGE);
        }
    }

    @Override
    public ResultOperation action(String currencyCode) {
        LOGGER.trace("action - start currencyCode : {}", currencyCode);
        try {
            Currency currency = currencyDao.findCurrencyByCode(currencyCode);
            if (Objects.isNull(currency)) {
                LOGGER.debug("action - save new currency with code : {}", currencyCode);
                currency = saveNewCurrency(currencyCode);
            }

            // должно быть обращение к SOAP и получение результата

            saveNewRequestResult(currency, true); //true ставлю как заглушку
            return ResultOperation.ok(new Object());
        } catch (Exception e) {
            LOGGER.error("action - ERROR!", e);
            return ResultOperation.error(SOME_ERROR_CODE, SOME_ERROR_MESSAGE);
        }
    }

    private void saveNewRequestResult(Currency currency, boolean isSuccess) {
        LOGGER.trace("saveNewCurrency - start currency : {} isSuccess : {}", currency, isSuccess);
        RequestResult requestResult = new RequestResult();
        requestResult.setCurrency(currency);
        requestResult.setSuccess(isSuccess);
        requestCounterDao.save(requestResult);
    }

    private Currency saveNewCurrency(String currencyCode) {
        LOGGER.trace("saveNewCurrency - start currencyCode : {}", currencyCode);
        Currency currency = new Currency();
        currency.setCode(currencyCode);
        currencyDao.save(currency);
        return currencyDao.findCurrencyByCode(currencyCode);
    }
}
