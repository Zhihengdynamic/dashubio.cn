package com.dashubio.utils;

/**
 * Created by Administrator on 2017/2/5.
 */

public class City {


    /**
     * w86 : {"w110000":"北京市","w120000":"天津市","w130000":"河北省","w140000":"山西省","w150000":"内蒙古自治区","w210000":"辽宁省","w220000":"吉林省","w230000":"黑龙江省","w310000":"上海市","w320000":"江苏省","w330000":"浙江省","w340000":"安徽省","w350000":"福建省","w360000":"江西省","w370000":"山东省","w410000":"河南省","w420000":"湖北省","w430000":"湖南省","w440000":"广东省","w450000":"广西壮族自治区","w460000":"海南省","w500000":"重庆市","w510000":"四川省","w520000":"贵州省","w530000":"云南省","w540000":"西藏自治区","w610000":"陕西省","w620000":"甘肃省","w630000":"青海省","w640000":"宁夏回族自治区","w650000":"新疆维吾尔自治区","w710000":"台湾省","w810000":"香港特别行政区","w820000":"澳门特别行政区"}
     * w110000 : {"w110100":"北京市市辖区"}
     * w110100 : {"w110101":"东城区","w110102":"西城区","w110105":"朝阳区","w110106":"丰台区","w110107":"石景山区","w110108":"海淀区","w110109":"门头沟区","w110111":"房山区","w110112":"通州区","w110113":"顺义区","w110114":"昌平区","w110115":"大兴区","w110116":"怀柔区","w110117":"平谷区","w110118":"密云区","w110119":"延庆区"}
     * w120000 : {"w120100":"天津市市辖区","w120200":"天津市郊县"}
     * w120100 : {"w120101":"和平区","w120102":"河东区","w120103":"河西区","w120104":"南开区","w120105":"河北区","w120106":"红桥区","w120110":"东丽区","w120111":"西青区","w120112":"津南区","w120113":"北辰区","w120114":"武清区","w120115":"宝坻区","w120116":"滨海新区","w120117":"宁河区","w120118":"静海区","w120225":"蓟县"}
     * w130000 : {"w130100":"石家庄市","w130200":"唐山市","w130300":"秦皇岛市","w130400":"邯郸市","w130500":"邢台市","w130600":"保定市","w130700":"张家口市","w130800":"承德市","w130900":"沧州市","w131000":"廊坊市","w131100":"衡水市"}
     * w130100 : {"w130102":"长安区","w130104":"桥西区","w130105":"新华区","w130107":"井陉矿区","w130108":"裕华区","w130109":"藁城区","w130110":"鹿泉区","w130111":"栾城区","w130121":"井陉县","w130123":"正定县","w130125":"行唐县","w130126":"灵寿县","w130127":"高邑县","w130128":"深泽县","w130129":"赞皇县","w130130":"无极县","w130131":"平山县","w130132":"元氏县","w130133":"赵县","w130181":"辛集市","w130183":"晋州市","w130184":"新乐市"}
     * w130200 : {"w130202":"路南区","w130203":"路北区","w130204":"古冶区","w130205":"开平区","w130207":"丰南区","w130208":"丰润区","w130209":"曹妃甸区","w130223":"滦县","w130224":"滦南县","w130225":"乐亭县","w130227":"迁西县","w130229":"玉田县","w130281":"遵化市","w130283":"迁安市"}
     * w130300 : {"w130302":"海港区","w130303":"山海关区","w130304":"北戴河区","w130306":"抚宁区","w130321":"青龙满族自治县","w130322":"昌黎县","w130324":"卢龙县"}
     * w130400 : {"w130402":"邯山区","w130403":"丛台区","w130404":"复兴区","w130406":"峰峰矿区","w130421":"邯郸县","w130423":"临漳县","w130424":"成安县","w130425":"大名县","w130426":"涉县","w130427":"磁县","w130428":"肥乡县","w130429":"永年县","w130430":"邱县","w130431":"鸡泽县","w130432":"广平县","w130433":"馆陶县","w130434":"魏县","w130435":"曲周县","w130481":"武安市"}
     * w130500 : {"w130502":"桥东区","w130503":"桥西区","w130521":"邢台县","w130522":"临城县","w130523":"内丘县","w130524":"柏乡县","w130525":"隆尧县","w130526":"任县","w130527":"南和县","w130528":"宁晋县","w130529":"巨鹿县","w130530":"新河县","w130531":"广宗县","w130532":"平乡县","w130533":"威县","w130534":"清河县","w130535":"临西县","w130581":"南宫市","w130582":"沙河市"}
     * w130600 : {"w130602":"竞秀区","w130606":"莲池区","w130607":"满城区","w130608":"清苑区","w130609":"徐水区","w130623":"涞水县","w130624":"阜平县","w130626":"定兴县","w130627":"唐县","w130628":"高阳县","w130629":"容城县","w130630":"涞源县","w130631":"望都县","w130632":"安新县","w130633":"易县","w130634":"曲阳县","w130635":"蠡县","w130636":"顺平县","w130637":"博野县","w130638":"雄县","w130681":"涿州市","w130682":"定州市","w130683":"安国市","w130684":"高碑店市"}
     * w130700 : {"w130702":"桥东区","w130703":"桥西区","w130705":"宣化区","w130706":"下花园区","w130721":"宣化县","w130722":"张北县","w130723":"康保县","w130724":"沽源县","w130725":"尚义县","w130726":"蔚县","w130727":"阳原县","w130728":"怀安县","w130729":"万全县","w130730":"怀来县","w130731":"涿鹿县","w130732":"赤城县","w130733":"崇礼县"}
     * w130800 : {"w130802":"双桥区","w130803":"双滦区","w130804":"鹰手营子矿区","w130821":"承德县","w130822":"兴隆县","w130823":"平泉县","w130824":"滦平县","w130825":"隆化县","w130826":"丰宁满族自治县","w130827":"宽城满族自治县","w130828":"围场满族蒙古族自治县"}
     * w130900 : {"w130902":"新华区","w130903":"运河区","w130921":"沧县","w130922":"青县","w130923":"东光县","w130924":"海兴县","w130925":"盐山县","w130926":"肃宁县","w130927":"南皮县","w130928":"吴桥县","w130929":"献县","w130930":"孟村回族自治县","w130981":"泊头市","w130982":"任丘市","w130983":"黄骅市","w130984":"河间市"}
     * w131000 : {"w131002":"安次区","w131003":"广阳区","w131022":"固安县","w131023":"永清县","w131024":"香河县","w131025":"大城县","w131026":"文安县","w131028":"大厂回族自治县","w131081":"霸州市","w131082":"三河市"}
     * w131100 : {"w131102":"桃城区","w131121":"枣强县","w131122":"武邑县","w131123":"武强县","w131124":"饶阳县","w131125":"安平县","w131126":"故城县","w131127":"景县","w131128":"阜城县","w131181":"冀州市","w131182":"深州市"}
     */

