package my.gototinkoff.controller;

import my.gototinkoff.pojo.ResultOperation;
import my.gototinkoff.service.RequestCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tinkoff")
public class RequestCounterController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestCounterController.class);

    private final RequestCounterService requestCounterService;

    @Autowired
    public RequestCounterController(RequestCounterService requestCounterService) {
        this.requestCounterService = requestCounterService;
    }

    @PostMapping("/{currencyCode}")
    public ResultOperation action(@PathVariable("currencyCode") String currencyCode) {
        LOGGER.trace("action - start currencyCode : {}", currencyCode);
        ResultOperation result = requestCounterService.action(currencyCode);
        LOGGER.debug("action - result : {}", result);
        return result;
    }

    @GetMapping("/stat")
    public ResultOperation stat() {
        LOGGER.trace("stat - start");
        ResultOperation result = requestCounterService.stat();
        LOGGER.debug("stat+ - result : {}", result);
        return result;
    }
}
