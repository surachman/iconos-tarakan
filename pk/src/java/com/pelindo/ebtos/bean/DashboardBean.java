/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimePeriodAnchor;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.CartesianChartModel;

/**
 *
 * @author senoanggoro
 */
@Named(value="dashboardBean")
@RequestScoped
public class DashboardBean {
    private ServiceVesselFacadeRemote serviceVesselFacade = lookupServiceVesselFacadeRemote();
    private Object[] vesselActivities;
    private List<Object[]> recentArrivalsAndDepartures;
    private CartesianChartModel vesselActivityModel;
    private StreamedContent chart;
    private List<String> vesselsCurrentlyInPort;

    public DashboardBean() {
        vesselsCurrentlyInPort = serviceVesselFacade.findVesselsOnPort();
        vesselActivities = serviceVesselFacade.getVesselActivities();
        recentArrivalsAndDepartures = serviceVesselFacade.findRecentArrivalsAndDepartures();
        try {
            XYDataset xydataset = createDataset();
            JFreeChart jfreechart = createChart(xydataset);
            File chartFile = new File("dynamichart");
            ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 700, 400);
            chart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {

        }
    }

    public StreamedContent getChart() {
            return chart;
    }
    public void setChart(StreamedContent chart) {
            this.chart = chart;
    }

    private XYDataset  createDataset() {
        TimeSeriesCollection  dataset = new TimeSeriesCollection();
        dataset.setDomainIsPointsInTime(true);
        dataset.setXPosition(TimePeriodAnchor.MIDDLE);
        TimeSeries arrival = new TimeSeries("Arrival");
        TimeSeries departure = new TimeSeries("Departure");

        List<Object[]> activities = serviceVesselFacade.findArrivalsAndDeparturesLastMonth();
        for (Object[] activity: activities) {
            arrival.add(new Day((Date) activity[0]), (BigDecimal) activity[1]);
            departure.add(new Day((Date) activity[0]), (BigDecimal) activity[2]);
        }

        dataset.addSeries(departure);
        dataset.addSeries(arrival);
        return dataset;
    }

    private JFreeChart createChart(XYDataset xydataset)
    {
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(null, "Date", "Number of Activities", xydataset, true, true, false);
        jfreechart.setBackgroundPaint(Color.white);
        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);
        org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer = xyplot.getRenderer();
        if(xyitemrenderer instanceof XYLineAndShapeRenderer)
        {
            XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyitemrenderer;
            xylineandshaperenderer.setShapesVisible(true);
            xylineandshaperenderer.setShapesFilled(true);
            xylineandshaperenderer.setItemLabelsVisible(true);
        }
        PeriodAxis periodaxis = new PeriodAxis(null);
        periodaxis.setAutoRangeTimePeriodClass(org.jfree.data.time.Day.class);
        PeriodAxisLabelInfo aperiodaxislabelinfo[] = new PeriodAxisLabelInfo[3];
        aperiodaxislabelinfo[0] = new PeriodAxisLabelInfo(org.jfree.data.time.Day.class, new SimpleDateFormat("d"));
        aperiodaxislabelinfo[1] = new PeriodAxisLabelInfo(org.jfree.data.time.Month.class, new SimpleDateFormat("MMM"), new RectangleInsets(2D, 2D, 2D, 2D), new Font("SansSerif", 1, 10), Color.blue, false, new BasicStroke(0.0F), Color.lightGray);
        aperiodaxislabelinfo[2] = new PeriodAxisLabelInfo(org.jfree.data.time.Year.class, new SimpleDateFormat("yyyy"));
        periodaxis.setLabelInfo(aperiodaxislabelinfo);
        xyplot.setDomainAxis(periodaxis);
        return jfreechart;
    }

    public String getVesselsOnPort(){
        if (!vesselsCurrentlyInPort.isEmpty()){
            String text = vesselsCurrentlyInPort.get(0);
            vesselsCurrentlyInPort.remove(0);
            for (String vessel: vesselsCurrentlyInPort){
                text += " - " + vessel;
            }
            return text;
        }
        return null;
    }

    public Object[] getVesselActivities(){
        return vesselActivities;
    }

    public List<Object[]> getRecentArrivalsAndDepartures(){
        return recentArrivalsAndDepartures;
    }

    private ServiceVesselFacadeRemote lookupServiceVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceVesselFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
