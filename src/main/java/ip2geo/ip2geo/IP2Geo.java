package ip2geo.ip2geo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("/ip2geo")
@Produces(MediaType.APPLICATION_JSON)
public class IP2Geo {
    
    private String defaultValue;
    private Map<String,IPInfo> ipMap;
    
    private JSONParser parser;
    private WebTarget ipPubblico;
    private WebTarget freeGeoIp;
    
    public IP2Geo(String defaultValue) {
        this.defaultValue = defaultValue;
        this.ipMap = new HashMap<String,IPInfo>();
        
        parser = new JSONParser();
        Client c = ClientBuilder.newClient();
        ipPubblico = c.target("http://localhost:56789/IPPubblico");
        freeGeoIp = c.target("https://freegeoip.net/json");
    }
    
    // TODO: POST 
    
    @GET
    @Path("/{ip}")
    public Response getIPInfo(
            @PathParam("ip") String ip,
            @QueryParam("from") Optional<String> from
    ) throws ParseException {
        // =================
        // CONTROLLI SU "ip"
        // =================
        
        // Verifica se l'IP è valido utilizzando il servizio "ipPubblico"
        Response rPub = ipPubblico.path(ip).request().get();
        
        // Se la risposta è diversa da "200 OK", significa che l'IP era 
        // non valido. Quindi restituisce direttamente la riposta ricevuta
        // (riportando l'errore al chiamante).
        if (rPub.getStatus() != Status.OK.getStatusCode())
            return rPub;

        // Altrimenti, recupera il dato che indica se l'IP sia pubblico
        JSONObject pub = (JSONObject) parser.parse(rPub.readEntity(String.class));
        String tipologia = (String) pub.get("tipologia");
        // Se l'IP non è pubblico, restituisce "400 Bad Request"
        if (!tipologia.equals("pubblico"))
            return Response.status(Status.BAD_REQUEST)
                    .entity("L'indirizzo IP deve essere pubblico")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        
        // ===================
        // CONTROLLI SU "from"
        // ===================
        
        // Se "from" non è indicato, restituisce "400 Bad Request"
        if(!from.isPresent())
            return Response.status(Status.BAD_REQUEST)
                    .entity("'from' deve essere indicato")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        
        // Se "from" è settato a valore sconosciuto, 
        // restituisce "400 Bad Request"
        if (!from.get().equals("local") && !from.get().equals("remote"))
            return Response.status(Status.BAD_REQUEST)
                    .entity("'from' deve essere 'local' o 'remote'")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        
        // =====================
        // ESAURIMENTO RICHIESTA
        // =====================
        
        // Inizializza la risposta con tutti "defaultValue"
        IPInfo info = new IPInfo(defaultValue,defaultValue,defaultValue,defaultValue,defaultValue,defaultValue,defaultValue);
        
        // Se "from" è "local", cerca la descrizione dell'IP 
        // indicato nella mappa "ipMap"
        if (from.get().equals("local")) {
            IPInfo localInfo = ipMap.get(ip);
            if(localInfo!=null) 
                info = localInfo; 
        }
        
        // Se "from" è "remote", richiede la decrizione dell'IP 
        // indicato a "freeGeoIp"
        if (from.get().equals("remote")) {
            Response rFreeGeoIp = freeGeoIp.path(ip).request().get();
            
            // Se la risposta è diversa da "200 OK", 
            // restituisce "500 Internal Server Error"
            if (rFreeGeoIp.getStatus() != Status.OK.getStatusCode())
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                            .entity("'FreeGeoIP' non raggiungibile.")
                            .type(MediaType.TEXT_PLAIN)
                            .build();
            
            // Altrimenti inserisce in "info" le informazioni
            // ricevute dal servizio "FreeGeoIP" (se non vuote)
            JSONObject remoteInfo = (JSONObject) parser.parse(rFreeGeoIp.readEntity(String.class));
            String ipAddress = (String) remoteInfo.get("ip");
            if (!ipAddress.isEmpty())
                info.setIpAddress(ipAddress);
            String countryCode = (String) remoteInfo.get("country_code");
            if (!countryCode.isEmpty())
                info.setCountryCode(countryCode);
            String countryName = (String) remoteInfo.get("country_name");
            if (!countryName.isEmpty())
                info.setCountryName(countryName);
            String cityName = (String) remoteInfo.get("city");
            if (!cityName.isEmpty())
                info.setCityName(cityName);
            String zipCode = (String) remoteInfo.get("zip_code");
            if (!zipCode.isEmpty())
                info.setZipCode(zipCode);
            String latitude = String.valueOf(
                    ((Double) remoteInfo.get("latitude")).doubleValue()
            );
            if (!latitude.isEmpty())
                info.setLatitude(latitude);
            String longitude = String.valueOf(
                    ((Double) remoteInfo.get("longitude")).doubleValue()
            );
            if (!longitude.isEmpty())
                info.setLongitude(longitude);
        }
        
        return Response.ok().entity(info).type(MediaType.APPLICATION_JSON).build();
    }
    
    // TODO: PUT 
    
    // TODO: DELETE
}
