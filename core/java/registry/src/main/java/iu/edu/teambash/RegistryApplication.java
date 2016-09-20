package iu.edu.teambash;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import iu.edu.teambash.auth.UserAuthenticator;
import iu.edu.teambash.core.UsersEntity;
import iu.edu.teambash.db.UserDao;
import iu.edu.teambash.resources.DisplayData;
import iu.edu.teambash.resources.LoginResource;

public class RegistryApplication extends Application<RegistryConfiguration> {

    private final HibernateBundle<RegistryConfiguration> hibernateBundle =
            new HibernateBundle<RegistryConfiguration>(UsersEntity.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(RegistryConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    public static void main(final String[] args) throws Exception {
        new RegistryApplication().run(args);
    }

    @Override
    public String getName() {
        return "APIGateway";
    }

    @Override
    public void initialize(final Bootstrap<RegistryConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }


    @Override
    public void run(final RegistryConfiguration configuration,
                    final Environment environment) {
        final UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
        final LoginResource loginresource = new LoginResource();
        final DisplayData displayresource = new DisplayData(userDao);
        UserAuthenticator userAuthenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(UserAuthenticator.class, UserDao.class, userDao);

        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<UsersEntity>()
                .setAuthenticator(userAuthenticator)
                .setRealm("User Authenticator")
                .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(UsersEntity.class));
        environment.jersey().register(loginresource);
        environment.jersey().register(displayresource);
    }

}
