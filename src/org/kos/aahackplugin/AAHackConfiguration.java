package org.kos.aahackplugin;

public class AAHackConfiguration {
	public int aaType;
	public int contrast;

	public AAHackConfiguration() {
		aaType = AAType.DEFAULT.ordinal();
		contrast = 110;
	}

	public AAHackConfiguration(AAType aaType, int contrast) {
		this.aaType = aaType.ordinal();
		this.contrast = contrast;
	}

	public AAType getAAType() {
		return AAType.fromOrdinal(aaType);
	}

	public void setAAType(AAType aaType) {
		this.aaType = aaType.ordinal();
	}

	public int getContrast() {
		return contrast;
	}

	public void setContrast(int contrast) {
		this.contrast = contrast;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AAHackConfiguration that = (AAHackConfiguration) o;

		return aaType == that.aaType && contrast == that.contrast;
	}

	public int hashCode() {
		int result;
		result = aaType;
		result = 31 * result + contrast;
		return result;
	}
}
