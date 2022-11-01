import java.io.IOException;
import java.util.Properties;

public class ApplicationPropertiesProvider {
    private static final Properties applicationProperties;

    static {
        applicationProperties = new Properties();

        try(final var propertiesInputStream = ApplicationPropertiesProvider.class.getResourceAsStream("/application.properties")) {
            applicationProperties.load(propertiesInputStream);
        }
        catch(IOException ioe) {
            throw new ExceptionInInitializerError(ioe);
        }
    }

    private ApplicationPropertiesProvider() {
        throw new UnsupportedOperationException();
    }

    public static Properties getApplicationProperties() {
        return applicationProperties;
    }
}
