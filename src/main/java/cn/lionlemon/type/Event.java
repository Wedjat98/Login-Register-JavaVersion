package cn.lionlemon.type;

import lombok.Data;

@Data
public class Event {
    private String id;
    private String title;
    private String description;
    private Double price;
    private String date;

}
