
var currentPositionLat;
var currentPositionLng;
var formattedAddress;
var directionsService;
var directionsDisplay;
var geocoder;
var map;
var infoWindow;

//Initialize the map. Also get the current location of the user.
function initMap() {
    
        directionsService = new google.maps.DirectionsService;
        directionsDisplay = new google.maps.DirectionsRenderer;
        
        map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: 12.98, lng: 77.58},
          zoom: 7
        });
        
        geocoder = new google.maps.Geocoder;
        
        directionsDisplay.setMap(map);
        
        // Create the search box and link it to the UI element.
        var input1 = document.getElementById('start');
        var searchBox1 = new google.maps.places.SearchBox(input1);
        
        // Create the search box and link it to the UI element.
        var input2 = document.getElementById('end');
        var searchBox2 = new google.maps.places.SearchBox(input2);

        infoWindow = new google.maps.InfoWindow({map: map});

        // Try HTML5 geolocation.
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
            var pos = {
              lat: position.coords.latitude,
              lng: position.coords.longitude
            };
            
            currentPositionLat = position.coords.latitude;
            currentPositionLng = position.coords.longitude;
            
            infoWindow.setContent('Your current location');
            infoWindow.setPosition(pos);
            
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

}

//function for geocoding
function geocodeLatLng(geocoder, map, infoWindow){
    
    var latlng = {lat: currentPositionLat, lng: currentPositionLng};
    geocoder.geocode({'location': latlng, 'partialmatch': true}, function(results, status) {
      if (status === google.maps.GeocoderStatus.OK) {
        if (results[1]) {
            formattedAddress = results[1].formatted_address;
            if(formattedAddress === undefined) {
                document.getElementById("start").value = currentPositionLat+","+currentPositionLng;
            }
            else {
                document.getElementById("start").value = formattedAddress;
            }
        } else {
          window.alert('No results found');
        }
      } else {
        window.alert('Geocoder failed due to: ' + status);
      }
    });
    
}

//function to calculate and display the route
function calculateAndDisplayRoute(directionsService, directionsDisplay) {
    directionsService.route({
      origin: document.getElementById('start').value,
      destination: document.getElementById('end').value,
      travelMode: google.maps.TravelMode.DRIVING
    }, function(response, status) {
      if (status === google.maps.DirectionsStatus.OK) {
        directionsDisplay.setDirections(response);
      } else {
        window.alert('Directions request failed due to ' + status);
      }
    });
  }

//function to exectue when "use my current location" is clicked
document.getElementById("locateButton").addEventListener("click", function (){

    geocodeLatLng(geocoder, map, infoWindow);
    
    
});

//function to exectue when "locate me" is clicked
document.getElementById('searchButton').addEventListener('click', function () {
    
    calculateAndDisplayRoute(directionsService, directionsDisplay); 
        
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

        //Add an Mini Cabs option to dropdown menu
        var optionMiniCab = document.createElement("option");
        optionMiniCab.text="Select a Cab";
        optionMiniCab.value="null";
        availableCabsDropdown.add(optionMiniCab,availableCabsDropdown.options[null]);
        
        if(availableCabsMini > 0)
        {
            //Add an Mini Cabs option to dropdown menu
            var optionMiniCab = document.createElement("option");
            optionMiniCab.text="Mini Cab";
            optionMiniCab.value="Mini Cab";
            availableCabsDropdown.add(optionMiniCab,availableCabsDropdown.options[null]);
        }
        if(availableCabsPlus > 0)
        {
            //Add an Plus Cabs option to dropdown menu
            var optionPlusCab = document.createElement("option");
            optionPlusCab.text="Plus Cab";
            optionPlusCab.value="Plus Cab";
            availableCabsDropdown.add(optionPlusCab,availableCabsDropdown.options[null]);
        }
        if(availableCabsPool > 0)
        {
            //Add an Pool Cabs option to dropdown menu
            var optionPoolCab = document.createElement("option");
            optionPoolCab.text="Pool Cab";
            optionPoolCab.value="Pool Cab";
            availableCabsDropdown.add(optionPoolCab,availableCabsDropdown.options[null]);
        }

        document.getElementById("bookCabButton").type="submit";        
    
});


