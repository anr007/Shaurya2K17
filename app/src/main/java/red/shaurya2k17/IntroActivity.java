package red.shaurya2k17;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Circle;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Rotate3D;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.ShapeReveal;
import su.levenetc.android.textsurface.animations.SideCut;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.animations.TransSurface;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Direction;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.Side;

public class IntroActivity extends AppCompatActivity {

    TextSurface textSurface;

    private boolean started = false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        setupWindowAnimations();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.setNavigationBarColor(getResources().getColor(R.color.mred));
            window.setStatusBarColor(getResources().getColor(R.color.mred));

        }

        textSurface=(TextSurface)findViewById(R.id.intro_text_surface);

        show();
        start();





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




    public static void play(TextSurface textSurface, AssetManager assetManager) {

        final Typeface robotoBlack = Typeface.createFromAsset(assetManager, "fonts/Roboto-Black.ttf");
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(robotoBlack);

        Text textHai = TextBuilder
                .create("Hi")
                .setPaint(paint)
                .setSize(54)
                .setAlpha(0)
                .setColor(Color.WHITE)
                .setPosition(Align.SURFACE_CENTER).build();

        Text textFromLead = TextBuilder
                .create("From Project Lead")
                .setPaint(paint)
                .setSize(40)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF, textHai).build();

        Text textReddy = TextBuilder
                .create("  Narayana Reddy")
                .setPaint(paint)
                .setSize(46)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.RIGHT_OF, textFromLead).build();

        Text textHello = TextBuilder
                .create("Hello !!")
                .setPaint(paint)
                .setSize(40)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textReddy).build();

        Text textEveryone = TextBuilder
                .create("Everyone")
                .setPaint(paint)
                .setSize(40)
                .setAlpha(0)
                .setColor(Color.WHITE)
                .setPosition(Align.BOTTOM_OF, textHello).build();

        Text textFrom = TextBuilder
                .create(" From Co Devs")
                .setPaint(paint)
                .setSize(40)
                .setAlpha(0)
                .setColor(Color.WHITE)
                .setPosition(Align.RIGHT_OF | Align.CENTER_OF, textEveryone).build();

        Text textShantanu = TextBuilder
                .create("Shantanu Acharya")
                .setPaint(paint)
                .setSize(40)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textFrom).build();

        Text textAnd= TextBuilder
                .create("&")
                .setPaint(paint)
                .setSize(38)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textShantanu).build();

        Text textAbhishek = TextBuilder
                .create("Abhishek Sharma")
                .setPaint(paint)
                .setSize(40)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textAnd).build();

        Text textSmile = TextBuilder
                .create(" :-) ")
                .setPaint(paint)
                .setSize(44)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textAbhishek).build();

        textSurface.play(
                new Sequential(
                        ShapeReveal.create(textHai, 750, SideCut.show(Side.LEFT), false),
                        new Parallel(ShapeReveal.create(textHai, 600, SideCut.hide(Side.LEFT), false), new Sequential(Delay.duration(300), ShapeReveal.create(textHai, 600, SideCut.show(Side.LEFT), false))),
                        Delay.duration(300),
                        new Parallel(new TransSurface(500, textFromLead, Pivot.CENTER),  ShapeReveal.create(textFromLead, 1500, SideCut.show(Side.LEFT), false)),
                        new Sequential(Delay.duration(1200)),
                        new Parallel(new TransSurface(750, textReddy, Pivot.CENTER), Slide.showFrom(Side.LEFT, textReddy, 750), ChangeColor.to(textReddy, 750, Color.WHITE)),
                        Delay.duration(800),
                        new Parallel(TransSurface.toCenter(textHello, 500), Rotate3D.showFromSide(textHello, 750, Pivot.TOP)),
                        new Parallel(TransSurface.toCenter(textEveryone, 500), Slide.showFrom(Side.TOP, textEveryone, 500)),
                        new Parallel(TransSurface.toCenter(textFrom, 750), Slide.showFrom(Side.LEFT, textFrom, 500)),
                        Delay.duration(500),
                        new Parallel(
                                new TransSurface(1500, textSmile, Pivot.CENTER),
                                new Sequential(
                                        new Sequential(ShapeReveal.create(textShantanu, 500, Circle.show(Side.CENTER, Direction.OUT), false)),
                                        new Sequential(ShapeReveal.create(textAnd, 500, Circle.show(Side.CENTER, Direction.OUT), false)),
                                        new Sequential(ShapeReveal.create(textAbhishek, 500, Circle.show(Side.CENTER, Direction.OUT), false)),
                                        new Sequential(ShapeReveal.create(textSmile, 500, Circle.show(Side.CENTER, Direction.OUT), false))
                                )
                        ),
                        Delay.duration(200),
                        new Parallel(
                                ShapeReveal.create(textShantanu, 1500, SideCut.hide(Side.LEFT), true),
                                new Sequential(Delay.duration(250), ShapeReveal.create(textAnd, 1500, SideCut.hide(Side.LEFT), true)),
                                new Sequential(Delay.duration(250), ShapeReveal.create(textAbhishek, 1500, SideCut.hide(Side.LEFT), true)),
                                new Sequential(Delay.duration(500), ShapeReveal.create(textSmile, 1500, SideCut.hide(Side.LEFT), true)),
                                Alpha.hide(textFrom, 1500),
                                Alpha.hide(textEveryone, 1500)
                        )
                )

        );


    }

    private void show() {
        textSurface.reset();
        play(textSurface, getAssets());
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
