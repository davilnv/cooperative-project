package br.com.davilnv.cooperative.domain.enums;

public enum MemberVote {
    YES("Sim", "Y"),
    NO("Não", "N");

    private final String description;
    private final String code;

    MemberVote(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public static MemberVote fromCode(String code) {
        for (MemberVote memberVote : MemberVote.values()) {
            if (memberVote.getCode().equals(code)) {
                return memberVote;
            }
        }
        return null;
    }
}
