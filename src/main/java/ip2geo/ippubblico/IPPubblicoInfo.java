package ip2geo.ippubblico;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPPubblicoInfo {
    
    private String ip;
    private String tipologia;
    
    public IPPubblicoInfo() { 
        // Ci pensa Jackson
    }
    
    public IPPubblicoInfo(String ip, String tipologia) {
        this.ip = ip;
        this.tipologia = tipologia;
    }
    
    @JsonProperty
    public String getIp() {
        return tipologia;
    }
    
    @JsonProperty
    public String getTipologia() {
        return tipologia;
    }
}
