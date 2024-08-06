package data.config;

import data.subcontent.FontData;
import data.subcontent.Node;
import lui.base.data.LDataList;
import lui.base.data.LInitializable;
import lui.graphics.LColor;

public class UIConfig implements LInitializable {

    public LDataList<BaseColor> baseColors = new LDataList<>();
    public LDataList<Node> colorMap = new LDataList<>();
    public LDataList<BaseFont> baseFonts = new LDataList<>();
    public LDataList<Node> fontMap = new LDataList<>();

    public UIConfig() {
        initialize();
    }

    @Override
    public void initialize() {
        baseColors.add(new BaseColor("white", 255, 255, 255, 255));
        baseColors.add(new BaseColor("black", 0, 0, 0, 255));
        baseColors.add(new BaseColor("red", 255, 0, 0, 255));
        baseColors.add(new BaseColor("green", 0, 255, 0, 255));
        baseColors.add(new BaseColor("blue", 0, 0, 255, 255));
        baseColors.add(new BaseColor("yellow", 255, 255, 0, 255));
        baseColors.add(new BaseColor("magenta", 255, 0, 255, 255));
        baseColors.add(new BaseColor("cyan", 0, 255, 255, 255));
        baseColors.add(new BaseColor("clear", 255, 255, 255, 0));

        baseColors.add(new BaseColor("babyPink",255 * 1.0, 255 * 0.55, 255 * 0.95, 255 * 1.0));
        baseColors.add(new BaseColor("babyBlue",255 * 0.65, 255 * 0.7, 255 * 1.0, 255 * 1.0));
        baseColors.add(new BaseColor("lightPurple",255 * 0.8, 255 * 0.5, 255 * 0.9, 255 * 0.85));
        baseColors.add(new BaseColor("lightBlue",255 * 0.4, 255 * 0.4, 255 * 1.0, 255 * 0.85));
        baseColors.add(new BaseColor("lightGreen",255 * 0.4, 255 * 1.0, 255 * 0.4, 255 * 0.85));
        baseColors.add(new BaseColor("lightRed",255 * 1.0, 255 * 0.4, 255 * 0.4, 255 * 0.85));
        baseColors.add(new BaseColor("lightGray",255 * 0.75, 255 * 0.75, 255 * 0.75,255 * 0.75));

        baseColors.add(new BaseColor("clearPurple",255 * 0.8, 255 * 0.45, 255 * 0.8, 255 * 0.4));
        baseColors.add(new BaseColor("clearBlue",255 * 0.3, 255 * 0.3, 255 * 1.0, 255 * 0.4));
        baseColors.add(new BaseColor("clearGreen",255 * 0.3, 255 * 1.0, 255 * 0.3, 255 * 0.4));
        baseColors.add(new BaseColor("clearRed",255 * 1.0, 255 * 0.3, 255 * 0.3, 255 * 0.4));
        baseColors.add(new BaseColor("clearGray",255 * 0.65, 255 * 0.65, 255 * 0.65, 255 * 0.2));

        baseColors.add(new BaseColor("opaqueYellow",255 * 1.0, 255 * 1.0, 255 * 0.5, 255 * 1.0));
        baseColors.add(new BaseColor("opaqueCyan",255 * 0.5, 255 * 1.0, 255 * 1.0, 255 * 1.0));
        baseColors.add(new BaseColor("opaquePink",255 * 1.0, 255 * 0.5, 255 * 1.0, 255 * 1.0));
        baseColors.add(new BaseColor("opaqueRed",255 * 1.0, 255 * 0.3, 255 * 0.3, 255 * 1.0));
        baseColors.add(new BaseColor("opaqueGreen",255 * 0.25, 255 * 1.0, 255 * 0.35, 255 * 1.0));
        baseColors.add(new BaseColor("opaqueBlue",255 * 0.35, 255 * 0.25, 255 * 1.0, 255 * 1.0));
        baseColors.add(new BaseColor("opaqueGray",255 * 0.8, 255 * 0.8, 255 * 0.8, 255 * 1.0));

        colorMap.add(new Node(getBaseColorIndex("lightPurple"), "tile_general"));
        colorMap.add(new Node(getBaseColorIndex("lightBlue"), "tile_move"));
        colorMap.add(new Node(getBaseColorIndex("lightGreen"), "tile_support"));
        colorMap.add(new Node(getBaseColorIndex("lightRed"), "tile_attack"));
        colorMap.add(new Node(getBaseColorIndex("lightGray"), "tile_nothing"));

        colorMap.add(new Node(getBaseColorIndex("clearPurple"), "tile_general_off"));
        colorMap.add(new Node(getBaseColorIndex("clearBlue"), "tile_move_off"));
        colorMap.add(new Node(getBaseColorIndex("clearGreen"), "tile_support_off"));
        colorMap.add(new Node(getBaseColorIndex("clearRed"), "tile_attack_off"));
        colorMap.add(new Node(getBaseColorIndex("clearGray"), "tile_nothing_off"));

        colorMap.add(new Node(getBaseColorIndex("opaqueYellow"), "popup_dmghp"));
        colorMap.add(new Node(getBaseColorIndex("opaquePink"), "popup_dmgsp"));
        colorMap.add(new Node(getBaseColorIndex("opaqueGreen"), "popup_healhp"));
        colorMap.add(new Node(getBaseColorIndex("opaqueCyan"), "popup_healsp"));
        colorMap.add(new Node(getBaseColorIndex("opaqueGray"), "popup_miss"));
        colorMap.add(new Node(getBaseColorIndex("white"), "popup_status_add"));
        colorMap.add(new Node(getBaseColorIndex("white"), "popup_status_remove"));
        colorMap.add(new Node(getBaseColorIndex("yellow"), "popup_levelup"));
        colorMap.add(new Node(getBaseColorIndex("white"), "popup_exp"));

        colorMap.add(new Node(getBaseColorIndex("white"), "menu_text_enabled"));
        colorMap.add(new Node(getBaseColorIndex("lightGray"), "menu_text_disabled"));
        colorMap.add(new Node(getBaseColorIndex("white"), "menu_icon_enabled"));
        colorMap.add(new Node(getBaseColorIndex("lightGray"), "menu_icon_disabled"));
        colorMap.add(new Node(getBaseColorIndex("opaqueGreen"), "barHP"));
        colorMap.add(new Node(getBaseColorIndex("opaqueBlue"), "barSP"));
        colorMap.add(new Node(getBaseColorIndex("opaqueYellow"), "barEXP"));
        colorMap.add(new Node(getBaseColorIndex("opaqueRed"), "barTC"));
        colorMap.add(new Node(getBaseColorIndex("green"), "positive_bonus"));
        colorMap.add(new Node(getBaseColorIndex("red"), "negative_bonus"));
        colorMap.add(new Node(getBaseColorIndex("green"), "element_weak"));
        colorMap.add(new Node(getBaseColorIndex("red"), "element_strong"));
        colorMap.add(new Node(getBaseColorIndex("white"), "element_neutral"));

        baseFonts.add(new BaseFont("huge_font", new FontData("Roboto", "ttf", 25)));
        baseFonts.add(new BaseFont("big_font", new FontData("Roboto", "ttf", 20)));
        baseFonts.add(new BaseFont("critical_font", new FontData("Roboto", "ttf", 17)));
        baseFonts.add(new BaseFont("default_font", new FontData("Roboto", "ttf", 14)));
        baseFonts.add(new BaseFont("medium_font", new FontData("Roboto", "ttf", 11)));
        baseFonts.add(new BaseFont("small_font", new FontData("Roboto", "ttf", 9)));
        baseFonts.add(new BaseFont("tiny_font", new FontData("Roboto", "ttf", 7.5f)));
        baseFonts.add(new BaseFont("log_font", new FontData("Roboto", "ttf", 5)));

        fontMap.add(new Node(getBaseFontIndex("huge_font"), "menu_title"));
        fontMap.add(new Node(getBaseFontIndex("default_font"), "menu_default"));
        fontMap.add(new Node(getBaseFontIndex("default_font"), "menu_button"));
        fontMap.add(new Node(getBaseFontIndex("medium_font"), "menu_dialogue"));
        fontMap.add(new Node(getBaseFontIndex("tiny_font"), "menu_tiny"));
        fontMap.add(new Node(getBaseFontIndex("small_font"), "menu_small"));
        fontMap.add(new Node(getBaseFontIndex("medium_font"), "menu_medium"));
        fontMap.add(new Node(getBaseFontIndex("big_font"), "menu_big"));
        fontMap.add(new Node(getBaseFontIndex("huge_font"), "menu_huge"));
        fontMap.add(new Node(getBaseFontIndex("medium_font"), "menu_tooltip"));

        fontMap.add(new Node(getBaseFontIndex("default_font"), "popup_dmghp"));
        fontMap.add(new Node(getBaseFontIndex("default_font"), "popup_dmgsp"));
        fontMap.add(new Node(getBaseFontIndex("default_font"), "popup_healhp"));
        fontMap.add(new Node(getBaseFontIndex("default_font"), "popup_healsp"));
        fontMap.add(new Node(getBaseFontIndex("critical_font"), "popup_dmghp_crit"));
        fontMap.add(new Node(getBaseFontIndex("critical_font"), "popup_dmgsp_crit"));
        fontMap.add(new Node(getBaseFontIndex("critical_font"), "popup_healhp_crit"));
        fontMap.add(new Node(getBaseFontIndex("critical_font"), "popup_healsp_crit"));

        fontMap.add(new Node(getBaseFontIndex("default_font"), "popup_miss"));
        fontMap.add(new Node(getBaseFontIndex("default_font"), "popup_status_add"));
        fontMap.add(new Node(getBaseFontIndex("default_font"), "popup_status_remove"));
        fontMap.add(new Node(getBaseFontIndex("default_font"), "popup_levelup"));
        fontMap.add(new Node(getBaseFontIndex("medium_font"), "popup_exp"));

        fontMap.add(new Node(getBaseFontIndex("log_font"), "log"));
        fontMap.add(new Node(getBaseFontIndex("medium_font"), "pause"));
    }

    public int getBaseColorIndex(String name) {
        for (int i = 0; i < baseColors.size(); i++) {
            if (baseColors.get(i).name.equals(name))
                return i;
        }
        return -1;
    }

    public int getBaseFontIndex(String name) {
        for (int i = 0; i < baseFonts.size(); i++) {
            if (baseFonts.get(i).name.equals(name))
                return i;
        }
        return -1;
    }

    public static class BaseColor {

        public String name = "colorname";
        public LColor color;

        public BaseColor(){ color = new LColor(255, 255, 255, 255); }
        public BaseColor(String name, int red, int green, int blue, int alpha) {
            this.name = name;
            color = new LColor(red, green, blue, alpha);
        }
        public BaseColor(String name, double red, double green, double blue, double alpha) {
            this.name = name;
            color = new LColor(red, green, blue, alpha);
        }
        public BaseColor(String name, LColor color) {
            this(name, color.red, color.green, color.blue, color.alpha);
        }
        public String toString() {
            return name;
        }

    }

    public static class BaseFont {

        public String name = "fontname";
        public FontData font;

        public BaseFont(){ font = new FontData(); }
        public BaseFont(String name, FontData font) {
            this.name = name;
            this.font = font;
        }
        public String toString() {
            return name;
        }

    }

}
