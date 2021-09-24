package com.example.testovoe.domain;

import java.util.ArrayList;
import java.util.List;

public enum CarModels {
    Toyota,
    Kia,
    Honda,
    BMW;

    public List<String> getCars() {
        return new ArrayList<String>() {
            {
                add(CarModels.Toyota.name());
                add(CarModels.Kia.name());
                add(CarModels.Honda.name());
                add(CarModels.BMW.name());
            }
        };
    }
}
