package com.company;

import java.io.Serializable;

public class RegistroTesteo implements Serializable
{
        private int temperatura;
        private long dni;

        public RegistroTesteo(long dni,int temperatura)
        {
            setDni(dni);
            setTemperatura(temperatura);
        }

        public RegistroTesteo()
        {

        }

        //Getters y Setters

        public int getTemperatura() {
            return temperatura;
        }
        public void setTemperatura(int temperatura) {
            this.temperatura = temperatura;
        }
        public long getDni() {
            return dni;
        }
        public void setDni(long dni) {
            this.dni = dni;
        }

        public String toString()
        {
            String Mensaje="DNI: %s \nTEMPERATURA: %d";
            return String.format(Mensaje,getDni(),getTemperatura());
        }

    }


