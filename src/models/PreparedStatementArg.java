package models;

public class PreparedStatementArg {

    private Integer intArg;
    private String stringArg;

    public PreparedStatementArg(Integer intArg, String stringArg) {
        if ((intArg != null && stringArg != null) || (intArg == null && stringArg == null)) {
            throw new Error("Need to initialise EITHER int or string");
        }
        this.intArg = intArg;
        this.stringArg = stringArg;
    }

    public Integer getIntArg() {
        return intArg;
    }

    public String getStringArg() {
        return stringArg;
    }

    public String getType() {
        if (intArg != null) {
            return "Integer";
        } else if (stringArg != null){
            return "String";
        }
    }

}
