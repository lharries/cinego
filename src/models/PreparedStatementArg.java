package models;

public class PreparedStatementArg {

    private Integer intArg;
    private String stringArg;

    private Boolean booleanArg;

    public PreparedStatementArg(Integer intArg, String stringArg, Boolean booleanArg) {
        if ((intArg != null && stringArg != null && booleanArg != null) || (intArg == null && stringArg == null && booleanArg == null)) {
            throw new Error("Need to initialise EITHER int or string");
        }
        this.intArg = intArg;
        this.stringArg = stringArg;
        this.booleanArg = booleanArg;
    }

    public Integer getIntArg() {
        return intArg;
    }

    public String getStringArg() {
        return stringArg;
    }

    public Boolean getBooleanArg() {
        return booleanArg;
    }

    public void setBooleanArg(Boolean booleanArg) {
        this.booleanArg = booleanArg;
    }

    public String getType() {
        if (intArg != null) {
            return "Integer";
        } else if (stringArg != null){
            return "String";
        } else if (booleanArg != null){
            return "Boolean";
        } else {
            throw new Error("No type found!");
        }
    }

}
