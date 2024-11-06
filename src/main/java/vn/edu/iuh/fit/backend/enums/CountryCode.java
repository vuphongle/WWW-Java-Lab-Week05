package vn.edu.iuh.fit.backend.enums;

public enum CountryCode {
    VIETNAM((short) 84, "Việt Nam"),
    USA((short) 1, "Hoa Kỳ"),
    UK((short) 44, "Vương quốc Anh"),
    CANADA((short) 124, "Canada"),
    AUSTRALIA((short) 61, "Úc"),
    // Thêm các quốc gia khác theo nhu cầu
    ;

    private final short code;
    private final String displayName;

    CountryCode(short code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public short getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static CountryCode fromCode(short code) {
        for (CountryCode cc : CountryCode.values()) {
            if (cc.getCode() == code) {
                return cc;
            }
        }
        throw new IllegalArgumentException("Unknown country code: " + code);
    }
}
