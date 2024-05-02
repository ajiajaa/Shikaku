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
    public void testTrueDiff5Lev1() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Level level= new Level(context, 5);
        level.readPuzzles(0);

        ArrayList<Rectangle> arrayList= new ArrayList<>();

        arrayList.add(createRectangle(0, 0, 2, 2, 9));
        arrayList.add(createRectangle(0, 3, 0, 4, 2));
        arrayList.add(createRectangle(1, 4, 4, 4, 4));
        arrayList.add(createRectangle(1, 3, 3, 3, 3));
        arrayList.add(createRectangle(3, 0, 3, 2, 3));
        arrayList.add(createRectangle(4, 0, 4, 1, 2));
        arrayList.add(createRectangle(4, 2, 4, 3, 2));

        Checker checker = new Checker(null, arrayList, level);
        boolean isValid = checker.validateBoard();
        assertTrue(isValid);
    }

    @Test
    public void testFalseDiff5Lev1() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Level level= new Level(context, 5);
        level.readPuzzles(0);

        ArrayList<Rectangle> arrayList= new ArrayList<>();

        arrayList.add(createRectangle(0, 0, 2, 2, 9));
        arrayList.add(createRectangle(0, 3, 0, 4, 2));
        arrayList.add(createRectangle(1, 4, 4, 4, 4));
        arrayList.add(createRectangle(1, 3, 3, 3, 3));
        arrayList.add(createRectangle(3, 0, 3, 2, 3));
        arrayList.add(createRectangle(4, 0, 4, 3, 4));

        Checker checker = new Checker(null, arrayList, level);
        boolean isValid = checker.validateBoard();
        assertFalse(isValid);
    }
    @Test
    public void testTrueDiff5Lev10() {
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
    public void testFalseDiff5Lev10() {
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
    public void testTrueDiff10Lev1() {
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
    public void testFalseDiff10Lev1() {

    }
    @Test
    public void testTrueDiff10Lev10() {
    }
    @Test
    public void testFalseDiff10Lev10() {

    }

    //15x15
    @Test
    public void testTrueDiff15Lev1() {
    }
    @Test
    public void testFalseDiff15Lev1() {

    }
    @Test
    public void testTrueDiff15Lev2() {
    }
    @Test
    public void testFalseDiff15Lev2() {

    }
}
