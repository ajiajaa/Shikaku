package com.raihan.shikaku;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import com.raihan.shikaku.model.Checker;
import com.raihan.shikaku.model.Level;
import com.raihan.shikaku.model.Rectangle;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class CheckerTest {
    //factory method
    public static Rectangle createRectangle(int x1, int y1, int x2, int y2, int n) {
        Rectangle rectangle = new Rectangle(n);
        rectangle.setIndex(x1, y1, x2, y2);
        return rectangle;
    }
    //5x5
    @Test
    public void pengujianMudahJawabanBenarVariasi1() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Level level= new Level(context, 5);
        level.readPuzzles(9);

        ArrayList<Rectangle> arrayList= new ArrayList<>();

        arrayList.add(createRectangle(0, 0, 3, 0, 4));
        arrayList.add(createRectangle(0, 1, 3, 1, 4));
        arrayList.add(createRectangle(0, 2, 3, 2, 4));
        arrayList.add(createRectangle(0, 3, 1, 4, 4));
        arrayList.add(createRectangle(2, 3, 3, 3, 2));
        arrayList.add(createRectangle(2, 4, 3, 4, 2));
        arrayList.add(createRectangle(4, 0, 4, 2, 3));
        arrayList.add(createRectangle(4, 3, 4, 4, 2));

        Checker checker = new Checker(null, arrayList, level);
        boolean isValid = checker.validateBoard();
        assertTrue(isValid);
    }
    @Test
    public void pengujianMudahJawabanBenarVariasi2() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Level level= new Level(context, 5);
        level.readPuzzles(9);

        ArrayList<Rectangle> arrayList= new ArrayList<>();

        arrayList.add(createRectangle(0, 0, 3, 0, 4));
        arrayList.add(createRectangle(0, 1, 1, 2, 4));
        arrayList.add(createRectangle(2, 1, 3, 2, 4));
        arrayList.add(createRectangle(0, 3, 1, 4, 4));
        arrayList.add(createRectangle(2, 3, 3, 3, 2));
        arrayList.add(createRectangle(2, 4, 3, 4, 2));
        arrayList.add(createRectangle(4, 0, 4, 2, 3));
        arrayList.add(createRectangle(4, 3, 4, 4, 2));

        Checker checker = new Checker(null, arrayList, level);
        boolean isValid = checker.validateBoard();
        assertTrue(isValid);
    }


    @Test
    public void pengujianMudahJawabanSalah() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Level level= new Level(context, 5);
        level.readPuzzles(9);

        ArrayList<Rectangle> arrayList= new ArrayList<>();

        arrayList.add(createRectangle(0, 0, 3, 0, 4));
        arrayList.add(createRectangle(0, 1, 3, 1, 4));
        arrayList.add(createRectangle(0, 2, 3, 2, 4));
        arrayList.add(createRectangle(0, 3, 1, 4, 4));
        arrayList.add(createRectangle(2, 3, 3, 3, 2));
        arrayList.add(createRectangle(2, 4, 3, 4, 2));
        arrayList.add(createRectangle(4, 0, 4, 1, 2));
        arrayList.add(createRectangle(4, 2, 4, 4, 3));

        Checker checker = new Checker(null, arrayList, level);
        boolean isValid = checker.validateBoard();
        assertFalse(isValid);
    }

    //10x10
    @Test
    public void pengujianSedangJawabanBenarVariasi1() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Level level= new Level(context, 10);
        level.readPuzzles(0);

        ArrayList<Rectangle> arrayList= new ArrayList<>();

        arrayList.add(createRectangle(0,0,2,4,15));
        arrayList.add(createRectangle(0,5,2,9,15));
        arrayList.add(createRectangle(3,0,8,1,12));
        arrayList.add(createRectangle(3,2,7,4,15));
        arrayList.add(createRectangle(3,5,5,5,3));
        arrayList.add(createRectangle(3,6,3,8,3));
        arrayList.add(createRectangle(3,9,9,9,7));
        arrayList.add(createRectangle(4,6,5,8,6));
        arrayList.add(createRectangle(6,5,7,8,8));
        arrayList.add(createRectangle(8,2,8,4,3));
        arrayList.add(createRectangle(8,5,9,8,8));
        arrayList.add(createRectangle(9,0,9,4,5));

        Checker checker = new Checker(null, arrayList, level);
        boolean isValid = checker.validateBoard();
        assertTrue(isValid);
    }
    @Test
    public void pengujianSedangJawabanBenarVariasi2() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Level level= new Level(context, 10);
        level.readPuzzles(0);

        ArrayList<Rectangle> arrayList= new ArrayList<>();

        arrayList.add(createRectangle(0,0,2,4,15));
        arrayList.add(createRectangle(0,5,2,9,15));
        arrayList.add(createRectangle(3,0,8,1,12));
        arrayList.add(createRectangle(3,2,7,4,15));
        arrayList.add(createRectangle(3,5,5,5,3));
        arrayList.add(createRectangle(3,6,3,8,3));
        arrayList.add(createRectangle(3,9,9,9,7));
        arrayList.add(createRectangle(4,6,5,8,6));
        arrayList.add(createRectangle(6,5,9,6,8));
        arrayList.add(createRectangle(8,2,8,4,3));
        arrayList.add(createRectangle(6,7,9,8,8));
        arrayList.add(createRectangle(9,0,9,4,5));

        Checker checker = new Checker(null, arrayList, level);
        boolean isValid = checker.validateBoard();
        assertTrue(isValid);
    }
    @Test
    public void pengujianSedangJawabanSalah() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Level level= new Level(context, 10);
        level.readPuzzles(0);

        ArrayList<Rectangle> arrayList= new ArrayList<>();

        arrayList.add(createRectangle(0,0,2,4,15));
        arrayList.add(createRectangle(0,5,2,9,15));
        arrayList.add(createRectangle(3,0,8,1,12));
        arrayList.add(createRectangle(3,2,7,4,15));
        arrayList.add(createRectangle(3,5,5,5,3));
        arrayList.add(createRectangle(3,6,3,8,3));
        arrayList.add(createRectangle(3,9,9,9,7));
        arrayList.add(createRectangle(4,6,5,8,6));
        arrayList.add(createRectangle(6,5,9,6,8));
        arrayList.add(createRectangle(8,2,9,4,6));
        arrayList.add(createRectangle(6,7,9,8,8));
        arrayList.add(createRectangle(9,0,9,1,2));

        Checker checker = new Checker(null, arrayList, level);
        boolean isValid = checker.validateBoard();
        assertFalse(isValid);
    }
    @Test
    public void testFalseDiff10Lev10() {

    }

    //15x15
    @Test
    public void pengujianSulitJawabanBenar() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Level level= new Level(context, 10);
        level.readPuzzles(0);

        ArrayList<Rectangle> arrayList= new ArrayList<>();

        arrayList.add(createRectangle(0,0,0,1,2));
        arrayList.add(createRectangle(0,2,0,4,3));
        arrayList.add(createRectangle(0,5,1,9,10));
        arrayList.add(createRectangle(0,10,1,10,2));
        arrayList.add(createRectangle(0,11,1,13,6));
        arrayList.add(createRectangle(0,14,1,14,2));
        arrayList.add(createRectangle(1,0,2,0,2));
        arrayList.add(createRectangle(1,1,5,4,20));
        arrayList.add(createRectangle(2,5,5,10,24));
        arrayList.add(createRectangle(2,11,3,11,2));
        arrayList.add(createRectangle(2,12,5,12,4));
        arrayList.add(createRectangle(2,13,2,14,2));
        arrayList.add(createRectangle(3,0,5,0,3));
        arrayList.add(createRectangle(4,11,5,11,2));
        arrayList.add(createRectangle(3,13,9,13,7));
        arrayList.add(createRectangle(3,14,4,14,2));
        arrayList.add(createRectangle(5,14,12,14,8));
        arrayList.add(createRectangle(6,0,6,3,4));
        arrayList.add(createRectangle(6,4,6,10,7));
        arrayList.add(createRectangle(6,11,6,12,2));
        arrayList.add(createRectangle(7,0,7,3,4));
        arrayList.add(createRectangle(7,4,8,8,10));
        arrayList.add(createRectangle(7,9,9,12,12));
        arrayList.add(createRectangle(8,0,8,3,4));
        arrayList.add(createRectangle(9,0,10,0,2));
        arrayList.add(createRectangle(9,1,13,2,10));
        arrayList.add(createRectangle(9,3,11,3,3));
        arrayList.add(createRectangle(9,4,10,7,8));
        arrayList.add(createRectangle(9,8,10,8,2));
        arrayList.add(createRectangle(10,9,10,13,5));
        arrayList.add(createRectangle(11,0,13,0,3));
        arrayList.add(createRectangle(11,4,11,5,2));
        arrayList.add(createRectangle(11,6,11,8,3));
        arrayList.add(createRectangle(11,9,11,11,3));
        arrayList.add(createRectangle(11,12,13,12,3));
        arrayList.add(createRectangle(11,13,12,13,2));
        arrayList.add(createRectangle(12,3,13,10,16));
        arrayList.add(createRectangle(12,11,13,11,2));
        arrayList.add(createRectangle(13,13,13,14,2));
        arrayList.add(createRectangle(14,0,14,1,2));
        arrayList.add(createRectangle(14,2,14,10,9));
        arrayList.add(createRectangle(14,11,14,14,4));

        Checker checker = new Checker(null, arrayList, level);
        boolean isValid = checker.validateBoard();
        assertFalse(isValid);
    }
    @Test
    public void pengujianSulitJawabanSalah() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Level level= new Level(context, 10);
        level.readPuzzles(0);

        ArrayList<Rectangle> arrayList= new ArrayList<>();

        arrayList.add(createRectangle(0,0,0,1,2));
        arrayList.add(createRectangle(0,2,0,4,3));
        arrayList.add(createRectangle(0,5,1,9,10));
        arrayList.add(createRectangle(0,10,1,10,2));
        arrayList.add(createRectangle(0,11,1,13,6));
        arrayList.add(createRectangle(0,14,1,14,2));
        arrayList.add(createRectangle(1,0,2,0,2));
        arrayList.add(createRectangle(1,1,5,4,20));
        arrayList.add(createRectangle(2,5,5,10,24));
        arrayList.add(createRectangle(2,11,3,11,2));
        arrayList.add(createRectangle(2,12,5,12,4));
        arrayList.add(createRectangle(2,13,2,14,2));
        arrayList.add(createRectangle(3,0,5,0,3));
        arrayList.add(createRectangle(4,11,5,11,2));
        arrayList.add(createRectangle(3,13,9,13,7));
        arrayList.add(createRectangle(3,14,4,14,2));
        arrayList.add(createRectangle(5,14,12,14,8));
        arrayList.add(createRectangle(6,0,6,3,4));
        arrayList.add(createRectangle(6,4,6,10,7));
        arrayList.add(createRectangle(6,11,6,12,2));
        arrayList.add(createRectangle(7,0,7,3,4));
        arrayList.add(createRectangle(7,4,8,8,10));
        arrayList.add(createRectangle(7,9,9,12,12));
        arrayList.add(createRectangle(8,0,8,3,4));
        arrayList.add(createRectangle(9,0,10,0,2));
        arrayList.add(createRectangle(9,1,13,2,10));
        arrayList.add(createRectangle(9,3,11,3,3));
        arrayList.add(createRectangle(9,4,10,7,8));
        arrayList.add(createRectangle(9,8,10,8,2));
        arrayList.add(createRectangle(10,9,10,13,5));
        arrayList.add(createRectangle(11,0,13,0,3));
        arrayList.add(createRectangle(11,4,11,5,2));
        arrayList.add(createRectangle(11,6,11,8,3));
        arrayList.add(createRectangle(11,9,11,11,3));
        arrayList.add(createRectangle(11,12,13,12,3));
        arrayList.add(createRectangle(11,13,12,13,2));
        arrayList.add(createRectangle(12,3,13,10,16));
        arrayList.add(createRectangle(12,11,13,11,2));
        arrayList.add(createRectangle(13,13,14,14,4));
        arrayList.add(createRectangle(14,0,14,1,2));
        arrayList.add(createRectangle(14,2,14,10,9));
        arrayList.add(createRectangle(14,11,14,12,2));

        Checker checker = new Checker(null, arrayList, level);
        boolean isValid = checker.validateBoard();
        assertFalse(isValid);
    }
}
