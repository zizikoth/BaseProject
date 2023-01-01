package com.memo.core.screen;

/**
 * title:进行一些适配，基本应该是足够了
 * describe:
 *
 * @author zhou
 * @date 2019-01-05 11:09
 */
public enum DimenTypes {
    dp_sw_240(240),
    dp_sw_320(320),
    dp_sw_360(360),
    dp_sw_362(362),
    dp_sw_384(384),
    dp_sw_392(392),
    dp_sw_400(400),
    dp_sw_410(410),
    dp_sw_411(411),
    dp_sw_432(432),
    dp_sw_480(480),
    dp_sw_533(533),
    dp_sw_540(540),
    dp_sw_592(592),
    dp_sw_600(600),
    dp_sw_640(640),
    dp_sw_662(662),
    dp_sw_720(720),
    dp_sw_768(768),
    dp_sw_800(800),
    dp_sw_811(811),
    dp_sw_820(820),
    dp_sw_960(960),
    dp_sw_961(961),
    dp_sw_1024(1024),
    dp_sw_1280(1280),
    dp_sw_1365(1365);


    private int swWidthDp;

    DimenTypes(int swWidthDp) {
        this.swWidthDp = swWidthDp;
    }

    public int getSwWidthDp() {
        return swWidthDp;
    }

}
