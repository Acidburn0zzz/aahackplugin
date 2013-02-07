package org.kos.aahackplugin;

import java.awt.*;

public enum AAType {
	ON, OFF, DEFAULT, GASP,
	HRGB, VRGB, HBGR, VBGR;

	public Object getRenderingHint() {
		switch (this) {
			case ON:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
			case OFF:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
			case DEFAULT:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT;
			case GASP:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_GASP;
			case HRGB:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB;
			case VRGB:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB;
			case HBGR:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR;
			case VBGR:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR;
			default:
				throw new RuntimeException("Unknown type: " + this);
		}
	}

	public static AAType fromOrdinal(int ord) {
		for (AAType t : AAType.values()) {
			if (t.ordinal() == ord) return t;
		}
		throw new RuntimeException("Non-existing ordinal: " + ord);
	}
}