    private W86Bean w86;
    private W110000Bean w110000;
    private W110100Bean w110100;
    private W120000Bean w120000;
    private W120100Bean w120100;
    private W130000Bean w130000;
    private W130100Bean w130100;
    private W130200Bean w130200;
    private W130300Bean w130300;
    private W130400Bean w130400;
    private W130500Bean w130500;
    private W130600Bean w130600;
    private W130700Bean w130700;
    private W130800Bean w130800;
    private W130900Bean w130900;
    private W131000Bean w131000;
    private W131100Bean w131100;

    @Override
    public String toString() {
        return "City{" +
                "w86=" + w86 +
                ", w110000=" + w110000 +
                ", w110100=" + w110100 +
                ", w120000=" + w120000 +
                ", w120100=" + w120100 +
                ", w130000=" + w130000 +
                ", w130100=" + w130100 +
                ", w130200=" + w130200 +
                ", w130300=" + w130300 +
                ", w130400=" + w130400 +
                ", w130500=" + w130500 +
                ", w130600=" + w130600 +
                ", w130700=" + w130700 +
                ", w130800=" + w130800 +
                ", w130900=" + w130900 +
                ", w131000=" + w131000 +
                ", w131100=" + w131100 +
                '}';
    }

    public W86Bean getW86() {
        return w86;
    }

    public void setW86(W86Bean w86) {
        this.w86 = w86;
    }

    public W110000Bean getW110000() {
        return w110000;
    }

    public void setW110000(W110000Bean w110000) {
        this.w110000 = w110000;
    }

    public W110100Bean getW110100() {
        return w110100;
    }

    public void setW110100(W110100Bean w110100) {
        this.w110100 = w110100;
    }

    public W120000Bean getW120000() {
        return w120000;
    }

    public void setW120000(W120000Bean w120000) {
        this.w120000 = w120000;
    }

    public W120100Bean getW120100() {
        return w120100;
    }

    public void setW120100(W120100Bean w120100) {
        this.w120100 = w120100;
    }

    public W130000Bean getW130000() {
        return w130000;
    }

    public void setW130000(W130000Bean w130000) {
        this.w130000 = w130000;
    }

    public W130100Bean getW130100() {
        return w130100;
    }

    public void setW130100(W130100Bean w130100) {
        this.w130100 = w130100;
    }

    public W130200Bean getW130200() {
        return w130200;
    }

    public void setW130200(W130200Bean w130200) {
        this.w130200 = w130200;
    }

    public W130300Bean getW130300() {
        return w130300;
    }

    public void setW130300(W130300Bean w130300) {
        this.w130300 = w130300;
    }

    public W130400Bean getW130400() {
        return w130400;
    }

    public void setW130400(W130400Bean w130400) {
        this.w130400 = w130400;
    }

    public W130500Bean getW130500() {
        return w130500;
    }

    public void setW130500(W130500Bean w130500) {
        this.w130500 = w130500;
    }

    public W130600Bean getW130600() {
        return w130600;
    }

    public void setW130600(W130600Bean w130600) {
        this.w130600 = w130600;
    }

    public W130700Bean getW130700() {
        return w130700;
    }

    public void setW130700(W130700Bean w130700) {
        this.w130700 = w130700;
    }

    public W130800Bean getW130800() {
        return w130800;
    }

    public void setW130800(W130800Bean w130800) {
        this.w130800 = w130800;
    }

    public W130900Bean getW130900() {
        return w130900;
    }

    public void setW130900(W130900Bean w130900) {
        this.w130900 = w130900;
    }

    public W131000Bean getW131000() {
        return w131000;
    }

    public void setW131000(W131000Bean w131000) {
        this.w131000 = w131000;
    }

    public W131100Bean getW131100() {
        return w131100;
    }

    public void setW131100(W131100Bean w131100) {
        this.w131100 = w131100;
    }

    public static class W86Bean {
        /**
         * w110000 : 北京市
         * w120000 : 天津市
         * w130000 : 河北省
         * w140000 : 山西省
         * w150000 : 内蒙古自治区
         * w210000 : 辽宁省
         * w220000 : 吉林省
         * w230000 : 黑龙江省
         * w310000 : 上海市
         * w320000 : 江苏省
         * w330000 : 浙江省
         * w340000 : 安徽省
         * w350000 : 福建省
         * w360000 : 江西省
         * w370000 : 山东省
         * w410000 : 河南省
         * w420000 : 湖北省
         * w430000 : 湖南省
         * w440000 : 广东省
         * w450000 : 广西壮族自治区
         * w460000 : 海南省
         * w500000 : 重庆市
         * w510000 : 四川省
         * w520000 : 贵州省
         * w530000 : 云南省
         * w540000 : 西藏自治区
         * w610000 : 陕西省
         * w620000 : 甘肃省
         * w630000 : 青海省
         * w640000 : 宁夏回族自治区
         * w650000 : 新疆维吾尔自治区
         * w710000 : 台湾省
         * w810000 : 香港特别行政区
         * w820000 : 澳门特别行政区
         */

        private String w110000;
        private String w120000;
        private String w130000;
        private String w140000;
        private String w150000;
        private String w210000;
        private String w220000;
        private String w230000;
        private String w310000;
        private String w320000;
        private String w330000;
        private String w340000;
        private String w350000;
        private String w360000;
        private String w370000;
        private String w410000;
        private String w420000;
        private String w430000;
        private String w440000;
        private String w450000;
        private String w460000;
        private String w500000;
        private String w510000;
        private String w520000;
        private String w530000;
        private String w540000;
        private String w610000;
        private String w620000;
        private String w630000;
        private String w640000;
        private String w650000;
        private String w710000;
        private String w810000;
        private String w820000;

        public String getW110000() {
            return w110000;
        }

        public void setW110000(String w110000) {
            this.w110000 = w110000;
        }

        public String getW120000() {
            return w120000;
        }

        public void setW120000(String w120000) {
            this.w120000 = w120000;
        }

        public String getW130000() {
            return w130000;
        }

        public void setW130000(String w130000) {
            this.w130000 = w130000;
        }

        public String getW140000() {
            return w140000;
        }

        public void setW140000(String w140000) {
            this.w140000 = w140000;
        }

        public String getW150000() {
            return w150000;
        }

        public void setW150000(String w150000) {
            this.w150000 = w150000;
        }

        public String getW210000() {
            return w210000;
        }

        public void setW210000(String w210000) {
            this.w210000 = w210000;
        }

        public String getW220000() {
            return w220000;
        }

        public void setW220000(String w220000) {
            this.w220000 = w220000;
        }

        public String getW230000() {
            return w230000;
        }

        public void setW230000(String w230000) {
            this.w230000 = w230000;
        }

        public String getW310000() {
            return w310000;
        }

        public void setW310000(String w310000) {
            this.w310000 = w310000;
        }

        public String getW320000() {
            return w320000;
        }

        public void setW320000(String w320000) {
            this.w320000 = w320000;
        }

        public String getW330000() {
            return w330000;
        }

        public void setW330000(String w330000) {
            this.w330000 = w330000;
        }

        public String getW340000() {
            return w340000;
        }

        public void setW340000(String w340000) {
            this.w340000 = w340000;
        }

