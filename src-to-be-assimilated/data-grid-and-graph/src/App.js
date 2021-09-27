import React from 'react';

import {
  PivotGrid,
  FieldChooser,
  Scrolling,
  Export
} from 'devextreme-react/pivot-grid';
import { DataGrid, Column } from 'devextreme-react/data-grid';
import { Popup } from 'devextreme-react/popup';
import PivotGridDataSource from 'devextreme/ui/pivot_grid/data_source';
import { createStore } from 'devextreme-aspnet-data-nojquery';

import Chart, {
  AdaptiveLayout,
  Legend,
  CommonSeriesSettings,
  Point,
  Size,
  Margin,
  ZoomAndPan,
  Tooltip,
  Export as ChartExport
} from 'devextreme-react/chart';

import Button from 'devextreme-react/button';

const ctxPath = window.location.pathname;
const ctxParent = ctxPath.substring(0, ctxPath.lastIndexOf("/")+1);
//console.log("ctxParent=" + ctxParent);
const searchStr = window.location.search;
//console.log("searchStr=" + searchStr);
var preset = null;
if (searchStr.includes("preset1")) preset = "preset1";
if (searchStr.includes("preset2")) preset = "preset2";

class App extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      popupTitle: '',
      drillDownDataSource: null,
      popupVisible: false
    };
    this.onCellClick = this.onCellClick.bind(this);
    this.onHiding = this.onHiding.bind(this);
    this.onShown = this.onShown.bind(this);
    this.getDataGridInstance = this.getDataGridInstance.bind(this);

    this.resetZoom = () => {
      this._chart.resetVisualRange();
    };
  }

  componentDidMount() {
      this._pivotGrid.bindChart(this._chart, {
        dataFieldsDisplayMode: 'splitPanes',
        alternateDataFields: false
      });
  }

  chart() {
    return(<Chart
              ref={(ref) => this._chart = ref.instance}
              palette="Harmony Light"
           >
                    <Size height={500} />
                    <Tooltip enabled={true} customizeTooltip={customizeTooltip} />
                    <Legend
                      verticalAlignment="center"
                      horizontalAlignment="right"
                    />
                    <CommonSeriesSettings type={chartType} width="1" >
                      <Point size="6" />
                    </CommonSeriesSettings>
                    <AdaptiveLayout  width={0} height={0} />
                    <Margin bottom={10} />
                    <ChartExport enabled={true} fileName="waste-data" margin={3} />
                    <ZoomAndPan
                      valueAxis="both"
                      argumentAxis="both"
                      dragToZoom={true}
                      allowMouseWheel={true}
                      panKey="shift" />
                  </Chart>);
  }

/*
    chartBubble() { // work in progress
      return(<Chart
                ref={(ref) => this._chart = ref.instance}
             >
                      <Size height={500} />
                      <CommonSeriesSettings type={chartType}
                          tagField="area"
                          valueField="tonnes"
                          sizeField="tonnes" >
                      </CommonSeriesSettings>
                      <AdaptiveLayout  width={0} height={0} />
                      <Margin bottom={10} />
                      <ChartExport enabled={true} fileName="waste-data" margin={3} />
                    </Chart>);
    }
 */

  render() {
    let { drillDownDataSource, popupTitle, popupVisible } = this.state;

    return (
      <React.Fragment>

        <div className="block title">
            <h1>Waste from businesses in Scotland</h1>
        </div>
        <center>{description}</center>

        {this.chart()}

        <div className='block centre'>
          <div className='buttonAreaMargins'>
            <Button
              id="reset-chart-zoom"
              text="Unzoom chart"
              onClick={this.resetZoom}
            ></Button>
          </div>
        </div>

        <PivotGrid
          id="pivotGrid"
          allowSortingBySummary={true}
          allowSorting={true}
          allowFiltering={true}
          allowExpandAll={true}
          height={560}
          showBorders={true}
          dataSource={dataSource}
          onCellClick={this.onCellClick}
          showColumnGrandTotals={false}
          showRowGrandTotals={false}
          //rowHeaderLayout={"tree"}
          //hideEmptySummaryCells={true}
          ref={(ref) => this._pivotGrid = ref.instance}
        >
          <FieldChooser enabled={true} />
          <Scrolling mode="virtual" />
          <Export enabled={true} allowExportSelectedData={true} fileName="waste-data" />
        </PivotGrid>

        <Popup
          visible={popupVisible}
          title={popupTitle}
          onHiding={this.onHiding}
          onShown={this.onShown}
        >
          <DataGrid
            dataSource={drillDownDataSource}
            ref={this.getDataGridInstance}
          >
            <Column dataField="year" dataType="year" />
            <Column dataField="area" />
            <Column dataField="waste-category" />
            <Column dataField="economic-sector" />
            <Column dataField="generator" />
            <Column dataField="top-selector" />
            <Column dataField="tonnes" dataType="number" format="decimal" />
          </DataGrid>
        </Popup>

        <div>
        <h5>Sources</h5>
            <ol>
                <li>"Generation and Management of Household Waste", created by SEPA in 2020.</li>
                <li>"Business Waste Data Tables", created by SEPA in 2019.</li>
            </ol>
        <h5>"Presets"</h5>
        <ol start="0">
            <li>
                <a href="index.html">
                No preset
                </a>
            </li>
            <li>
                <a href="index.html?preset1">
                Fife's four largest, business wastes vs their averages
                </a>
            </li>
            <li>
                <a href="index.html?preset2">
                Business combustion wastes by sector
                </a>
            </li>
        </ol>
        </div>

      </React.Fragment>
    );
  }

  getDataGridInstance(ref) {
    this.dataGrid = ref.instance;
  }


  onCellClick(e) {
    /*  Commented out (for now) this hook-in of the drilldown popup because it seems to have stopped working ...maybe when I added the chart.
    if (e.area === 'data') {
      var pivotGridDataSource = e.component.getDataSource(),
        rowPathLength = e.cell.rowPath.length,
        rowPathName = e.cell.rowPath[rowPathLength - 1];

      this.setState({
        popupTitle: `${rowPathName ? rowPathName : 'Total'} Drill Down Data`,
        drillDownDataSource: pivotGridDataSource.createDrillDownDataSource(e.cell),
        popupVisible: true
      });
    }
    */
  }


  onHiding() {
    this.setState({
      popupVisible: false
    });
  }

  onShown() {
    this.dataGrid.updateDimensions();
  }
}

