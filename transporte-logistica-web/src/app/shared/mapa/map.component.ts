import { Component, AfterViewInit, Input, OnChanges, SimpleChanges, SimpleChange } from '@angular/core';
import * as L from 'leaflet';
import { FormBuilder, FormGroup} from '@angular/forms';
import {DomUtil} from "leaflet";
import get = DomUtil.get;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit, OnChanges {
  private map!: L.Map;
  private marker!: L.Marker;
  @Input() latitude!: number;
  @Input() longitude!: number;
  @Input() form!: FormGroup;

  ngOnChanges(changes: SimpleChanges): void {
    if (this.map && changes && changes['latitude'] && changes['latitude'].currentValue
      && changes['longitude'] && changes['longitude'].currentValue) {
      const newLatLng = L.latLng(changes['latitude'].currentValue, changes['longitude'].currentValue);
      this.marker.setLatLng(newLatLng);
      this.map.panTo(newLatLng);
      this.form.get('endereco.latitude')?.setValue(this.latitude);
      this.form.get('endereco.longitude')?.setValue(this.longitude);
    }
  }

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

  constructor(private fb: FormBuilder) { }

  ngAfterViewInit(): void {
    this.initMap();
    this.map.on('click', this.onMapClick.bind(this));
  }

  onMapClick(e: L.LeafletMouseEvent) {
    this.atualizarCoordenadas(e.latlng.lat, e.latlng.lng);
  }

  private atualizarCoordenadas(lat: number, lng: number) {
    this.latitude = lat;
    this.longitude = lng;
    this.ngOnChanges({
      latitude: new SimpleChange(this.latitude, lat, this.marker === undefined),
      longitude: new SimpleChange(this.longitude, lng, this.marker === undefined)
    });
  }
}
