package com.ssu.howabouthere.helper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KakaoMapHelperTest {
    private KakaoMapHelper helper = new KakaoMapHelper();

    @Test
    void 카카오맵_api_테스트() throws Exception {
        //given
        Double x_1 = 127.1086228, y_1 = 37.4012191;
        Double x_2 = 127.1111111, y_2 = 37.3984851;
        Double x_3 = 126.90334266801297 , y_3 = 37.51726764037236;
        Double x_4 = 126.9561, y_4 = 37.505;
        Double x_5 = 126.9607, y_5 = 37.4882D;

        //when
        List<String> firstLocation = helper.getDivisionByAxis(x_1, y_1);
        List<String> secondLocation = helper.getDivisionByAxis(x_2, y_2);
        List<String> thirdLocation = helper.getDivisionByAxis(x_3, y_3);
        List<String> fourthLocation = helper.getDivisionByAxis(x_4, y_4);
        List<String> fifthLocation = helper.getDivisionByAxis(x_5, y_5);

        //then
        assertEquals(firstLocation.get(2), secondLocation.get(2));
        assertEquals("영등포동4가", thirdLocation.get(2));
        System.out.println(fourthLocation);
        System.out.println(fifthLocation);
    }

    @Test
    void 주변동_찾기() {
        // given
        Double longitude = 126.9558, latitude = 37.497;

        // when
        List<String> neighbors = helper.getNeighbors(longitude, latitude);

        // then
        System.out.println(neighbors);
    }
}