        public String getW350000() {
            return w350000;
        }

        public void setW350000(String w350000) {
            this.w350000 = w350000;
        }

        public String getW360000() {
            return w360000;
        }

        public void setW360000(String w360000) {
            this.w360000 = w360000;
        }

        public String getW370000() {
            return w370000;
        }

        public void setW370000(String w370000) {
            this.w370000 = w370000;
        }

        public String getW410000() {
            return w410000;
        }

        public void setW410000(String w410000) {
            this.w410000 = w410000;
        }

        public String getW420000() {
            return w420000;
        }

        public void setW420000(String w420000) {
            this.w420000 = w420000;
        }

        public String getW430000() {
            return w430000;
        }

        public void setW430000(String w430000) {
            this.w430000 = w430000;
        }

        public String getW440000() {
            return w440000;
        }

        public void setW440000(String w440000) {
            this.w440000 = w440000;
        }

        public String getW450000() {
            return w450000;
        }

        public void setW450000(String w450000) {
            this.w450000 = w450000;
        }

        public String getW460000() {
            return w460000;
        }

        public void setW460000(String w460000) {
            this.w460000 = w460000;
        }

        public String getW500000() {
            return w500000;
        }

        public void setW500000(String w500000) {
            this.w500000 = w500000;
        }

        public String getW510000() {
            return w510000;
        }

        public void setW510000(String w510000) {
            this.w510000 = w510000;
        }

        public String getW520000() {
            return w520000;
        }

        public void setW520000(String w520000) {
            this.w520000 = w520000;
        }

        public String getW530000() {
            return w530000;
        }

        public void setW530000(String w530000) {
            this.w530000 = w530000;
        }

        public String getW540000() {
            return w540000;
        }

        public void setW540000(String w540000) {
            this.w540000 = w540000;
        }

        public String getW610000() {
            return w610000;
        }

        public void setW610000(String w610000) {
            this.w610000 = w610000;
        }

        public String getW620000() {
            return w620000;
        }

        public void setW620000(String w620000) {
            this.w620000 = w620000;
        }

        public String getW630000() {
            return w630000;
        }

        public void setW630000(String w630000) {
            this.w630000 = w630000;
        }

        public String getW640000() {
            return w640000;
        }

        public void setW640000(String w640000) {
            this.w640000 = w640000;
        }

        public String getW650000() {
            return w650000;
        }

        public void setW650000(String w650000) {
            this.w650000 = w650000;
        }

        public String getW710000() {
            return w710000;
        }

        public void setW710000(String w710000) {
            this.w710000 = w710000;
        }

        public String getW810000() {
            return w810000;
        }

        public void setW810000(String w810000) {
            this.w810000 = w810000;
        }

        public String getW820000() {
            return w820000;
        }

        public void setW820000(String w820000) {
            this.w820000 = w820000;
        }
    }

    public static class W110000Bean {
        /**
         * w110100 : 北京市市辖区
         */

        private String w110100;

        public String getW110100() {
            return w110100;
        }

        public void setW110100(String w110100) {
            this.w110100 = w110100;
        }
    }

    public static class W110100Bean {
        /**
         * w110101 : 东城区
         * w110102 : 西城区
         * w110105 : 朝阳区
         * w110106 : 丰台区
         * w110107 : 石景山区
         * w110108 : 海淀区
         * w110109 : 门头沟区
         * w110111 : 房山区
         * w110112 : 通州区
         * w110113 : 顺义区
         * w110114 : 昌平区
         * w110115 : 大兴区
         * w110116 : 怀柔区
         * w110117 : 平谷区
         * w110118 : 密云区
         * w110119 : 延庆区
         */

        private String w110101;
        private String w110102;
        private String w110105;
        private String w110106;
        private String w110107;
        private String w110108;
        private String w110109;
        private String w110111;
        private String w110112;
        private String w110113;
        private String w110114;
        private String w110115;
        private String w110116;
        private String w110117;
        private String w110118;
        private String w110119;

        public String getW110101() {
            return w110101;
        }

        public void setW110101(String w110101) {
            this.w110101 = w110101;
        }

        public String getW110102() {
            return w110102;
        }

        public void setW110102(String w110102) {
            this.w110102 = w110102;
        }

        public String getW110105() {
            return w110105;
        }

        public void setW110105(String w110105) {
            this.w110105 = w110105;
        }

        public String getW110106() {
            return w110106;
        }

        public void setW110106(String w110106) {
            this.w110106 = w110106;
        }

        public String getW110107() {
            return w110107;
        }

        public void setW110107(String w110107) {
            this.w110107 = w110107;
        }

        public String getW110108() {
            return w110108;
        }

        public void setW110108(String w110108) {
            this.w110108 = w110108;
        }

        public String getW110109() {
            return w110109;
        }

        public void setW110109(String w110109) {
            this.w110109 = w110109;
        }

        public String getW110111() {
            return w110111;
        }

        public void setW110111(String w110111) {
            this.w110111 = w110111;
        }

        public String getW110112() {
            return w110112;
        }

        public void setW110112(String w110112) {
            this.w110112 = w110112;
        }

        public String getW110113() {
            return w110113;
        }

        public void setW110113(String w110113) {
            this.w110113 = w110113;
        }

        public String getW110114() {
            return w110114;
        }

        public void setW110114(String w110114) {
            this.w110114 = w110114;
        }

        public String getW110115() {
            return w110115;
        }

        public void setW110115(String w110115) {
            this.w110115 = w110115;
        }

        public String getW110116() {
            return w110116;
        }

        public void setW110116(String w110116) {
            this.w110116 = w110116;
        }

        public String getW110117() {
            return w110117;
        }

        public void setW110117(String w110117) {
            this.w110117 = w110117;
        }

        public String getW110118() {
            return w110118;
        }

        public void setW110118(String w110118) {
            this.w110118 = w110118;
        }

        public String getW110119() {
            return w110119;
        }

        public void setW110119(String w110119) {
            this.w110119 = w110119;
        }
    }

    public static class W120000Bean {
        /**
         * w120100 : 天津市市辖区
         * w120200 : 天津市郊县
         */

        private String w120100;
        private String w120200;

        public String getW120100() {
            return w120100;
        }

        public void setW120100(String w120100) {
            this.w120100 = w120100;
        }

        public String getW120200() {
            return w120200;
        }

        public void setW120200(String w120200) {
            this.w120200 = w120200;
        }
    }

    public static class W120100Bean {
        /**
         * w120101 : 和平区
         * w120102 : 河东区
         * w120103 : 河西区
         * w120104 : 南开区
         * w120105 : 河北区
         * w120106 : 红桥区
         * w120110 : 东丽区
         * w120111 : 西青区
         * w120112 : 津南区
         * w120113 : 北辰区
         * w120114 : 武清区
         * w120115 : 宝坻区
         * w120116 : 滨海新区
         * w120117 : 宁河区
         * w120118 : 静海区
         * w120225 : 蓟县
         */