var fieldsConfig = [{
                 caption: 'Selection',
                 width: 90,
                 dataField: 'top-selector',
                 filterType: 'exclude',
                 filterValues: ['by-sector']
               }, {
                 caption: 'Area',
                 width: 90,
                 dataField: 'area',
                 area: 'row',
                 filterType: 'exclude',
                 filterValues: []
               }, {
                 caption: 'Waste generator',
                 dataField: 'generator',
                 width: 90,
                 area: 'row',
                 filterType: 'exclude',
                 filterValues: []
               }, {
                 caption: 'Material',
                 dataField: 'waste-category',
                 width: 90,
                 area: 'row',
                 filterType: 'exclude',
                 filterValues: []
               }, {
                 caption: 'Business sector',
                 dataField: 'economic-sector',
                 width: 90,
                 filterType: 'exclude',
                 filterValues: []
               }, {
                 dataField: 'year',
                 dataType: 'year',
                 area: 'column',
                 filterType: 'exclude',
                 filterValues: [ ]
               }, {
                 caption: 'Tonnes of waste',
                 dataField: 'tonnes',
                 area: 'data',
                 dataType: 'number',
                 summaryType: 'sum',
                 format: 'decimal'
               }];

var description = "";

var chartType = "line";

switch(preset){
    case("preset1"): // Fife's four largest, business wastes vs their averages
        fieldsConfig = fieldsConfig
            .map(o => {
                if (o.dataField==="top-selector") {
                    o.filterType = 'include'
                    o.filterValues = ['by-area'];
                    o.expanded = true;
                }
                return o; })
            .map(o => {
                if (o.dataField==="area") {
                    o.filterType = 'include'
                    o.filterValues = ['Fife', 'average'];
                    o.expanded = true;
                }
                return o; })
            .map(o => {
                if (o.dataField==="waste-category") {
                    o.filterType = 'include'
                    o.filterValues = ['Animal faeces, urine and manure',
                                      'Combustion wastes',
                                      'Household and similar wastes',
                                      'Metallic wastes, ferrous'];
                    o.expanded = true;
                }
                return o; })
            .map(o => {
                if (o.dataField==="generator") {
                    o.filterType = 'include'
                    o.filterValues = ['business'];
                    o.expanded = true;
                }
                return o; });
        description = "Fife's four largest, business wastes vs their averages";
        break;
    case("preset2"): // Business combustion wastes by sector
        fieldsConfig = fieldsConfig
            .map(o => {
                if (o.dataField==="top-selector") {
                    o.filterType = 'include'
                    o.filterValues = ['by-economic-sector'];
                    o.expanded = true;
                }
                return o; })
            .map(o => {
                if (o.dataField==="waste-category") {
                    o.filterType = 'include'
                    o.filterValues = ['Combustion wastes'];
                    o.expanded = true;
                }
                return o; })
            .map(o => {
                if (o.dataField==="economic-sector") {
                    o.filterType = 'include' // I couldn't exclude the 'blanks' so, for now, explicitly including...
                    o.filterValues = ["Agriculture, forestry and fishing",
                                      "Commerce",
                                      "Manufacture of chemicals, plastics and pharmaceuticals",
                                      "Manufacture of food and beverage products",
                                      "Manufacture of wood products",
                                      "Mining and quarrying",
                                      "Other manufacturing",
                                      "Power industry",
                                      "Waste management",
                                      "Water industry"];
                    o.expanded = true;
                    o.area = 'row';
                }
                return o; })
            .map(o => {
                if (o.dataField==="generator") {
                    o.area = null;
                    o.visible = false;
                }
                return o; })
            .map(o => {
                if (o.dataField==="area") {
                    o.area = null;
                    o.visible = false;
                }
                return o; });
        description = "Business combustion wastes by sector";
        break;
    default:
        // no op
}

const dataSource = new PivotGridDataSource({
  fields: fieldsConfig,
  store: createStore({
    loadUrl: ctxParent + "dx-data.json"
  })
});

function customizeTooltip(arg) {
  return {
    html: `${arg.argumentText}<br/>${arg.seriesName}<br/>${arg.valueText}`
  };
}

export default App;




