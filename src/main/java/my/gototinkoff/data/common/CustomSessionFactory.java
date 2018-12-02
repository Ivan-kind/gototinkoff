package my.gototinkoff.data.common;

import my.gototinkoff.data.common.hibernateh2.HibernateH2CustomSessionFactoryImpl;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.Objects;

/**
 * Some wrapper of SessionFactory
 * Somebody can add custom implementation of this interface and use it in EventService
 */
public abstract class CustomSessionFactory {

    public static final String HIBERNATE_H2_KEY = "hybernateH2";

    private static volatile CustomSessionFactory instance;

    /**
     * Method create new session
     *
     * @return new session
     */
    public abstract Session openSession();

    public static CustomSessionFactory getInstance(String key, int maxConnectionPull) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (Objects.isNull(instance)) {
            synchronized (CustomSessionFactory.class) {
                if (Objects.isNull(instance)) {
                    switch (key) {
                        default: instance = new HibernateH2CustomSessionFactoryImpl(maxConnectionPull); // default because we have only one implimentation

                    }
                }
            }
        }
        return instance;
    }


}
