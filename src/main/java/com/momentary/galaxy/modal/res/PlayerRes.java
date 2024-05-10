package com.momentary.galaxy.modal.res;

import com.momentary.galaxy.enity.Player;
import com.momentary.galaxy.modal.BaseRs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PlayerRes extends BaseRs{

    protected Player player;

}
