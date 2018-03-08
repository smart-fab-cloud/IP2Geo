package ip2geo.ippubblico;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPPubblicoInfo {
    
    private boolean pubblico;
    
    public IPPubblicoInfo() { 
        // Ci pensa Jackson
    }
    
    public IPPubblicoInfo(boolean pubblico) {
        this.pubblico = pubblico;
    }
    
    @JsonProperty
    public boolean getPubblico() {
        return pubblico;
    }
}
