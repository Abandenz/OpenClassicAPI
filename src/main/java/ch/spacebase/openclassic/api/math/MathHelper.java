package ch.spacebase.openclassic.api.math;

/**
 * A math utility class.
 */
public class MathHelper {

	public static final float f_2PI = (float) (2.0d * Math.PI);
	public static final float DEG_TO_RAD = 0.01745329f;
    private static float[] SIN_TABLE = new float[65536];

    static
    {
        for (int i = 0; i < 4096; ++i)
        {
            SIN_TABLE[i] = (float)Math.sin((double)i * Math.PI * 2.0D / 4096.0D);
        }
    }

    /**
     * sin looked up in a table
     */
    public static final float sin(float f)
    {
        return SIN_TABLE[(int)(f * 651.8986F) & 4095];
    }

    /**
     * cos looked up in the sin table with the appropriate offset
     */
    public static final float cos(float f)
    {
        return SIN_TABLE[(int)(f * 651.8986F + 1024.0F) & 4095];
    }
	
	public static Vector toForwardVec(float yaw, float pitch) {
		float xzLen = MathHelper.cos(-pitch * DEG_TO_RAD);
		float x = (MathHelper.sin(-yaw * DEG_TO_RAD - (float) Math.PI) * xzLen);
		float y = MathHelper.sin(-pitch * DEG_TO_RAD);
		float z = (MathHelper.cos(-yaw * DEG_TO_RAD - (float) Math.PI) * xzLen);
		return new Vector(x, y, z);
	}
	
	/**
	 * Casts the given object to an integer if applicable.
	 * @param o Object to cast.
	 * @return The resulting integer.
	 */
	public static Integer castInt(Object o) {
		if (o instanceof Number) {
			return ((Number) o).intValue();
		}

		return null;
	}

	/**
	 * Casts the given object to a double if applicable.
	 * @param o Object to cast.
	 * @return The resulting double.
	 */
	public static Double castDouble(Object o) {
		if (o instanceof Number) {
			return ((Number) o).doubleValue();
		}

		return null;
	}

	/**
	 * Casts the given object to a float if applicable.
	 * @param o Object to cast.
	 * @return The resulting float.
	 */
	public static Float castFloat(Object obj) {
		if (obj instanceof Number) {
			return ((Number) obj).floatValue();
		}

		return null;
	}

	/**
	 * Casts the given object to a boolean if applicable.
	 * @param o Object to cast.
	 * @return The resulting boolean.
	 */
	public static Boolean castBoolean(Object o) {
		if (o instanceof Boolean) {
			return (Boolean) o;
		} else if (o instanceof String) {
			try {
				return Boolean.parseBoolean((String) o);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}

		return null;
	}

}
