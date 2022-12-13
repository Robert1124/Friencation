//
//  ContentView.swift
//  Friencation
//
//  Created by Yiwen Wu on 12/9/22.
//

import SwiftUI
import MapKit
import Combine

struct ContentView: View {
    
    @ObservedObject private var locationManager = LocationManager()
    @State private var region = MKCoordinateRegion.init()
    @State private var cancellable: AnyCancellable?
    
    private func setCurrentLocation() {
        cancellable = locationManager.$location.sink {
            location in region = MKCoordinateRegion(center: location?.coordinate ?? CLLocationCoordinate2D(), latitudinalMeters: 500, longitudinalMeters: 500)
        }
    }
    
    var body: some View {
        
        let coordinate = self.locationManager.location != nil ? self.locationManager.location!.coordinate : CLLocationCoordinate2D()
        
        VStack {
            if locationManager.location != nil {
                
                ZStack {
                    Map(coordinateRegion: $region, interactionModes: .all, showsUserLocation: true, userTrackingMode: nil)
                    Text("\(coordinate.latitude),\(coordinate.longitude)")
//                        .foregroundColor(.white)
//                        .padding()
//                        .background(.green)
//                        .cornerRadius(10)
                }
                
            } else {
                Text("Locating user location...")
            }
        }
        
        .onAppear {
            setCurrentLocation()
        }
        
        
        
//        let coordinate = self.locationManager.location != nil ? self.locationManager.location!.coordinate : CLLocationCoordinate2D()
//
//        return ZStack {
//            MapView()
//            Text("\(coordinate.latitude), \(coordinate.longitude)")
//                .foregroundColor(.white)
//                .padding()
//                .background(.green)
//                .cornerRadius(10)
//        }
        
        
        
        //Text("Hello World!")
            
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
