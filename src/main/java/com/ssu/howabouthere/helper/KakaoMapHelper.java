package com.ssu.howabouthere.helper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@PropertySource(value = "classpath:application.properties")
public class KakaoMapHelper {
    @Value("${kakao.rest.api.key}")
    private final String key = "KakaoAK 82539956587f4e4d0f90ccd3173513e3";

    public List<String> getDivisionByAxis(Double longitude, Double latitude) throws Exception {
        String x = String.format("%.6f", longitude);
        String y = String.format("%.6f", latitude);
        String url = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x="+x+"&y="+y;
        // rest
        RestTemplate restTemplate = new RestTemplate();
        // header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", key);

        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        List<String> location = parseLocationJson(response);

        return location;
    }

    @SuppressWarnings("unchecked")
    private List<String> parseLocationJson(HttpEntity<String> response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        List<String> locationName = new ArrayList<>();

        Map<String, Object> locationInfo = objectMapper.readValue(response.getBody(), Map.class);
        Map<String, Object> meta = (Map<String, Object>) locationInfo.get("meta");
        List<Map<String, Object>> documents = (List<Map<String, Object>>) locationInfo.get("documents");
        // 법정동 인덱스
        int indexOfH = 0;
        for(int i=0; i<(Integer)meta.get("total_count"); i++) {
            String region_type = (String) documents.get(i).get("region_type");
            if(region_type.equals("B")) indexOfH = i;
        }

        locationName.add((String)documents.get(indexOfH).get("region_1depth_name"));
        locationName.add((String)documents.get(indexOfH).get("region_2depth_name"));
        locationName.add((String)documents.get(indexOfH).get("region_3depth_name"));
        locationName.add((String)documents.get(indexOfH).get("region_4depth_name"));

        return locationName;
    }

    /*
    우선은 반경 1KM를 주변 동으로 본다.
    위도 1도당 110.850~110.941KM 이므로
    위도 0.01도당 1KM 정도이고,
    경도 1도당 96~91KM 이므로
    경도 0.01도당 900m 정도이다.
    즉, 주변의 위도 0.01도, 경도 0.01도 차이나는 곳까지 모두 주변 동으로 하도록 한다.
     */
    @SuppressWarnings("unchecked")
    public List<String> getNeighbors(Double Longitude, Double latitude) {
        List<String> neighbors = new ArrayList<>();
        return neighbors;
    }
}
