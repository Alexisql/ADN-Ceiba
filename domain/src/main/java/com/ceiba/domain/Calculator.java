package com.ceiba.domain;

import java.util.Calendar;

public class Calculator {

    private Calculator(){
    }

    public static int calculateHoursInparking(Calendar dateEntry, Calendar dateExit) {
        float hourInMilliseconds = 3600000;
        long differenceDates = dateExit.getTimeInMillis() - dateEntry.getTimeInMillis();
        return (int) Math.ceil(differenceDates / hourInMilliseconds);
    }

    public static int valueSubTotalParkingVehicle(Calendar dateEntry, Calendar dateExit, String typeVehicle) {
        int hoursInParking = calculateHoursInparking(dateEntry, dateExit);
        int valueHourVehicle = Constants.HOUR_MOTORCYCLE;
        int valueDayVehicle = Constants.DAY_MOTORCYCLE;
        if (typeVehicle.equals("car")) {
            valueHourVehicle = Constants.HOUR_CAR;
            valueDayVehicle = Constants.DAY_CAR;
        }
        int valueSubTotal = 0;
        if (hoursInParking < 9) {
            valueSubTotal = valueHourVehicle * hoursInParking;
        } else if (hoursInParking < 25) {
            valueSubTotal = valueDayVehicle;
        } else {
            int diasParqueadero = hoursInParking % 24;
            if (diasParqueadero == 0) {
                valueSubTotal = (hoursInParking / 24) * valueDayVehicle;
            } else if (diasParqueadero < 9) {
                valueSubTotal += Math.floor(hoursInParking / 24f) * valueDayVehicle;
                valueSubTotal += diasParqueadero * valueHourVehicle;
            } else {
                valueSubTotal = (int) Math.ceil(hoursInParking / 24f) * valueDayVehicle;
            }
        }
        return valueSubTotal;
    }
}
