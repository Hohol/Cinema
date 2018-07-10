import { Component, OnInit } from '@angular/core';
import {ApiService} from '../../services/api.service';
import {Movie} from '../../model/model.movie';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent implements OnInit {

  movies: Movie[];

  constructor(private api: ApiService) { }

  ngOnInit() {
    this.api.getMovies()
      .subscribe(movies => this.movies = movies);
  }
}