        private String w120101;
        private String w120102;
        private String w120103;
        private String w120104;
        private String w120105;
        private String w120106;
        private String w120110;
        private String w120111;
        private String w120112;
        private String w120113;
        private String w120114;
        private String w120115;
        private String w120116;
        private String w120117;
        private String w120118;
        private String w120225;

        public String getW120101() {
            return w120101;
        }

        public void setW120101(String w120101) {
            this.w120101 = w120101;
        }

        public String getW120102() {
            return w120102;
        }

        public void setW120102(String w120102) {
            this.w120102 = w120102;
        }

        public String getW120103() {
            return w120103;
        }

        public void setW120103(String w120103) {
            this.w120103 = w120103;
        }

        public String getW120104() {
            return w120104;
        }

        public void setW120104(String w120104) {
            this.w120104 = w120104;
        }

        public String getW120105() {
            return w120105;
        }

        public void setW120105(String w120105) {
            this.w120105 = w120105;
        }

        public String getW120106() {
            return w120106;
        }

        public void setW120106(String w120106) {
            this.w120106 = w120106;
        }

        public String getW120110() {
            return w120110;
        }

        public void setW120110(String w120110) {
            this.w120110 = w120110;
        }

        public String getW120111() {
            return w120111;
        }

        public void setW120111(String w120111) {
            this.w120111 = w120111;
        }

        public String getW120112() {
            return w120112;
        }

        public void setW120112(String w120112) {
            this.w120112 = w120112;
        }

        public String getW120113() {
            return w120113;
        }

        public void setW120113(String w120113) {
            this.w120113 = w120113;
        }

        public String getW120114() {
            return w120114;
        }

        public void setW120114(String w120114) {
            this.w120114 = w120114;
        }

        public String getW120115() {
            return w120115;
        }

        public void setW120115(String w120115) {
            this.w120115 = w120115;
        }

        public String getW120116() {
            return w120116;
        }

        public void setW120116(String w120116) {
            this.w120116 = w120116;
        }

        public String getW120117() {
            return w120117;
        }

        public void setW120117(String w120117) {
            this.w120117 = w120117;
        }

        public String getW120118() {
            return w120118;
        }

        public void setW120118(String w120118) {
            this.w120118 = w120118;
        }

        public String getW120225() {
            return w120225;
        }

        public void setW120225(String w120225) {
            this.w120225 = w120225;
        }
    }

    public static class W130000Bean {
        /**
         * w130100 : 石家庄市
         * w130200 : 唐山市
         * w130300 : 秦皇岛市
         * w130400 : 邯郸市
         * w130500 : 邢台市
         * w130600 : 保定市
         * w130700 : 张家口市
         * w130800 : 承德市
         * w130900 : 沧州市
         * w131000 : 廊坊市
         * w131100 : 衡水市
         */

        private String w130100;
        private String w130200;
        private String w130300;
        private String w130400;
        private String w130500;
        private String w130600;
        private String w130700;
        private String w130800;
        private String w130900;
        private String w131000;
        private String w131100;

        public String getW130100() {
            return w130100;
        }

        public void setW130100(String w130100) {
            this.w130100 = w130100;
        }

        public String getW130200() {
            return w130200;
        }

        public void setW130200(String w130200) {
            this.w130200 = w130200;
        }

        public String getW130300() {
            return w130300;
        }

        public void setW130300(String w130300) {
            this.w130300 = w130300;
        }

        public String getW130400() {
            return w130400;
        }

        public void setW130400(String w130400) {
            this.w130400 = w130400;
        }

        public String getW130500() {
            return w130500;
        }

        public void setW130500(String w130500) {
            this.w130500 = w130500;
        }

        public String getW130600() {
            return w130600;
        }

        public void setW130600(String w130600) {
            this.w130600 = w130600;
        }

        public String getW130700() {
            return w130700;
        }

        public void setW130700(String w130700) {
            this.w130700 = w130700;
        }

        public String getW130800() {
            return w130800;
        }

        public void setW130800(String w130800) {
            this.w130800 = w130800;
        }

        public String getW130900() {
            return w130900;
        }

        public void setW130900(String w130900) {
            this.w130900 = w130900;
        }

        public String getW131000() {
            return w131000;
        }

        public void setW131000(String w131000) {
            this.w131000 = w131000;
        }

        public String getW131100() {
            return w131100;
        }

        public void setW131100(String w131100) {
            this.w131100 = w131100;
        }
    }

    public static class W130100Bean {
        /**
         * w130102 : 长安区
         * w130104 : 桥西区
         * w130105 : 新华区
         * w130107 : 井陉矿区
         * w130108 : 裕华区
         * w130109 : 藁城区
         * w130110 : 鹿泉区
         * w130111 : 栾城区
         * w130121 : 井陉县
         * w130123 : 正定县
         * w130125 : 行唐县
         * w130126 : 灵寿县
         * w130127 : 高邑县
         * w130128 : 深泽县
         * w130129 : 赞皇县
         * w130130 : 无极县
         * w130131 : 平山县
         * w130132 : 元氏县
         * w130133 : 赵县
         * w130181 : 辛集市
         * w130183 : 晋州市
         * w130184 : 新乐市
         */

        private String w130102;
        private String w130104;
        private String w130105;
        private String w130107;
        private String w130108;
        private String w130109;
        private String w130110;
        private String w130111;
        private String w130121;
        private String w130123;
        private String w130125;
        private String w130126;
        private String w130127;
        private String w130128;
        private String w130129;
        private String w130130;
        private String w130131;
        private String w130132;
        private String w130133;
        private String w130181;
        private String w130183;
        private String w130184;

        public String getW130102() {
            return w130102;
        }

        public void setW130102(String w130102) {
            this.w130102 = w130102;
        }

        public String getW130104() {
            return w130104;
        }

        public void setW130104(String w130104) {
            this.w130104 = w130104;
        }

        public String getW130105() {
            return w130105;
        }

        public void setW130105(String w130105) {
            this.w130105 = w130105;
        }

        public String getW130107() {
            return w130107;
        }

        public void setW130107(String w130107) {
            this.w130107 = w130107;
        }

        public String getW130108() {
            return w130108;
        }

        public void setW130108(String w130108) {
            this.w130108 = w130108;
        }

        public String getW130109() {
            return w130109;
        }

        public void setW130109(String w130109) {
            this.w130109 = w130109;
        }

        public String getW130110() {
            return w130110;
        }

        public void setW130110(String w130110) {
            this.w130110 = w130110;
        }

        public String getW130111() {
            return w130111;
        }

        public void setW130111(String w130111) {
            this.w130111 = w130111;
        }

        public String getW130121() {
            return w130121;
        }

        public void setW130121(String w130121) {
            this.w130121 = w130121;
        }

        public String getW130123() {
            return w130123;
        }

        public void setW130123(String w130123) {
            this.w130123 = w130123;
        }

        public String getW130125() {
            return w130125;
        }

        public void setW130125(String w130125) {
            this.w130125 = w130125;
        }

        public String getW130126() {
            return w130126;
        }

        public void setW130126(String w130126) {
            this.w130126 = w130126;
        }

        public String getW130127() {
            return w130127;
        }

        public void setW130127(String w130127) {
            this.w130127 = w130127;
        }

