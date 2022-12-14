//
//  FriencationApp.swift
//  Friencation
//
//  Created by Yiwen Wu on 12/9/22.
//

import SwiftUI

@main
struct FriencationApp: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
