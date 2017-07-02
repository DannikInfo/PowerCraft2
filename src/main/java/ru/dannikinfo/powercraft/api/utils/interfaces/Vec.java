package ru.dannikinfo.powercraft.api.utils.interfaces;

import java.io.Serializable;


public interface Vec<t extends Number, ret extends Vec> extends INBT, Serializable {
	
	public t getX();
	
	public t getY();
	
	public t getZ();
	
	public ret setX(Number x);
	
	public ret setY(Number y);
	
	public ret setZ(Number z);
	
	public ret setTo(Vec vec);
	
	public ret setTo(Number x, Number y, Number z);
	
	public ret add(Vec vec);
	
	public ret add(Number n);
	
	public ret add(Number x, Number y, Number z);
	
	public ret offset(Vec vec);
	
	public ret offset(Number n);
	
	public ret offset(Number x, Number y, Number z);
	
	public ret sub(Vec vec);
	
	public ret sub(Number n);
	
	public ret sub(Number x, Number y, Number z);
	
	public ret mul(Vec vec);
	
	public ret mul(Number n);
	
	public ret mul(Number x, Number y, Number z);
	
	public ret div(Vec vec);
	
	public ret div(Number n);
	
	public ret div(Number x, Number y, Number z);
	
	public double length();
	
	public double distanceTo(Vec vec);
	
	public double distanceTo(Number x, Number y, Number z);
	
	public ret normalize();
	
	public ret clamp(Vec min, Vec max);
	
	public ret clamp(Vec min, t max);
	
	public ret clamp(t min, Vec max);
	
	public ret clamp(t min, t max);
	
	public ret max(Vec max);
	
	public ret max(t max);
	
	public ret min(Vec min);
	
	public ret min(t min);
	
	public ret copy();
	
}
