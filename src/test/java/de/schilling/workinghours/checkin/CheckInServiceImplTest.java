package de.schilling.workinghours.checkin;

import de.schilling.workinghours.duration.Duration;
import de.schilling.workinghours.duration.DurationService;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * David Schilling - davejs92@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class CheckInServiceImplTest {

    private CheckInServiceImpl sut;

    @Mock
    private CheckInRepository checkInRepositoryMock;
    @Mock
    private DurationService durationServiceMock;

    @Before
    public void setUp() {
        sut = new CheckInServiceImpl(checkInRepositoryMock, durationServiceMock);
    }

    @Test(expected = CheckInServiceException.class)
    public void getNotFound() {

        when(checkInRepositoryMock.findByUsername("foo")).thenReturn(null);

        sut.get("foo");
    }

    @Test
    public void get() {

        CheckIn checkIn = new CheckIn(LocalDateTime.now(), "foo");
        when(checkInRepositoryMock.findByUsername("foo")).thenReturn(checkIn);

        CheckIn result = sut.get("foo");

        assertThat(result, is(checkIn));
    }

    @Test
    public void checkIn() {

        CheckIn checkIn = new CheckIn(LocalDateTime.now(), "foo");
        sut.checkIn(checkIn);

        verify(checkInRepositoryMock).save(checkIn);

    }

    @Test
    public void checkOut() {
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        CheckIn checkIn = new CheckIn(LocalDateTime.now(), "foo");

        ArgumentCaptor<Duration> captor = ArgumentCaptor.forClass(Duration.class);

        when(checkInRepositoryMock.findByUsername("foo")).thenReturn(checkIn);
        sut.checkOut(new CheckOut("foo", endTime));

        verify(durationServiceMock).create(captor.capture());

        Duration value = captor.getValue();
        assertThat(value.getStartTime(), is(checkIn.getStartTime()));
        assertThat(value.getEndTime(), is(endTime));
        assertThat(value.getUsername(), is("foo"));

        verify(checkInRepositoryMock).delete(checkIn);
    }


}