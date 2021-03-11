package com.ceiba.dominio;

import java.util.Calendar;

public class Calculator {

    private Calculator(){
    }

    public static int calcularTotalHorasEnParqueadero(Calendar fechaIngreso, Calendar fechaSalida) {
        float horaEnMilisegundos = 3600000;
        long diferenciaEntreFechas = fechaSalida.getTimeInMillis() - fechaIngreso.getTimeInMillis();
        return (int) Math.ceil(diferenciaEntreFechas / horaEnMilisegundos);
    }

    public static int valorSubTotalDeParqueadero(Calendar fechaIngreso, Calendar fechaSalida, String tipoVehiculo) {
        int totalHorasEnParqueadero = calcularTotalHorasEnParqueadero(fechaIngreso, fechaSalida);
        int valorHoraVehiculo = Constants.HOUR_MOTORCYCLE;
        int valorDiaVehiculo = Constants.DAY_MOTORCYCLE;
        if (tipoVehiculo.equals("car")) {
            valorHoraVehiculo = Constants.HOUR_CAR;
            valorDiaVehiculo = Constants.DAY_CAR;
        }
        int valorSubTotal = 0;
        if (totalHorasEnParqueadero < 9) {
            valorSubTotal = valorHoraVehiculo * totalHorasEnParqueadero;
        } else if (totalHorasEnParqueadero < 25) {
            valorSubTotal = valorDiaVehiculo;
        } else {
            int diasEnParqueadero = totalHorasEnParqueadero % 24;
            if (diasEnParqueadero == 0) {
                valorSubTotal = (totalHorasEnParqueadero / 24) * valorDiaVehiculo;
            } else if (diasEnParqueadero < 9) {
                valorSubTotal += Math.floor(totalHorasEnParqueadero / 24f) * valorDiaVehiculo;
                valorSubTotal += diasEnParqueadero * valorHoraVehiculo;
            } else {
                valorSubTotal = (int) Math.ceil(totalHorasEnParqueadero / 24f) * valorDiaVehiculo;
            }
        }
        return valorSubTotal;
    }
}
