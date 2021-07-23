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
else if (searchStr.includes("preset2")) preset = "preset2"
else if (searchStr.includes("preset3")) preset = "preset3"
else if (searchStr.includes("preset4")) preset = "preset4"

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
                    <ChartExport enabled={true} fileName="household-waste" margin={3} />
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
                      <ChartExport enabled={true} fileName="household-waste" margin={3} />
                    </Chart>);
    }
 */

  render() {
    let { drillDownDataSource, popupTitle, popupVisible } = this.state;

    return (
      <React.Fragment>

        <div className="block title">
            <h1>Household waste in Scotland</h1>
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
          <Export enabled={true} allowExportSelectedData={true} fileName="household-waste" />
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
            <Column dataField="endState" />
            <Column dataField="material" />
            <Column dataField="tonnes" dataType="number" format="decimal" />
            <Column dataField="tonnesPerCitizen" dataType="number" format="decimal" />
            <Column dataField="tonnesPerHousehold" dataType="number" format="decimal" />
          </DataGrid>
        </Popup>

        <div>
        <h5>Sources</h5>
            <ol>
                <li>
                "Generation and Management of Household Waste",
                created by SEPA on 31st August 2020,
                downloaded from <a
                    href="http://statistics.gov.scot/data/household-waste"
                    target="_blank">http://statistics.gov.scot/data/household-waste</a> on
                    26th October 2020.
                </li>
                <li>
                "Carbon footprint",
                created by SEPA in 2019,
                downloaded from  Wikidata (property P5991 on instances of Q15060255) on 26th October 2020.
                </li>
                <li>
                "Population Estimates (Current Geographic Boundaries)",
                created by NRS on 1st May 2020,
                downloaded from <a
                    href="http://statistics.gov.scot/data/population-estimates-current-geographic-boundaries"
                    target="_blank">http://statistics.gov.scot/data/population-estimates-current-geographic-boundaries</a> on
                    26th October 2020.
                </li>
                <li>
                "Mid-Year Household Estimates",
                created by NRS on 23rd June 2020,
                downloaded from <a
                    href="http://statistics.gov.scot/data/mid-year-household-estimates"
                    target="_blank">http://statistics.gov.scot/data/mid-year-household-estimates</a> on
                    26th October 2020.
                </li>
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
                How does Aberdeen City compare with Dundee (and Scotland as a whole) for the amounts of household waste per citizen that it landfills?
                </a>
            </li>
            <li>
                <a href="index.html?preset2">
                How many tonnes of each household waste material ended up recycled, landfilled, etc. in Stirling in 2018?
                </a>
            </li>
            <li>
                <a href="index.html?preset3">
                What proportion of a tonne of household waste has ended up recycled, landfilled, etc. in Edinburgh through the years?
                </a>
            </li>
            <li>
                <a href="index.html?preset4">
                What does the correlation look like between the amounts of household waste solids and their calculated carbon impacts?
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
                 caption: 'Area',
                 width: 90,
                 dataField: 'area',
                 area: 'row',
                 filterType: 'exclude',
                 filterValues: [
                   'Scotland'
                 ]
               },
               {
                 caption: 'End state',
                 width: 90,
                 dataField: 'endState',
                 area: 'row',
                 filterType: 'exclude',
                 filterValues: [
                   'Waste Generated',
                   'Other Diversion (pre 2014 method)',
                   'Recycled (pre 2014 method)'
                 ]
               }, {
                 caption: 'Material',
                 dataField: 'material',
                 width: 90,
                 area: 'row',
                 filterType: 'exclude',
                 filterValues: [
                   'Total Waste'
                 ]
               }, {
                 dataField: 'year',
                 dataType: 'year',
                 area: 'column',
                 filterType: 'exclude',
                 filterValues: [ 2011, 2012, 2013 ]
               }, {
                 caption: 'Tonnes of solids',
                 dataField: 'tonnes',
                 dataType: 'number',
                 summaryType: 'sum',
                 format: 'decimal'
               },
               {
                 caption: 'Tonnes of solids per citizen',
                 dataField: 'tonnesPerCitizen',
                 dataType: 'number',
                 summaryType: 'sum',
                 format: {
                   precision: 5,
                   type: "fixedPoint"
                 },
                 area: 'data'
               },
               {
                 caption: 'Tonnes of solids per household',
                 dataField: 'tonnesPerHousehold',
                 dataType: 'number',
                 summaryType: 'sum',
                 format: {
                   precision: 5,
                   type: "fixedPoint"
                 }
               },
               {
                 caption: 'Tonnes of CO2e',
                 dataField: 'co2e',
                 dataType: 'number',
                 summaryType: 'sum',
                 format: 'decimal'
               },
               {
                 caption: 'Tonnes of CO2e per citizen',
                 dataField: 'co2ePerCitizen',
                 dataType: 'number',
                 summaryType: 'sum',
                 format: {
                   precision: 5,
                   type: "fixedPoint"
                 }
               },
               {
                 caption: 'Tonnes of CO2e per household',
                 dataField: 'co2ePerHousehold',
                 dataType: 'number',
                 summaryType: 'sum',
                 format: {
                   precision: 5,
                   type: "fixedPoint"
                 }
               }];

