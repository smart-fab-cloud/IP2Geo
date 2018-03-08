package ip2geo.ip2geo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPInfo {

    private String ipAddress;
    private String countryCode;
    private String countryName;
    private String cityName;
    private String zipCode;
    private String latitude;
    private String longitude;

    public IPInfo() { 
        // Ci pensa Jackson
    }
    
    public IPInfo(
            String ipAddress,
            String countryCode,
            String countryName,
            String cityName,
            String zipCode,
            String latitude,
            String longitude
    ) {
        this.ipAddress = ipAddress;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.cityName = cityName;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
        
    }
    
    @JsonProperty
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @JsonProperty
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @JsonProperty
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @JsonProperty
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @JsonProperty
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonProperty
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
}
