package com.momentary.galaxy.modal.req;

import java.util.List;

import com.momentary.galaxy.enity.Player;
import com.momentary.galaxy.modal.BaseRq;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TeamReq extends BaseRq{

    String name;
    
}
