package ip2geo.ip2geo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/ip2geo")
@Produces(MediaType.APPLICATION_JSON)
public class IP2Geo {
    
    private String defaultValue;
    private List<IPInfo> ips;
    
    public IP2Geo(String defaultValue) {
        this.defaultValue = defaultValue;
        this.ips = new ArrayList<IPInfo>();
    }
    
    @GET
    @Path("/{ip}")
    public IPInfo getIPInfo(
            @PathParam("ip") String ip,
            @QueryParam("from") Optional<String> from
    ) {
        return new IPInfo(defaultValue,defaultValue,defaultValue,defaultValue,defaultValue,defaultValue,defaultValue);
    }
}