        public String getW130128() {
            return w130128;
        }

        public void setW130128(String w130128) {
            this.w130128 = w130128;
        }

        public String getW130129() {
            return w130129;
        }

        public void setW130129(String w130129) {
            this.w130129 = w130129;
        }

        public String getW130130() {
            return w130130;
        }

        public void setW130130(String w130130) {
            this.w130130 = w130130;
        }

        public String getW130131() {
            return w130131;
        }

        public void setW130131(String w130131) {
            this.w130131 = w130131;
        }

        public String getW130132() {
            return w130132;
        }

        public void setW130132(String w130132) {
            this.w130132 = w130132;
        }

        public String getW130133() {
            return w130133;
        }

        public void setW130133(String w130133) {
            this.w130133 = w130133;
        }

        public String getW130181() {
            return w130181;
        }

        public void setW130181(String w130181) {
            this.w130181 = w130181;
        }

        public String getW130183() {
            return w130183;
        }

        public void setW130183(String w130183) {
            this.w130183 = w130183;
        }

        public String getW130184() {
            return w130184;
        }

        public void setW130184(String w130184) {
            this.w130184 = w130184;
        }
    }

    public static class W130200Bean {
        /**
         * w130202 : 路南区
         * w130203 : 路北区
         * w130204 : 古冶区
         * w130205 : 开平区
         * w130207 : 丰南区
         * w130208 : 丰润区
         * w130209 : 曹妃甸区
         * w130223 : 滦县
         * w130224 : 滦南县
         * w130225 : 乐亭县
         * w130227 : 迁西县
         * w130229 : 玉田县
         * w130281 : 遵化市
         * w130283 : 迁安市
         */

        private String w130202;
        private String w130203;
        private String w130204;
        private String w130205;
        private String w130207;
        private String w130208;
        private String w130209;
        private String w130223;
        private String w130224;
        private String w130225;
        private String w130227;
        private String w130229;
        private String w130281;
        private String w130283;

        public String getW130202() {
            return w130202;
        }

        public void setW130202(String w130202) {
            this.w130202 = w130202;
        }

        public String getW130203() {
            return w130203;
        }

        public void setW130203(String w130203) {
            this.w130203 = w130203;
        }

        public String getW130204() {
            return w130204;
        }

        public void setW130204(String w130204) {
            this.w130204 = w130204;
        }

        public String getW130205() {
            return w130205;
        }

        public void setW130205(String w130205) {
            this.w130205 = w130205;
        }

        public String getW130207() {
            return w130207;
        }

        public void setW130207(String w130207) {
            this.w130207 = w130207;
        }

        public String getW130208() {
            return w130208;
        }

        public void setW130208(String w130208) {
            this.w130208 = w130208;
        }

        public String getW130209() {
            return w130209;
        }

        public void setW130209(String w130209) {
            this.w130209 = w130209;
        }

        public String getW130223() {
            return w130223;
        }

        public void setW130223(String w130223) {
            this.w130223 = w130223;
        }

        public String getW130224() {
            return w130224;
        }

        public void setW130224(String w130224) {
            this.w130224 = w130224;
        }

        public String getW130225() {
            return w130225;
        }

        public void setW130225(String w130225) {
            this.w130225 = w130225;
        }

        public String getW130227() {
            return w130227;
        }

        public void setW130227(String w130227) {
            this.w130227 = w130227;
        }

        public String getW130229() {
            return w130229;
        }

        public void setW130229(String w130229) {
            this.w130229 = w130229;
        }

        public String getW130281() {
            return w130281;
        }

        public void setW130281(String w130281) {
            this.w130281 = w130281;
        }

        public String getW130283() {
            return w130283;
        }

        public void setW130283(String w130283) {
            this.w130283 = w130283;
        }
    }

    public static class W130300Bean {
        /**
         * w130302 : 海港区
         * w130303 : 山海关区
         * w130304 : 北戴河区
         * w130306 : 抚宁区
         * w130321 : 青龙满族自治县
         * w130322 : 昌黎县
         * w130324 : 卢龙县
         */

        private String w130302;
        private String w130303;
        private String w130304;
        private String w130306;
        private String w130321;
        private String w130322;
        private String w130324;

        public String getW130302() {
            return w130302;
        }

        public void setW130302(String w130302) {
            this.w130302 = w130302;
        }

        public String getW130303() {
            return w130303;
        }

        public void setW130303(String w130303) {
            this.w130303 = w130303;
        }

        public String getW130304() {
            return w130304;
        }

        public void setW130304(String w130304) {
            this.w130304 = w130304;
        }

        public String getW130306() {
            return w130306;
        }

        public void setW130306(String w130306) {
            this.w130306 = w130306;
        }

        public String getW130321() {
            return w130321;
        }

        public void setW130321(String w130321) {
            this.w130321 = w130321;
        }

        public String getW130322() {
            return w130322;
        }

        public void setW130322(String w130322) {
            this.w130322 = w130322;
        }

        public String getW130324() {
            return w130324;
        }

        public void setW130324(String w130324) {
            this.w130324 = w130324;
        }
    }

    public static class W130400Bean {
        /**
         * w130402 : 邯山区
         * w130403 : 丛台区
         * w130404 : 复兴区
         * w130406 : 峰峰矿区
         * w130421 : 邯郸县
         * w130423 : 临漳县
         * w130424 : 成安县
         * w130425 : 大名县
         * w130426 : 涉县
         * w130427 : 磁县
         * w130428 : 肥乡县
         * w130429 : 永年县
         * w130430 : 邱县
         * w130431 : 鸡泽县
         * w130432 : 广平县
         * w130433 : 馆陶县
         * w130434 : 魏县
         * w130435 : 曲周县
         * w130481 : 武安市
         */

        private String w130402;
        private String w130403;
        private String w130404;
        private String w130406;
        private String w130421;
        private String w130423;
        private String w130424;
        private String w130425;
        private String w130426;
        private String w130427;
        private String w130428;
        private String w130429;
        private String w130430;
        private String w130431;
        private String w130432;
        private String w130433;
        private String w130434;
        private String w130435;
        private String w130481;

        public String getW130402() {
            return w130402;
        }

        public void setW130402(String w130402) {
            this.w130402 = w130402;
        }

        public String getW130403() {
            return w130403;
        }

        public void setW130403(String w130403) {
            this.w130403 = w130403;
        }

        public String getW130404() {
            return w130404;
        }

        public void setW130404(String w130404) {
            this.w130404 = w130404;
        }

        public String getW130406() {
            return w130406;
        }

        public void setW130406(String w130406) {
            this.w130406 = w130406;
        }

        public String getW130421() {
            return w130421;
        }

        public void setW130421(String w130421) {
            this.w130421 = w130421;
        }

        public String getW130423() {
            return w130423;
        }

        public void setW130423(String w130423) {
            this.w130423 = w130423;
        }

        public String getW130424() {
            return w130424;
        }

        public void setW130424(String w130424) {
            this.w130424 = w130424;
        }

        public String getW130425() {
            return w130425;
        }

