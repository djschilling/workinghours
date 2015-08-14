package de.schilling.workinghours.checkin;

/**
 * David Schilling - davejs92@gmail.com
 **/
public interface CheckInService {

    CheckIn get(String username);

    void checkIn(CheckIn checkIn);

    void checkOut(CheckOut checkOut);
}
