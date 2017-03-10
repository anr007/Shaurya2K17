package red.shaurya2k17;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;
import su.levenetc.android.textsurface.animations.AnimationsSet;
import su.levenetc.android.textsurface.animations.Circle;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Loop;
import su.levenetc.android.textsurface.animations.Rotate3D;
import su.levenetc.android.textsurface.animations.ShapeReveal;
import su.levenetc.android.textsurface.animations.SideCut;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.animations.TransSurface;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Axis;
import su.levenetc.android.textsurface.contants.Direction;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.Side;
import su.levenetc.android.textsurface.contants.TYPE;

public class AboutNITMZ extends AppCompatActivity {

    TextSurface textSurface;

    private boolean started = false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_nitmz);

        setupWindowAnimations();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        }

        textSurface=(TextSurface)findViewById(R.id.about_nitmz_text_surface);

        show();
        //start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            show();
            if(started) {
                start();
            }
        }
    };



    public void stop() {
        started = false;
        handler.removeCallbacks(runnable);
    }

    public void start() {
        started = true;
        handler.postDelayed(runnable, 10000);
    }

    public static void play(TextSurface textSurface) {

        Text textA = TextBuilder.create("Shaurya 2K17 is the Annual Sports meet of NIT Mizoram")
                .setPosition(Align.SURFACE_CENTER)
                .setSize(15)
                .setColor(android.R.color.black)
                .build();
        Text textB = TextBuilder.create("All the Departments of NIT Mizoram Participate In this Event")
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textA)
                .setSize(15)
                .setColor(android.R.color.black)
                .build();
        Text textC = TextBuilder.create("somethinggggggggggg")
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textB)
                .setSize(30)
                .setColor(android.R.color.black)
                .build();
        Text textD = TextBuilder.create("somethinggggggggggggggg")
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textC)
                .setSize(30)
                .setColor(android.R.color.black)
                .build();
        Text textE = TextBuilder.create("somethinggggg")
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textD)
                .setSize(30)
                .setColor(android.R.color.black)
                .build();
        Text textF = TextBuilder.create("somethinggg")
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textE)
                .setSize(30)
                .setColor(android.R.color.black)
                .build();
        Text textG = TextBuilder.create("somethingg")
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textF)
                .setSize(30)
                .setColor(android.R.color.black)
                .build();
        Text textH = TextBuilder.create("something")
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textG)
                .setSize(30)
                .setColor(android.R.color.black)
                .build();


        final int flash = 1500;

        textSurface.play(
                new Loop(
                        Rotate3D.showFromCenter(textA, 500, Direction.CLOCK, Axis.X),
                        new AnimationsSet(TYPE.PARALLEL,
                                ShapeReveal.create(textA, flash, SideCut.hide(Side.LEFT), false),
                                new AnimationsSet(TYPE.SEQUENTIAL, Delay.duration(flash / 4), ShapeReveal.create(textA, flash, SideCut.show(Side.LEFT), false))
                        ),
                        new AnimationsSet(TYPE.PARALLEL,
                                Rotate3D.showFromSide(textB, 500, Pivot.TOP),
                                new TransSurface(500, textB, Pivot.CENTER)
                        ),
                        Delay.duration(500),
                        new AnimationsSet(TYPE.PARALLEL,
                                Slide.showFrom(Side.TOP, textC, 500),
                                new TransSurface(1000, textC, Pivot.CENTER)
                        ),
                        Delay.duration(500),
                        new AnimationsSet(TYPE.PARALLEL,
                                ShapeReveal.create(textD, 500, Circle.show(Side.CENTER, Direction.OUT), false),
                                new TransSurface(1500, textD, Pivot.CENTER)
                        ),
                        Delay.duration(500),
                        new AnimationsSet(TYPE.PARALLEL,
                                ShapeReveal.create(textE, 500, Circle.show(Side.CENTER, Direction.OUT), false),
                                new TransSurface(1500, textE, Pivot.CENTER)
                        ),
                        Delay.duration(500),
                        new AnimationsSet(TYPE.PARALLEL,
                                ShapeReveal.create(textF, 500, Circle.show(Side.CENTER, Direction.OUT), false),
                                new TransSurface(1500, textF, Pivot.CENTER)
                        ),
                        Delay.duration(500),
                        new AnimationsSet(TYPE.PARALLEL,
                                ShapeReveal.create(textG, 500, Circle.show(Side.CENTER, Direction.OUT), false),
                                new TransSurface(1500, textG, Pivot.CENTER)
                        ),
                        Delay.duration(500),
                        new AnimationsSet(TYPE.PARALLEL,
                                ShapeReveal.create(textH, 500, Circle.show(Side.CENTER, Direction.OUT), false),
                                new TransSurface(1500, textH, Pivot.CENTER)
                        ),
                        Delay.duration(500),

                        new AnimationsSet(TYPE.PARALLEL,
                                new AnimationsSet(TYPE.PARALLEL, Alpha.hide(textH, 700), ShapeReveal.create(textH, 1000, SideCut.hide(Side.LEFT), true)),
                                new AnimationsSet(TYPE.SEQUENTIAL, Delay.duration(500), new AnimationsSet(TYPE.PARALLEL, Alpha.hide(textG, 700), ShapeReveal.create(textG, 1000, SideCut.hide(Side.LEFT), true))),
                                new AnimationsSet(TYPE.SEQUENTIAL, Delay.duration(1000), new AnimationsSet(TYPE.PARALLEL, Alpha.hide(textF, 700), ShapeReveal.create(textF, 1000, SideCut.hide(Side.LEFT), true))),
                                new AnimationsSet(TYPE.SEQUENTIAL, Delay.duration(1500), new AnimationsSet(TYPE.PARALLEL, Alpha.hide(textE, 700), ShapeReveal.create(textE, 1000, SideCut.hide(Side.LEFT), true))),
                                new AnimationsSet(TYPE.SEQUENTIAL, Delay.duration(2000), new AnimationsSet(TYPE.PARALLEL, Alpha.hide(textD, 700), ShapeReveal.create(textD, 1000, SideCut.hide(Side.LEFT), true))),
                                new AnimationsSet(TYPE.SEQUENTIAL, Delay.duration(2500), new AnimationsSet(TYPE.PARALLEL, Alpha.hide(textC, 700), ShapeReveal.create(textC, 1000, SideCut.hide(Side.LEFT), true))),
                                new AnimationsSet(TYPE.SEQUENTIAL, Delay.duration(3000), new AnimationsSet(TYPE.PARALLEL, Alpha.hide(textB, 700), ShapeReveal.create(textB, 1000, SideCut.hide(Side.LEFT), true))),
                                new AnimationsSet(TYPE.SEQUENTIAL, Delay.duration(3500), new AnimationsSet(TYPE.PARALLEL, Alpha.hide(textA, 700), ShapeReveal.create(textA, 1000, SideCut.hide(Side.LEFT), true))),
                                new TransSurface(4000, textA, Pivot.CENTER)
                        )
                )
        );
    }

    private void show() {
        textSurface.reset();
        play(textSurface);
    }

    private void setupWindowAnimations() {

        if (Build.VERSION.SDK_INT >= 21) {
            android.transition.Slide slideExit = new android.transition.Slide(Gravity.START);
            slideExit.setDuration(400);
            getWindow().setExitTransition(slideExit);

            android.transition.Slide slideEnter = new android.transition.Slide(Gravity.END);
            slideEnter.setDuration(400);
            getWindow().setEnterTransition(slideEnter);


        }


    }

    @Override
    protected void onDestroy() {
        stop();
        super.onDestroy();
    }
}