        public void setW130425(String w130425) {
            this.w130425 = w130425;
        }

        public String getW130426() {
            return w130426;
        }

        public void setW130426(String w130426) {
            this.w130426 = w130426;
        }

        public String getW130427() {
            return w130427;
        }

        public void setW130427(String w130427) {
            this.w130427 = w130427;
        }

        public String getW130428() {
            return w130428;
        }

        public void setW130428(String w130428) {
            this.w130428 = w130428;
        }

        public String getW130429() {
            return w130429;
        }

        public void setW130429(String w130429) {
            this.w130429 = w130429;
        }

        public String getW130430() {
            return w130430;
        }

        public void setW130430(String w130430) {
            this.w130430 = w130430;
        }

        public String getW130431() {
            return w130431;
        }

        public void setW130431(String w130431) {
            this.w130431 = w130431;
        }

        public String getW130432() {
            return w130432;
        }

        public void setW130432(String w130432) {
            this.w130432 = w130432;
        }

        public String getW130433() {
            return w130433;
        }

        public void setW130433(String w130433) {
            this.w130433 = w130433;
        }

        public String getW130434() {
            return w130434;
        }

        public void setW130434(String w130434) {
            this.w130434 = w130434;
        }

        public String getW130435() {
            return w130435;
        }

        public void setW130435(String w130435) {
            this.w130435 = w130435;
        }

        public String getW130481() {
            return w130481;
        }

        public void setW130481(String w130481) {
            this.w130481 = w130481;
        }
    }

    public static class W130500Bean {
        /**
         * w130502 : 桥东区
         * w130503 : 桥西区
         * w130521 : 邢台县
         * w130522 : 临城县
         * w130523 : 内丘县
         * w130524 : 柏乡县
         * w130525 : 隆尧县
         * w130526 : 任县
         * w130527 : 南和县
         * w130528 : 宁晋县
         * w130529 : 巨鹿县
         * w130530 : 新河县
         * w130531 : 广宗县
         * w130532 : 平乡县
         * w130533 : 威县
         * w130534 : 清河县
         * w130535 : 临西县
         * w130581 : 南宫市
         * w130582 : 沙河市
         */

        private String w130502;
        private String w130503;
        private String w130521;
        private String w130522;
        private String w130523;
        private String w130524;
        private String w130525;
        private String w130526;
        private String w130527;
        private String w130528;
        private String w130529;
        private String w130530;
        private String w130531;
        private String w130532;
        private String w130533;
        private String w130534;
        private String w130535;
        private String w130581;
        private String w130582;

        public String getW130502() {
            return w130502;
        }

        public void setW130502(String w130502) {
            this.w130502 = w130502;
        }

        public String getW130503() {
            return w130503;
        }

        public void setW130503(String w130503) {
            this.w130503 = w130503;
        }

        public String getW130521() {
            return w130521;
        }

        public void setW130521(String w130521) {
            this.w130521 = w130521;
        }

        public String getW130522() {
            return w130522;
        }

        public void setW130522(String w130522) {
            this.w130522 = w130522;
        }

        public String getW130523() {
            return w130523;
        }

        public void setW130523(String w130523) {
            this.w130523 = w130523;
        }

        public String getW130524() {
            return w130524;
        }

        public void setW130524(String w130524) {
            this.w130524 = w130524;
        }

        public String getW130525() {
            return w130525;
        }

        public void setW130525(String w130525) {
            this.w130525 = w130525;
        }

        public String getW130526() {
            return w130526;
        }

        public void setW130526(String w130526) {
            this.w130526 = w130526;
        }

        public String getW130527() {
            return w130527;
        }

        public void setW130527(String w130527) {
            this.w130527 = w130527;
        }

        public String getW130528() {
            return w130528;
        }

        public void setW130528(String w130528) {
            this.w130528 = w130528;
        }

        public String getW130529() {
            return w130529;
        }

        public void setW130529(String w130529) {
            this.w130529 = w130529;
        }

        public String getW130530() {
            return w130530;
        }

        public void setW130530(String w130530) {
            this.w130530 = w130530;
        }

        public String getW130531() {
            return w130531;
        }

        public void setW130531(String w130531) {
            this.w130531 = w130531;
        }

        public String getW130532() {
            return w130532;
        }

        public void setW130532(String w130532) {
            this.w130532 = w130532;
        }

        public String getW130533() {
            return w130533;
        }

        public void setW130533(String w130533) {
            this.w130533 = w130533;
        }

        public String getW130534() {
            return w130534;
        }

        public void setW130534(String w130534) {
            this.w130534 = w130534;
        }

        public String getW130535() {
            return w130535;
        }

        public void setW130535(String w130535) {
            this.w130535 = w130535;
        }

        public String getW130581() {
            return w130581;
        }

        public void setW130581(String w130581) {
            this.w130581 = w130581;
        }

        public String getW130582() {
            return w130582;
        }

        public void setW130582(String w130582) {
            this.w130582 = w130582;
        }
    }

    public static class W130600Bean {
        /**
         * w130602 : 竞秀区
         * w130606 : 莲池区
         * w130607 : 满城区
         * w130608 : 清苑区
         * w130609 : 徐水区
         * w130623 : 涞水县
         * w130624 : 阜平县
         * w130626 : 定兴县
         * w130627 : 唐县
         * w130628 : 高阳县
         * w130629 : 容城县
         * w130630 : 涞源县
         * w130631 : 望都县
         * w130632 : 安新县
         * w130633 : 易县
         * w130634 : 曲阳县
         * w130635 : 蠡县
         * w130636 : 顺平县
         * w130637 : 博野县
         * w130638 : 雄县
         * w130681 : 涿州市
         * w130682 : 定州市
         * w130683 : 安国市
         * w130684 : 高碑店市
         */

        private String w130602;
        private String w130606;
        private String w130607;
        private String w130608;
        private String w130609;
        private String w130623;
        private String w130624;
        private String w130626;
        private String w130627;
        private String w130628;
        private String w130629;
        private String w130630;
        private String w130631;
        private String w130632;
        private String w130633;
        private String w130634;
        private String w130635;
        private String w130636;
        private String w130637;
        private String w130638;
        private String w130681;
        private String w130682;
        private String w130683;
        private String w130684;

        public String getW130602() {
            return w130602;
        }

        public void setW130602(String w130602) {
            this.w130602 = w130602;
        }

        public String getW130606() {
            return w130606;
        }

        public void setW130606(String w130606) {
            this.w130606 = w130606;
        }

        public String getW130607() {
            return w130607;
        }

        public void setW130607(String w130607) {
            this.w130607 = w130607;
        }

        public String getW130608() {
            return w130608;
        }

        public void setW130608(String w130608) {
            this.w130608 = w130608;
        }

        public String getW130609() {
            return w130609;
        }

        public void setW130609(String w130609) {
            this.w130609 = w130609;
        }

        public String getW130623() {
            return w130623;
        }

        public void setW130623(String w130623) {
            this.w130623 = w130623;
        }

        public String getW130624() {
            return w130624;
        }

        public void setW130624(String w130624) {
            this.w130624 = w130624;
        }

