package com.momentary.galaxy.modal;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseRs implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String code;

	protected String msg;

	protected Object data;

}
