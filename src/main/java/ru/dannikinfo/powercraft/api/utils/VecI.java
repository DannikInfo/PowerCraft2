package ru.dannikinfo.powercraft.api.utils;

import net.minecraft.nbt.NBTTagCompound;
import ru.dannikinfo.powercraft.api.utils.interfaces.Vec;

public class VecI implements Vec<Integer, VecI> {
	
	public static final long serialVersionUID = 1745800961264333413L;
	
	public int x;
	public int y;
	public int z;
	
	public VecI() {
		this(0, 0, 0);
	}
	
	public VecI(int x) {
		this(x, 0, 0);
	}
	
	public VecI(int x, int y) {
		this(x, y, 0);
	}
	
	public VecI(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public VecI(Vec vec) {
		x = vec.getX().intValue();
		y = vec.getY().intValue();
		z = vec.getZ().intValue();
	}
	
	@Override
	public Integer getX() {
		return x;
	}
	
	@Override
	public Integer getY() {
		return y;
	}
	
	@Override
	public Integer getZ() {
		return z;
	}
	
	@Override
	public VecI setX(Number x) {
		this.x = x.intValue();
		return this;
	}
	
	@Override
	public VecI setY(Number y) {
		this.y = y.intValue();
		return this;
	}
	
	@Override
	public VecI setZ(Number z) {
		this.z = z.intValue();
		return this;
	}
	
	@Override
	public VecI setTo(Vec vec) {
		return setTo(vec.getX(), vec.getY(), vec.getZ());
	}
	
	@Override
	public VecI setTo(Number x, Number y, Number z) {
		this.x = x.intValue();
		this.y = y.intValue();
		this.z = z.intValue();
		return this;
	}
	
	@Override
	public VecI add(Vec vec) {
		return add(vec.getX(), vec.getY(), vec.getZ());
	}
	
	@Override
	public VecI add(Number n) {
		return add(n, n, n);
	}
	
	@Override
	public VecI add(Number x, Number y, Number z) {
		this.x += x.doubleValue();
		this.y += y.doubleValue();
		this.z += z.doubleValue();
		return this;
	}
	
	@Override
	public VecI offset(Vec vec) {
		return copy().add(vec);
	}
	
	@Override
	public VecI offset(Number n) {
		return copy().add(n);
	}
	
	@Override
	public VecI offset(Number x, Number y, Number z) {
		return copy().add(x, y, z);
	}
	
	@Override
	public VecI sub(Vec vec) {
		return sub(vec.getX(), vec.getY(), vec.getZ());
	}
	
	@Override
	public VecI sub(Number n) {
		return sub(n, n, n);
	}
	
	@Override
	public VecI sub(Number x, Number y, Number z) {
		this.x -= x.doubleValue();
		this.y -= y.doubleValue();
		this.z -= z.doubleValue();
		return this;
	}
	
	@Override
	public VecI mul(Vec vec) {
		return mul(vec.getX(), vec.getY(), vec.getZ());
	}
	
	@Override
	public VecI mul(Number n) {
		return mul(n, n, n);
	}
	
	@Override
	public VecI mul(Number x, Number y, Number z) {
		this.x = (int) (this.x * x.doubleValue());
		this.y = (int) (this.y * y.doubleValue());
		this.z = (int) (this.z * z.doubleValue());
		return this;
	}
	
	@Override
	public VecI div(Vec vec) {
		return div(vec.getX(), vec.getY(), vec.getZ());
	}
	
	@Override
	public VecI div(Number n) {
		return div(n, n, n);
	}
	
	@Override
	public VecI div(Number x, Number y, Number z) {
		this.x /= x.doubleValue();
		this.y /= y.doubleValue();
		this.z /= z.doubleValue();
		return this;
	}
	
	@Override
	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	@Override
	public double distanceTo(Vec vec) {
		return copy().sub(vec).length();
	}
	
	@Override
	public double distanceTo(Number x, Number y, Number z) {
		return copy().sub(x, y, z).length();
	}
	
	@Override
	public VecI normalize() {
		double length = length();
		x /= length;
		y /= length;
		z /= length;
		return this;
	}
	
	@Override
	public VecI clamp(Vec min, Vec max) {
		int minVal, maxVal;
		minVal = min.getX().intValue();
		maxVal = max.getX().intValue();
		if (minVal > maxVal) {
			if (x < minVal)
				x = minVal;
			else if (x > maxVal)
				x = maxVal;
		}
		minVal = min.getY().intValue();
		maxVal = max.getY().intValue();
		if (minVal > maxVal) {
			if (y < minVal)
				y = minVal;
			else if (y > maxVal)
				y = maxVal;
		}
		minVal = min.getZ().intValue();
		maxVal = max.getZ().intValue();
		if (minVal > maxVal) {
			if (z < minVal)
				z = minVal;
			else if (z > maxVal)
				z = maxVal;
		}
		return this;
	}
	
	@Override
	public VecI clamp(Vec min, Integer max) {
		return clamp(min, new VecI(max, max, max));
	}
	
	@Override
	public VecI clamp(Integer min, Vec max) {
		return clamp(new VecI(min, min, min), max);
	}
	
	@Override
	public VecI clamp(Integer min, Integer max) {
		return clamp(new VecI(min, min, min), new VecI(max, max, max));
	}
	
	@Override
	public VecI max(Vec max) {
		int maxVal;
		maxVal = max.getX().intValue();
		if (x > maxVal)
			x = maxVal;
		maxVal = max.getY().intValue();
		if (y > maxVal)
			y = maxVal;
		maxVal = max.getZ().intValue();
		if (z > maxVal)
			z = maxVal;
		return this;
	}
	
	@Override
	public VecI max(Integer max) {
		if (x > max)
			x = max;
		if (y > max)
			y = max;
		if (z > max)
			z = max;
		return this;
	}
	
	@Override
	public VecI min(Vec min) {
		int minVal;
		minVal = min.getX().intValue();
		if (x < minVal)
			x = minVal;
		minVal = min.getY().intValue();
		if (y < minVal)
			y = minVal;
		minVal = min.getZ().intValue();
		if (z < minVal)
			z = minVal;
		return this;
	}
	
	@Override
	public VecI min(Integer min) {
		if (x < min)
			x = min;
		if (y < min)
			y = min;
		if (z < min)
			z = min;
		return this;
	}
	
	@Override
	public VecI copy() {
		return new VecI(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Vec)) {
			return false;
		}
		Vec vec = (Vec) o;
		if (x != vec.getX().intValue())
			return false;
		if (y != vec.getY().intValue())
			return false;
		if (z != vec.getZ().intValue())
			return false;
		return true;
	}
	
	@Override
	public VecI readFromNBT(NBTTagCompound nbttag) {
		x = nbttag.getInteger("x");
		y = nbttag.getInteger("y");
		z = nbttag.getInteger("z");
		return this;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttag) {
		nbttag.setInteger("x", x);
		nbttag.setInteger("y", y);
		nbttag.setInteger("z", z);
		return nbttag;
	}
	
	@Override
	public String toString() {
		return "Vec[" + x + ", " + y + ", " + z + "]";
	}

	@Override
	public int hashCode() {
		return x+y*1000+z*1000000;
	}
	
}
