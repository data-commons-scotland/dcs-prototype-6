
const hvbDesignationColours = {
    "Household": "#0099cc",
    "Business": "#ffcc66"
};

const hvbDesignations = Object.entries(hvbDesignationColours).map(([k,v]) => k);
const hvbColours = Object.entries(hvbDesignationColours).map(([k,v]) => v);

const hvbQuantitiesFn =
    (records, area, year) => hvbDesignations.map(designation => {
        let ix = records.findIndex(y => (y.area == area) && (y.year == year) && (y.designation == designation));
        if (ix == -1) console.log("Warning: no record for: " + area + ", " + year + ", " + designation);
        return ix == -1 ? 0 : records[ix].tonnes;
});

const hvbDetailHtmlFn =
    (records, area, year) => {
        let s = hvbDesignations.map(designation => {
            let ix = records.findIndex(y => (y.area == area) && (y.year == year) && (y.designation == designation));
            return `<dt>${designation}:</dt><dd><span style="font-weight: bold; color: ${hvbDesignationColours[designation]}">${(ix == -1 ? '?' : records[ix].tonnes.toFixed(0))}</dd>`;
        }).join(" ");
        return `<h4>${area} ${year}</h4><div class='qual'>Tonnes per year</div><dl>${s}</dl>`;
};

const hvbLegendHtml = "<div class='qual'>Tonnes per year</div>" +
                        Object.entries(hvbDesignationColours).map(([k,v]) => `<i style="background: ${v}"></i>${k}<br/>`).join(" ");

const hvbConfig = {
    chartType: "bar",
    quantitiesFn: hvbQuantitiesFn,
    detailHtmlFn: hvbDetailHtmlFn,
    colours: hvbColours,
    legendHtml: hvbLegendHtml
};


// ---------------------------------------------

export { hvbConfig };