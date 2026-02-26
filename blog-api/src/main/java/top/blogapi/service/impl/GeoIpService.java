package top.blogapi.service.impl;

import com.maxmind.geoip2.model.AsnResponse;
import com.maxmind.geoip2.model.CityResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.maxmind.geoip2.DatabaseReader;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeoIpService {
    DatabaseReader cityReader;
    DatabaseReader asnReader;
    @PostConstruct
    public void init() throws Exception {
        cityReader = buildReader("ipdb/City.mmdb");
        asnReader = buildReader("ipdb/ASN.mmdb");
        testConnection();
    }

    private DatabaseReader buildReader(String path) throws  IOException {
        try (InputStream stream = getClass()
                .getClassLoader()
                .getResourceAsStream(path)) {

            if (stream == null)
                throw new IllegalStateException("Database not found: " + path);


            return new DatabaseReader.Builder(stream).build();
        }
    }

    private void testConnection() throws Exception {
        CityResponse response = getCity("1.54.59.59");
        System.out.println("Country: " + response.country().name());
        System.out.println("Country: " + response.country().names());
        System.out.println("ISO Code: " + response.country().isoCode());
        System.out.println("City: " + response.city().name());
        System.out.println("City: " + response.city().names());
        System.out.println("Province: " + response.mostSpecificSubdivision().name());
        System.out.println("Province: " + response.mostSpecificSubdivision().names());
        System.out.println("Latitude: " + response.location().latitude());
        System.out.println("Longitude: " + response.location().longitude());
        System.out.println("ISP/ASN: (not in City DB)");
        log.info("Test -: {}:{}",response.country().name(),response.mostSpecificSubdivision().name());
    }

    public CityResponse getCity(String ip) throws Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        return cityReader.city(ipAddress);
    }

    public AsnResponse getAsn(String ip) throws Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        return asnReader.asn(ipAddress);
    }


    @PreDestroy
    public void close() throws Exception {
        if (cityReader != null) cityReader.close();
        if (asnReader != null) asnReader.close();
    }
}
