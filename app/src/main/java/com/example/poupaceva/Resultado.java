package com.example.poupaceva;

import java.io.Serializable;

class Resultado implements Serializable {
    Float seiscentos;
    Float quatrocentos;
    Float trezentos;

    Resultado(Float seiscentos, Float quatrocentos, Float trezentos) {
        this.seiscentos = seiscentos;
        this.quatrocentos = quatrocentos;
        this.trezentos = trezentos;
    }
}
