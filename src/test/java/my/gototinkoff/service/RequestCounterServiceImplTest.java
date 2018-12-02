package my.gototinkoff.service;

import my.gototinkoff.data.dao.CurrencyDao;
import my.gototinkoff.data.dao.RequestCounterDao;
import my.gototinkoff.data.entity.Currency;
import my.gototinkoff.pojo.ResultOperation;
import my.gototinkoff.pojo.StatResult;
import my.gototinkoff.service.impl.RequestCounterServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestCounterServiceImplTest {

    private RequestCounterService requestCounterService;
    private CurrencyDao currencyDao;
    private RequestCounterDao requestCounterDao;

    private final String existCurrencyCode = "existCurrency";
    private final String newCurrencyCode = "newCurrency";

    private final long allCountTest = 10L;
    private final long allSuccessCountTest = 5L;
    private final long existCurrSuccessTest = 2L;

    @Before
    public void init() {
        currencyDao = Mockito.mock(CurrencyDao.class);
        Currency existCurrency = new Currency();
        existCurrency.setCode(existCurrencyCode);
        existCurrency.setId(1L);
        Mockito.when(currencyDao.findCurrencyByCode(Mockito.eq(existCurrencyCode))).thenReturn(existCurrency);
        Mockito.when(currencyDao.findCurrencyByCode(Mockito.eq(newCurrencyCode))).thenReturn(null);
        List<Currency> existCurrencies = new ArrayList<>();
        existCurrencies.add(existCurrency);
        Mockito.when(currencyDao.getSavedCurrecies()).thenReturn(existCurrencies);

        requestCounterDao = Mockito.mock(RequestCounterDao.class);
        Mockito.when(requestCounterDao.getAllCount()).thenReturn(allCountTest);
        Mockito.when(requestCounterDao.getAllSuccessCount()).thenReturn(allSuccessCountTest);
        Mockito.when(requestCounterDao.getSuccessCountByCurrencyCode(Mockito.eq(existCurrencyCode))).thenReturn(existCurrSuccessTest);

        requestCounterService = new RequestCounterServiceImpl(currencyDao, requestCounterDao);
    }

    @Test
    public void statTest() {
        StatResult statResult = new StatResult();
        statResult.setSuccessRequestCount(allSuccessCountTest);
        statResult.setAllRequestCount(allCountTest);
        Map<String, Long> successReuqestCntByCurrencies = new HashMap<>();
        successReuqestCntByCurrencies.put(existCurrencyCode, existCurrSuccessTest);
        statResult.setSuccessReuqestCntByCurrencies(successReuqestCntByCurrencies);
        Assert.assertEquals(ResultOperation.ok(statResult), requestCounterService.stat());
        Mockito.verify(currencyDao, Mockito.only()).getSavedCurrecies();
    }

    @Test
    public void actionTest() {
        requestCounterService.action(existCurrencyCode);
        Mockito.verify(currencyDao, Mockito.times(1)).findCurrencyByCode(Mockito.eq(existCurrencyCode));

        requestCounterService.action(newCurrencyCode);
        Mockito.verify(currencyDao, Mockito.times(2)).findCurrencyByCode(Mockito.eq(newCurrencyCode));
        Currency newCurrency = new Currency();
        newCurrency.setCode(newCurrencyCode);
        Mockito.verify(currencyDao, Mockito.times(1)).save(Mockito.eq(newCurrency));
    }


}
