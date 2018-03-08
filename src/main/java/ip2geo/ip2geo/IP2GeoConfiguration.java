package ip2geo.ip2geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class IP2GeoConfiguration extends Configuration {
    
    @NotEmpty
    private String defaultValue;
    
    @JsonProperty
    public String getDefaultValue() {
        return defaultValue;
    }
    
    @JsonProperty
    public void setDefaultValue() {
        this.defaultValue = defaultValue;
    }
    
}
