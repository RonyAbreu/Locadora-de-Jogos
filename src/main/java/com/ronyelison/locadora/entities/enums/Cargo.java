package com.ronyelison.locadora.entities.enums;

public enum Cargo {
    ADMIN("admin"),
    COMUM("comum");
    private String cargo;

    Cargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }
}
