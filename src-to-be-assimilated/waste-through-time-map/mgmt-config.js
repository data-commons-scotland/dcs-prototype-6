
const mgmtEndStateColours = {
    "Recycled": "#62EFB3",
    "Other Diversion": "#EFB562",
    "Landfilled": "#EF6F62"
};

const mgmtEndStates = Object.entries(mgmtEndStateColours).map(([k,v]) => k);
const mgmtColours = Object.entries(mgmtEndStateColours).map(([k,v]) => v);

const mgmtQuantitiesFn =
    (records, area, year) => mgmtEndStates.map(endState => {
        let ix = records.findIndex(y => (y.area == area) && (y.year == year) && (y.endState == endState));
        if (ix == -1) console.log("Warning: no record for: " + area + ", " + year + ", " + endState);
        return ix == -1 ? 0 : records[ix].tonnes;
});

const mgmtDetailHtmlFn =
    (records, area, year) => {
        let s = mgmtEndStates.map(endState => {
            let ix = records.findIndex(y => (y.area == area) && (y.year == year) && (y.endState == endState));
            return `<dt>${endState}:</dt><dd><span style="font-weight: bold; color: ${mgmtEndStateColours[endState]}">${(ix == -1 ? '?' : records[ix].tonnes.toFixed(3))}</dd>`;
        }).join(" ");
        return `<h4>${area} ${year}</h4><div class='qual'>Tonnes per citizen per year</div><dl>${s}</dl>`;
};

const mgmtLegendHtml = "<div class='qual'>Tonnes per citizen per year</div>" +
                            Object.entries(mgmtEndStateColours).map(([k,v]) => `<i style="background: ${v}"></i>${k}<br/>`).join(" ");

const mgmtConfig = {
    chartType: "pie",
    quantitiesFn: mgmtQuantitiesFn,
    detailHtmlFn: mgmtDetailHtmlFn,
    colours: mgmtColours,
    legendHtml: mgmtLegendHtml
};


// ---------------------------------------------

export { mgmtConfig };