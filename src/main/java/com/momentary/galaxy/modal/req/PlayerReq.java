package com.momentary.galaxy.modal.req;

import com.momentary.galaxy.modal.BaseRq;
import com.momentary.galaxy.modal.BaseRs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerReq extends BaseRq{
    String name;
    Long teamId;
}
