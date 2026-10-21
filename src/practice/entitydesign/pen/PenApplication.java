package practice.entitydesign.pen;

import practice.entitydesign.pen.entity.Ink;
import practice.entitydesign.pen.entity.Pen;
import practice.entitydesign.pen.entity.Refill;
import practice.entitydesign.pen.enums.PenType;
import practice.entitydesign.pen.strategy.Cap;
import practice.entitydesign.pen.strategy.ClickButton;

public class PenApplication {

    public static void main(String[] args) {

        // --------------------------------
        // GEL PEN
        // --------------------------------
        Ink blueInk = new Ink("Blue");
        Refill blueRefill = new Refill(blueInk, 0.5);

        Pen gelPen = new Pen(
                "Reynolds",
                "Trimax",
                50,
                PenType.GEL,
                blueRefill,
                null,
                new Cap()
        );

        gelPen.open();
        gelPen.write();
        gelPen.close();
        System.out.println();


        // --------------------------------
        // BALL PEN
        // --------------------------------
        Ink blackInk = new Ink("Black");

        Refill blackRefill = new Refill(blackInk, 0.7);
        Pen ballPen = new Pen(
                "Parker",
                "Jotter",
                100,
                PenType.BALL,
                blackRefill,
                null,
                new ClickButton()
        );

        ballPen.open();
        ballPen.write();
        ballPen.close();
        System.out.println();


        // --------------------------------
        // FOUNTAIN PEN
        // --------------------------------
        Ink fountainInk = new Ink("Blue");

        Pen fountainPen = new Pen(
                "Parker",
                "Vector",
                1000,
                PenType.FOUNTAIN,
                null,
                fountainInk,
                new Cap()
        );

        fountainPen.open();
        fountainPen.write();
        fountainPen.close();
        System.out.println();


        // --------------------------------
        // REFILL GEL PEN
        // --------------------------------
        Ink redInk = new Ink("Red");
        Refill redRefill = new Refill(redInk, 0.5);
        gelPen.open();
        gelPen.refill(redRefill);
        gelPen.write();
        gelPen.close();
    }
}