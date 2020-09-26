package com.example.ey_application.Model.Word;

import java.util.List;

public class Detail {
    private String type;
    private List<String> list;

    public Detail(String type, List<String> listDefinition) {
        this.type = type;
        this.list = listDefinition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> listDefinition) {
        this.list = listDefinition;
    }
}
