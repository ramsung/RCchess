/*
    MD Chess - An Android chess program.
    Copyright (C) 2011  Peter Österlund, peterosterlund2@gmail.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.beyonity.chess;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;

public class ColorTheme {
    public final static int FONT_FOREGROUND = 0;
    public final static int GENERAL_BACKGROUND = 17;
    final static int DARK_SQUARE = 0;
    final static int BRIGHT_SQUARE = 1;
    final static int SELECTED_SQUARE = 2;
    final static int CURSOR_SQUARE = 3;
    final static int DARK_PIECE = 4;
    final static int BRIGHT_PIECE = 5;
    final static int CURRENT_MOVE = 6;
    final static int ARROW_0 = 7;
    final static int ARROW_1 = 8;
    final static int ARROW_2 = 9;
    final static int ARROW_3 = 10;
    final static int ARROW_4 = 11;
    final static int ARROW_5 = 12;
    final static int SQUARE_LABEL = 13;
    final static int DECORATION = 14;
    final static int PGN_COMMENT = 15;
    final static int[] themeNames = {
            R.string.colortheme_original,
            R.string.colortheme_xboard,
            R.string.colortheme_blue,
            R.string.colortheme_grey,
            R.string.colortheme_scid_default,
            R.string.colortheme_scid_brown,
            R.string.colortheme_scid_green
    };
    private final static int numColors = 18;
    private static final String[] prefNames = {
            "darkSquare", "brightSquare", "selectedSquare", "cursorSquare", "darkPiece",
            "brightPiece", "currentMove",
            "arrow0", "arrow1", "arrow2", "arrow3", "arrow4", "arrow5", "squareLabel", "decoration",
            "pgnComment",
            "fontForeground", "generalBackground"
    };
    private static final String prefPrefix = "color_";
    private final static int defaultTheme = 2;
    private final static String themeColors[][] = {
            { // Original
                    "#616161", "#9E9E9E", "#FFFF0000", "#FF00FF00", "#FF000000", "#FFFFFFFF",
                    "#FF888888",
                    "#A01F1FFF", "#A0FF1F1F", "#501F1FFF", "#50FF1F1F", "#1E1F1FFF", "#28FF1F1F",
                    "#FFFF0000",
                    "#FF9F9F66", "#FFC0C000", "#FFF7FBC6", "#FF292C10"
            },
            { // XBoard
                    "#7CB342", "#AED581", "#FFFFFF00", "#FF00FF00", "#FF202020", "#FFFFFFCC",
                    "#FF6B9262",
                    "#A01F1FFF", "#A0FF1F1F", "#501F1FFF", "#50FF1F1F", "#1E1F1FFF", "#28FF1F1F",
                    "#FFFF0000",
                    "#FF808080", "#FFC0C000", "#FFEFFBBC", "#FF28320C"
            },
            { // Blue
                    "#1E88E5", "#64B5F6", "#FF3232D1", "#FF5F5FFD", "#FF282828", "#FFF0F0F0",
                    "#FF3333FF",
                    "#A01F1FFF", "#A01FFF1F", "#501F1FFF", "#501FFF1F", "#1E1F1FFF", "#281FFF1F",
                    "#FFFF0000",
                    "#FF808080", "#FFC0C000", "#FFFFFF00", "#FF2E2B53"
            },
            { // Grey
                    "#757575", "#E0E0E0", "#FFFF0000", "#FF0000FF", "#FF000000", "#FFFFFFFF",
                    "#FF888888",
                    "#A01F1FFF", "#A0FF1F1F", "#501F1FFF", "#50FF1F1F", "#1E1F1FFF", "#28FF1F1F",
                    "#FFFF0000",
                    "#FF909090", "#FFC0C000", "#FFFFFFFF", "#FF202020"
            },
            { // Scid Default
                    "#FB8C00", "#FFB74D", "#FFFF0000", "#FF00FF00", "#FF000000", "#FFFFFFFF",
                    "#FF666666",
                    "#A01F1FFF", "#A0FF1F1F", "#501F1FFF", "#50FF1F1F", "#1E1F1FFF", "#28FF1F1F",
                    "#FFFF0000",
                    "#FF808080", "#FFC0C000", "#FFDEFBDE", "#FF213429"
            },
            { // Scid Brown
                    "#6D4C41", "#A1887F", "#FFFF0000", "#FF00FF00", "#FF000000", "#FFFFFFFF",
                    "#FF666666",
                    "#A01F1FFF", "#A0FF1F1F", "#501F1FFF", "#50FF1F1F", "#1E1F1FFF", "#28FF1F1F",
                    "#FFFF0000",
                    "#FF808080", "#FFC0C000", "#FFF7FAE3", "#FF40260A"
            },
            { // Scid Green
                    "#43A047", "#81C784", "#FFFF0000", "#FF0000FF", "#FF000000", "#FFFFFFFF",
                    "#FF666666",
                    "#A01F1FFF", "#A0FF1F1F", "#501F1FFF", "#50FF1F1F", "#1E1F1FFF", "#28FF1F1F",
                    "#FFFF0000",
                    "#FF808080", "#FFC0C000", "#FFDEE3CE", "#FF183C21"
            }
    };
    private static ColorTheme inst = null;
    private int colorTable[] = new int[numColors];

    /**
     * Get singleton instance.
     */
    public static final ColorTheme instance() {
        if (inst == null) {
            inst = new ColorTheme();
        }
        return inst;
    }

    final void readColors(SharedPreferences settings) {
        for (int i = 0; i < numColors; i++) {
            String prefName = prefPrefix + prefNames[i];
            String defaultColor = themeColors[defaultTheme][i];
            String colorString = settings.getString(prefName, defaultColor);
            colorTable[i] = 0;
            try {
                colorTable[i] = Color.parseColor(colorString);
            } catch (IllegalArgumentException e) {
            } catch (StringIndexOutOfBoundsException e) {
            }
        }
    }

    final void setTheme(SharedPreferences settings, int themeType) {
        Editor editor = settings.edit();
        for (int i = 0; i < numColors; i++) {
            editor.putString(prefPrefix + prefNames[i], themeColors[themeType][i]);
        }
        editor.apply();
        readColors(settings);
    }

    public final int getColor(int colorType) {
        return colorTable[colorType];
    }
}
