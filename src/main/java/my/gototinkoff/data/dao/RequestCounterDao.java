package my.gototinkoff.data.dao;

import my.gototinkoff.data.entity.RequestResult;

public interface RequestCounterDao {

    /**
     * Save new result of request
     *
     * @param requestResult result request obj
     */
    public void save(RequestResult requestResult);

    /**
     * Method that calculate all success request by currency
     *
     * @param currencyCode currency code
     * @return success request cnt
     */
    public long getSuccessCountByCurrencyCode(String currencyCode);

    /**
     * Method that calculate all request
     *
     * @return request cnt
     */
    public long getAllCount();


    /**
     * Method that calculate all success request
     *
     * @return success request cnt
     */
    public long getAllSuccessCount();
}
