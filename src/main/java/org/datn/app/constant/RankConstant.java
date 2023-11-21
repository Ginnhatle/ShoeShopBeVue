package org.datn.app.constant;
import lombok.Getter;
import lombok.Setter;

public enum RankConstant {
    BRONZE(0, 0, 0),
    SILVER(1000000, 1000000, 3),   // giảm 3%
    GOLD(2000000, 2000000, 5),     // giảm 5%
    PLATINUM(3000000, 3000000, 7), // giảm 7%
    DIAMOND(4000000, 4000000, 10); // giảm 10%
    public static RankConstant getRankType(int rank) {
        switch (rank) {
            case 2:
                return SILVER;
            case 3:
                return GOLD;
            case 4:
                return PLATINUM;
            case 5:
                return DIAMOND;
            default:
                return BRONZE;
        }
    }

    @Getter
    @Setter
    public int condition;

    @Getter
    @Setter
    public int targetMoney;

    @Getter
    @Setter
    public double discount;
     RankConstant(int condition, int targetMoney, double discount) {
        this.condition = condition;
        this.targetMoney = targetMoney;
        this.discount = discount;
    }

}
