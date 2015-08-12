package de.schilling.workinghours.duration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * David Schilling- davejs92@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class DurationValidationServiceImplTest {

    @Mock
    private DurationRepository durationRepositoryMock;

    private DurationValidationServiceImpl sut;
    private static final String USERNAME = "username";

    @Before
    public void setUp() {
        sut = new DurationValidationServiceImpl(durationRepositoryMock);
    }

    @Test
    public void validateNewDurationStartsAtEndOfExisting() {
        LocalDateTime now = LocalDateTime.now();

        Duration existingDuration = new Duration(now.minusHours(2), now.minusHours(1), USERNAME);
        Duration newDuration = new Duration(now.minusHours(1), now, USERNAME);
        existingDuration.setId(1l);
        newDuration.setId(2l);
        when(durationRepositoryMock.findByUsernameOrderByStartTimeDesc(USERNAME)).thenReturn(singletonList(existingDuration));

        boolean isValid = sut.validateNewDuration(newDuration);

        assertThat(isValid, is(true));
    }

    @Test
    public void validateNewDurationStartsAfterEndOfExisting() {
        LocalDateTime now = LocalDateTime.now();

        Duration existingDuration = new Duration(now.minusHours(2), now.minusHours(1), USERNAME);
        Duration newDuration = new Duration(now.minusHours(1).plusMinutes(1), now, USERNAME);
        existingDuration.setId(1l);
        newDuration.setId(2l);
        when(durationRepositoryMock.findByUsernameOrderByStartTimeDesc(USERNAME)).thenReturn(singletonList(existingDuration));

        boolean isValid = sut.validateNewDuration(newDuration);

        assertThat(isValid, is(true));
    }

    @Test
    public void validateNewDurationEndsBeforeStartOfExisting() {
        LocalDateTime now = LocalDateTime.now();

        Duration existingDuration = new Duration(now.minusHours(2), now.minusHours(1), USERNAME);
        Duration newDuration = new Duration(now.minusHours(3), now.minusHours(2).minusMinutes(1), USERNAME);
        existingDuration.setId(1l);
        newDuration.setId(2l);
        when(durationRepositoryMock.findByUsernameOrderByStartTimeDesc(USERNAME)).thenReturn(singletonList(existingDuration));

        boolean isValid = sut.validateNewDuration(newDuration);

        assertThat(isValid, is(true));
    }

    @Test
    public void validateNewDurationEndsAtStartOfExisting() {
        LocalDateTime now = LocalDateTime.now();

        Duration existingDuration = new Duration(now.minusHours(2), now.minusHours(1), USERNAME);
        Duration newDuration = new Duration(now.minusHours(3), now.minusHours(2), USERNAME);
        existingDuration.setId(1l);
        newDuration.setId(2l);
        when(durationRepositoryMock.findByUsernameOrderByStartTimeDesc(USERNAME)).thenReturn(singletonList(existingDuration));

        boolean isValid = sut.validateNewDuration(newDuration);

        assertThat(isValid, is(false));
    }

    @Test
    public void validateNewDurationIsBetweenExisting() {
        LocalDateTime now = LocalDateTime.now();

        Duration existingDuration = new Duration(now.minusHours(2), now.plusHours(2), USERNAME);
        Duration newDuration = new Duration(now.minusHours(1), now.plusHours(1), USERNAME);
        existingDuration.setId(1l);
        newDuration.setId(2l);
        when(durationRepositoryMock.findByUsernameOrderByStartTimeDesc(USERNAME)).thenReturn(singletonList(existingDuration));

        boolean isValid = sut.validateNewDuration(newDuration);

        assertThat(isValid, is(false));
    }

    @Test
    public void validateNewDurationOverlapBeginOfExisting() {
        LocalDateTime now = LocalDateTime.now();

        Duration existingDuration = new Duration(now.minusHours(2), now.plusHours(2), USERNAME);
        Duration newDuration = new Duration(now.minusHours(3), now.minusHours(1), USERNAME);
        existingDuration.setId(1l);
        newDuration.setId(2l);
        when(durationRepositoryMock.findByUsernameOrderByStartTimeDesc(USERNAME)).thenReturn(singletonList(existingDuration));

        boolean isValid = sut.validateNewDuration(newDuration);

        assertThat(isValid, is(false));
    }

    @Test
    public void validateNewDurationOverlapEndOfExisting() {
        LocalDateTime now = LocalDateTime.now();

        Duration existingDuration = new Duration(now.minusHours(2), now.plusHours(2), USERNAME);
        Duration newDuration = new Duration(now.plusHours(1), now.plusHours(3), USERNAME);
        existingDuration.setId(1l);
        newDuration.setId(2l);
        when(durationRepositoryMock.findByUsernameOrderByStartTimeDesc(USERNAME)).thenReturn(singletonList(existingDuration));

        boolean isValid = sut.validateNewDuration(newDuration);

        assertThat(isValid, is(false));
    }
}