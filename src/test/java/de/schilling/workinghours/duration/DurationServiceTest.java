package de.schilling.workinghours.duration;

import de.schilling.workinghours.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DurationServiceTest {
    @Mock
    private DurationRepository durationRepositoryMock;
    @Mock
    private UserService userServiceMock;
    private DurationService sut;

    @Before
    public void setUp() throws Exception {
        sut = new DurationService(durationRepositoryMock, userServiceMock);
    }

    @Test
    public void testCalculateDurationSum() throws Exception {
        List<Duration> durationList = new ArrayList<>();
        durationList.add(new Duration(LocalDateTime.now(), LocalDateTime.now().plusMinutes(7), "Pustekuchen"));
        durationList.add(new Duration(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), "Pustekuchen"));
        durationList.add(new Duration(LocalDateTime.now(), LocalDateTime.now().plusMinutes(12), "Pustekuchen"));
        assertThat(sut.calculateDurationSum(durationList), is(20L));
    }
}