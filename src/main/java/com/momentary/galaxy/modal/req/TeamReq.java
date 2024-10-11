package com.momentary.galaxy.modal.req;

import java.util.List;

import com.momentary.galaxy.enity.Player;
import com.momentary.galaxy.modal.BaseRq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamReq extends BaseRq{

    String name;

    int teamNumbers;

    int courtCnt;
    
    List<PlayerReq> players;
}
