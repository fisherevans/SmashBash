package com.fisherevans.wipgame.launcher;

import com.fisherevans.wipgame.GameLauncher;
import com.fisherevans.wipgame.resources.Settings;
import net.miginfocom.swing.MigLayout;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Launcher implements ActionListener {
    private Resolution[] _displayModes;

    private JFrame _frame;
    private JPanel _panel;
    private Resolution _currentResolution;

    private JComboBox<Resolution> _comboBoxResolution;
    private JComboBox<String> _comboBoxFullscreen;

    public Launcher() {
        JFrame loadingFrame = getLoadingFrame();
        loadingFrame.setVisible(true);

        try {
            GameLauncher.loadResources();
            _displayModes = getResolutions();
            initLaunchFrame();
        } catch (Exception e) {
            String error = "<h1>Fatal Error</h1>"
                    + "<p>An error was encountered while loading the game resources and the program has exited..."
                    + "<br><br>" + e.toString() + "</p>";
            JOptionPane.showMessageDialog(loadingFrame,
                    "<html>" + error + "</html>",
                    "Fatal Error Occurred",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }


        loadingFrame.dispose();
        _frame.setVisible(true);
        _frame.toFront();
        _frame.setState(Frame.NORMAL);
    }

    private Resolution[] getResolutions() throws LWJGLException {
        List<Resolution> resolutionList = new ArrayList<Resolution>();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        for(DisplayMode displayMode:Display.getAvailableDisplayModes()) {
            if(displayMode.getFrequency() == 60
                    && displayMode.getBitsPerPixel() == 32
                    && displayMode.getWidth()*displayMode.getHeight() >= 480000) {
                Resolution resolution = new Resolution(displayMode);
                resolutionList.add(resolution);
                if(displayMode.getWidth() == dim.getWidth()
                        && displayMode.getHeight() == dim.getHeight())
                    _currentResolution = resolution;
            }
        }
        Collections.sort(resolutionList);
        Resolution[] resolutions = new Resolution[resolutionList.size()];
        for(int i = 0;i < resolutionList.size();i++)
            resolutions[i] = resolutionList.get(i);
        return resolutions;
    }

    private JFrame getLoadingFrame() {
        JFrame loadingFrame = new JFrame("");
        loadingFrame.add(new JLabel(new ImageIcon("res/img/launcher/loading_01.png"), SwingConstants.CENTER));

        loadingFrame.setUndecorated(true);
        loadingFrame.setResizable(false);
        loadingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIcon(loadingFrame);

        loadingFrame.pack();
        centerJFrame(loadingFrame);

        return loadingFrame;
    }

    private void initLaunchFrame() {
        _frame = new JFrame(Settings.getString("launcher.title"));
        _panel = new JPanel(new MigLayout());
        _frame.add(_panel);

        _frame.setResizable(false);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labelTitle = new JLabel(Settings.getString("launcher.title"), SwingConstants.CENTER);
        JLabel labelResolution = new JLabel(Settings.getString("launcher.resolution"), SwingConstants.RIGHT);
        JLabel labelFullscreen = new JLabel(Settings.getString("launcher.fullscreen"), SwingConstants.RIGHT);

        _comboBoxResolution = new JComboBox(_displayModes);
        _comboBoxFullscreen = new JComboBox(new String[] {Settings.getString("launcher.fullscreen.true"), Settings.getString("launcher.fullscreen.false")});

        JButton buttonLaunch = new JButton(Settings.getString("launcher.launch"));

        labelTitle.setFont(labelTitle.getFont().deriveFont(18f));
        _comboBoxResolution.setSelectedItem(_currentResolution);
        buttonLaunch.addActionListener(this);

        _panel.add(labelTitle, "span 2,growx,wrap,gapbottom 10px, gapleft 50px, gapright 50px");
        _panel.add(labelResolution);
        _panel.add(_comboBoxResolution, "growx,wrap");
        _panel.add(labelFullscreen);
        _panel.add(_comboBoxFullscreen, "growx,wrap");
        _panel.add(buttonLaunch, "span 2,wrap,growx,gaptop 10px");

        setIcon(_frame);
        _frame.pack();
        centerJFrame(_frame);

        buttonLaunch.grabFocus();
    }

    private void closeLauncher() {
        _frame.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Resolution selectedResolution = (Resolution) _comboBoxResolution.getSelectedItem();
        String selectedFullscreen = (String) _comboBoxFullscreen.getSelectedItem();
        if(selectedResolution == null || selectedFullscreen == null) {
            JOptionPane.showMessageDialog(_frame,
                    selectedFullscreen == null ? "Please select a valid 'Resolution' option"
                            : "Please select a valid 'Render In' option",
                    "Invalid Configuration",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        closeLauncher();
        GameLauncher.startGame(selectedResolution.getDisplayMode(), selectedFullscreen.equals(Settings.getString("launcher.fullscreen.true")));
    }

    private static void centerJFrame(JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    private static void setIcon(JFrame frame) {
        try {
            File iconFile = new File("res/img/icon.png");
            ImageIcon icon = new ImageIcon(iconFile.getAbsoluteFile().toURI().toURL());
            frame.setIconImage(icon.getImage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private class Resolution implements Comparable {
        private DisplayMode _displayMode;

        private Resolution(DisplayMode displayMode) {
            _displayMode = displayMode;
        }

        private DisplayMode getDisplayMode() {
            return _displayMode;
        }

        @Override
        public String toString() {
            return _displayMode.getWidth() + "x" + _displayMode.getHeight();
        }

        @Override
        public int compareTo(Object o) {
            if(o instanceof Resolution) {
                Resolution r = (Resolution) o;
                return r.getDisplayMode().getWidth()*r.getDisplayMode().getHeight()
                        - _displayMode.getWidth()*_displayMode.getHeight();
            } else
                return -1;
        }
    }


}
