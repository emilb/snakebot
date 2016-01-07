var React = require('react');
var ReactDOM = require('react-dom');
var Router = require('react-router').Router;
var Routes = require('./config/Routes');

ReactDOM.render(
    <Router>{Routes}</Router>,
    document.getElementById('app')
);