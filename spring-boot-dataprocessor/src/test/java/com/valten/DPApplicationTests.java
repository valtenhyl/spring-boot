package com.valten;

import com.valten.model.RailWayDocument;
import com.valten.repository.RailWayRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DPApplicationTests {

    @Autowired
    private RailWayRepository railWayRepository;

    @Test
    public void saveAll() {
        RailWayDocument r1 = new RailWayDocument();
        r1.setId("1");
        r1.setBoardTrainCode("BoardTrainCode1");
        r1.setCoachNo("CoachNo1");
        r1.setFromStationName("FromStationName1");
        r1.setToStationName("ToStationName1");

        RailWayDocument r2 = new RailWayDocument();
        r2.setId("2");
        r2.setBoardTrainCode("BoardTrainCode2");
        r2.setCoachNo("CoachNo2");
        r2.setFromStationName("FromStationName2");
        r2.setToStationName("ToStationName2");

        RailWayDocument r3 = new RailWayDocument();
        r3.setId("3");
        r3.setBoardTrainCode("BoardTrainCode3");
        r3.setCoachNo("CoachNo3");
        r3.setFromStationName("FromStationName3");
        r3.setToStationName("ToStationName3");

        List<RailWayDocument> list = new ArrayList<>();
        list.add(r1);
        list.add(r2);
        list.add(r3);

        railWayRepository.saveAll(list);
    }
}
