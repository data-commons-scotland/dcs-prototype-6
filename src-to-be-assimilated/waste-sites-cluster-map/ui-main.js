"use strict";

// Thanks to Bård Romstad for the outline of this.

var geojson,
    metadata,
    geojsonPath = 'waste-sites.geojson',
    categoryField = 't', // This is the fieldname for marker category (used in the pie and legend)
    iconField = 'not-in-use', // This is the fieldname for marker icon
    popupFields = ['n','r','p','s','a','k','z'], // Pop-up will display these fields
    tileServer = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
    rmax = 35, // Max radius for cluster pies
    markerclusters = L.markerClusterGroup({
      singleMarkerMode: true,
      maxClusterRadius: 2*rmax,
      iconCreateFunction: defineClusterIcon // Where the magic happens
    }),
    map = L.map('map').setView([56.12, -3.87], 7); // Centre on Polmaise!

    addControlPositionings(map);
    createUiTitle().addTo(map);

    map.attributionControl.setPrefix(false);
    map.attributionControl.addAttribution('<a href="attributions.html">Attributions</a>');

  // Add basemap
  L.tileLayer(tileServer, {maxZoom: 18}).addTo(map);
  // And the empty markercluster layer
  map.addLayer(markerclusters);

  // Ready to go, load the geojson
  d3.json(geojsonPath, function(error, data) {
      if (!error) {
          geojson = data;
          metadata = data.properties;
          var markers = L.geoJson(geojson, {
			pointToLayer: defineFeature,
			onEachFeature: defineFeaturePopup
          });
          markerclusters.addLayer(markers);
          map.fitBounds(markers.getBounds());
          createUiLegend().addTo(map);
          renderLegend();
      } else {
	  console.log('Could not load data...');
      }
  });


//X For an individual waste-site...
//X site-name = feature.properties['9993']
function defineFeature(feature, latlng) {
  var categoryVal = feature.properties[categoryField],
    iconVal = feature.properties[iconField];
    var myClass = 'marker category-m1 icon-1'; //X it was:   'marker category-'+categoryVal+' icon-'+iconVal;
    var myIcon = L.divIcon({
        className: myClass,
        iconSize:null
    });
    return L.marker(latlng, {icon: myIcon});
}

function defineFeaturePopup(feature, layer) {
  var props = feature.properties,
    fields = metadata.fields,
    popupContent = '';

  popupFields.map( function(key) {
    if (props[key]) {
      var val = props[key],
        label = fields[key].name;
      if (fields[key].lookup) {
        val = fields[key].lookup[val];
      }
      if (key == 'z') { val = val.toFixed(); }
      popupContent += '<span class="attribute"><span class="label">'+label+':</span> '+val+'</span>';
    }
  });
  popupContent = '<div class="map-popup">'+popupContent+'</div>';
  layer.bindPopup(popupContent,{offset: L.point(1,-2)});
}

function calcN(children){ //X this was:   children.length, ...i.e. simply the number of markers in cluster
    return children
            .map(child => child.feature.properties['z'])
            .reduce((acc, x) => acc + x, 0);
}

function bin(zz){
    if      (zz <      100) return 12;
    else if (zz <     1000) return 10;
    else if (zz <    10000) return  8;
    else if (zz <   100000) return  6;
    else if (zz <  1000000) return  4;
    else if (zz < 10000000) return  2;
    else                    return  0;
}

function defineClusterIcon(cluster) {
    var children = cluster.getAllChildMarkers(),
        //X first-site-name = children[0].feature.properties['9993']
        n = children.length, // the number of markers in cluster
        zz = calcN(children), // total tonnes in the cluster
        strokeWidth = 1, //Set clusterpie stroke width
        //X Radius value will be 1 of 4 discrete values
        r = rmax-2*strokeWidth-bin(zz), //Calculate clusterpie radius...
        iconDim = (r+strokeWidth)*2, //...and divIcon dimensions (leaflet really want to know the size)
        //X My version of grouping the data...
        //X
        data = Array.from(Array(34).keys()).map(x => String("m" + x)) // manufacture the keys
                // return an array of...
                .map(key => {
                        // ...object where each object has a 'key' and a 'values' property:
                        return {key: key,
                                values: children
                                            // only the children whose tonnesIncoming contains a non-null for the key
                                            .filter(candidate => candidate.feature.properties['t'].hasOwnProperty(key))
                                            .map(original => {
                                                // return a new object whose tonnesIncoming has been narrowed w.r.t. the key
                                                // NB I'm not modifying the original object because it contains all kinds of stuff that will be used elsewhere.
                                                //    Also, (I think) what is returned here is used only in the displaying of this cluster
                                                return {feature: {
                                                            properties: {
                                                                tonnesIncoming: original.feature.properties['t'][key]}}}})}}),
        //X ...replaces:
        //X
        //X    data = d3.nest() //Build a dataset for the pie chart
        //X      .key(function(d) { return d.feature.properties[categoryField]; }) //X Group by yielding an array of pairs (key,values)
        //X      .entries(children, d3.map),
        // Bake some svg markup
        myValueFunc = d => {
                //console.log(d);
                    // A thought... might have to skew values in this function so that
                    //   even small segments are observable. The real values can still be displayed
                    //   by the myValueFuncForPathStarFuncs function.
                if (d.values)
                    return d.values.map(o => o.feature.properties.tonnesIncoming).reduce((acc, x) => acc + x, 0);
                else
                    return 0;
           },
           myValueFuncForPathStarFuncs = d => {
                           //console.log(d);
                           if (d.data.values)
                               return d.data.values.map(o => o.feature.properties.tonnesIncoming).reduce((acc, x) => acc + x, 0);
                           else
                               return 0;
                      },
        html = bakeThePie({data: data,
                            //X My version of the valueFunc...
                            //X
                            valueFunc: myValueFunc,
                            //X ...replaces:
                            //X
                            //X    valueFunc: function(d){return d.values.length;}, //X A simple count
                            strokeWidth: 1,
                            outerRadius: r,
                            innerRadius: r-15,
                            pieClass: 'cluster-pie',
                            pieLabel: n,
                            pieLabelClass: 'marker-cluster-pie-label',
                            //X Fns used to label/colour the segments (?)
                            pathClassFunc: function(d){return "category-"+d.data.key;},
                            pathTitleFunc: function(d){return metadata.fields[categoryField].lookup[d.data.key]+' ('+myValueFuncForPathStarFuncs(d).toFixed()+' tonnes)';}
                          }),
        // Create a new divIcon and assign the svg markup to the html property
        myIcon = new L.DivIcon({
            html: html,
            className: 'marker-cluster',
            iconSize: new L.Point(iconDim, iconDim)
        });
    return myIcon;
}

