/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Lenovo
 */
@ManagedBean(name="containerOverallViewLegend")
@RequestScoped

public class ContainerOverallViewLegend {
    @EJB
    MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB MasterSettingAppFacadeRemote masterSettingAppFacadeRemote;
    
    StreamedContent legend, notes, statistic;
    StreamedContent chart;
    
    List<String> blocks;
    
    @PostConstruct
    public void initData(){
        try {
//            Long capacity = masterYardFacadeRemote.findYardCapacity();
            int capacity = masterSettingAppFacadeRemote.findTotalCYCapacity();
//            int totalBox = Integer.valueOf(capacity.intValue());

            drawTotalCapacities(capacity);
            drawNotes();
            Integer[] objs = new Integer[3];
            objs = masterYardCoordinatFacadeRemote.findStatistics();
            drawYardStatistics(objs[0], objs[1]/2, objs[2]/3);
            int used = (objs[0]+ (objs[1]/2) + objs[2]/3)/capacity*100;
            createChart(capacity, (objs[0]+ (objs[1]/2) + objs[2]/3));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Kesalahan", ex.getMessage())); 
            Logger.getLogger(ContainerOverallViewLegend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void drawTotalCapacities(int value) throws IOException {
        int areaWidth = 250;
        int areaHeight = 100;
        BufferedImage bufferedImg = new BufferedImage(areaWidth, areaHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImg.createGraphics();
        g2d.setComposite(AlphaComposite.Src);
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(Color.white);
        g2d.fillRect(1, 1, areaWidth - 2, areaHeight - 2);
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, areaWidth, areaHeight);

        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout text1 = new TextLayout("Total Kapasitas Lapangan :",
                new Font("Arial", Font.BOLD, 14), frc);

        TextLayout text2 = new TextLayout(String.valueOf(value) + " (teus)",
                new Font("Arial", Font.BOLD, 16), frc);

        int text1width = (int) text1.getBounds().getWidth();
        int text2width = (int) text2.getBounds().getWidth();

        int text1height = (int) text1.getBounds().getHeight();
        int text2height = (int) text2.getBounds().getHeight();

        text1.draw(g2d, areaWidth / 2 - text1width / 2, areaHeight / 2 - text1height / 2);

        g2d.setColor(Color.red);
        text2.draw(g2d, areaWidth / 2 - text2width / 2, areaHeight / 2 + text2height / 2 + text1height);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImg, "png", os);
        legend = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");
    }
        
    private void drawNotes() throws IOException {
        int areaWidth = 250;
        int areaHeight = 120;
        BufferedImage bufferedImg = new BufferedImage(areaWidth, areaHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImg.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(Color.white);
        g2d.fillRect(1, 1, areaWidth-2, areaHeight-2);
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, areaWidth, areaHeight);
        
        FontRenderContext frc = g2d.getFontRenderContext();
        
        TextLayout title = new TextLayout("Keterangan :",
                new Font("Arial", Font.BOLD, 12), frc);
        title.draw(g2d, 20, 20);
        
        drawRectText(g2d, Color.red, "above 4 Tier", 30, 30);
        drawRectText(g2d, Color.green, "2nd Tier", 150, 30);
        drawRectText(g2d, Color.yellow, "4th Tier", 30, 60);
        drawRectText(g2d, Color.cyan, "1st Tier", 150, 60);
        drawRectText(g2d, Color.magenta, "3rd Tier", 30, 90);
        drawRectText(g2d, Color.white, "empty", 150, 90);
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImg, "png", os);
        notes = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");
    }
    
    private void drawRectText(Graphics2D g2d, Color c, String text, int x, int y){
        g2d.setColor(c);
        g2d.fillRect(x, y, 20, 15);
        g2d.setColor(Color.black);
        g2d.drawRect(x, y, 20, 15);
        
        FontRenderContext frc = g2d.getFontRenderContext();
        
        TextLayout notes = new TextLayout(text,
                new Font("Arial", Font.PLAIN, 10), frc);
        notes.draw(g2d, x+30, y+13);
    }
    
    private void drawYardStatistics(int total20, int total40, int total45) throws IOException{
        int areaWidth = 250;
        int areaHeight = 120;
        BufferedImage bufferedImg = new BufferedImage(areaWidth, areaHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImg.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(Color.white);
        g2d.fillRect(1, 1, areaWidth-2, areaHeight-2);
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, areaWidth, areaHeight);
        
        FontRenderContext frc = g2d.getFontRenderContext();
        
//        g2d.setColor(Color.blue);
        TextLayout title = new TextLayout("Yard Statistics",
                new Font("Arial", Font.BOLD, 12), frc);
        int y = 20;
        title.draw(g2d, 20, y);
        g2d.setColor(Color.blue.darker());
        int htext = (int)title.getBounds().getHeight()/2;
        int wtext = (int)title.getBounds().getWidth();
        
        int x = areaWidth/4;
        int right = areaWidth/2;
        
        g2d.setColor(Color.black);
        TextLayout text = new TextLayout("Total 20 \" :", new Font("Arial", Font.PLAIN, 12), frc);
        text.draw(g2d, x, y+20);
        text = new TextLayout(String.valueOf(total20), new Font("Arial", Font.PLAIN, 12), frc);
        text.draw(g2d, x+right-(int)text.getBounds().getWidth(), y+20);
        
        text = new TextLayout("Total 40 \" :", new Font("Arial", Font.PLAIN, 12), frc);
        text.draw(g2d, x, y+40);
        text = new TextLayout(String.valueOf(total40), new Font("Arial", Font.PLAIN, 12), frc);
//        text.draw(g2d, x+100, y+40);
        text.draw(g2d, x+right-(int)text.getBounds().getWidth(), y+40);
        
        text = new TextLayout("Total 45 \" :", new Font("Arial", Font.PLAIN, 12), frc);
        text.draw(g2d, x, y+60);
        text = new TextLayout(String.valueOf(total45), new Font("Arial", Font.PLAIN, 12), frc);
        text.draw(g2d, x+right-(int)text.getBounds().getWidth(), y+60);
        
        g2d.drawLine(x, y+65, x+130, y+65);
        
        text = new TextLayout("Grand Total :", new Font("Arial", Font.BOLD, 12), frc);
        text.draw(g2d, x, y+80);
        text = new TextLayout(String.valueOf(total20+total40+total45), new Font("Arial", Font.BOLD, 12), frc);
        text.draw(g2d, x+right-(int)text.getBounds().getWidth(), y+80);
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImg, "png", os);
        statistic = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");
    }
    
