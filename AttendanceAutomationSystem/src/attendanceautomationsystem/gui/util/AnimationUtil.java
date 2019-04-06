/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.gui.util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Acer
 */
public class AnimationUtil {
        
    public static SequentialTransition createShakingAnimation(Node element)
    {
        int duration = 70;
        int count = 2;
        
        SequentialTransition transition = new SequentialTransition();

        TranslateTransition transition1 = new TranslateTransition(Duration.millis(duration), element);
        transition1.setFromX(0);
        transition1.setToX(-6);
        transition1.setInterpolator(Interpolator.LINEAR);

        TranslateTransition transition2 = new TranslateTransition(Duration.millis(duration), element);
        transition2.setFromX(-6);
        transition2.setToX(6);
        transition2.setInterpolator(Interpolator.LINEAR);
        transition2.setCycleCount(count);

        TranslateTransition transition3 = new TranslateTransition(Duration.millis(duration), element);
        transition3.setToX(0);
        transition3.setInterpolator(Interpolator.LINEAR);

        transition.getChildren().addAll(transition1,transition2,transition3);
            
        return transition;
    }
    
    public static FadeTransition createDelayedDisplayAnimation(Pane background)
    {
        background.setOpacity(0);
        FadeTransition fade = new FadeTransition(Duration.millis(600),background);
        fade.setFromValue(0);
        fade.setToValue(1.0);
        fade.setDelay(Duration.millis(300));
        return fade;
    }
    
    public static FadeTransition createFadeAnimation(Pane background)
    {
        FadeTransition fade = new FadeTransition(Duration.millis(600),background);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        return fade;
    }

    public static ScaleTransition createEnlargmentAnimation(Node element)
    {
        ScaleTransition st = new ScaleTransition(Duration.millis(475), element);
        st.setFromX(0);
        st.setFromY(0);
        st.setToX(1);
        st.setToY(1);

        return st;
    }
    
    public static SequentialTransition createPopupAnimation(Node element)
    {
        SequentialTransition transition = new SequentialTransition();

        
        ScaleTransition transition1 = new ScaleTransition(Duration.millis(400), element);
        transition1.setFromX(0.6);
        transition1.setFromY(0.6);
        transition1.setToX(1);
        transition1.setToY(1);
        
        ScaleTransition transition2 = new ScaleTransition(Duration.millis(100), element);
        transition2.setFromX(1.3);
        transition2.setFromY(1.3);
        transition2.setToX(1);
        transition2.setToY(1);

        transition.getChildren().addAll(transition1,transition2);

        return transition;
    }
    
    public static ParallelTransition createFadeWithHorizontalSlideAnimation(int startingPosition, int endingPosition, Node element)
    {
        int slideDuration = 240;
        int fadeDuration = 240;
        ParallelTransition transition = new ParallelTransition();
        TranslateTransition translate = new TranslateTransition(Duration.millis(slideDuration), element);
        translate.setFromX(startingPosition);
        translate.setToX(endingPosition);
        FadeTransition fade = new FadeTransition(Duration.millis(fadeDuration), element);
        fade.setToValue(0);
        transition.getChildren().addAll(translate,fade);
        return transition;
    }
    
    public static ParallelTransition createDisplayWithHorizontalSlideAnimation(int startingPosition, int endingPosition, Node element)
    {
        int slideDuration = 240;
        int fadeDuration = 240;
        ParallelTransition transition = new ParallelTransition();
        TranslateTransition translate = new TranslateTransition(Duration.millis(slideDuration), element);
        translate.setFromX(startingPosition);
        translate.setToX(endingPosition);
        FadeTransition fade = new FadeTransition(Duration.millis(fadeDuration), element);
        fade.setToValue(1);
        transition.getChildren().addAll(translate,fade);
        return transition;
    }

    public static Timeline createDisplayStageAnimation(Stage currentStage)
    {
        currentStage.setOpacity(0);
        final Timeline timeline = new Timeline();
        final KeyValue kv = new KeyValue(currentStage.opacityProperty(), 1);
        final KeyFrame kf = new KeyFrame(Duration.millis(200), kv);
        timeline.getKeyFrames().add(kf);
        return timeline;
    }
    
    public static Timeline createFadeStageAnimation(Stage currentStage)
    {       
        currentStage.setOpacity(1);
        final KeyValue kv = new KeyValue(currentStage.opacityProperty(), 0);
        final Timeline timeline = new Timeline();
        final KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        timeline.getKeyFrames().add(kf);
        return timeline;
    }
    
    public static Timeline createVerticalSlideWithFadingIn(Stage currentStage)
    {
        currentStage.setY(currentStage.getY()- 50);
        AtomicInteger moveCount = new AtomicInteger(0);
        int delay = 1;
        int period = 9;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {                   
                moveCount.incrementAndGet();
                currentStage.setY(currentStage.getY()+1.4);
                int limit = moveCount.get();
                if(limit >= 35)
                {
                    timer.cancel();
                }
            }
        }, delay, period);
        currentStage.setOpacity(0);
        final Timeline timeline = new Timeline();
        final KeyValue kv = new KeyValue(currentStage.opacityProperty(), 1);
        final KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        timeline.getKeyFrames().add(kf);
        return timeline;
    }
    
    public static Timeline createVerticalSlideWithFadingOut(Stage currentStage)
    {       
        AtomicInteger moveCount = new AtomicInteger(0);
        int delay = 1;
        int period = 9;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                moveCount.incrementAndGet();
                currentStage.setY(currentStage.getY()-1.4);
                int limit = moveCount.get();
                if(limit >= 70)
                {
                    timer.cancel();
                }
            }
        }, delay, period);
        currentStage.setOpacity(1);
        final KeyValue kv = new KeyValue(currentStage.opacityProperty(), 0);
        final Timeline timeline = new Timeline();
        final KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        timeline.getKeyFrames().add(kf);
        return timeline;
    }
    
}
