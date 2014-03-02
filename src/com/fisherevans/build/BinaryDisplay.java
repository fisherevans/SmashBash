package com.fisherevans.build;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class BinaryDisplay extends JFrame implements KeyListener {
    public static final long TIME_PER_FRAME = 10;

    public static final int WIDTH = 1920;

    public static final int HEIGHT = 1080;

    public static final int BIT_SIZE = 16;

    public static final float FADE_SPEED = 4f;

    public static final float HUE_SPEED = 1/6f;

    public static BinaryDisplay frame;

    public static boolean running = false;

    public static void main(String[] args) {
        frame = new BinaryDisplay();
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(true);
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    private long _startTime, _lastUpdate;

    private double _timeElapsedD;

    private int _current = 0;

    private float _hue = 0;

    private float[] _colors = new float[BIT_SIZE];

    public BinaryDisplay() {
        addKeyListener(this);
        init();
    }

    public void init() {
        _startTime = System.currentTimeMillis();
        _lastUpdate = System.currentTimeMillis();
    }

    private int getNumber(long timeElapsed) {
        _timeElapsedD = timeElapsed / 1000.0;
        //return (int)(_timeElapsedD*2);
        return (int) (_timeElapsedD * _timeElapsedD * _timeElapsedD);
        //return (int) (_timeElapsedD * _timeElapsedD);
        //return (int)(_timeElapsedD*11930464);
        //return (int)Math.pow(2, _timeElapsedD);
    }

    private void update() {
        long delta = System.currentTimeMillis() - _lastUpdate;
        _lastUpdate = System.currentTimeMillis();

        _current = getNumber(System.currentTimeMillis() - _startTime);

        _hue += delta/1000f*HUE_SPEED;
        _hue %= 1f;

        String bin = Integer.toBinaryString(_current);
        char c;
        for (int id = 1; id <= bin.length(); id++) {
            c = bin.charAt(bin.length() - id);
            if(c == '1')
                _colors[BIT_SIZE - id] = 1f;
            else
                _colors[BIT_SIZE - id] -= delta / 1000f * FADE_SPEED;
        }
    }

    @Override
    public void paint(Graphics globalG) {
        update();

        Image buffer = createImage(WIDTH, HEIGHT);
        Graphics g = buffer.getGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        Color color;
        int width = WIDTH / BIT_SIZE;
        float v;
        for (int id = 0; id < _colors.length; id++) {
            v = _colors[id] < 0 ? 0 : _colors[id];
            //color = new Color(v, v, v);
            color = Color.getHSBColor(_hue, 1f, v);
            g.setColor(color);
            g.fillRect(id * width, 0, width, HEIGHT);
        }

        globalG.drawImage(buffer, 0, 0, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(running)
            System.exit(0);
        else {
            running = true;

            frame.init();
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            frame.repaint();
                            Thread.sleep(TIME_PER_FRAME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                    }
                }
            }.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}