    private void createChart(int totalCapacity, int usedValue) throws Exception {
        DefaultPieDataset dataset = new DefaultPieDataset();

        double dUsedVal = usedValue;
        double dCap = totalCapacity;
        double used = dUsedVal/dCap *100;
        double free = 100 - used;
        
        DecimalFormatSymbols symbol = new DecimalFormatSymbols();
        symbol.setDecimalSeparator(',');
        symbol.setGroupingSeparator('.');
//        symbol.setPercent('%');
        DecimalFormat df = new DecimalFormat("##0.##", symbol);

        dataset.setValue("Used=" + df.format(used) + "%", used);
        dataset.setValue("Free=" + df.format(free) + "%", free);

        JFreeChart jfreechart = ChartFactory.createPieChart("YOR Graph", dataset, true, false, false);
//        PiePlot plot = (PiePlot) jfreechart.getPlot();
//        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}", df, DecimalFormat.getPercentInstance()));
        
        File chartFile = new File("containerOverallViewLegend");
        ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 250, 250);
        chart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
    }

    public StreamedContent getLegend() {
        return legend;
    }

    public StreamedContent getChart() {
        return chart;
    }

    public StreamedContent getNotes() {
        return notes;
    }

    public StreamedContent getStatistic() {
        return statistic;
    }
}
