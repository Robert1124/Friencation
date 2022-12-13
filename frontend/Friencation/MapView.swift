//
//  MapView.swift
//  Friencation
//
//  Created by Yiwen Wu on 12/9/22.
//

import SwiftUI
import MapKit
import CoreLocation

struct MapView: UIViewRepresentable {
    
    func makeUIView(context: Context) -> MKMapView {
        let map = MKMapView()
        map.showsUserLocation = true
        map.delegate = context.coordinator
        return map
    }
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }
    
    func updateUIView(_ uiView: MKMapView, context: UIViewRepresentableContext<MapView>) {
        
        //@ObservedObject var locationManager = LocationManager()
        //let test = locationManager.location != nil ? locationManager.location!.coordinate : CLLocationCoordinate2D()
        
        // location
        // get data from device
        // let latitude = 40.605587227953485
        // let longitude = -75.37110686578305
        // let coordinate = CLLocationCoordinate2D(latitude: test.latitude, longitude: test.longitude)
        // span
        // get span from user touch
        // let span = MKCoordinateSpan(latitudeDelta: 0.01, longitudeDelta: 0.01)
        
        //let region = MKCoordinateRegion(center: coordinate, span: span)
        //uiView.setRegion(region, animated: true)
    }
}

struct MapView_Previews: PreviewProvider {
    static var previews: some View {
        MapView()
    }
}
