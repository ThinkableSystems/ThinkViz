/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package thinksys.thinkviz.main.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.FloatFieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.HTMLPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.PropertyGridPanel;
import com.gwtext.client.widgets.layout.AccordionLayout;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {
	
	private Panel panel;
	private TabPanel centerPanel;
	private GridPanel grid;
	private Store store;
	
	public void onModuleLoad() {
		panel = new Panel();  
		panel.setTitle("ThinkableSystems Data Visualizations");
        panel.setBorder(false);  
        panel.setPaddings(15);  
        panel.setLayout(new FitLayout());
        panel.setFrame(true);
        
        loadLayout();
        
        new Viewport(panel);
	}
	
	private void loadLayout(){
		Panel borderPanel = new Panel();  
        borderPanel.setLayout(new BorderLayout());
        loadNorthPanel(borderPanel);
        loadSouthPanel(borderPanel);
        loadEastPanel(borderPanel);
        loadWestPanel(borderPanel);
        loadCenterPanel(borderPanel);
        panel.add(borderPanel);
	}
	
	private void loadNorthPanel(Panel borderPanel){
        BoxComponent northPanel = new BoxComponent();  
        northPanel.setEl(new HTML("<p>north - generally for menus, toolbars" +  
        		" and/or advertisements</p>").getElement());  
        northPanel.setHeight(32);  
        borderPanel.add(northPanel, new BorderLayoutData(RegionPosition.NORTH));
	}
	
	private void loadSouthPanel(Panel borderPanel){
        Panel southPanel = new HTMLPanel("<p>south - generally for informational stuff," +  
        		" also could be for status bar</p>");  
        southPanel.setHeight(100);  
        southPanel.setCollapsible(true);  
        southPanel.setTitle("South");  

        BorderLayoutData southData = new BorderLayoutData(RegionPosition.SOUTH);  
        southData.setMinSize(100);  
        southData.setMaxSize(200);  
        southData.setMargins(new Margins(0, 0, 0, 0));  
        southData.setSplit(true);  
        borderPanel.add(southPanel, southData);
	}
	
	private void loadEastPanel(Panel borderPanel){
		Panel eastPanel = new Panel();  
        eastPanel.setTitle("East Side");  
        eastPanel.setCollapsible(true);  
        eastPanel.setWidth(225);  
        eastPanel.setLayout(new FitLayout());
        
        BorderLayoutData eastData = new BorderLayoutData(RegionPosition.EAST);  
        eastData.setSplit(true);  
        eastData.setMinSize(175);  
        eastData.setMaxSize(400);  
        eastData.setMargins(new Margins(0, 0, 5, 0));  
  
        borderPanel.add(eastPanel, eastData);
        
        TabPanel tabPanel = new TabPanel();  
        tabPanel.setBorder(false);  
        tabPanel.setActiveTab(1);  
  
        Panel tabOne = new Panel();  
        tabOne.setHtml("<p>A TabPanel component can be a region.</p>");  
        tabOne.setTitle("A Tab");  
        tabOne.setAutoScroll(true);  
        tabPanel.add(tabOne);  
  
        PropertyGridPanel propertyGrid = new PropertyGridPanel();  
        propertyGrid.setTitle("Property Grid");  
  
        Map source = new HashMap<>();  
        source.put("(name)", "Properties Grid");  
        source.put("grouping", Boolean.FALSE);  
        source.put("autoFitColumns", Boolean.TRUE);  
        source.put("productionQuality", Boolean.FALSE);  
        source.put("created", new Date());  
        source.put("tested", Boolean.FALSE);  
        source.put("version", new Float(0.1f));  
        source.put("borderWidth", new Integer(1));  
  
        propertyGrid.setSource(source);  
  
        tabPanel.add(propertyGrid);  
        eastPanel.add(tabPanel);
	}
	
	private void loadWestPanel(Panel borderPanel){
		final AccordionLayout accordion = new AccordionLayout(true);  
		  
        Panel westPanel = new Panel();  
        westPanel.setTitle("West");  
        westPanel.setCollapsible(true);  
        westPanel.setWidth(200);  
        westPanel.setLayout(accordion);
        
        Panel navPanel = new Panel();  
//        navPanel.setHtml("<p>Hi. I'm the west panel.</p>");  
        navPanel.setTitle("Navigation");  
        navPanel.setBorder(false);  
        navPanel.setIconCls("forlder-icon"); 
        
        Button btnLineChart = new Button("Create Line Chart", new ButtonListenerAdapter() {  
            public void onClick(Button button, EventObject e) {  
                Panel tab = new LineChartPanel(store); 
                centerPanel.add(tab);
                centerPanel.activate(tab.getId());  
                centerPanel.scrollToTab(tab, true);  
            }  
        });
        navPanel.add(btnLineChart);
        
        westPanel.add(navPanel);  
  
        Panel settingsPanel = new Panel();  
        settingsPanel.setHtml("<p>Some settings in here.</p>");  
        settingsPanel.setTitle("Settings");  
        settingsPanel.setBorder(false);  
        settingsPanel.setIconCls("settings-icon");  
        westPanel.add(settingsPanel);  
  
        BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);  
        westData.setSplit(true);  
        westData.setMinSize(175);  
        westData.setMaxSize(400);  
        westData.setMargins(new Margins(0, 5, 0, 0));  
  
        borderPanel.add(westPanel, westData);  
	}
	
	private void loadCenterPanel(Panel borderPanel){
		centerPanel = new TabPanel();  
        centerPanel.setDeferredRender(false);  
        centerPanel.setActiveTab(0);  
  
        Panel centerPanelOne = new HTMLPanel();  
        centerPanelOne.setHtml(  
                "<p><b>Done reading me? Close me by clicking the X in the top right corner.</b></p>\n" +  
                        "<p>" + getShortBogusMarkup() + "</p>\n" +  
                        "<p>" + getShortBogusMarkup() + "</p>\n" +  
                        "<p>" + getShortBogusMarkup() + "</p>\n"  
        );  
        centerPanelOne.setTitle("Close Me");  
        centerPanelOne.setAutoScroll(true);  
        centerPanelOne.setClosable(true);  
  
        centerPanel.add(centerPanelOne);  
  
        Panel centerPanelTwo = new HTMLPanel();  
        centerPanelTwo.setHtml(  
                "<p>My closable attribute is set to false so you can't close me. The other center panels " +  
                        "can be closed.</p>\n" +  
                        "<p>The center panel automatically grows to fit the remaining space in the container " +  
                        "that isn't taken up by the border regions.</p>\n" +  
                        "<hr>\n" +  
                        "<p>" + getShortBogusMarkup() + "</p>\n" +  
                        "<p>" + getShortBogusMarkup() + "</p>\n" +  
                        "<p>" + getShortBogusMarkup() + "</p>\n" +  
                        "<p>" + getShortBogusMarkup() + "</p>\n");  
        centerPanelTwo.setTitle("Center Panel");  
        centerPanelTwo.setAutoScroll(true);  
  
        centerPanel.add(centerPanelTwo);  
        centerPanel.add(loadGrid());
        borderPanel.add(centerPanel, new BorderLayoutData(RegionPosition.CENTER)); 
	}
	
	private static String getShortBogusMarkup() {  
        return "<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +  
                "Sed metus nibh, sodales a, porta at, vulputate eget, dui.  " +  
                "In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, " +  
                "cursus a, fringilla vel, urna.";  
    }  
	
	private Panel loadGrid(){
		RecordDef recordDef = new RecordDef(  
                new FieldDef[]{  
                		new IntegerFieldDef("ID")
                		,new StringFieldDef("DataSet")
                		,new IntegerFieldDef("Patient")
                		,new IntegerFieldDef("Variable")
                		,new IntegerFieldDef("Function")
                		,new FloatFieldDef("R2")
                		,new FloatFieldDef("P1")
                		,new FloatFieldDef("P2")
                		,new FloatFieldDef("P3")
                		,new FloatFieldDef("P4")
                }  
        ); 
		
		grid = new GridPanel();
		Object[][] data = getData();  
        MemoryProxy proxy = new MemoryProxy(data);
        ArrayReader reader = new ArrayReader(recordDef);  
        store = new Store(proxy, reader);  
        store.load();  
//		SrvDataAsync srvData = SrvData.Util.getInstance();
//        srvData.getData(new AsyncCallback<String[][]>() {
//			
//			public void onSuccess(String[][] result) {
//				MemoryProxy proxy = new MemoryProxy(result);
//				store = new Store(proxy, reader);  
//		        store.add();
//		        grid.getView().refresh();
//			}
//			
//			public void onFailure(Throwable caught) {
//				System.out.println("Failed");
//			}
//        });
        
        grid.setStore(store);
        ColumnConfig[] columns = new ColumnConfig[]{  
                new ColumnConfig("ID", "ID", 100, true, null, "ID"), 
                new ColumnConfig("DataSet", "DataSet", 300, true, null, "DataSet"), 
                new ColumnConfig("Patient", "Patient", 100),
                new ColumnConfig("Variable", "Variable", 100),
                new ColumnConfig("Function", "Function", 100),
                new ColumnConfig("R2", "R2", 100),
                new ColumnConfig("P1", "P1", 100),
                new ColumnConfig("P2", "P2", 100),
                new ColumnConfig("P3", "P3", 100),
                new ColumnConfig("P4", "P4", 100),
        }; 
        
        ColumnModel columnModel = new ColumnModel(columns);  
        grid.setColumnModel(columnModel);  
  
        grid.setFrame(true);  
        grid.setStripeRows(true);  
        grid.setAutoExpandColumn("DataSet");  
  
//        grid.setHeight(350);  
//        grid.setWidth(600);  
        grid.setTitle("Data");  
        
        Toolbar bottomToolbar = new Toolbar();  
        bottomToolbar.addFill();  
        bottomToolbar.addButton(new ToolbarButton("Load Data", new ButtonListenerAdapter() {  
            public void onClick(Button button, EventObject e) {  
                grid.clearSortState(true); 
                store.load();
                grid.getView().refresh();
            }  
        }));  
        grid.setBottomToolbar(bottomToolbar);
        
        return grid;
	}
	
	private static Object[][] getData() {  
        return new Object[][]{  
        		
        		new Object[]{new Integer(1)	,	"mc1076_CY1_CY2_Norm_Clean"	, new Integer(1)	, new Integer(0)	, new Integer(5)	, new Double(0.777750498)},
        		new Object[]{new Integer(2)	,	"mc1076_CY1_CY2_Norm_Clean"	, new Integer(1)	, new Integer(1)	, new Integer(5)	, new Double(0.578642884)},
        		new Object[]{new Integer(3)	,	"mc1076_CY1_CY2_Norm_Clean"	, new Integer(1)	, new Integer(2)	, new Integer(5)	, new Double(0.626465515)},
        		new Object[]{new Integer(4)	,	"mc1076_CY1_CY2_Norm_Clean"	, new Integer(1)	, new Integer(3)	, new Integer(5)	, new Double(0.819368186)},
        		new Object[]{new Integer(5)	,	"mc1076_CY1_CY2_Norm_Clean"	, new Integer(1)	, new Integer(4)	, new Integer(5)	, new Double(0.528674904)},
        		new Object[]{new Integer(6)	,	"mc1076_CY1_CY2_Norm_Clean"	, new Integer(2)	, new Integer(5)	, new Integer(5)	, new Double(0.763150865)},
        		new Object[]{new Integer(7)	,	"mc1076_CY1_CY2_Norm_Clean"	, new Integer(2)	, new Integer(6)	, new Integer(5)	, new Double(0.794040135)},
        		new Object[]{new Integer(8)	,	"mc1076_CY1_CY2_Norm_Clean"	, new Integer(2)	, new Integer(7)	, new Integer(5)	, new Double(0.061239562)},
        		new Object[]{new Integer(9)	,	"mc1076_CY1_CY2_Norm_Clean"	, new Integer(2)	, new Integer(8)	, new Integer(5)	, new Double(0.769409176)},
        		new Object[]{new Integer(10)	,	"mc1076_CY1_CY2_Norm_Clean"	, new Integer(3)	, new Integer(9)	, new Integer(5)	, new Double(0.172398085)},
        		new Object[]{new Integer(11)	,	"mc1076_CY1_CY2_Norm_Clean"	, new Integer(3)	, new Integer(10)	, new Integer(5)	, new Double(0.369625337)},
        		new Object[]{new Integer(12)	,	"mc1076_CY1_CY2_Norm_Clean"	, new Integer(3)	, new Integer(11)	, new Integer(5)	, new Double(0.731187236)}
        };  
    }  
}
