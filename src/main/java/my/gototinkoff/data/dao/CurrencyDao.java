package my.gototinkoff.data.dao;

import my.gototinkoff.data.entity.Currency;

import java.util.List;

public interface CurrencyDao {

    /**
     * Save new currency in DB
     *
     * @param currency currency obj
     */
    public void save(Currency currency);

    /**
     * Method that find all saved currencies
     *
     * @return list of saved currencies
     */
    public List<Currency> getSavedCurrecies();

    /**
     * Method that find currency by code
     *
     * @param code currency code
     * @return currency code or null if it not exist
     */
    public Currency findCurrencyByCode(String code);
}
