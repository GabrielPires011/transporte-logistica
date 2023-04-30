import { Component, AfterViewInit } from '@angular/core';
import * as L from 'leaflet';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit {
  private map!: L.Map;
  private marker!: L.Marker;
  latitude: number = -15.826691;
  longitude: number = -47.921820;

  private initMap(): void {
    this.map = L.map('map', {
      center: [this.latitude, this.longitude],
      zoom: 4
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(this.map);
    const iconUrl = 'https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_960_720.jpg';
    const icon = L.icon({
      iconUrl: iconUrl,
      iconSize: [50, 50],
      iconAnchor: [25, 50]
    });
    this.marker = L.marker([this.latitude, this.longitude], { icon: icon }).addTo(this.map);
  }

  constructor() { }

  ngAfterViewInit(): void {
    this.initMap();
    this.map.on('click', this.onMapClick.bind(this));
  }

  onMapClick(e: L.LeafletMouseEvent) {
    if (this.marker) {
      this.marker.remove();
    }

    this.latitude = e.latlng.lat;
    this.longitude = e.latlng.lng;
    const iconUrl = 'https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_960_720.jpg';
    const icon = L.icon({
      iconUrl: iconUrl,
      iconSize: [50, 50],
      iconAnchor: [25, 50]
    });
    this.marker = L.marker([this.latitude, this.longitude], { icon: icon }).addTo(this.map);
  }
}

