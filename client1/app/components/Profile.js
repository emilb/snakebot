var React = require('react');
var Router = require('react-router');
var Repos = require('./GitHub/Repos');
var UserProfile = require('./GitHub/UserProfile');
var Notes = require('./Notes/Notes');
//var Firebase = require('firebase');

var Profile = React.createClass({
    //mixins: [ReactFireMixin],
    getInitialState: function() {
        return {
            notes: [1,2,3],
            bio: {
                name: 'Jompii'
            },
            repos: ['a','b','c']
        }
    },
    //componentDidMount: function () {
    //    this.ref = new Firebase('https://incandescent-torch-4001.firebaseio.com/');
    //    var childRef = this.ref.child(this.props.params.username);
    //    this.bindAsArray(childRef, 'notes');
    //},
    //componentWillUnmount: function () {
    //  this.unbind('notes');
    //},
    render: function () {
        return (
            <div className="row">
                <div className="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                    <UserProfile username={this.props.params.username} bio={this.state.bio}/>
                </div>
                <div className="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                    <Repos username={this.props.params.username} repos={this.state.repos}/>
                </div>
                <div className="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                    <Notes username={this.props.params.username} notes={this.state.notes}/>
                </div>
            </div>
        )
    }
});

module.exports = Profile;