


function createUiTitle(){
    var uiTitle = L.control({position: 'topcenter'});

    uiTitle.onAdd = function(uiMap){
        this._div = L.DomUtil.create('div', 'map-title');
        this.update();
        return this._div;
    };

    uiTitle.update = function(){
        this._div.innerHTML = "<h2>Scotland's waste</h2>";
    };

    return uiTitle;
}


// ---------------------------------------------

function createUiSwapper(optLabels, swapFn, initIx){
        var uiSwapper = L.control({position: 'topleft', bubblingMouseEvents: false});

        uiSwapper.onAdd = function(uiMap){
            this._div = L.DomUtil.create('div', 'swapper');

            var select = L.DomUtil.create('select');

            optLabels.forEach((v,ix) => {
                var opt = L.DomUtil.create('option');
                opt.value = ix;
                opt.innerHTML = v;
                select.appendChild(opt);
            });

            select.value = initIx;

            this._div.appendChild(select);

            L.DomEvent.disableClickPropagation(this._div);
            L.DomEvent.addListener(this._div, 'mousedown', e => L.DomEvent.stopPropagation(e));
            L.DomEvent.addListener(this._div, 'change', e => swapFn(e.target.value));

            return this._div;
        }

        return uiSwapper;
}


// ---------------------------------------------

function createUiLegend(legendHtml){
    var uiLegend = L.control({position: 'bottomright'});

    uiLegend.onAdd = function(uiMap){
        this._div = L.DomUtil.create('div', 'info legend');
        this.update();
        return this._div;
    };

    uiLegend.update = function(){
        this._div.innerHTML = legendHtml;
    };

    return uiLegend;
}


// ---------------------------------------------

function createUiYear(getYear){
    var uiYear = L.control({position: 'topleft'});

    uiYear.onAdd = function(uiMap){
        this._div = L.DomUtil.create('div', 'info');
        this.update();
        return this._div;
    };

    uiYear.update = function(){
        let year = getYear();
        this._div.innerHTML = `<h2>${year}</h2>`;
    };

    return uiYear;
}


// ---------------------------------------------

function createUiDetail(getYear, wasteData, detailHtmlfn){
    var uiDetail = L.control({position: 'bottomleft'});

    uiDetail.onAdd = function(uiMap){
        this._div = L.DomUtil.create('div', 'info');
        this.update();
        return this._div;
    };

    function detailHtml(props, year){
        return detailHtmlfn(wasteData, props.LAD13NM, year);
    }

    uiDetail.update = function(props){
        let year = getYear();
        this._div.innerHTML = (props ? detailHtml(props, year) : 'Try changing the year or<br/>hovering over a council area');
    };

    return uiDetail;
}


// ---------------------------------------------

function createUiSlider(min, max, downstreamFn){
    var uiSlider = L.control({position: 'topleft', bubblingMouseEvents: false});

    uiSlider.onAdd = function(uiMap){
        this._div = L.DomUtil.create('div', 'slidecontainer');

        this._div.innerHTML = `${min}`;

        var input = L.DomUtil.create('input');

        input.id = 'year';
        input.type = 'range';
        input.max = max;
        input.min = min;
        input.step = 1;
        input.setAttribute('list', 'years');

        this._div.appendChild(input);
        this._div.innerHTML += '<datalist id="years">' +
                               Array.from({length: ((max - min) + 1)}, (v, k) => k + min).map(x => `<option>${x}</option>`).join('') +
                               '</datalist>';
        this._div.innerHTML += `${max}`;

        L.DomEvent.disableClickPropagation(this._div);
        L.DomEvent.addListener(this._div, 'mousedown', e => L.DomEvent.stopPropagation(e));
        L.DomEvent.addListener(this._div, 'input change', e => downstreamFn(/* e.srcElement.value */));

        return this._div;
    }

    return uiSlider;
}


// ---------------------------------------------

function keyHandler(min, max, downstreamFn){
    let slider = document.getElementById('year');
    let val = slider.value;
    let key = event.which;

    if (key === 32) { // spacebar
        val++;
        if (val > max) val = min;
        slider.value = val;
        downstreamFn();
    }
}


// ---------------------------------------------

/* KEEP this
// Utility fn that alows the user to click on a point - a 'within' - and notes those points.
var withinsVar = [];
function addWithin(m){
    console.log(m);

    let ix = withinsVar.findIndex( x => x.area == m.area );
    if (ix == -1) withinsVar.push(m);
    else withinsar.splice(ix, 1, m);

    let n = withinsVar.length;
    console.log(n + " withins");

    if ((n > 25) && (n < 32)) {
        // let difference = arr1.filter(x => !arr2.includes(x));
        let a = geoJson.features.map( x => x.properties.LAD13NM );
        let b = withinsVar.map( x => x.area );
        let diff = a.filter( x => !b.includes(x) );
        console.log("missing: " + diff);
    }
    if (n > 31) {
        console.log(withinsVar.sort( (a, b) => {
            let aLabel = a.area;
            let bLabel = b.area;
            if (aLabel < bLabel) return -1;
            if (aLabel > bLabel) return 1;
            return 0; } ));
    }
}
*/


// ---------------------------------------------

export { createUiTitle, createUiSwapper, createUiLegend, createUiYear, createUiSlider, createUiDetail, keyHandler };


