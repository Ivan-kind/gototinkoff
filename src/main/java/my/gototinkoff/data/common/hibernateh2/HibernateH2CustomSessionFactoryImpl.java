package my.gototinkoff.data.common.hibernateh2;

import my.gototinkoff.data.common.CustomSessionFactory;
import my.gototinkoff.data.common.hibernateh2.initializer.HibernateH2StorageInitializerImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.sql.SQLException;

public class HibernateH2CustomSessionFactoryImpl extends CustomSessionFactory {

    private final SessionFactory sessionFactory;

    public HibernateH2CustomSessionFactoryImpl(int maxConnectionPull) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        HibernateH2StorageInitializerImpl storageInitializer = new HibernateH2StorageInitializerImpl();
        storageInitializer.initStorage();
        Configuration configuration = storageInitializer.getConfiguration(maxConnectionPull);
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
                configuration.getProperties()).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public Session openSession() {
        return sessionFactory.openSession();
    }
}
