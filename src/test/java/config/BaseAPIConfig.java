package config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;

import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class BaseAPIConfig {
    public static Properties configProperties;
    private static URI baseURI = null;

    //load configuration properties in a static block
    static {
        InputStream isForConfig = null;
        try {
            configProperties = new Properties();
            isForConfig = ClassLoader.class.getResourceAsStream("/config.properties");
            configProperties.load( isForConfig );
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public static void setup() {

        String baseHost = System.getProperty( "server.host" );
        if ( baseHost == null || baseHost.isEmpty() ) {
            if ( (baseHost = configProperties.getProperty( "server.host" )) == null ) {
                baseHost = "http://localhost";
            }
        }

        try {
            baseURI = new URI( baseHost );
        } catch ( URISyntaxException e ) {
            e.printStackTrace();
        }

        System.out.println( ":::: SERVER PROPERTIES ::::" );
        System.out.println( "server.host: " + baseHost );

        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();

        //RestAssured settings
        RestAssured.baseURI = baseURI.toString();
        RestAssured.useRelaxedHTTPSValidation();

        RestAssured.authentication = authScheme;
        RequestSpecification reqSpec = new RequestSpecBuilder().setUrlEncodingEnabled(true).build();
        RestAssured.requestSpecification = reqSpec;

        RestAssured.config = RestAssured.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
                new Jackson2ObjectMapperFactory() {
                    @Override
                    public ObjectMapper create(Type arg0, String arg1) {
                        ObjectMapper objectMapper = new ObjectMapper()
                                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        return objectMapper;
                    }
                }
        ));
    }
}
