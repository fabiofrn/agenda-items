package com.fabio.agenda.repository;

public enum VoteYesOrNo {
    YES("S", "Sim"),
    NO("N", "Não");

    private final String code;

    private final String description;

    VoteYesOrNo(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
