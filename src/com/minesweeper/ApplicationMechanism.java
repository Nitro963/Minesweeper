package com.minesweeper;

import java.io.Serializable;

public class ApplicationMechanism implements Serializable{


	private static final long serialVersionUID = 7558619796743341723L;
	public static final ApplicationMechanism Flood = new ApplicationMechanism("Flood");
	public static final ApplicationMechanism Player = new ApplicationMechanism("Player");
	
	protected String mechanism;
	protected ApplicationMechanism(String mechanism) {
		super();
		this.mechanism = mechanism;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationMechanism other = (ApplicationMechanism) obj;
		if (mechanism == null) {
			if (other.mechanism != null)
				return false;
		} else if (!mechanism.equals(other.mechanism))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return mechanism;
	}
	
}
