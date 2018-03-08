package ip2geo.main;

import ip2geo.ip2geo.IP2Geo;
import ip2geo.ip2geo.IP2GeoConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import ip2geo.ippubblico.IPPubblico;

public class IP2GeoApp extends Application<IP2GeoConfiguration> {
    
    public static void main(String[] args) throws Exception {
        new IP2GeoApp().run(args);
    }

    @Override
    public void run(IP2GeoConfiguration c, Environment e) throws Exception {
        // Registrazione IP2Geo
        final IP2Geo ip2geo = new IP2Geo(c.getDefaultValue());
        e.jersey().register(ip2geo);
        
        // Registrazione IPPubblico
        final IPPubblico ipPubblico = new IPPubblico();
        e.jersey().register(ipPubblico);
    }
    
}
