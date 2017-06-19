package org.beyonity.chess;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.WindowManager;

import org.beyonity.chess.gamelogic.Piece;
import org.beyonity.chess.gamelogic.Position;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public final class Util {
    public final static String boldStart;
    public final static String boldStop;

    static {
        boldStart = "<b>";
        boldStop = "</b>";
    }

    /** Read a text file. Return string array with one string per line. */
    public static String[] readFile(String networkEngineToConfig) throws IOException {
        ArrayList<String> ret = new ArrayList<>();
        InputStream inStream = new FileInputStream(networkEngineToConfig);
        InputStreamReader inFile = new InputStreamReader(inStream);
        BufferedReader inBuf = new BufferedReader(inFile);
        String line;
        while ((line = inBuf.readLine()) != null) {
            ret.add(line);
        }
        inBuf.close();
        return ret.toArray(new String[ret.size()]);
    }

    /** Read all data from an input stream. Return null if IO error. */
    public static String readFromStream(InputStream is) {
        InputStreamReader isr;
        try {
            isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            br.close();
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /** Compute material difference for a position. */
    public static MaterialDiff getMaterialDiff(Position pos) {
        StringBuilder whiteString = new StringBuilder();
        StringBuilder blackString = new StringBuilder();
        for (int p = Piece.WPAWN; p >= Piece.WKING; p--) {
            int diff = pos.nPieces(p) - pos.nPieces(Piece.swapColor(p));
            while (diff < 0) {
                whiteString.append(Piece.toUniCode(Piece.swapColor(p)));
                diff++;
            }
            while (diff > 0) {
                blackString.append(Piece.toUniCode(p));
                diff--;
            }
        }
        return new MaterialDiff(whiteString, blackString);
    }

    /** Enable/disable full screen mode for an activity. */
    public static void setFullScreenMode(Activity a, SharedPreferences settings) {
        boolean fullScreenMode = settings.getBoolean("fullScreenMode", false);
        WindowManager.LayoutParams attrs = a.getWindow().getAttributes();
        if (fullScreenMode) {
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else {
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        a.getWindow().setAttributes(attrs);
    }

    /** Change foreground/background color in a view. */
    /*private static void overrideViewAttribs(final View v) {
        if (v == null) {
            return;
        }
        final int bg = ColorTheme.instance().getColor(ColorTheme.GENERAL_BACKGROUND);
        Object tag = v.getTag();
        final boolean excludedItems =
                v instanceof Button || v instanceof EditText || v instanceof ImageButton
                        || "title".equals(tag);
        if (!excludedItems) {
            int c = bg;
            if ("thinking".equals(tag)) {
                float[] hsv = new float[3];
                Color.colorToHSV(c, hsv);
                hsv[2] += hsv[2] > 0.5f ? -0.1f : 0.1f;
                c = Color.HSVToColor(Color.alpha(c), hsv);
            }
            v.setBackgroundColor(c);
        }
        if (v instanceof ListView) {
            ((ListView) v).setCacheColorHint(bg);
        }
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                overrideViewAttribs(child);
            }
        } else if (!excludedItems && (v instanceof TextView)) {
            int fg = ColorTheme.instance().getColor(ColorTheme.FONT_FOREGROUND);
            ((TextView) v).setTextColor(fg);
        } else if (!excludedItems && (v instanceof MoveListView)) {
            int fg = ColorTheme.instance().getColor(ColorTheme.FONT_FOREGROUND);
            ((MoveListView) v).setTextColor(fg);
        }
    }*/

    /** Represent material difference as two unicode strings. */
    public final static class MaterialDiff {
        public final CharSequence white;
        public final CharSequence black;

        MaterialDiff(CharSequence w, CharSequence b) {
            white = w;
            black = b;
        }
    }
}
