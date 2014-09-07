package thinksys.thinkviz.main.client;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.FloatFieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.chart.yui.LineChart;
import com.gwtext.client.widgets.chart.yui.NumericAxis;
import com.gwtext.client.widgets.chart.yui.SeriesDefY;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.event.EditorGridListenerAdapter;
import com.gwtext.client.widgets.layout.VerticalLayout;

public class LineChartPanel extends Panel{
	
	private Store store;

	public LineChartPanel(Store store){
		this.setAutoScroll(true);  
        this.setTitle("Line Chart");  
        this.setIconCls("tab-icon");  
        this.setClosable(true);  
        this.setFrame(true);
        this.store = store;
		loadChart();
	}
	
	private void loadChart(){
        
        SeriesDefY[] seriesDef = new SeriesDefY[]{  
				  
                new SeriesDefY("Patient", "Patient"),  
//                new SeriesDefY("Utilities", "utilities")  
  
        };
        
        NumericAxis axis = new NumericAxis();  
//        axis.setMinimum(800);  
//        axis.setLabelFunction("formatCurrencyAxisLabel");  
        final LineChart chart = new LineChart();  
        chart.setTitle("CDTTS");  
        chart.setWMode("transparent");  
        chart.setStore(store);  
        chart.setSeries(seriesDef);  
        chart.setXField("R2");  
        chart.setYAxis(axis);  
        chart.setDataTipFunction("getDataTipText");  
        chart.setExpressInstall("js/yui/assets/expressinstall.swf");  
        chart.setWidth(800);  
        chart.setHeight(400);  
  
        this.add(chart);
	}
	
	
}
