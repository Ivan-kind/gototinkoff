package my.gototinkoff.controller;

import my.gototinkoff.pojo.ResultOperation;
import my.gototinkoff.service.RequestCounterService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RequestCounterControllerTest {

    private RequestCounterService requestCounterService;
    private RequestCounterController requestCounterController;

    private final String errorCurrencyCode = "errCode";
    private final String okCurrencyCode = "okCode";
    private final int errCode = 1;
    private final String errMessage = "errMsg";
    private final String okResult = "ok";

    @Before
    public void init() {
        requestCounterService = Mockito.mock(RequestCounterService.class);
        Mockito.when(requestCounterService.action(Mockito.eq(errorCurrencyCode))).thenReturn(ResultOperation.error(errCode, errMessage));
        Mockito.when(requestCounterService.action(Mockito.eq(okCurrencyCode))).thenReturn(ResultOperation.ok(okResult));
        Mockito.when(requestCounterService.stat()).thenReturn(ResultOperation.ok(okResult));
        requestCounterController = new RequestCounterController(requestCounterService);
    }

    @Test
    public void statTest() {
        Assert.assertEquals(ResultOperation.ok(okResult), requestCounterController.stat());
        Mockito.verify(requestCounterService, Mockito.only()).stat();
    }

    @Test
    public void actionTest() {
        Assert.assertEquals(ResultOperation.ok(okResult), requestCounterController.action(okCurrencyCode));
        Mockito.verify(requestCounterService, Mockito.times(1)).action(Mockito.eq(okCurrencyCode));
        Assert.assertEquals(ResultOperation.error(errCode, errMessage), requestCounterController.action(errorCurrencyCode));
        Mockito.verify(requestCounterService, Mockito.times(1)).action(Mockito.eq(errorCurrencyCode));
    }
}
