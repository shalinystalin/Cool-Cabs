/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global google */

function initMap() {
  var directionsService = new google.maps.DirectionsService;
  var directionsDisplay = new google.maps.DirectionsRenderer;
  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 7,
    center: {lat: 12.98, lng: 77.58}
  });
  directionsDisplay.setMap(map);
  
  var geocoder = new google.maps.Geocoder;
  var infowindow = new google.maps.InfoWindow;
  
        // Try HTML5 geolocation.
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
            var pos = {
              lat: position.coords.latitude,
              lng: position.coords.longitude
            };

            infoWindow.setPosition(pos);
            infoWindow.setContent('Location found.');
            map.setCenter(pos);
          }, function() {
            handleLocationError(true, infoWindow, map.getCenter());
          });
        } else {
          // Browser doesn't support Geolocation
          handleLocationError(false, infoWindow, map.getCenter());
        }
      

      function handleLocationError(browserHasGeolocation, infoWindow, pos) {
        infoWindow.setPosition(pos);
        infoWindow.setContent(browserHasGeolocation ?
                              'Error: The Geolocation service failed.' :
                              'Error: Your browser doesn\'t support geolocation.');
      }

  // Create the search box and link it to the UI element.
  var input1 = document.getElementById('start');
  var searchBox1 = new google.maps.places.SearchBox(input1);
  //map.controls[google.maps.ControlPosition.TOP_LEFT].push(input1);
  
  // Create the search box and link it to the UI element.
  var input2 = document.getElementById('end');
  var searchBox2 = new google.maps.places.SearchBox(input2);
  //map.controls[google.maps.ControlPosition.TOP_LEFT].push(input2);
  
  //changed
  var onChangeHandler = function() {
    calculateAndDisplayRoute(directionsService, directionsDisplay);
  };
  
  // lines added
  
  document.getElementById("searchButton").addEventListener("click",function() {

    var input = document.getElementById('LocationLatLng').value;
    var latlngStr = input.split(',', 2);
    var latlng = {lat: parseFloat(latlngStr[0]), lng: parseFloat(latlngStr[1])};
    geocoder.geocode({'location': latlng}, function(results, status) {
      if (status === google.maps.GeocoderStatus.OK) {
        if (results[1]) {
            //document.getElementById("formattedAddress").innerHTML = results[1].formatted_address;
            document.getElementById("formattedAddress").value = results[1].formatted_address;
            calculateAndDisplayRoute(directionsService, directionsDisplay);
            
            infowindow.setContent(results[1].formatted_address);
            infowindow.open(map, marker);
        } else {
          window.alert('No results found');
        }
      } else {
        window.alert('Geocoder failed due to: ' + status);
      }
    });
    
    var availableCabsMini = Math.floor((Math.random() * 5));
    var availableCabsPlus = Math.floor((Math.random() * 4));
    var availableCabsPool = Math.floor((Math.random() * 6));
    
    document.getElementById("availableCabsMini").innerHTML = availableCabsMini;
    document.getElementById("availableCabsPlus").innerHTML = availableCabsPlus;
    document.getElementById("availableCabsPool").innerHTML = availableCabsPool;
    document.getElementById("availableCabsTotal").innerHTML = availableCabsMini + availableCabsPlus + availableCabsPool;
    
    var availableCabsDropdown = document.getElementById("selectCab");
    while (availableCabsDropdown.firstChild) {
        availableCabsDropdown.removeChild(availableCabsDropdown.firstChild);
    }
    
    if(availableCabsMini > 0)
    {
        //Add an Mini Cabs option to dropdown menu
        var optionMiniCab = document.createElement("option");
        optionMiniCab.text="Mini Cab ("+availableCabsMini+" available)";
        optionMiniCab.value="Mini Cab";
        availableCabsDropdown.add(optionMiniCab,availableCabsDropdown.options[null]);
    }
    if(availableCabsPlus > 0)
    {
        //Add an Plus Cabs option to dropdown menu
        var optionPlusCab = document.createElement("option");
        optionPlusCab.text="Plus Cab ("+availableCabsPlus+" available)";
        optionPlusCab.value="Plus Cab";
        availableCabsDropdown.add(optionPlusCab,availableCabsDropdown.options[null]);
    }
    if(availableCabsPool > 0)
    {
        //Add an Pool Cabs option to dropdown menu
        var optionPoolCab = document.createElement("option");
        optionPoolCab.text="Pool Cab ("+availableCabsPool+" available)";
        optionPoolCab.value="Pool Cab";
        availableCabsDropdown.add(optionPoolCab,availableCabsDropdown.options[null]);
    }
    
    document.getElementById("bookCabButton").type="submit";
    document.getElementById("formattedAddress").type="text";
    
    

  document.getElementById('start').addEventListener('change', onChangeHandler);
  document.getElementById('end').addEventListener('change', onChangeHandler);
  
  calculateAndDisplayRoute(directionsService, directionsDisplay);

});

function calculateAndDisplayRoute(directionsService, directionsDisplay) {
    directionsService.route({
        origin: document.getElementById('start').value,
        destination: document.getElementById('end').value,
        travelMode: google.maps.TravelMode.DRIVING
        }, 
        function(response, status) {
            if (status === google.maps.DirectionsStatus.OK) 
            {
              directionsDisplay.setDirections(response);
            } 
            else 
            {
            window.alert('Directions request failed due to ' + status);
            }
    });
}

  // Create the search box and link it to the UI element.
  var input = document.getElementById('pac-input');
  var searchBox = new google.maps.places.SearchBox(input);
  //map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

  // Bias the SearchBox results towards current map's viewport.
  map.addListener('bounds_changed', function() {
    searchBox.setBounds(map.getBounds());
  });
 
}