


var optionsObjMakerForConstructor = null;
var optionsObjMakerForUpdate = null;

const sumThem = (acc, x) => acc + x;

function setOptionsObjMakerFns(chartType, maxValue, colours){
    if ("pie" == chartType){
        let multiplier = 53 / maxValue;
        optionsObjMakerForConstructor =
            quantities => {
                return {data: quantities,
                        maxValues: maxValue,
                        width: (multiplier * quantities.reduce(sumThem, 0)),
                        type: chartType,
                        colors: colours,
                        labels: "auto",
                        labelMinSize: 6};
            };
        optionsObjMakerForUpdate =
            quantities => {
                return {data: quantities,
                        width: (multiplier * quantities.reduce(sumThem, 0))};
            };
    } else { // assume it is a bar chart
        let multiplier = 700 / maxValue;
        optionsObjMakerForConstructor =
            quantities => {
                return {data: quantities,
                        maxValues: maxValue,
                        height: (multiplier * quantities.reduce(sumThem, 0)),
                        width: 50,
                        type: chartType,
                        colors: colours,
                        labels: "auto",
                        labelMinSize: 6};
            };
        optionsObjMakerForUpdate =
            quantities => {
                return {data: quantities};
            };
    }
}


function createUiCharts(initialYear, uiMap, centres, wasteData, maxTonnesPerYear, quantitiesFn, colours, chartType){
    setOptionsObjMakerFns(chartType, maxTonnesPerYear, colours); // yucky hack
    var uiCharts = {};
    centres.forEach(x => {
        let quantities = quantitiesFn(wasteData, x.area, initialYear);
        uiCharts[x.area] = L.minichart([x.lat, x.lng], optionsObjMakerForConstructor(quantities));
        uiMap.addLayer(uiCharts[x.area]);
    });
    return uiCharts;
}


function updateUiCharts(getYear, uiCharts, centres, wasteData, maxTonnesPerYear, quantitiesFn) {
    let year = getYear();
    let widthMultiplier = 53 / maxTonnesPerYear;
    centres.forEach(x => {
        let quantities = quantitiesFn(wasteData, x.area, year);
        uiCharts[x.area].setOptions(optionsObjMakerForUpdate(quantities))
    });
}


// ---------------------------------------------

export { createUiCharts, updateUiCharts };