// Generate a svg markup for the pie chart
function bakeThePie(options) {
    // Data and valueFunc are required
    if (!options.data || !options.valueFunc) {
        return '';
    }
    var data = options.data,
        valueFunc = options.valueFunc,
        r = options.outerRadius?options.outerRadius:28, // Default outer radius = 28px
        rInner = options.innerRadius?options.innerRadius:r-10, // Default inner radius = r-10
        strokeWidth = options.strokeWidth?options.strokeWidth:1, // Default stroke is 1
        pathClassFunc = options.pathClassFunc?options.pathClassFunc:function(){return '';}, // Class for each path
        pathTitleFunc = options.pathTitleFunc?options.pathTitleFunc:function(){return '';}, // Title for each path
        pieClass = options.pieClass?options.pieClass:'marker-cluster-pie', // Class for the whole pie
        //X The label for the whole pie is a number that is calculated to be sum of the counts in each group (?)
        pieLabel = options.pieLabel?options.pieLabel:d3.sum(data,valueFunc), // Label for the whole pie
        pieLabelClass = options.pieLabelClass?options.pieLabelClass:'marker-cluster-pie-label',// Class for the pie label

        origo = (r+strokeWidth), // Center coordinate
        w = origo*2, // Width and height of the svg element
        h = w,
        donut = d3.layout.pie(),
        arc = d3.svg.arc().innerRadius(rInner).outerRadius(r);

    // Create an svg element
    var svg = document.createElementNS(d3.ns.prefix.svg, 'svg');
    // Create the pie chart
    var vis = d3.select(svg)
        .data([data]) //X Assoc the data with the pie's SVG element. Is the data grouped here?
        .attr('class', pieClass)
        .attr('width', w)
        .attr('height', h);

    var arcs = vis.selectAll('g.arc')
        .data(donut.value(valueFunc)) //X The valueFunc will be given an x where a-site-name = x.values[0].feature.properties['9993']
                                      //X   so each x as, I think, one of the groupings that was created by the d3.nest.key() above.
        .enter().append('svg:g')
        .attr('class', 'arc')
        .attr('transform', 'translate(' + origo + ',' + origo + ')');

    arcs.append('svg:path')
        .attr('class', pathClassFunc)
        .attr('stroke-width', strokeWidth)
        .attr('d', arc)
        .append('svg:title')
        .text(pathTitleFunc); //X The pathTitleFunc will be given an x where a-grouping-key = x.data.key

    vis.append('text')
        .attr('x',origo)
        .attr('y',origo)
        .attr('class', pieLabelClass)
        .attr('text-anchor', 'middle')
        //.attr('dominant-baseline', 'central')
        /* IE doesn't seem to support dominant-baseline, but setting dy to .3em does the trick [] */
        .attr('dy','.3em')
        .text(pieLabel);

    // Return the svg-markup rather than the actual element
    return serializeXmlNode(svg);
}


function serializeXmlNode(xmlNode) {
    if (typeof window.XMLSerializer != "undefined") {
        return (new window.XMLSerializer()).serializeToString(xmlNode);
    } else if (typeof xmlNode.xml != "undefined") {
        return xmlNode.xml;
    }
    return "";
}

function createUiTitle(){
    var uiTitle = L.control({position: 'topcenter'});

    uiTitle.onAdd = function(uiMap){
        this._div = L.DomUtil.create('div', 'title');
        this.update();
        return this._div;
    };

    uiTitle.update = function(){
        this._div.innerHTML = "<h2>The quantities of materials<br/>that came into waste sites in 2019</h2>";
    };

    return uiTitle;
}

// Generate a legend with the same categories as in the clusterPie
function renderLegend() {
    var data = d3.entries(metadata.fields[categoryField].lookup),
      legenddiv = d3.select('#legend-container').append('div')
        .attr('id','legend');

    var heading = legenddiv.append('div')
        .classed('legendheading', true)
        .text(metadata.fields[categoryField].name);

    var legenditems = legenddiv.selectAll('.legenditem')
        .data(data);

    legenditems
        .enter()
        .append('div')
        .attr('class',function(d){return 'category-'+d.key;})
        .classed({'legenditem': true})
        .text(function(d){return d.value;});
}

// Generate a legend with the same categories as in the clusterPie.
function createUiLegend(){
    var uiLegend = L.control({position: 'middleright'});

    uiLegend.onAdd = function(uiMap){
        this._div = L.DomUtil.create('div', 'info legend');
        this._div.id = 'legend-container';
        return this._div;
    };

    return uiLegend;
}

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