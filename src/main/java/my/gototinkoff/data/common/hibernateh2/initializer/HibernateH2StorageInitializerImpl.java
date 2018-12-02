package my.gototinkoff.data.common.hibernateh2.initializer;

import my.gototinkoff.data.entity.Currency;
import my.gototinkoff.data.entity.RequestResult;
import my.gototinkoff.util.IOUtil;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class HibernateH2StorageInitializerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateH2StorageInitializerImpl.class);
    private static final String CREATE_DB_SQL_SCRIPT_NAME = "create_db.sql";
    private static final String CONNECT_PATH = "jdbc:h2:mem:testYandex;DB_CLOSE_DELAY=-1";
    private static final String H2_DRIVER = "org.h2.Driver";

    public void initStorage() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        LOGGER.trace("initStorage - start");
        try {
            Class.forName(H2_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(CONNECT_PATH);
            LOGGER.debug("initStorage - conn : {}", conn);
            String initialScript = IOUtil.getScriptByResource(this.getClass().getClassLoader().getResource(CREATE_DB_SQL_SCRIPT_NAME));
            if (Objects.isNull(initialScript)) throw new RuntimeException("Cannot read initial script!");
            Statement st = conn.createStatement();
            st.execute(initialScript);
        } catch (Exception e) {
            LOGGER.error("Error on init storage!", e);
            throw e;
        }
    }

    public Configuration getConfiguration(int maxConnectionPull) {
        LOGGER.trace("getConfiguration - start");
        return new Configuration()
                .setProperty( "hibernate.connection.driver_class",
                        "org.h2.Driver" )
                .setProperty( "hibernate.connection.url", "jdbc:h2:mem:testTinkoff" )
                .setProperty( "hibernate.connection.pool_size", String.valueOf(maxConnectionPull) )
                .setProperty( "hibernate.connection.autocommit", "false" )
                .setProperty( "hibernate.cache.provider_class",
                        "org.hibernate.cache.NoCacheProvider" )
                .setProperty( "hibernate.cache.use_second_level_cache",
                        "false" )
                .setProperty( "hibernate.cache.use_query_cache", "false" )
                .setProperty( "hibernate.dialect",
                        "org.hibernate.dialect.H2Dialect" )
                .setProperty( "hibernate.show_sql","true" )
                .setProperty( "hibernate.current_session_context_class",
                        "thread" )
                .addAnnotatedClass(Currency.class)
                .addAnnotatedClass(RequestResult.class);
    }
}
