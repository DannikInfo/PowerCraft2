package ru.dannikinfo.powercraft.api.utils;

import net.minecraft.nbt.NBTTagCompound;
import ru.dannikinfo.powercraft.api.utils.interfaces.Vec;

public class VecF implements Vec<Float, VecF> {
	
	public static final long serialVersionUID = 1745800961264333414L;
	
	public float x;
	public float y;
	public float z;
	
	public VecF() {
		this(0.0f, 0.0f, 0.0f);
	}
	
	public VecF(float x) {
		this(x, 0.0f, 0.0f);
	}
	
	public VecF(float x, float y) {
		this(x, y, 0.0f);
	}
	
	public VecF(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public VecF(Vec vec) {
		x = vec.getX().floatValue();
		y = vec.getY().floatValue();
		z = vec.getZ().floatValue();
	}
	
	@Override
	public Float getX() {
		return x;
	}
	
	@Override
	public Float getY() {
		return y;
	}
	
	@Override
	public Float getZ() {
		return z;
	}
	
	@Override
	public VecF setX(Number x) {
		this.x = x.floatValue();
		return this;
	}
	
	@Override
	public VecF setY(Number y) {
		this.y = y.floatValue();
		return this;
	}
	
	@Override
	public VecF setZ(Number z) {
		this.z = z.floatValue();
		return this;
	}
	
	@Override
	public VecF setTo(Vec vec) {
		x = vec.getX().floatValue();
		y = vec.getY().floatValue();
		z = vec.getZ().floatValue();
		return this;
	}
	
	@Override
	public VecF setTo(Number x, Number y, Number z) {
		this.x = x.floatValue();
		this.y = y.floatValue();
		this.z = z.floatValue();
		return this;
	}
	
	@Override
	public VecF add(Vec vec) {
		return add(vec.getX(), vec.getY(), vec.getZ());
	}
	
	@Override
	public VecF add(Number n) {
		return add(n, n, n);
	}
	
	@Override
	public VecF add(Number x, Number y, Number z) {
		this.x += x.doubleValue();
		this.y += y.doubleValue();
		this.z += z.doubleValue();
		return this;
	}
	
	@Override
	public VecF offset(Vec vec) {
		return copy().add(vec);
	}
	
	@Override
	public VecF offset(Number n) {
		return copy().add(n);
	}
	
	@Override
	public VecF offset(Number x, Number y, Number z) {
		return copy().add(x, y, z);
	}
	
	@Override
	public VecF sub(Vec vec) {
		return sub(vec.getX(), vec.getY(), vec.getZ());
	}
	
	@Override
	public VecF sub(Number n) {
		return sub(n, n, n);
	}
	
	@Override
	public VecF sub(Number x, Number y, Number z) {
		this.x -= x.doubleValue();
		this.y -= y.doubleValue();
		this.z -= z.doubleValue();
		return this;
	}
	
	@Override
	public VecF mul(Vec vec) {
		return mul(vec.getX(), vec.getY(), vec.getZ());
	}
	
	@Override
	public VecF mul(Number n) {
		return mul(n, n, n);
	}
	
	@Override
	public VecF mul(Number x, Number y, Number z) {
		this.x *= x.doubleValue();
		this.y *= y.doubleValue();
		this.z *= z.doubleValue();
		return this;
	}
	
	@Override
	public VecF div(Vec vec) {
		return div(vec.getX(), vec.getY(), vec.getZ());
	}
	
	@Override
	public VecF div(Number n) {
		return div(n, n, n);
	}
	
	@Override
	public VecF div(Number x, Number y, Number z) {
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
	public VecF normalize() {
		double length = length();
		x /= length;
		y /= length;
		z /= length;
		return this;
	}
	
	@Override
	public VecF clamp(Vec min, Vec max) {
		float minVal, maxVal;
		minVal = min.getX().floatValue();
		maxVal = max.getX().floatValue();
		if (minVal > maxVal) {
			if (x < minVal)
				x = minVal;
			else if (x > maxVal)
				x = maxVal;
		}
		minVal = min.getY().floatValue();
		maxVal = max.getY().floatValue();
		if (minVal > maxVal) {
			if (y < minVal)
				y = minVal;
			else if (y > maxVal)
				y = maxVal;
		}
		minVal = min.getZ().floatValue();
		maxVal = max.getZ().floatValue();
		if (minVal > maxVal) {
			if (z < minVal)
				z = minVal;
			else if (z > maxVal)
				z = maxVal;
		}
		return this;
	}
	
	@Override
	public VecF clamp(Vec min, Float max) {
		return clamp(min, new VecF(max, max, max));
	}
	
	@Override
	public VecF clamp(Float min, Vec max) {
		return clamp(new VecF(min, min, min), max);
	}
	
	@Override
	public VecF clamp(Float min, Float max) {
		return clamp(new VecF(min, min, min), new VecF(max, max, max));
	}
	
	@Override
	public VecF max(Vec max) {
		float maxVal;
		maxVal = max.getX().floatValue();
		if (x > maxVal)
			x = maxVal;
		maxVal = max.getY().floatValue();
		if (y > maxVal)
			y = maxVal;
		maxVal = max.getZ().floatValue();
		if (z > maxVal)
			z = maxVal;
		return this;
	}
	
	@Override
	public VecF max(Float max) {
		if (x > max)
			x = max;
		if (y > max)
			y = max;
		if (z > max)
			z = max;
		return this;
	}
	
	@Override
	public VecF min(Vec min) {
		float minVal;
		minVal = min.getX().floatValue();
		if (x < minVal)
			x = minVal;
		minVal = min.getY().floatValue();
		if (y < minVal)
			y = minVal;
		minVal = min.getZ().floatValue();
		if (z < minVal)
			z = minVal;
		return this;
	}
	
	@Override
	public VecF min(Float min) {
		if (x < min)
			x = min;
		if (y < min)
			y = min;
		if (z < min)
			z = min;
		return this;
	}
	
	@Override
	public VecF copy() {
		return new VecF(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Vec)) {
			return false;
		}
		Vec vec = (Vec) o;
		if (x != vec.getX().floatValue())
			return false;
		if (y != vec.getY().floatValue())
			return false;
		if (z != vec.getZ().floatValue())
			return false;
		return true;
	}
	
	@Override
	public VecF readFromNBT(NBTTagCompound nbttag) {
		x = nbttag.getFloat("x");
		y = nbttag.getFloat("y");
		z = nbttag.getFloat("z");
		return this;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttag) {
		nbttag.setFloat("x", x);
		nbttag.setFloat("y", y);
		nbttag.setFloat("z", z);
		return nbttag;
	}
}