        public String getW130626() {
            return w130626;
        }

        public void setW130626(String w130626) {
            this.w130626 = w130626;
        }

        public String getW130627() {
            return w130627;
        }

        public void setW130627(String w130627) {
            this.w130627 = w130627;
        }

        public String getW130628() {
            return w130628;
        }

        public void setW130628(String w130628) {
            this.w130628 = w130628;
        }

        public String getW130629() {
            return w130629;
        }

        public void setW130629(String w130629) {
            this.w130629 = w130629;
        }

        public String getW130630() {
            return w130630;
        }

        public void setW130630(String w130630) {
            this.w130630 = w130630;
        }

        public String getW130631() {
            return w130631;
        }

        public void setW130631(String w130631) {
            this.w130631 = w130631;
        }

        public String getW130632() {
            return w130632;
        }

        public void setW130632(String w130632) {
            this.w130632 = w130632;
        }

        public String getW130633() {
            return w130633;
        }

        public void setW130633(String w130633) {
            this.w130633 = w130633;
        }

        public String getW130634() {
            return w130634;
        }

        public void setW130634(String w130634) {
            this.w130634 = w130634;
        }

        public String getW130635() {
            return w130635;
        }

        public void setW130635(String w130635) {
            this.w130635 = w130635;
        }

        public String getW130636() {
            return w130636;
        }

        public void setW130636(String w130636) {
            this.w130636 = w130636;
        }

        public String getW130637() {
            return w130637;
        }

        public void setW130637(String w130637) {
            this.w130637 = w130637;
        }

        public String getW130638() {
            return w130638;
        }

        public void setW130638(String w130638) {
            this.w130638 = w130638;
        }

        public String getW130681() {
            return w130681;
        }

        public void setW130681(String w130681) {
            this.w130681 = w130681;
        }

        public String getW130682() {
            return w130682;
        }

        public void setW130682(String w130682) {
            this.w130682 = w130682;
        }

        public String getW130683() {
            return w130683;
        }

        public void setW130683(String w130683) {
            this.w130683 = w130683;
        }

        public String getW130684() {
            return w130684;
        }

        public void setW130684(String w130684) {
            this.w130684 = w130684;
        }
    }

    public static class W130700Bean {
        /**
         * w130702 : 桥东区
         * w130703 : 桥西区
         * w130705 : 宣化区
         * w130706 : 下花园区
         * w130721 : 宣化县
         * w130722 : 张北县
         * w130723 : 康保县
         * w130724 : 沽源县
         * w130725 : 尚义县
         * w130726 : 蔚县
         * w130727 : 阳原县
         * w130728 : 怀安县
         * w130729 : 万全县
         * w130730 : 怀来县
         * w130731 : 涿鹿县
         * w130732 : 赤城县
         * w130733 : 崇礼县
         */

        private String w130702;
        private String w130703;
        private String w130705;
        private String w130706;
        private String w130721;
        private String w130722;
        private String w130723;
        private String w130724;
        private String w130725;
        private String w130726;
        private String w130727;
        private String w130728;
        private String w130729;
        private String w130730;
        private String w130731;
        private String w130732;
        private String w130733;

        public String getW130702() {
            return w130702;
        }

        public void setW130702(String w130702) {
            this.w130702 = w130702;
        }

        public String getW130703() {
            return w130703;
        }

        public void setW130703(String w130703) {
            this.w130703 = w130703;
        }

        public String getW130705() {
            return w130705;
        }

        public void setW130705(String w130705) {
            this.w130705 = w130705;
        }

        public String getW130706() {
            return w130706;
        }

        public void setW130706(String w130706) {
            this.w130706 = w130706;
        }

        public String getW130721() {
            return w130721;
        }

        public void setW130721(String w130721) {
            this.w130721 = w130721;
        }

        public String getW130722() {
            return w130722;
        }

        public void setW130722(String w130722) {
            this.w130722 = w130722;
        }

        public String getW130723() {
            return w130723;
        }

        public void setW130723(String w130723) {
            this.w130723 = w130723;
        }

        public String getW130724() {
            return w130724;
        }

        public void setW130724(String w130724) {
            this.w130724 = w130724;
        }

        public String getW130725() {
            return w130725;
        }

        public void setW130725(String w130725) {
            this.w130725 = w130725;
        }

        public String getW130726() {
            return w130726;
        }

        public void setW130726(String w130726) {
            this.w130726 = w130726;
        }

        public String getW130727() {
            return w130727;
        }

        public void setW130727(String w130727) {
            this.w130727 = w130727;
        }

        public String getW130728() {
            return w130728;
        }

        public void setW130728(String w130728) {
            this.w130728 = w130728;
        }

        public String getW130729() {
            return w130729;
        }

        public void setW130729(String w130729) {
            this.w130729 = w130729;
        }

        public String getW130730() {
            return w130730;
        }

        public void setW130730(String w130730) {
            this.w130730 = w130730;
        }

        public String getW130731() {
            return w130731;
        }

        public void setW130731(String w130731) {
            this.w130731 = w130731;
        }

        public String getW130732() {
            return w130732;
        }

        public void setW130732(String w130732) {
            this.w130732 = w130732;
        }

        public String getW130733() {
            return w130733;
        }

        public void setW130733(String w130733) {
            this.w130733 = w130733;
        }
    }

    public static class W130800Bean {
        /**
         * w130802 : 双桥区
         * w130803 : 双滦区
         * w130804 : 鹰手营子矿区
         * w130821 : 承德县
         * w130822 : 兴隆县
         * w130823 : 平泉县
         * w130824 : 滦平县
         * w130825 : 隆化县
         * w130826 : 丰宁满族自治县
         * w130827 : 宽城满族自治县
         * w130828 : 围场满族蒙古族自治县
         */

        private String w130802;
        private String w130803;
        private String w130804;
        private String w130821;
        private String w130822;
        private String w130823;
        private String w130824;
        private String w130825;
        private String w130826;
        private String w130827;
        private String w130828;

        public String getW130802() {
            return w130802;
        }

        public void setW130802(String w130802) {
            this.w130802 = w130802;
        }

        public String getW130803() {
            return w130803;
        }

        public void setW130803(String w130803) {
            this.w130803 = w130803;
        }

        public String getW130804() {
            return w130804;
        }

        public void setW130804(String w130804) {
            this.w130804 = w130804;
        }

        public String getW130821() {
            return w130821;
        }

        public void setW130821(String w130821) {
            this.w130821 = w130821;
        }

        public String getW130822() {
            return w130822;
        }

        public void setW130822(String w130822) {
            this.w130822 = w130822;
        }

        public String getW130823() {
            return w130823;
        }

        public void setW130823(String w130823) {
            this.w130823 = w130823;
        }

        public String getW130824() {
            return w130824;
        }

        public void setW130824(String w130824) {
            this.w130824 = w130824;
        }

        public String getW130825() {
            return w130825;
        }

        public void setW130825(String w130825) {
            this.w130825 = w130825;
        }

        public String getW130826() {
            return w130826;
        }

        public void setW130826(String w130826) {
            this.w130826 = w130826;
        }