var description = "";

var chartType = "line";

switch(preset){
    case("preset1"): // compare tonnes landfilled per Aberdeen City/Dundee/Scottish citizen per year
        fieldsConfig = fieldsConfig
            .map(o => {
                if (o.dataField==="area") {
                    o.filterType = 'include'
                    o.filterValues = ['Aberdeen City', 'Dundee City', 'Scotland'];
                    o.expanded = true;
                }
                return o; })
            .map(o => {
                if (o.dataField==="endState") {
                    o.filterType = 'include'
                    o.filterValues = ['Landfilled'];
                }
                return o; });
        description = "How does Aberdeen City compare with Dundee (and Scotland as a whole) for the amounts of household waste per citizen that it landfills?";
        break;
    case("preset2"): // tonnes per end-state per material for Stirling in 2018
            fieldsConfig = fieldsConfig
                .map(o => {
                    if (o.dataField==="tonnes") {
                        o.area = "data";
                    }
                    return o; })
                .map(o => {
                    if (o.dataField==="tonnesPerCitizen") {
                        o.area = null;
                    }
                    return o; })
                .map(o => {
                    if (o.dataField==="area") {
                        o.filterType = 'include'
                        o.filterValues = ['Stirling'];
                        o.expanded = true;
                    }
                    return o; })
                .map(o => {
                    if (o.dataField==="endState") {
                        o.filterType = 'include'
                        o.filterValues = ['Recycled', 'Other Diversion', 'Landfilled'];
                        o.expanded = true;
                    }
                    return o; })
                .map(o => {
                    if (o.dataField==="year") {
                        o.filterType = 'include'
                        o.filterValues = [2018];
                     }
                     return o; });
            chartType = "bar";
            description = "How many tonnes of each household waste material ended up recycled, landfilled, etc. in Stirling in 2018?";
            break;
    case("preset3"): // proportion per end-state for Edinburgh
            fieldsConfig = fieldsConfig
                .map(o => {
                    if (o.dataField==="tonnes") {
                        o.area = "data";
                    }
                    return o; })
                .map(o => {
                    if (o.dataField==="tonnesPerCitizen") {
                        o.area = null;
                    }
                    return o; })
                .map(o => {
                    if (o.dataField==="area") {
                        o.filterType = 'include'
                        o.filterValues = ['City of Edinburgh'];
                        o.expanded = true;
                    }
                    return o; })
                .map(o => {
                    if (o.dataField==="endState") {
                        o.filterType = 'include'
                        o.filterValues = ['Recycled', 'Other Diversion', 'Landfilled'];
                    }
                    return o; })
                .map(o => {
                    if (o.dataField==="year") {
                        o.filterType = 'exclude'
                        o.filterValues = [];
                     }
                     return o; });
            chartType = "fullstackedbar";
            description = "What proportion of a tonne of household waste has ended up recycled, landfilled, etc. in Edinburgh through the years?";
            break;
    case("preset4"): // correlations between solids and carbon impact
            fieldsConfig = fieldsConfig
                .map(o => {
                    if (o.dataField==="co2ePerCitizen") {
                        o.area = 'data';
                    }
                    return o; })
                .map(o => {
                    if (o.dataField==="area") {
                        o.filterType = 'include'
                        o.filterValues = ['Scotland'];
                    }
                    return o; })
                .map(o => {
                    if (o.dataField==="year") {
                        o.filterType = 'include'
                        o.filterValues = [2017, 2018];
                     }
                     return o; });
            chartType="area";
            description = "What does the correlation look like between the amounts of household waste solids and their calculated carbon impacts?";
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




