package com.freelkee.carmanager.response;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.freelkee.carmanager.service.ObjectCenterDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = ObjectCenterDeserializer.class)
public class ObjectCenter {

    private double lat;

    private double lon;

}
