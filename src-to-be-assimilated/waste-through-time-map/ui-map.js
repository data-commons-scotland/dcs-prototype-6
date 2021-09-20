

// Create additional control positionings
function addControlPositionings(uiMap) {
    var corners = uiMap._controlCorners = {},
        l = 'leaflet-',
        container = uiMap._controlContainer = L.DomUtil.create('div', l + 'control-container', uiMap._container);

    function createCorner(vSide, hSide) {
        var className = l + vSide + ' ' + l + hSide;

        corners[vSide + hSide] = L.DomUtil.create('div', className, container);
    }

    createCorner('top', 'left');
        createCorner('top', 'right');
        createCorner('bottom', 'left');
        createCorner('bottom', 'right');

        createCorner('top', 'center');
        createCorner('middle', 'center');
        createCorner('middle', 'left');
        createCorner('middle', 'right');
        createCorner('bottom', 'center');
}

// ---------------------------------------------


function style(feature) {
    return {
        // fillColor: getColor(feature.properties.density),
        weight: 1,
        opacity: 0.7,
        color: 'grey',
        fillOpacity: 0.2
    };
}


/*
fetch("lad.json")
    .then(response => response.json())
    .then(data => {
        console.log(data.length);
        L.geoJson(data, {style: style}).addTo(map); });
*/


// ---------------------------------------------

const centre = [56.142, -3.744];

function createUiMap(geoJson, getUiDetail, setHighlightedFeature){
    var uiMap = L.map('map', {zoomSnap: 0.05}).setView(centre, 8.0);

    uiMap.attributionControl.setPrefix(false);
    uiMap.attributionControl.addAttribution('<a href="attributions.html">Attributions</a>');

    //var tiles = L.tileLayer('http://server.arcgisonline.com/ArcGIS/rest/services/Canvas/World_Light_Gray_Base/MapServer/tile/{z}/{y}/{x}'); // Not as nice as stadiamaps (below) but no account needed for non-localhost serving
    //var tiles = L.tileLayer('https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png', {maxZoom: 20}); // Nice but needs an account for non-localhost serving
    var tiles = L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {maxZoom: 20}); 
    var tiles = L.tileLayer('', {maxZoom: 20});
    tiles.addTo(uiMap);

    // KEEP for now
    //const zoomToFeature = e => addWithin({area: e.target.feature.properties.LAD13NM, lat: e.latlng.lat, lng: e.latlng.lng});

    const zoomToFeature = e => uiMap.fitBounds(e.target.getBounds());

    var geoJsonVar = null;

    const resetHighlight = e => {
        geoJsonVar.resetStyle(e.target);
        setHighlightedFeature();
        getUiDetail().update();
    }

    const highlightFeature = e => {
        let layer = e.target;
        layer.setStyle({
            weight: 2,
            color: '#666',
            dashArray: '',
            fillOpacity: 0.4
        });
        if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge){
            layer.bringToFront();
        }
        setHighlightedFeature(layer.feature.properties);
        getUiDetail().update(layer.feature.properties);
    }

    const onEachFeature = (feature, layer) => layer.on({
        mouseover: highlightFeature,
        mouseout: resetHighlight,
        click: zoomToFeature
    });

    geoJsonVar = L.geoJson(geoJson, {style: style, onEachFeature: onEachFeature}).addTo(uiMap);

    addControlPositionings(uiMap);

    // Change the position of the zoom control to one of the positionings that got created above
    uiMap.zoomControl.setPosition('topright');

    return uiMap;
}


// -------------------------------------------

export {  createUiMap };

