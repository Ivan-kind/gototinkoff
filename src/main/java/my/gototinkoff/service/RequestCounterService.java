package my.gototinkoff.service;

import my.gototinkoff.pojo.ResultOperation;

public interface RequestCounterService {

    /**
     * Method that processed stat action
     *
     * @return result of stat operation
     */
    public ResultOperation stat();

    /**
     * Method that processed action operation
     *
     * @param currencyCode currency code for request to SOAP
     * @return result of operation (success or fail)
     */
    public ResultOperation action(String currencyCode);
}
