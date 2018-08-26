package javafx.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Graph {

    private JFrame window;
    private XYSeries series;

    public Graph(String title, String yAxisLabel) {
        window = new JFrame();
        window.setTitle(title);
        window.setSize(600, 400);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                window.setVisible(false);
            }
        });
        series = new XYSeries("Light Sensor Readings");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(yAxisLabel, "Час (сек)", yAxisLabel, dataset,
                PlotOrientation.VERTICAL, false, true, false);
        try {
            chart.setBackgroundImage(ImageIO.read(new URL(
                    new File(System.getProperty("user.dir") +
                            File.separator + "resources" +
                            File.separator + "javafx" +
                            File.separator + "images" +
                            File.separator + "background.jpg").toURI().toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        chart.setBackgroundPaint(new Color(0, 132, 181));
        chart.getXYPlot().getDomainAxis().setTickLabelPaint(Color.WHITE);
        chart.getXYPlot().getRangeAxis().setTickLabelPaint(Color.WHITE);
        chart.getTitle().setPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setLabelPaint(Color.WHITE);
        chart.getXYPlot().getRangeAxis().setLabelPaint(Color.WHITE);
        chart.removeLegend();
        AbstractRenderer xy = (AbstractRenderer) chart.getXYPlot().getRenderer();
        xy.setSeriesPaint(0, new Color(0, 132, 181));
        window.add(new ChartPanel(chart), BorderLayout.CENTER);
    }

    public boolean isClose(){
        return window.isVisible();
    }

    public JFrame getWindow() {
        return window;
    }

    public XYSeries getSeries() {
        return series;
    }

}