        public String getW130827() {
            return w130827;
        }

        public void setW130827(String w130827) {
            this.w130827 = w130827;
        }

        public String getW130828() {
            return w130828;
        }

        public void setW130828(String w130828) {
            this.w130828 = w130828;
        }
    }

    public static class W130900Bean {
        /**
         * w130902 : 新华区
         * w130903 : 运河区
         * w130921 : 沧县
         * w130922 : 青县
         * w130923 : 东光县
         * w130924 : 海兴县
         * w130925 : 盐山县
         * w130926 : 肃宁县
         * w130927 : 南皮县
         * w130928 : 吴桥县
         * w130929 : 献县
         * w130930 : 孟村回族自治县
         * w130981 : 泊头市
         * w130982 : 任丘市
         * w130983 : 黄骅市
         * w130984 : 河间市
         */

        private String w130902;
        private String w130903;
        private String w130921;
        private String w130922;
        private String w130923;
        private String w130924;
        private String w130925;
        private String w130926;
        private String w130927;
        private String w130928;
        private String w130929;
        private String w130930;
        private String w130981;
        private String w130982;
        private String w130983;
        private String w130984;

        public String getW130902() {
            return w130902;
        }

        public void setW130902(String w130902) {
            this.w130902 = w130902;
        }

        public String getW130903() {
            return w130903;
        }

        public void setW130903(String w130903) {
            this.w130903 = w130903;
        }

        public String getW130921() {
            return w130921;
        }

        public void setW130921(String w130921) {
            this.w130921 = w130921;
        }

        public String getW130922() {
            return w130922;
        }

        public void setW130922(String w130922) {
            this.w130922 = w130922;
        }

        public String getW130923() {
            return w130923;
        }

        public void setW130923(String w130923) {
            this.w130923 = w130923;
        }

        public String getW130924() {
            return w130924;
        }

        public void setW130924(String w130924) {
            this.w130924 = w130924;
        }

        public String getW130925() {
            return w130925;
        }

        public void setW130925(String w130925) {
            this.w130925 = w130925;
        }

        public String getW130926() {
            return w130926;
        }

        public void setW130926(String w130926) {
            this.w130926 = w130926;
        }

        public String getW130927() {
            return w130927;
        }

        public void setW130927(String w130927) {
            this.w130927 = w130927;
        }

        public String getW130928() {
            return w130928;
        }

        public void setW130928(String w130928) {
            this.w130928 = w130928;
        }

        public String getW130929() {
            return w130929;
        }

        public void setW130929(String w130929) {
            this.w130929 = w130929;
        }

        public String getW130930() {
            return w130930;
        }

        public void setW130930(String w130930) {
            this.w130930 = w130930;
        }

        public String getW130981() {
            return w130981;
        }

        public void setW130981(String w130981) {
            this.w130981 = w130981;
        }

        public String getW130982() {
            return w130982;
        }

        public void setW130982(String w130982) {
            this.w130982 = w130982;
        }

        public String getW130983() {
            return w130983;
        }

        public void setW130983(String w130983) {
            this.w130983 = w130983;
        }

        public String getW130984() {
            return w130984;
        }

        public void setW130984(String w130984) {
            this.w130984 = w130984;
        }
    }

    public static class W131000Bean {
        /**
         * w131002 : 安次区
         * w131003 : 广阳区
         * w131022 : 固安县
         * w131023 : 永清县
         * w131024 : 香河县
         * w131025 : 大城县
         * w131026 : 文安县
         * w131028 : 大厂回族自治县
         * w131081 : 霸州市
         * w131082 : 三河市
         */

        private String w131002;
        private String w131003;
        private String w131022;
        private String w131023;
        private String w131024;
        private String w131025;
        private String w131026;
        private String w131028;
        private String w131081;
        private String w131082;

        public String getW131002() {
            return w131002;
        }

        public void setW131002(String w131002) {
            this.w131002 = w131002;
        }

        public String getW131003() {
            return w131003;
        }

        public void setW131003(String w131003) {
            this.w131003 = w131003;
        }

        public String getW131022() {
            return w131022;
        }

        public void setW131022(String w131022) {
            this.w131022 = w131022;
        }

        public String getW131023() {
            return w131023;
        }

        public void setW131023(String w131023) {
            this.w131023 = w131023;
        }

        public String getW131024() {
            return w131024;
        }

        public void setW131024(String w131024) {
            this.w131024 = w131024;
        }

        public String getW131025() {
            return w131025;
        }

        public void setW131025(String w131025) {
            this.w131025 = w131025;
        }

        public String getW131026() {
            return w131026;
        }

        public void setW131026(String w131026) {
            this.w131026 = w131026;
        }

        public String getW131028() {
            return w131028;
        }

        public void setW131028(String w131028) {
            this.w131028 = w131028;
        }

        public String getW131081() {
            return w131081;
        }

        public void setW131081(String w131081) {
            this.w131081 = w131081;
        }

        public String getW131082() {
            return w131082;
        }

        public void setW131082(String w131082) {
            this.w131082 = w131082;
        }
    }

    public static class W131100Bean {
        /**
         * w131102 : 桃城区
         * w131121 : 枣强县
         * w131122 : 武邑县
         * w131123 : 武强县
         * w131124 : 饶阳县
         * w131125 : 安平县
         * w131126 : 故城县
         * w131127 : 景县
         * w131128 : 阜城县
         * w131181 : 冀州市
         * w131182 : 深州市
         */

        private String w131102;
        private String w131121;
        private String w131122;
        private String w131123;
        private String w131124;
        private String w131125;
        private String w131126;
        private String w131127;
        private String w131128;
        private String w131181;
        private String w131182;

        public String getW131102() {
            return w131102;
        }

        public void setW131102(String w131102) {
            this.w131102 = w131102;
        }

        public String getW131121() {
            return w131121;
        }

        public void setW131121(String w131121) {
            this.w131121 = w131121;
        }

        public String getW131122() {
            return w131122;
        }

        public void setW131122(String w131122) {
            this.w131122 = w131122;
        }

        public String getW131123() {
            return w131123;
        }

        public void setW131123(String w131123) {
            this.w131123 = w131123;
        }

        public String getW131124() {
            return w131124;
        }

        public void setW131124(String w131124) {
            this.w131124 = w131124;
        }

        public String getW131125() {
            return w131125;
        }

        public void setW131125(String w131125) {
            this.w131125 = w131125;
        }

        public String getW131126() {
            return w131126;
        }

        public void setW131126(String w131126) {
            this.w131126 = w131126;
        }

        public String getW131127() {
            return w131127;
        }

        public void setW131127(String w131127) {
            this.w131127 = w131127;
        }

        public String getW131128() {
            return w131128;
        }

        public void setW131128(String w131128) {
            this.w131128 = w131128;
        }

        public String getW131181() {
            return w131181;
        }

        public void setW131181(String w131181) {
            this.w131181 = w131181;
        }

        public String getW131182() {
            return w131182;
        }

        public void setW131182(String w131182) {
            this.w131182 = w131182;
        }
    }
}
